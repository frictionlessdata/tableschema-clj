(ns tableschema-clj.types.date
  (:require [clojure.spec.alpha :as s]
            [java-time :refer [local-date]]))

(defn date-formatter [fmt]
  (-> fmt
      (clojure.string/replace #"%d" "dd")
      (clojure.string/replace #"%m" "MM")
      (clojure.string/replace #"%y" "yy")))

(defn make-date [fmt date]
  (try (local-date (date-formatter fmt) date)
       (catch Exception e false)))

(defn sql-date-to-date [sql]
  (local-date sql))

(def date-regexes
  {:iso #"^\d\d\d\d-(0[1-9]|1[012])-(0[1-9]|[12]\d|3[01])"})

(s/def ::iso-date #(re-matches (:iso date-regexes) %))

(s/def ::string string?)
(s/def ::date-type #(= java.time.LocalDate (type %)))

(s/def ::date-string (s/and ::string ::iso-date))

(s/def ::date (s/or :string ::date-string :date ::date-type))

(s/def ::date-pair (fn [{:keys [format value]}] (make-date format value)))

(defn cast-date [format value]
  (if (s/valid? ::date value)
    (cond
      (s/valid? ::date-type value) value
      (s/valid? ::iso-date value) (sql-date-to-date value)
      :else ::s/invalid)
    (if (s/valid? ::date-pair {:format format :value value}) (make-date format value) ::s/invalid)))

(s/fdef cast-date
        :args (s/cat :format keyword? :value ::date)
        :ret ::date-type)

