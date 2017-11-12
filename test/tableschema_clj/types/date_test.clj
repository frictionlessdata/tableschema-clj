(ns tableschema-clj.types.date-test
  (:require [tableschema-clj.types.date :refer :all]
            [java-time :refer [local-date]]
            [clojure.test :refer :all]))

(defonce INVALID :clojure.spec.alpha/invalid)

(deftest test-cast-date
  (are [format value result] (= (cast-date format value) result)
    :default (local-date 2019 1 1) (local-date 2019 1 1)
    :default "2019-01-01" (local-date 2019 1 1)
    :default "10th Jan 1969" INVALID
    :default true INVALID
    :default "" INVALID
    :any (local-date 2019 1 1) (local-date 2019 1 1)
    :any "2019-01-01" (local-date 2019 1 1)
    :any "10th Jan 1969" (local-date 2019 1 1)
    :any "10th Jan nineteen sixty nine" INVALID
    :any "invalid" INVALID
    :any true INVALID
    :any "" INVALID
    :dd-mm-yy (local-date 2019 1 1) (local-date 2019 1 1)
    :dd-mm-yy "21/11/06" (local-date 2006 11 21)
    :dd-mm-yy "21/11/06 16:30" INVALID
    :dd-mm-yy "invalid" INVALID
    :dd-mm-yy "" INVALID
    ))
