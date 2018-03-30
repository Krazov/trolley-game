(ns trolley-dilemma.messages
    (:require
        [trolley-dilemma.utils :as u]))

;; helpers
(defn plural? [number]
    (if (< 1 (u/absolute number)) "s" ""))

;; dilemmas
(defn real-trolley [lowertrack uppertrack]
    [(str
         "A runaway trolley is barrelling towards "
         (if (= 1 lowertrack)
             "one worker who is mysteriously tied up."
             (str lowertrack " workers who are mysteriously tied up.")))
     (str
         "You can pull a lever to divert the trolley to another track, containting "
         (if (= 1 uppertrack)
             "one worker who is also tied up."
             (str uppertrack " workers who are also tied up.")))])

;; decisions
(defn pull-lever? []
    ["Do you pull the lever? [Yes/No]"])

(defn keep-playing? []
    ["Do you want to keep playing? [Yes/No]"])

;; summary
(defn summary [data]
    (let
        [rounds (:rounds data)
         points (:points data)
         kantpoints (:kantpoints points)
         utils (:utils points)]
        [(str "You have encountered " rounds " ethical dilemma" (plural? rounds))
         (str "You have " kantpoints " kantpoint" (plural? kantpoints))
         (str "You have " utils " util" (plural? utils))]))