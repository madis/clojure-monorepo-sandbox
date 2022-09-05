(ns is.mad.shared.low-level.core-tests
  (:require
    [cljs.test :refer [deftest is testing run-tests use-fixtures]]
    [is.mad.shared.low-level.core :as core]))

(deftest low-level-api-tests
  (testing "doing calculation"
    (is (= 42 (:correct-answer (core/calculate! "anything"))))
    (is (not (= (core/calculate! "anything") (core/calculate! "anything"))))))

