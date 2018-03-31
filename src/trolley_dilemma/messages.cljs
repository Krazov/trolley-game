(ns trolley-dilemma.messages
    (:require
        [reagent.core :as r]
        [trolley-dilemma.utils :as u]))

;; helpers
(defn plural? [number]
    (if (< 1 (u/absolute number)) "s" ""))

(defonce message-count (r/atom 0))
(defn generate-message [message]
    {:id (reset! message-count (inc @message-count)) :message message})

;; dilemmas
(defn real-trolley [lowertrack uppertrack]
    ["---"
     (str
         "A runaway trolley is barrelling towards "
         (case lowertrack
             0 "no workers who would be"
             1 "one worker who is"
             (str lowertrack " workers who are"))
         " mysteriously tied up.")
     (str
         "You can pull a lever to divert the trolley to another track, containting "
         (case uppertrack
             0 "no workers who would be"
             1 "one worker who is also"
             (str uppertrack " workers who are also"))
         " tied up.")])

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