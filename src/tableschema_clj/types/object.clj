(ns tableschema-clj.types.object
  (:require [clojure.spec.alpha :as s]
            [clojure.data.json :as json]))

(s/def ::json-object #(map? (try
                                  (json/read-str %)
                                  (catch Exception e false))))

(defn cast-object [format value]
  (cond (map? value) value
        (s/valid? ::json-object value) (json/read-str value)
        :else ::s/invalid))


