(ns sudoku-solver.core-test
  (:require [clojure.test :refer :all]
            [sudoku-solver.core :refer :all]))

(deftest cell-is-valid
  (testing "A cell should have a value between 1-9 only."
    (is (<= 1 (make-n) 9))))

(deftest row-count-is-9
  (testing "A row should have 9 numbers."
    (let [row (make-row)]
      (is (= (count row) 9)))))

(deftest row-is-distinct
  (testing "A row should have 9 distinct numbers"
    (let [row (make-row)]
      (is (apply distinct? row)))))


(deftest column-is-valid
  (testing "A column should have 9 numbers."
    (let [column (make-column)]
      (is (= 9 (count column))))))
