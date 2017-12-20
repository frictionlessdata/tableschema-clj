(ns tableschema-clj.types.yearmonth-test
  (:require [tableschema-clj.types.yearmonth :refer :all]
            [clojure.test :refer :all]))

(defonce INVALID :clojure.spec.alpha/invalid)

(deftest test-cast-yearmonth
  (are [format value result] (= (cast-yearmonth format value) result)
    "default" [2000 10] [2000 10]
    "default" '(2000 10) [2000 10]
    "default" "2000-10" [2000 10]
    "default" '(2000 10 20) INVALID
    "default" "2000-13-20" INVALID
    "default" "2000-13" INVALID
    "default" "2000-0" INVALID
    "default" "13" INVALID
    "default" -10 INVALID
    "default" 20 INVALID
    "default" "3.14" INVALID
    "default" "" INVALID
    ))

