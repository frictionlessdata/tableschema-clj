(ns tableschema-clj.types.array
  (:require [clojure.spec.alpha :as s]
            [clojure.data.json :as json])
            )

(defn read-json [json-str]
  (try (json/read-str json-str)
       (catch Exception e
         nil)))

(s/def ::sequential sequential?)
(s/def ::string string?)

;; make a variable argument function
;; explain that "format" is for the API
(defn cast-vector [format value]
  (cond
    (s/valid? ::sequential value) (into [] value)
    (s/valid? ::string value) (let [parsed (read-json value)]
                                (s/conform ::sequential parsed))
    :else :clojure.spec.alpha/invalid))

(defn cast-array [format value]
  (cast-vector format value))
