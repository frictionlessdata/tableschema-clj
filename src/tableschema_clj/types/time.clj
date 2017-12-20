(ns tableschema-clj.types.time
  (:require [clojure.spec.alpha :as s]
            [java-time :refer [local-time]]))

(def iso-time-regex #"(0[1-9]|1[012]):([0-5][0-9]):([0-5][0-9])")

(defn iso-time-to-time [iso-time]
  (local-time iso-time))

(defn parse-time-string [time]
  (try
    (local-time time)
    (catch Exception e false)))

(defn time-formatter [fmt]
  (try
    (-> fmt
        (clojure.string/replace #"%H" "HH")
        (clojure.string/replace #"%M" "mm"))
    (catch Exception e false)))

(defn make-time [fmt time]
  (try
    (if (s/valid? ::time-type time) time
        (local-time (time-formatter fmt) time))
       (catch Exception e false)))

(s/def ::time-formatter-pair (fn [{:keys [format value]}] (make-time format value)))

(s/def ::time-type #(= (type %) java.time.LocalTime))
(s/def ::time-string #(parse-time-string %))
(s/def ::iso-time-string (s/and string? #(re-matches iso-time-regex %)))

(defn cast-time [format value]
  (case format
    "default" (cond (s/valid? ::time-type value) value
                    (s/valid? ::iso-time-string value) (iso-time-to-time value)
                    :else ::s/invalid)
    "any" (cond (s/valid? ::time-type value) value
                (s/valid? ::time-string value) (parse-time-string value)
                :else ::s/invalid)
    (if (s/valid? ::time-formatter-pair {:format format :value value}) (make-time format value) ::s/invalid)))
