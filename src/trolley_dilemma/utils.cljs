(ns trolley-dilemma.utils)

(defn absolute [number]
    (.abs js/Math number))

(defn range-from [from to]
    (range (- to from)))