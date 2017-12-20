(ns tableschema-clj.types.datetime-test
  (:require [tableschema-clj.types.datetime :refer :all]
            [java-time :refer [local-date-time]]
            [clojure.test :refer :all]))

(defonce INVALID :clojure.spec.alpha/invalid)

(deftest test-cast-datetime
  (are [format value result] (= (cast-datetime format value) result)
    "default" (local-date-time 2014 1 1 6) (local-date-time 2014 1 1 6)
    "default" "2014-01-01T06:00:00Z" (local-date-time 2014 1 1 6)
    "default" "Mon 1st Jan 2014 9 am" INVALID
    "default" "invalid" INVALID
    "default" true INVALID
    "default" "" INVALID
    "any" (local-date-time 2014 1 1 6) (local-date-time 2014 1 1 6)
    ;; "any" "10th Jan 1969 9 am" (local-date-time 1969 1 10 9)
    "any" "invalid" INVALID
    "any" true INVALID
    "any" "" INVALID
    "%d/%m/%y %H:%M" (local-date-time 2006 11 21 16 30) (local-date-time 2006 11 21 16 30)
    "%d/%m/%y %H:%M" "21/11/06 16:30" (local-date-time 2006 11 21 16 30)
    "%H:%M %d/%m/%y" "21/11/06 16:30" INVALID
    "%d/%m/%y %H:%M" "invalid" INVALID
    "%d/%m/%y %H:%M" true INVALID
    "%d/%m/%y %H:%M" "" INVALID
    "invalid" "21/11/06 16:30" INVALID
    ))
