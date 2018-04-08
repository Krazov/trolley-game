(ns trolley-dilemma.core
    (:require
        [reagent.core :as r]
        [trolley-dilemma.dilemmas :as d]
        [trolley-dilemma.dilemmas.real-trolley :as rt]
        [trolley-dilemma.messages :as m]
        [trolley-dilemma.views :as v]))

(enable-console-print!)

;; -------------------------
;; data

(defonce player-data (r/atom {:kantpoints 0
                              :utils 0
                              :rounds 0
                              :weight nil}))

(defonce current-dilemma (r/atom nil))

(defonce decision (r/atom nil))

(defonce result (r/atom nil))

(defonce stage (r/atom nil))

(defonce listen? (r/atom false))

(defonce messages (r/atom [{:id (:rounds @player-data)
                            :message "Welcome to Trolley Dilemma: The Game"}]))

;; -------------------------
;; utils

(defn get-count []
    (:rounds @player-data))

;; -------------------------
;; gameplay

(defn get-next-round [current]
    (case current
        nil :dilemma
        :dilemma :decision?
        :decision? :calculate
        :calculate :results
        :results :update
        :update :quit?
        :quit? :dilemma
        nil))

(defn create-real-trolley []
    (let
        [data (rt/generate)
         lowertrack (:lowertrack data)
         uppertrack (:uppertrack data)]
        (do
            (reset! current-dilemma data)
            (rt/instruction lowertrack uppertrack))))

(defn create-message [stage]
    (case stage
        :dilemma (create-real-trolley)
        :decision? (m/pull-lever?)
        :decision-yes (m/lever-pulled)
        :decision-no (m/lever-left)
        :results (rt/summary @result)
        :update (m/summary @player-data)
        :quit? (m/keep-playing?)
        :quit-yes (m/keep-playing)
        :quit-no (m/stop-playing)
        nil))

(defn add-message [stage]
    (swap! messages into (map m/generate-message (create-message stage))))

(defn listen-time? [stage]
    (or
        (= stage :quit?)
        (= stage :decision?)))

(defn play []
    (let
        [stage (reset! stage (get-next-round @stage))]
        (when (= stage :dilemma)
            (swap! player-data assoc :rounds (inc (:rounds @player-data))))
        (when (= stage :calculate)
            (reset! result (rt/summarize @current-dilemma @decision))
            (swap! player-data assoc
                   :utils (+ (:utils @player-data) (:points (:utils @result)))
                   :kantpoints (+ (:kantpoints @player-data) (:points (:kantpoints @result)))))
        (when-not (= stage :calculate)
            (add-message stage))
        (when (listen-time? stage)
            (reset! listen? true))
        (when-not (listen-time? stage)
            (reset! listen? false)
            ; TODO: vary the time depending on the stage
            (js/setTimeout play 1000))
        ))

(defn yes []
    (do
        (when (= @stage :decision?)
            (add-message :decision-yes)
            (reset! decision :yes))
        (when (= @stage :quit?)
            (add-message :quit-yes))
        (play)))

(defn no []
    (do
        (when (= @stage :decision?)
            (add-message :decision-no)
            (reset! decision :no))
        (when (= @stage :quit?)
            (add-message :quit-no))
        (play)))

;; -------------------------
;; initialize app

(defn mount-root []
    (r/render [v/home-page messages listen? yes no] (.getElementById js/document "app")))

(defn init! []
    (do
        (mount-root)
        (play)))
