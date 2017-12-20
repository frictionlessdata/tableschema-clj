(ns tableschema-clj.types.time-test
  (:require [tableschema-clj.types.time :refer :all]
            [java-time :refer [local-time]]
            [clojure.test :refer :all]))

(defonce INVALID :clojure.spec.alpha/invalid)

(deftest test-cast-time
  (are [format value result] (= (cast-time format value) result)
    "default" (local-time 6) (local-time 6)
    "default" "06:00:00" (local-time 6)
    "default" "09:00" INVALID
    "default" "3 am" INVALID
    "default" "3.00" INVALID
    "default" "invalid" INVALID
    "default" true INVALID
    "default" "" INVALID
    "any" "06:00:00" (local-time 6)
    ;; "any" "3:00 am" (local-time 3)
    "any" "some night" INVALID
    "any" "invalid" INVALID
    "any" true INVALID
    "any" "" INVALID
    "%H:%M" (local-time 6) (local-time 6)
    "%H:%M" "06:00" (local-time 6)
    "%M:%H" "06:50" INVALID
    "%H:%M" "3:00 am" INVALID
    "%H:%M" "some night" INVALID
    "%H:%M" "invalid" INVALID
    "%H:%M" true INVALID
    "%H:%M" "" INVALID
    "invalid" "" INVALID))
