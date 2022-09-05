(ns is.mad.server.high.core-tests
  (:require
    [cljs.test :refer [deftest is testing run-tests use-fixtures]]
    [is.mad.server.high.core :as core]))

(deftest high-level-api-tests
  (testing "basic stuff"
    (is (= "Hello World!"))))

