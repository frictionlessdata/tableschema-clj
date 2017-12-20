(ns tableschema-clj.types.duration-test
  (:require
   [tableschema-clj.types.duration :refer :all]
   [java-time :refer [period duration plus]]
   [clojure.test :refer :all]))

(defonce INVALID :clojure.spec.alpha/invalid)

(deftest test-cast-duration
  (are [format value result] (= (cast-duration format value) result)
    "default" (period 1 :years) (period 1 :years)
    "default" "P1Y10M3DT5H11M7S" {:period (plus
                                           (period 1 :years)
                                           (period 10 :months)
                                           (period 3 :days))
                                  :duration (plus
                                             (duration 5 :hours)
                                             (duration 11 :minutes)
                                             (duration 7 :seconds))}
    "default" "P1Y" (period 1 :years)
    "default" "P1M" (period 1 :months)
    "default" "P1M1Y" INVALID
    ;; "default" "P-1Y" INVALID
    "default" "year" INVALID
    "default" true INVALID
    "default" false INVALID
    ;; "default" 1 INVALID
    "default" "" INVALID
    "default" [] INVALID
    "default" {} INVALID))

