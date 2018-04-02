(ns trolley-dilemma.dilemmas.real-trolley
    (:require
        [trolley-dilemma.utils :as u]))

; create dilemma
(defn generate []
    {:type :real-trolley
     :uppertrack (u/rand-from-to 0 5)
     :lowertrack (u/rand-from-to 1 10)})

; summarize player’s choice
(defn- summarize-utils [dilemma decision]
    (let
        [lowertrack (:lowertrack dilemma)
         uppertrack (:uppertrack dilemma)
         diff (- lowertrack uppertrack)
         absolute-diff (u/absolute diff)]
        (cond
            ; there is one more case to cover for "multi" choice but there is no interface for it yet (esc key?)
            (and (< diff 0) (= decision :yes)) {:decision :wrong, :points absolute-diff}
            (= lowertrack uppertrack) {:decision :neutral, :points 0}
            (or
                (and (> diff 0) (= decision :yes))
                (and (< diff 0) (= decision :no))) {:decision :correct, :points absolute-diff})))

(defn- summarize-kantpoints [dilemma decision]
    (let
        [diff (- (:lowertrack dilemma) (:uppertrack dilemma))]
        (cond
            (= decision :no) {:decision :default, :points 0}
            (and (= decision :yes) (> diff 0)) {:decision :lives-saved, :points 1}
            :else {:decision :murderer, :points -10})))


; create text messages for dilemma
(defn instruction [lowertrack uppertrack]
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

(defn summary [dilemma decision]
    (let
        [utils (summarize-utils dilemma decision)
         kantpoints (summarize-kantpoints dilemma decision)]
        ["---"
         (case (:decision utils)
             :wrong (str "You have made the wrong utilitarian decision. Lose " (:points utils) " util" (u/plural? (:points utils)) "!")
             :neutral "Your choice is neutral on utilitarian grounds."
             :correct (str "You have made the correct utilitarian decision and saved a net " (:points utils) (u/plural? (:points utils) " life" " lives") "."))
         (case (:decision kantpoints)
             :default "The dilemma is not your problem. On Kantian grounds, that is enough. There is no change in your Kant points. #NotYourProblem."
             :lives-saved "You have a hypothetical imperative to save lives, but not a categorical one. Gain 1 Kant point."
             :murderer "You are a MURDERER who has violated the categorical imperative! Lose 10 Kant points.")]))
