(ns trolley-dilemma.dilemmas)

(defn real-trolley []
    {:type :real-trolley
     :uppertrack (rand-int 6)
     :lowertrack (inc (rand-int 10))})

(defn fat-man []
    {:type :fat-man
     :workers (rand-int 4)})

(defn murderer-liar []
    {:type :murderer-liar
     :friends (+ 2 (rand-int 9))
     :entropy 0.1})

(defn harambe-trolley []
    {:type :harambe-trolley
     :uppertrack (+ 3 (rand-int 18))
     :lowertrack 0.3
     :entropy 0.02})

(defn book-trolley []
    {:type :book-trolley
     :book-utils (+ 3 (rand-int 8))
     :entropy 0.2})

(defn drowning-child []
    (let
        [special (= 1 (rand-int 5))]
        {:type :drowning-child
         :clothes (+ 500 (rand-int 1501))
         :special special
         :entropy (if (true? special) 0.1 0.5)}))