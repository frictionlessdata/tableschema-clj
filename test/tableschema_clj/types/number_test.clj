(ns tableschema-clj.types.number-test
  (:require [tableschema-clj.types.number :refer :all]
            [clojure.test :refer :all]))

(defonce INVALID :clojure.spec.alpha/invalid)

(deftest test-cast-number
  (are [format value result options] (= (cast-number format value options) result)
    "default" (float 1) (float 1) {}
    "default" 1.0 (float 1) {}
    "default" (bit-shift-left 1 63) (float (bit-shift-left 1 63)) {}
    "default" "1" (float 1) {}
    "default" "10.00" (float 10) {}
    "default" "10.50" (float 10.5) {}
    "default" "100%" (float 100) {:bare-number false}
    "default" "1000%" (float 1000) {:bare-number false}
    "default" "-1000" (float -1000) {}
    "default" "1,000" (float 1000) {:group-char ","}
    "default" "10,000.00" (float 10000) {:group-char ","}
    "default" "10,000,000.50" (float 10000000.5) {:group-char ","}
    "default" "10#000.00" (float 10000) {:group-char "#"}
    "default" "10#000#000.50" (float 10000000.5) {:group-char "#"}
    "default" "10.50" (float 10.5) {:group-char "#"}
    "default" "1#000" (float 1000) {:group-char "#"}
    "default" "10#000@00" (float 10000) {:group-char "#" :decimal-char "@"}
    "default" "10#000#000@50" (float 10000000.5) {:group-char "#" :decimal-char "@"}
    "default" "10@50" (float 10.5) {:group-char "#" :decimal-char "@"}
    "default" "1#000" (float 1000) {:group-char "#" :decimal-char "@"}
    "default" "10,000.00" (float 10000) {:group-char "," :bare-number false}
    "default" "10,000,000.00" (float 10000000) {:group-char "," :bare-number false}
    "default" "$10000.00" (float 10000) {:bare-number false}
    "default" "  10000.00 €" (float 10000) {:group-char "," :bare-number false}
    "default" "10 000,00" (float 10000) {:group-char " " :decimal-char ","}
    "default" "10 000 000,00" (float 10000000) {:group-char " " :decimal-char ","}
    ;; "default" "10000,00 ₪" (float 10000) {:group-char " " :decimal-char "," :bare-number false}
    ;; "default" "  10 000,00 £" (float 10000) {:group-char " " :decimal-char "," :bare-number false}
    "default" "10,000a.00" INVALID {}
    "default" "10+000.00" INVALID {}
    "default" "$10:000.00" INVALID {}
    "default" "string" INVALID {}
    "default" "" INVALID {}
    ))



