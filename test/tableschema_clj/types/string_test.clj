(ns tableschema-clj.types.string-test
  (:require [tableschema-clj.types.string :refer :all]
            [clojure.spec.gen.alpha :as gen]
            [clojure.spec.alpha :as s]
            [clojure.test :refer :all]))

(defonce INVALID :clojure.spec.alpha/invalid)

(deftest test-cast-string
  (are [format value result] (= (cast-string format value) result)
      :default "string" "string"
      :default "" ""
      :default true INVALID
      :default 0 INVALID
      :uri "http://google.com" "http://google.com"
      :uri "string" INVALID
      :uri "" INVALID
      :uri 0 INVALID
      :email "name@google.com" "name@google.com"
      :email "http://google.com" INVALID
      :email "string" INVALID
      :email "" INVALID
      :email 0 INVALID
      :binary "dGVzdA==" "dGVzdA=="
      :binary "" ""
      :binary 0 INVALID
      :uuid "76ae35fa-66d6-4499-9ae9-daaa16865446" "76ae35fa-66d6-4499-9ae9-daaa16865446"
      :uuid "string" INVALID
      :uuid "" INVALID
      :uuid 0 INVALID
      :notaformat "string" INVALID
      ))


