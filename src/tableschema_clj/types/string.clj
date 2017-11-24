(ns tableschema-clj.types.string
  (:require [clojure.spec.alpha :as s])
  (:import
   (java.util Base64)
   (org.apache.commons.validator UrlValidator)
   (org.apache.commons.validator.routines EmailValidator)))

(defn valid-url? [url-str]
  (let [validator (UrlValidator.)]
    (.isValid validator url-str)))

(defn valid-email? [email-str]
  (let [validator (EmailValidator/getInstance)]
    (.isValid validator email-str)))

(defn valid-binary? [binary-str]
  (try (String. (.decode (Base64/getDecoder) binary-str))
       (catch Exception e
         false)))

(def uuid-regex #"^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$")

(s/def ::string string?)
(s/def :string/default ::string)
(s/def :string/email (s/and ::string valid-email?))
(s/def :string/uri (s/and ::string valid-url?))
(s/def :string/uuid (s/and ::string #(re-matches uuid-regex %)))
(s/def :string/binary (s/and ::string valid-binary?))

(defn cast-string [format value]
  (case format
    :default (s/conform ::string value)
    :email (s/conform :string/email value)
    :uri (s/conform :string/uri value)
    :uuid (s/conform :string/uuid value)
    :binary (s/conform :string/binary value)
    ::s/invalid))
