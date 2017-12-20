(ns tableschema-clj.types.object-test
  (:require [tableschema-clj.types.object :refer :all]
            [clojure.test :refer :all]))

(defonce INVALID :clojure.spec.alpha/invalid)

(deftest test-cast-object
  (are [format value result] (= (cast-object format value) result)
    "default" {} {}
    "default" "{}" {}
    "default" {"key" "value"} {"key" "value"}
    "default" "{\"key\": \"value\"}" {"key" "value"}
    "default" "[\"key\", \"value\"]" INVALID
    "default" "string" INVALID
    "default" 1 INVALID
    "default" "3.14" INVALID
    "default" "" INVALID))
