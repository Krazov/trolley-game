(ns trolley-dilemma.utils)

(defn absolute [number]
    (.abs js/Math number))

(defn rand-from-to [from to]
    (+ from (rand-int (- to (inc from)))))