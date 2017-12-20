(ns tableschema-clj.types.datetime
  (:require [clojure.spec.alpha :as s]
            [tableschema-clj.types.date :refer [date-formatter]]
            [tableschema-clj.types.time :refer [time-formatter]]
            [java-time :refer [local-date-time formatter]]))


(defn datetime-formatter [fmt]
  (try
    (-> fmt
        date-formatter
        time-formatter
        formatter)
    (catch Exception e false)))

(defn make-datetime [fmt datetime]
  (try (local-date-time (datetime-formatter fmt) datetime)
       (catch Exception e false)))

(defn sql-datetime-to-datetime [sql]
  (local-date-time (apply str (butlast sql))))

(def datetime-regexes
  {:iso #"^\d\d\d\d-(0[1-9]|1[012])-(0[1-9]|[12]\d|3[01])T(0[1-9]|1[012]):([0-5][0-9]):([0-5][0-9])Z$"})

(s/def ::iso-datetime #(re-matches (:iso datetime-regexes) %))

(s/def ::datetime-type #(= (type %) java.time.LocalDateTime))
(s/def ::datetime-format-string #(datetime-formatter %))

(s/def ::datetime (s/or :datetime ::datetime-type :iso (s/or :datetime ::datetime-type :string (s/and string? ::iso-datetime))))

(s/def ::datetime-pair (fn [{:keys [format value]}] (make-datetime format value)))

(s/def ::string string?)

(defn cast-datetime [format value]
  (if (s/valid? ::datetime value)
    (cond
      (s/valid? ::datetime-type value) value
      (s/valid? ::iso-datetime value) (sql-datetime-to-datetime value)
      :else ::s/invalid)
    (if (s/valid? ::datetime-pair {:format format :value value}) (make-datetime format value) ::s/invalid)
    )
  )
