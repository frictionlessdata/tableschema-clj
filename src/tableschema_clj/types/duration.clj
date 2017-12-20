(ns tableschema-clj.types.duration
  (:require
   [clojure.spec.alpha :as s]
   [java-time :refer [duration period plus]]))

(def iso-duration-regex #"(?<period>\w+)T(?<duration>\w+)")


(s/def ::period #(try
                   (period %)
                   (catch Exception e false)))

(s/def ::duration #(try
                   (duration %)
                   (catch Exception e false)))

(defn duration-period [iso-string]
  (let [matcher (re-matcher iso-duration-regex iso-string)]
    (if (re-find matcher)
      (let [p-match (.group matcher "period")
            d-match (str "PT" (.group matcher "duration"))]
        (if (and (s/valid? ::period p-match) (s/valid? ::duration d-match)) {:period (period p-match)
                                                                               :duration (duration d-match)}
            ::s/invalid))
      ::s/invalid)))

(s/def ::duration-period #(try
                            (duration-period %)
                            (catch Exception e false)))

(s/def ::period-type #(= (type %) java.time.Period))
(s/def ::duration-type #(= (type %) java.time.Duration))

(defn cast-duration [format value]
  (cond
    (s/valid? ::period-type value) value
    (s/valid? ::duration-type value) value
    (s/valid? ::period value) (period value)
    (s/valid? ::duration value) (duration value)
    (s/valid? ::duration-period value) (duration-period value)
    :else ::s/invalid))
