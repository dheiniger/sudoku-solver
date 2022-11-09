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
   (let [
         options (vec (difference (set (range 1 10))
                                  (set row)
                                  (set col)
                                  (set block)))
         n (if (empty? options) :x (rand-nth options))]
     (println "options are " options)
     (println "finding n...")
     (println "candidate is " n)
     n)))

(defn next-row-num [row row-num]
  (if (= (count row) row-length)
    (inc row-num)
    row-num))

(defn next-col-num [col-num]
  (if (= col-num row-length)
    1
    (inc col-num)))

(defn next-block-num[row-num col-num block-num]
  (if (= (mod col-num 3) 0)
    (if (= col-num 9)
      (cond (< row-num 3) 1
            (< row-num 6) 4
            (< row-num 9) 7)
      (inc block-num))
    block-num))

(defn get-element [elements n]
  (println "getting index ["n"] from elements["elements"]")
  (get elements (- n 1)))

(defn update-element [elements n element]
  (let [index (- n 1)]
    (assoc elements index element)))

(defn get-next-row [rows row-num]
  (let [current-row (get-element rows row-num)
        next-row-num (next-row-num current-row row-num)]
    (println "current-row " current-row)
    (println "next row num "  next-row-num)
    {:row-num next-row-num :row (get-element rows next-row-num)}))

(defn get-next-col [cols col-num]
  (let [current-col (get-element cols col-num)
        next-col-num (next-col-num col-num)
        next-col (get-element cols next-col-num)]
    (println "current-col " current-col)
    (println "next col num " next-col-num)
    (println "next col  " next-col)
    {:col-num next-col-num :col (get-element cols next-col-num)}))

(defn get-next-block [blocks row-num col-num block-num]
  (let [current-block (get-element blocks block-num)
        next-block-num (next-block-num row-num col-num block-num)
        next-block (get-element blocks next-block-num)]
    (println "current-block " current-block)
    (println "next block num " next-block-num)
    (println "next block " next-block)
    {:block-num next-block-num :block (get-element blocks next-block-num)}))

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
   (if-not (= (count (flatten (:rows board-elements))) (* row-length col-length))
     (let [next-row (get-next-row (:rows board-elements) row-num)
           next-col (get-next-col (:cols board-elements) col-num)
           next-block (get-next-block (:blocks board-elements) row-num col-num block-num)
           n (make-n (:row next-row) (:col next-col) (:block next-block))
           next-row-candidate (into [] (conj (:row next-row) n));;TODO move these into the 'get-next' functions
           updated-rows (update-element (:rows board-elements) (:row-num next-row) next-row-candidate)
           next-col-candidate (into [] (conj (:col next-col) n))
           updated-cols (update-element (:cols board-elements) (:col-num next-col) next-col-candidate)           next-block-candidate (into [] (conj (:block next-block) n))
           updated-blocks (update-element (:blocks board-elements) (:block-num next-block) next-block-candidate)
           next-board-elements (assoc board-elements :rows updated-rows :cols updated-cols :blocks updated-blocks)
           ]
              
       (println "next row " next-row-candidate)
       (println "updated rows " updated-rows)
       (println "next col " next-col-candidate)
       (println "next board elements " next-board-elements)

       (create-board-3 next-board-elements (:row-num next-row) (:col-num next-col) (:block-num next-block)))
     
     board-elements)))
