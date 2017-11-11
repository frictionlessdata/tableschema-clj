(ns tableschema-clj.field
  (:require [clojure.spec.alpha :as s]))

(s/def ::name string?)
(s/def ::title string?)
(s/def ::type string?)
(s/def ::format string?)
(s/def ::description string?)
(s/def ::constraints string?)

(s/def ::field-descriptor (s/keys :req [::name]
                                  :opt [::title ::type ::format ::description ::constraints]))

(defn make-descriptor [{:keys [name type format constraints]
                        :or {type "string"
                             format "default"
                             constraints {}}
                        :as desc-map}]
  (merge
   desc-map
   {:type type
    :format format
    :constraints constraints
    :required (if (:required constraints) true false)}))

(defn cast-value [descriptor value])

(defn test-value [descriptor value])




