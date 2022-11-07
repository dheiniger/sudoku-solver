(ns sudoku-solver.core-test
  (:require [clojure.test :refer :all]
            [sudoku-solver.core :refer :all]))

(def test-board [1 2 3 4 5 6 7 8 9
                 2 3 4 5 6 7 8 9 1
                 3 4 5 6 7 8 9 1 2
                 4 5 6 7 8 9 1 2 3
                 5 6 7 8 9 1 2 3 4
                 6 7 8 9 1 2 3 4 5
                 7 8 9 1 2 3 4 5 6
                 8 9 1 2 3 4 5 6 7
                 9 1 2 3 4 5])

(deftest cell-is-valid
  (testing "A cell should have a value between 1-9 only."
    (is (<= 1 (make-n) 9))))

#_(deftest block-row-count-is-3
  (testing "A row should have 3 numbers."
    (let [row (make-block-row)]
      (is (= (count row) 3)))))

#_(deftest row-is-distinct
  (testing "A row should have 9 distinct numbers"
    (let [row (make-block-row)]
      (is (apply distinct? row)))))


#_(deftest derived-row-number-is-valid
  (testing
      (is (and (= 1 (derive-row-number 1))
               (= 1 (derive-row-number 2))
               (= 1 (derive-row-number 3))
               (= 1 (derive-row-number 4))
               (= 1 (derive-row-number 5))
               (= 1 (derive-row-number 6))
               (= 1 (derive-row-number 7))
               (= 1 (derive-row-number 8))
               (= 1 (derive-row-number 9))
               (= 2 (derive-row-number 10))
               (= 4 (derive-row-number 28))))))


#_(deftest derived-column-number-is-valid
  (testing
      (is (and (= 1 (derive-column-number 1))
               (= 2 (derive-column-number 2))
               (= 3 (derive-column-number 3))
               (= 4 (derive-column-number 4))
               (= 5 (derive-column-number 5))
               (= 6 (derive-column-number 6))
               (= 7 (derive-column-number 7))
               (= 8 (derive-column-number 8))
               (= 9 (derive-column-number 9))
               (= 1 (derive-column-number 10))))))

#_(deftest derived-block-number-is-valid
  (testing
      (is (and (= 1 (derive-block-number 1 3))
               (= 2 (derive-block-number 1 4))
               (= 3 (derive-block-number 3 9))
               (= 4 (derive-block-number 5 2))
               (= 5 (derive-block-number 5 5))
               (= 6 (derive-block-number 6 8))
               (= 7 (derive-block-number 7 1))
               (= 8 (derive-block-number 7 4))
               (= 9 (derive-block-number 9 9 ))))))

#_(deftest derive-row-start-is-valid
  (testing
      (is (and (= 1 (derive-row-start 1))
               (= 10 (derive-row-start 2))
               (= 19 (derive-row-start 3))
               (= 28 (derive-row-start 4))
               (= 37 (derive-row-start 5))
               (= 46 (derive-row-start 6))
               (= 55 (derive-row-start 7))
               (= 64 (derive-row-start 8))
               (= 73 (derive-row-start 9))))))

#_(deftest derive-row-is-valid
  (testing
      (is  (and (= [1 2 3 4 5 6 7 8 9] (derive-row test-board 1))
                (= [2 3 4 5 6 7 8 9 1] (derive-row test-board 2))
                (= [3 4 5 6 7 8 9 1 2] (derive-row test-board 3))
                (= [4 5 6 7 8 9 1 2 3] (derive-row test-board 4))
                (= [5 6 7 8 9 1 2 3 4] (derive-row test-board 5))
                (= [6 7 8 9 1 2 3 4 5] (derive-row test-board 6))
                (= [7 8 9 1 2 3 4 5 6] (derive-row test-board 7))
                (= [8 9 1 2 3 4 5 6 7] (derive-row test-board 8))
                (= [9 1 2 3 4 5] (derive-row test-board 9))))))


#_(deftest derive-col-is-valid
  (testing
      (is (= [1 2 3 4 5 6 7 8 9] (derive-col test-board 1)))
      (is (= [2 3 4 5 6 7 8 9 1] (derive-col test-board 2)))
      (is (= [3 4 5 6 7 8 9 1 2] (derive-col test-board 3)))
      (is (= [4 5 6 7 8 9 1 2 3] (derive-col test-board 4)))
      (is (= [5 6 7 8 9 1 2 3 4] (derive-col test-board 5)))
      (is (= [6 7 8 9 1 2 3 4 5] (derive-col test-board 6)))
      (is (= [7 8 9 1 2 3 4 5] (derive-col test-board 7)))
      (is (= [8 9 1 2 3 4 5 6] (derive-col test-board 8)))
      (is (= [9 1 2 3 4 5 6 7] (derive-col test-board 9)))))
(deftest next-block-num-is-valid
  (testing
      (is (= 1 (next-block-num 1 1)))
      (is (= 1 (next-block-num 2 1)))
      (is (= 2 (next-block-num 3 1)))
      (is (= 2 (next-block-num 4 2)))
      (is (= 2 (next-block-num 5 2)))
      (is (= 3 (next-block-num 6 2)))
      (is (= 3 (next-block-num 7 3)))
      (is (= 3 (next-block-num 8 3)))
      (is (= 4 (next-block-num 9 3)))
      (is (= 4 (next-block-num 1 4)))
      (is (= 4 (next-block-num 2 4)))
      (is (= 5 (next-block-num 3 4)))
      (is (= 5 (next-block-num 4 5)))
      (is (= 5 (next-block-num 5 5)))
      (is (= 6 (next-block-num 6 5)))
      (is (= 6 (next-block-num 7 6)))
      (is (= 6 (next-block-num 8 6)))
      (is (= 7 (next-block-num 9 6)))
      (is (= 7 (next-block-num 1 7)))
      (is (= 7 (next-block-num 2 7)))
      (is (= 8 (next-block-num 3 7)))
      (is (= 8 (next-block-num 4 8)))
      (is (= 8 (next-block-num 5 8)))
      (is (= 9 (next-block-num 6 8)))
      (is (= 9 (next-block-num 7 9)))
      (is (= 9 (next-block-num 8 9)))))
    
