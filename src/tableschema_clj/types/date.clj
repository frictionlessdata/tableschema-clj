(ns tableschema-clj.types.date
  (:require [clojure.spec.alpha :as s]
            [java-time :refer [local-date]]))

(defn date-formatter [fmt]
  (-> fmt
      (clojure.string/replace #"%d" "dd")
      (clojure.string/replace #"%m" "MM")
      (clojure.string/replace #"%y" "yy")))

(def date-regexes
  {:ddmmyy #"^(0[1-9]|[12]\d|3[01])[/-](0[1-9]|1[012])[/-]\d\d$"
   :ddmmyyyy #"^(0[1-9]|[12]\d|3[01])[/-](0[1-9]|1[012])[/-]\d\d\d\d$"
   :yymmdd #"^\d\d[/-](0[1-9]|1[012])[/-](0[1-9]|[12]\d|3[01])$"
   :yyyymmdd #"^\d\d\d\d[/-](0[1-9]|1[012])[/-](0[1-9]|[12]\d|3[01])$"})

(s/def ::string string?)
(s/def ::dd-mm-yy #(re-matches (:ddmmyy date-regexes) %))
(s/def ::dd-mm-yyyy #(re-matches (:ddmmyyyy date-regexes) %))
(s/def ::yy-mm-dd #(re-matches (:yymmdd date-regexes) %))
(s/def ::yyyy-mm-dd #(re-matches (:yyyymmdd date-regexes) %))
(s/def ::date-type #(= java.time.LocalDate (type %)))

(s/def ::date-string (s/and ::string (s/or :ddmmyy ::dd-mm-yy :ddmmyyyy ::dd-mm-yyyy
                                           :yymmdd ::yy-mm-dd :yyyymmdd ::yyyy-mm-dd)))

(s/def ::date (s/or :string ::date-string :date ::date-type))

(defn cast-date [format value]
  (if (s/valid? ::date value)
    (case format
      :default (cond
                 (s/valid? ::date-type value) value
                 (s/valid? ::yyyy-mm-dd value) (local-date "yyyy-MM-dd" value)
                 :else ::s/invalid)
      :any (if (s/valid? ::date value) (local-date value) ::s/invalid)
      :dd-mm-yy (cond
                  (s/valid? ::date-type value) value
                  (s/valid? ::dd-mm-yy value) (local-date "dd/MM/yy" value)
                  :else ::s/invalid))
    ::s/invalid))

(s/fdef cast-date
        :args (s/cat :format keyword? :value ::date)
        :ret ::date-type)

