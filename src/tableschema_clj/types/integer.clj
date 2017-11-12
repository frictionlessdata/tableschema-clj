(ns tableschema-clj.types.integer
  (:require [clojure.spec.alpha :as s]))

(defn safe-parse [s]
  (try
    (read-string s)
    (catch Exception e
      nil)))

(s/def ::string string?)
(s/def ::integer-type integer?)
(s/def ::stringint (s/and ::string
                          #(int? (safe-parse %))))
(s/def ::integer (s/or :string ::stringint :integer ::integer-type))

(defn cast-integer [value]
  (let [parsed (s/conform ::integer value)]
    (if (= parsed ::s/invalid) ::s/invalid
        (let [[type val] parsed]
          (case type
            :integer value
            :string (parse-int value))))))

