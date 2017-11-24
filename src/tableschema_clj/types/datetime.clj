(ns tableschema-clj.types.datetime
  (:require [clojure.spec.alpha :as s]
            [tableschema-clj.types.date]
            [java-time :refer [local-date-time]]))

(s/def ::datetime-type #(= java.time.LocalDateTime))

(defn cast-datetime [format value]
  )
