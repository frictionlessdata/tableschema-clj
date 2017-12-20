(ns tableschema-clj.types.yearmonth
  (:require [clojure.spec.alpha :as s]
            [tableschema-clj.types.year :as y]))


(s/def ::month #(and (number? %) (> % 0) (< % 13)))

(s/def ::yearmonth (s/cat :year ::y/year :month ::month))

(defn convert-yearmonth [value]
  (let [matches (re-matches #"(\d\d\d\d)-(\d\d)" value)
        year (try (Integer/parseInt (second matches))
                  (catch Exception e nil))
        month (try (Integer/parseInt (last matches))
                   (catch Exception e nil))]
    (if (s/valid? ::yearmonth [year month]) [year month])))

(s/def ::yearmonth-string (s/and string? convert-yearmonth))

(defn cast-yearmonth [format value]
  (cond (s/valid? ::yearmonth value) value
        (s/valid? ::yearmonth-string value) (convert-yearmonth value)
        :else ::s/invalid))

