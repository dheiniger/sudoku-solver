(ns sudoku-solver.core
  (:use [clojure.set :only [intersection difference]]))

(def row-length 9)
(def col-length 9)

(defn make-n
  ([]
   (inc (rand-int 9)))
  ([row col block]
   (make-n row col block (make-n)))
  ([row col block n]
   (println "generating random number from row: " row "\ncol: " col "\nblock: " block)
   (let [;row-exclusions (if (= (count row) row-length) [] row)
         options (vec (difference (set (range 1 10))
                                  (set row);row-exclusions)
                                  (set col)
                                  (set block)))
                                        ;         n (rand-nth options)]
         n (if (empty? options) (rand-nth (range 1 10)) (rand-nth options))];;TODO this shouldn't
     (println "options are " options)
     (println "finding n...")
     (println "candidate is " n)
     n)))


(defn next-row-num [row row-num]
  (println "finding next row num for row \n" row "\n with row number of " row-num) 
  (if (= (count row) row-length)
    (inc row-num)
    row-num))

(defn next-col-num [row col-num]
  (if (= (count row) row-length)
    1
    (inc col-num)))

(defn next-block-num[col-num block-num]
  (if (= (mod col-num 3) 0)
    (inc block-num)
    block-num))

(defn get-row [rows n]
  (get rows (- n 1)))

(defn get-col [cols n]
  (get cols (- n 1)))

(defn get-block [blocks n]
  (get blocks (- n 1)))

(defn update-rows [rows n row]
  (println "rows are " rows)
  (println "adding row " row)
  (println "for index " n)
  (let [index (- n 1)]
    (assoc rows index row)))

(defn update-cols [cols n col]
  (let [index (- n 1)]
    (println "associng to cols " cols " col " col " for index " n)
    (assoc cols index col)))

(defn update-blocks [blocks n block]
  (let [index (- n 1)]
    (println "associng to blocks " blocks " block " block " for index " n)
    (assoc blocks index block)))

(defn create-board-3
  ([]
   (let [n (make-n)
         initial-value [n]]
     (create-board-3 {:rows [initial-value] :cols [initial-value] :blocks [initial-value]}  1 1 1)))
  ([board-elements row-num col-num block-num]
   (println "creating board.  elements are " board-elements)
   (println "row num is " row-num)
   (println "col num is " col-num)
   (println "block num is " block-num)
   (if (= (count (flatten (:rows board-elements))) (* row-length col-length))
     board-elements
     (let [current-row (get-row (:rows board-elements) row-num)
           current-col (get-col (:cols board-elements) col-num)
           current-block (get-block (:blocks board-elements) block-num)
           next-row-num (next-row-num current-row row-num)
           next-col-num (next-col-num current-row col-num)
           next-block-num (next-block-num col-num block-num)
           next-row (get-row (:rows board-elements) next-row-num)
           next-col (get-col (:cols board-elements) next-col-num)
           next-block (get-block (:blocks board-elements) next-block-num)
           n (make-n next-row next-col next-block)
           next-row-candidate (into [] (conj next-row n));
           updated-rows (update-rows (:rows board-elements) next-row-num next-row-candidate)
           next-col-candidate (into [] (conj next-col n))
           updated-cols (update-cols (:cols board-elements) next-col-num next-col-candidate)           next-block-candidate (into [] (conj next-block n))
           updated-blocks (update-blocks (:blocks board-elements) next-block-num next-block-candidate)
           next-board-elements (assoc board-elements :rows updated-rows :cols updated-cols :blocks updated-blocks)
           ]
       (println "current-row " current-row)
       (println "current-col " current-col)
       (println "current-block " current-block)
       (println "next row num "  next-row-num)
       (println "next col num " next-col-num)
       (println "next block num " next-block-num)
       (println "next row " next-row-candidate)
       (println "updated rows " updated-rows)
       (println "next col " next-col-candidate)
       (println "next board elements " next-board-elements)
       
       (create-board-3 next-board-elements next-row-num next-col-num next-block-num)
       ))))

(comment(defn board->str
          ([board]
           (board->str board 1))
          ([board row-number]
           (if (<= row-number 9);;todo hardcode
             (do (println (get-row ));;(derive-row board row-number))
                 (board->str board (+ row-number 1)))))))
