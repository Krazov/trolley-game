(ns trolley-dilemma.consequences)

(defn calculate-real-trolley [dilemma]
    (- (:lowertrack dilemma) (:uppertrack dilemma)))

(defn update-real-trolley [player-data decision]
    (let
        [kantpoints (:kantpoints player-data)
         utils (:utils player-data)]
        (assoc player-data :kantpoints (inc kantpoints) :utils (inc utils))))

(defn consequences? [type player-data decision]
    (case type
        :real-trolley (update-real-trolley player-data decision)
        nil))