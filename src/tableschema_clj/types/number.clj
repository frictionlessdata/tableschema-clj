(ns tableschema-clj.types.number
  (:require [clojure.spec.alpha :as s]))

(s/def ::decimal float?)
(s/def ::castable-dec #(try (float %)
                            (catch Exception e false)))

(defn regex-matches [value group decimal bare]
  (let [reg (re-pattern (str "^(?<int>-?\\d+|\\d{1,3}(\\" group "\\d{3})*)(?<frac>\\" decimal "\\d+)?$"))
        matcher (re-matcher reg value)]
    (do (re-find matcher)
        (if (.matches matcher)
          {:int (clojure.string/replace (.group matcher "int") group "")
           :frac (if (.group matcher "frac") (clojure.string/replace (.group matcher "frac") decimal "") "0")}))))

(defn strip-string [value group decimal]
  (clojure.string/replace value (re-pattern (str "[^-\\" group "\\" decimal "\\d]")) ""))

(defn string-to-decimal [value {:keys [group-char decimal-char bare-number]}]
  (let [stripped (if bare-number value (strip-string value group-char decimal-char))
        matches (regex-matches stripped group-char decimal-char bare-number)]
    (if matches
      (try
        (Float/parseFloat (str (:int matches) "." (:frac matches)))
        (catch Exception e false)))))

(s/def ::decimal-string (fn [{:keys [string] :as opts}]
                          (string-to-decimal string opts)))


(defn cast-number [format value options]
  (let [opt-map (merge {:string value} {:group-char "," :decimal-char "." :bare-number true} options)]
    (cond
      (s/valid? ::decimal value) value
      (s/valid? ::castable-dec value) (float value)
      (s/valid? ::decimal-string opt-map) (string-to-decimal value opt-map)
      :else ::s/invalid)))


(string-to-decimal "1000,00 &" {:group-char " " :decimal-char "," :bare-number false})
