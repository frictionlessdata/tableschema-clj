(ns tableschema-clj.types.boolean
  (:require [clojure.spec.alpha :as s]))

(s/def ::true #(or
                (= true %)
                (= "true" %)
                (= "True" %)
                (= "TRUE" %)
                (= "1" %)
                (= "yes" %)))
(s/def ::false #(or
                (= false %)
                (= "false" %)
                (= "False" %)
                (= "FALSE" %)
                (= "0" %)
                (= "no" %)))

(defn cast-boolean [value]
  (cond (s/valid? ::true value) true
        (s/valid? ::false value) false
        :else :clojure.spec.alpha/invalid))

