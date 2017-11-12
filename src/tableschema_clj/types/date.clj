(ns tableschema-clj.types.date
  (:require [clojure.spec.alpha :as s]
            [java-time :refer [local-date]]))

(def ddmmyy-regex #"^(0[1-9]|[12]\d|3[01])[/-](0[1-9]|1[012])[/-]\d\d$")
(def ddmmyyyy-regex #"^(0[1-9]|[12]\d|3[01])[/-](0[1-9]|1[012])[/-]\d\d\d\d$")
(def yymmdd-regex #"^\d\d[/-](0[1-9]|1[012])[/-](0[1-9]|[12]\d|3[01])$")
(def yyyymmdd-regex #"^\d\d\d\d[/-](0[1-9]|1[012])[/-](0[1-9]|[12]\d|3[01])$")

(s/def ::string string?)
(s/def ::dd-mm-yy #(re-matches ddmmyy-regex %))
(s/def ::dd-mm-yyyy #(re-matches ddmmyyyy-regex %))
(s/def ::yy-mm-dd #(re-matches yymmdd-regex %))
(s/def ::yyyy-mm-dd #(re-matches yyyymmdd-regex %))
(s/def ::date-type #(= java.time.LocalDate (type %)))

(s/def ::date-string (s/and ::string (s/or :ddmmyy ::dd-mm-yy :ddmmyyyy ::dd-mm-yyyy
                                           :yymmdd ::yy-mm-dd :yyyymmdd ::yyyy-mm-dd)))

(s/def ::date (s/or :string ::date-string :date ::date-type))

(defn cast-date [format value]
  (if (s/valid? ::date value)
    (case format
      :default (if (s/valid? ::date value) (local-date value))
      :any (local-date value)
      :dd-mm-yy (cond
                  (s/valid? ::date-type value) value
                  (s/valid? ::dd-mm-yy value) (local-date "dd/MM/yy" value)
                  :else ::s/invalid))
    ::s/invalid))

(s/fdef cast-date
        :args (s/cat :format keyword? :value ::date)
        :ret ::date-type)
