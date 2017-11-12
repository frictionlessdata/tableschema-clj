(ns tableschema-clj.types.array-test
  (:require [tableschema-clj.types.array :refer :all]
            [clojure.test :refer :all]))

(defonce INVALID :clojure.spec.alpha/invalid)

(deftest test-cast-array
  (are [format value result] (= (cast-array format value) result)
    :default [] []
    :default '() '()
    :default "[]" []
    :default "[\"boo\", \"ya\"]" ["boo" "ya"]
    :default "{\"key\": \"value\"}" INVALID
    :default {"key" "value"} INVALID
    :default "string" INVALID
    :default 1 INVALID
    :default "3.14" INVALID
    :default "" INVALID
    ))
