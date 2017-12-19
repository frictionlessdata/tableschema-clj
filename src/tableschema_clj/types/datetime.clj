(ns tableschema-clj.types.datetime
  (:require [clojure.spec.alpha :as s]
            [tableschema-clj.types.date :refer [date-formatter]]
            [java-time :refer [local-date-time]]))

(defn time-formatter [fmt]
  (-> fmt
      (clojure.string/replace #"%H" "HH")
      (clojure.string/replace #"%M" "mm")))

(defn datetime-formatter [fmt]
  (try
    (-> fmt
        date-formatter
        time-formatter)
    (catch Exception e false)))

(def datetime-regexes
  {:iso #"^\d\d\d\d-(0[1-9]|1[012])-(0[1-9]|[12]\d|3[01])T(0[1-9]|1[012]):([0-5][0-9]):([0-5][0-9])Z$"})

(s/def ::iso-datetime #(re-matches (:iso datetime-regexes) %))

(s/def ::datetime-type #(= (type %) java.time.LocalDateTime))
(s/def ::datetime-format-string #(datetime-formatter %))

(s/def ::datetime (s/or :datetime ::datetime-type :iso (s/or :datetime ::datetime-type :string (s/and string? ::iso-datetime))))

(defn cast-datetime [format value]
  (cond 
    (= format "default") (s/conform ::datetime value)
    (= format "any") (s/conform ::datetime value)
    (datetime-formatter format) (local-date-time (datetime-formatter format) value)
    :else ::s/invalid)
  )

;; (cast-datetime "%d/%m/%y %H:%M")
(local-date-time 2006 11 21 16 30)
(s/conform ::datetime-type (local-date-time 2014 1 1 6))
(cast-datetime "default" (local-date-time 2014 1 1 6))
;; (cast-datetime "default" true)
(s/conform ::datetime "2014-01-01T06:00:00Z")
(s/conform ::datetime-type "2014-01-01T06:00:00Z")
(s/conform ::iso-datetime "2014-01-01T06:00:00Z")

(cast-datetime "%d/%m/%y %H:%M" "21/11/06 16:30")
(datetime-formatter "%d/%m/%y %H:%M")
