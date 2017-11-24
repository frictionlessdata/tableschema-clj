(ns tableschema-clj.types.array-test
  (:require [tableschema-clj.types.array :refer :all]
            [clojure.test :refer :all]
            [clojure.spec.alpha :as s]))

(defonce INVALID :clojure.spec.alpha/invalid)

(s/fdef cast-array
        :args (s/cat :format keyword? :value (s/or :string ::string :seq ::sequential))
        :ret (s/or :vec vector? :invalid INVALID))

(deftest test-cast-array
  (are [format value result] (= (cast-array format value) result)
    :default [] []
    :default '() [] ;; = doesn't actually test that it's a vector, though
    :default "[]" []
    :default "[\"boo\", \"ya\"]" ["boo" "ya"]
    :default "{\"key\": \"value\"}" INVALID
    :default {"key" "value"} INVALID
    :default "string" INVALID
    :default 1 INVALID
    :default "3.14" INVALID
    :default "" INVALID
    ))
