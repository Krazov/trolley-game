(ns ^:figwheel-no-load trolley-dilemma.dev
  (:require
    [trolley-dilemma.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
