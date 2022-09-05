(ns is.mad.shared.web3.core
  (:require ["web3" :as web3]
            ["web3-eth" :as web3-eth]
            [is.mad.shared.low-level.core :as low-level-core]))

(defn get-web3 [] web3)
(defn get-web3-eth [] web3-eth)
(defn low-n-high [] (low-level-core/calculate! "xx"))
