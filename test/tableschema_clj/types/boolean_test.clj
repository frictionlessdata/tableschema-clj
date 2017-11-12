(ns tableschema-clj.types.boolean-test
  (:require
   [tableschema-clj.types.boolean :refer :all]
   [clojure.test :refer :all]))

(defonce INVALID :clojure.spec.alpha/invalid)

(deftest test-cast-boolean
  (are [value result] (= (cast-boolean value) result)
       true true
       "true" true
       "True" true
       "TRUE" true
       "1" true
       "yes" true
       false false
       "false" false
       "False" false
       "FALSE" false
       "0" false
       "no" false
       "t" INVALID
       "YES" INVALID
       "Yes" INVALID
       "f" INVALID
       "NO" INVALID
       "No" INVALID
       0 INVALID
       1 INVALID
       "3.14" INVALID
       "" INVALID))
