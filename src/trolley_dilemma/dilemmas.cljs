(ns trolley-dilemma.dilemmas
    (:require
        [trolley-dilemma.utils :as u]))

(defn real-trolley []
    {:type :real-trolley
     :uppertrack (u/rand-from-to 0 5)
     :lowertrack (u/rand-from-to 1 10)})

(defn fat-man []
    {:type :fat-man
     :workers (u/rand-from-to 0 4)})

(defn murderer-liar []
    {:type :murderer-liar
     :friends (u/rand-from 2 10)
     :entropy 0.1})

(defn harambe-trolley []
    {:type :harambe-trolley
     :uppertrack (u/rand-from-to 3 20)
     :lowertrack 0.3
     :entropy 0.02})

(defn book-trolley []
    {:type :book-trolley
     :book-utils (u/rand-from-to 3 10)
     :entropy 0.2})

(defn drowning-child []
    (let
        [special (= 1 (rand-int 5))]
        {:type :drowning-child
         :clothes (u/rand-from-to 500 2000)
         :children (u/rand-from-to 0 8)
         :special special
         :entropy (if (true? special) 0.1 0.5)}))