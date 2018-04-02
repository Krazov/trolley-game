(ns trolley-dilemma.utils)

; absolute number
(defn absolute [number]
    (.abs js/Math number))

; random number from to (both inclusive)
(defn rand-from-to [from to]
    (+ from (rand-int (- to (inc from)))))

; returning singular or plural word (by defaul trailing "s" or "")
(defn plural?
    ([number one many] (if (< 1 (absolute number)) many one))
    ([number] (plural? number "s" "")))