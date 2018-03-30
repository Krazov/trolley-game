(ns trolley-dilemma.core
    (:require
        [reagent.core :as r]
        [trolley-dilemma.dilemmas :as d]
        [trolley-dilemma.views :as v]
        [trolley-dilemma.messages :as m]))

(enable-console-print!)

;; -------------------------
;; data

(defonce player-data (r/atom {:kantpoints 0
                              :utils 0
                              :dilemma nil
                              :rounds 0
                              :weight nil}))

(defonce stage (r/atom nil))

(defonce message-count (r/atom 0))

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
    (condp = current
        nil :dilemma
        :dilemma :decision?
        :decision? :results
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
            (swap! player-data assoc :dilemma data)
            (m/real-trolley lowertrack uppertrack))))

(defn create-message [stage-key]
    (condp = stage-key
        :dilemma (create-real-trolley)
        :decision? (m/pull-lever?)
        :results ["This is what you have done"]
        :update ["Data has been updated"]
        :quit? (m/keep-playing?)
        nil))

; todo: multi method with empty getting current round
(defn listen-time? [stage]
    (or
        (= stage :quit?)
        (= stage :decision?)))

(defn play []
    (let
        [next-stage (get-next-round @stage)]
        (do
            (reset! stage next-stage)
            (swap! player-data assoc :rounds (inc (:rounds @player-data)))
            (swap! messages into (map
                                     (fn [message] {:id (reset! message-count (inc @message-count)) :message message})
                                     (create-message next-stage)))
            (when (listen-time? next-stage)
                (do
                    (reset! listen? true)))
            (when-not (listen-time? next-stage)
                (do
                    (reset! listen? false)
                    (js/setTimeout play 1000)))
            )))

(defn yes []
    (do (play)))

(defn no []
    (do (play)))

;; -------------------------
;; initialize app

(defn mount-root []
    (r/render [v/home-page messages listen? yes no] (.getElementById js/document "app")))

(defn init! []
    (do
        (mount-root)
        (play)))
