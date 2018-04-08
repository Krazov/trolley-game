(ns trolley-dilemma.messages
    (:require
        [reagent.core :as r]
        [trolley-dilemma.utils :as u]))

;; helpers
(defn plural? [number]
    (if (< 1 (u/absolute number)) "s" ""))

(defn- create-generate-message []
    (let
        [message-count (r/atom 0)]
        (fn [message]
            {:id (reset! message-count (inc @message-count))
             :message message})))

(defonce generate-message (create-generate-message))

;; decisions
(defn pull-lever? []
    ["---" "Do you pull the lever?"])

(defn lever-pulled []
    ["---" "You have pulled the lever."])

(defn lever-left []
    ["---" "You let the lever be."])

(defn keep-playing? []
    ["---" "Do you want to keep playing?"])

(defn keep-playing []
    ["---" "Alright, let’s keep going."])

(defn stop-playing []
    ["---" "Life is a series of trolley problems. You cannot avoid them."])

;; consequences
(defn real-trolley-consequences [dilemma result]
;    (let
;        [uppertrack? (> 1 (:uppertrack dilemma))]
;        (cond
;            (and uppertrack? ())))
    )

;; summary
(defn summary [data]
    (let
        [rounds (:rounds data)
         points (:points data)
         kantpoints (:kantpoints data)
         utils (:utils data)]
        ["---"
         (str "You have encountered " rounds " ethical dilemma" (plural? rounds))
         (str "You have " kantpoints " kantpoint" (plural? kantpoints))
         (str "You have " utils " util" (plural? utils))]))