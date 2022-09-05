(ns is.mad.shared.web3.core-tests
  (:require
    [cljs.test :refer [deftest is testing run-tests use-fixtures]]
    [is.mad.shared.web3.core :as core]))

(deftest api-sanity-tests
  (testing "basic operations"
    (is (not (nil? (core/get-web3)))))

  (testing "library interaction"
    (is (= 42 (:correct-answer (core/low-n-high))))))
