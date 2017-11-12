(ns tableschema-clj.types.any-test
  (:require [tableschema-clj.types.any :refer :all]
            [clojure.test :refer :all]))

(defonce INVALID :clojure.spec.alpha/invalid)

(deftest test-cast-any
  (are [format value result] (= (cast-any format value) result)
    :default 1 1
    :default "1" "1"
    :default "3.14" "3.14"
    :default true true
    :default "" ""))
