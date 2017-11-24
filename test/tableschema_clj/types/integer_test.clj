(ns tableschema-clj.types.integer-test
  (:require
   [tableschema-clj.types.integer :refer :all]
   [clojure.spec.alpha :as s]
   [clojure.test :refer :all]))

;; TODO: bare-number check
(deftest test-cast-integer
  (are [value result] (= (cast-integer value) result)
    "1" 1
    "string1" ::s/invalid
    1 1))


