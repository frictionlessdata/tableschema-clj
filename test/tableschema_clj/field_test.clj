(ns tableschema-clj.field-test
  (:require [tableschema-clj.field :refer :all]
            [clojure.test :refer :all]))

;; TODO: augment with spec/gen generated data
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

(deftest test-make-descriptor
  (is (= (make-descriptor DESCRIPTOR-MIN) DESCRIPTOR-MIN-PROCESSED)
      "A minimal field descriptor should return a descriptor with defaults")
  (are [field descriptor] (get (make-descriptor DESCRIPTOR-MIN) field)
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

;; TODO: custom exceptions
(deftest test-cast-value
  (let [desc-max (make-descriptor DESCRIPTOR-MAX)]
    (is (= (cast-value desc-max "£10") (float 10.0))
        "cast-value should cast valid value")
    (is (thrown? Exception (cast-value desc-max "notdecimal"))
        "cast-value should with an incorrect value")))

(deftest test-test-value
  (let [desc-max (make-descriptor DESCRIPTOR-MAX)]
    (is (= (test-value desc-max "30.78") true)
        "test-value should return true for valid value")
    (is (= (test-value desc-max 100) false)
        "test-value should return false for invalid value")))
