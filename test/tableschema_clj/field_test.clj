(ns tableschema-clj.field-test
  (:require [tableschema-clj.field :refer :all]
            [clojure.test :refer :all]))

;; TODO: replace with spec/gen generated data
(defonce DESCRIPTOR-MIN {:name "id"})
(defonce DESCRIPTOR-MIN-PROCESSED {:name "id"
                                   :type "string"
                                   :format "default"
                                   :constraints {}})

(defonce DESCRIPTOR-MAX {:name "amount"
                         :type "number"
                         :format "default"
                         :bare-number "false"
                         :constraints {:required true}})

;; TODO: cast-value
;; TODO: cast-value-error
(deftest test-make-field
  (is (= (make-field DESCRIPTOR-MIN) DESCRIPTOR-MIN-PROCESSED)
      "A minimal field descriptor should return a descriptor with defaults")
  (are [field descriptor] (get (make-field DESCRIPTOR-MIN) field)
    ;; name
    [:name DESCRIPTOR-MIN] "id"
    [:name DESCRIPTOR-MAX] "amount"
    ;; type
    [:type DESCRIPTOR-MIN] "string"
    [:type DESCRIPTOR-MAX] "number"
    ;; format
    [:format DESCRIPTOR-MIN] "default"
    [:format DESCRIPTOR-MAX] "default"
    ;; constraints
    [:constraints DESCRIPTOR-MIN] {}
    [:constraints DESCRIPTOR-MAX] {:required true}
    ;; required
    [:required DESCRIPTOR-MIN] false
    [:required DESCRIPTOR-MAX] true
    )
  )

(deftest cast-value)

