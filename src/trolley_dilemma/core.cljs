(ns trolley-dilemma.core
    (:require
        [reagent.core :as r]
        [trolley-dilemma.dilemmas :as d]
        [trolley-dilemma.messages :as m]
        [trolley-dilemma.consequences :as c]
        [trolley-dilemma.views :as v]))

(enable-console-print!)

;; -------------------------
;; data

(defonce player-data (r/atom {:kantpoints 0
                              :utils 0
                              :rounds 0
                              :weight nil}))

(defonce current-dilemma (r/atom nil))

(defonce stage (r/atom nil))

(defonce listen? (r/atom false))

(defonce messages (r/atom [{:id      (:rounds @player-data)
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
        [data (d/real-trolley)
         lowertrack (:lowertrack data)
         uppertrack (:uppertrack data)]
        (do
            (reset! current-dilemma data)
            (m/real-trolley lowertrack uppertrack))))

(defn update-player-data [decision]
    (let
        [data @player-data
         type (:type @current-dilemma)]
        (reset! player-data (c/consequences? type data decision))))

(defn create-message [stage]
    (case stage
        :dilemma (create-real-trolley)
        :decision? (m/pull-lever?)
        :decision-yes (m/lever-pulled)
        :decision-no (m/lever-left)
        :results ["---" "This is what you have done"]
        :update (m/summary @player-data)
        :quit? (m/keep-playing?)
        :quit-yes (m/keep-playing)
        :quit-no (m/stop-playing)
        nil))

; todo: multi method with empty getting current round
(defn listen-time? [stage]
    (or
        (= stage :quit?)
        (= stage :decision?)))

(defn play []
    (let
        [next-stage (get-next-round @stage)
         stage (reset! stage next-stage)]
        (do
            (when (= stage :dilemma)
                (swap! player-data assoc :rounds (inc (:rounds @player-data))))
            (when-not (= stage :calculate)
                (swap! messages into (map m/generate-message (create-message stage))))
            (when (listen-time? stage)
                (do
                    (reset! listen? true)))
            (when-not (listen-time? stage)
                (do
                    (reset! listen? false)
                    (js/setTimeout play 1000)))
            )))

(defn yes []
    (do
        (when (= @stage :decision?)
            (do
                (swap! messages into (map m/generate-message (create-message :decision-yes)))
                (swap! messages into (map m/generate-message (create-message :results)))
                (update-player-data :yes)))
        (when (= @stage :quit?)
            (swap! messages into (map m/generate-message (create-message :quit-yes))))
        (play)))

(defn no []
    (do
        (when (= @stage :decision?)
            (do
                (swap! messages into (map m/generate-message (create-message :decision-no)))
                (update-player-data :no)
                (println @player-data)))
        (when (= @stage :quit?)
            (swap! messages into (map m/generate-message (create-message :quit-no))))
        (js/setTimeout play 1000)))

;; -------------------------
;; initialize app

(defn mount-root []
    (r/render [v/home-page messages listen? yes no] (.getElementById js/document "app")))

(defn init! []
    (do
        (mount-root)
        (play)))
