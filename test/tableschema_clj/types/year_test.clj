(ns tableschema-clj.types.year-test
  (:require [tableschema-clj.types.year :refer :all]
            [clojure.test :refer :all]))

(defonce INVALID :clojure.spec.alpha/invalid)

(deftest test-cast-year
  (are [format value result] (= (cast-year format value) result)
    "default" 2000 2000
    "default" "2000" 2000
    "default" -2000 INVALID
    "default" 20000 INVALID
    "default" "3.14" INVALID
    "default" "" INVALID))
