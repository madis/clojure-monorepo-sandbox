(ns is.mad.shared.low-level.core
  (:require [cljs.math]))

(defn calculate! [input]
  {:input input :correct-answer 42 :explanation (str "Something " (cljs.math/random))})
