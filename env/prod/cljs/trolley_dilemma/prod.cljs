(ns trolley-dilemma.prod
  (:require
    [trolley-dilemma.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
