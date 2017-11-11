(ns tableschema-clj.helpers
  (:require [tableschema-clj.types :refer :all]))

(def type-lookup
  {:any ::any
   :array ::array
   :base ::base
   :boolean ::boolean
   :date ::date
   :datetime ::datetime
   :geojson ::geojson
   :geopoint ::geopoint
   :integer ::integer
   :number ::number
   :object ::object
   :string ::string
   :time ::time
   :year ::year
   :yearmonth ::yearmonth
   :duration ::duration})

(defn get-type [])

