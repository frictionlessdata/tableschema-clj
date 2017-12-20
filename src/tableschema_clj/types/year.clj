(ns tableschema-clj.types.year
  (:require [clojure.spec.alpha :as s]))

(defn convert-year [value]
  (let [intyear (if (number? value) value (try (Integer/parseInt value)
                                              (catch Exception e nil)))]
    (if (and intyear (< intyear 4000) (> intyear 0)) intyear)))

(s/def ::year convert-year)

(defn cast-year [format value]
  (if (s/valid? ::year value) (convert-year value)
      ::s/invalid))
