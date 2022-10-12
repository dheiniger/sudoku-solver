(ns sudoku-solver.core)

(defn make-n []
  (inc (rand-int 10)))  

(defn make-block-row
  ([]
   (make-block-row 3 #{}))
  ([size row]
   (println "size is " size "row is " row)
   (if (= (count row) size)
     row
     (make-block-row size (conj row (make-n))))))

(defn block []
  [(make-block-row)
   (make-block-row)
   (make-block-row)])


(def BOARD
  [(block) (block) (block)
   (block) (block) (block)
   (block) (block) (block)])

(defn solve[board]
  (prn "Solving. board.."))

(defn generate-board [difficulty]
  (if (= :easy difficulty)
    (println "Generating easy board...")
    (println "Generating hard board..."))
  BOARD)

(defn get-block
  ([board n]
   (get-block board n board))
  ([board n blocks]
   (if (= n 0)
     (first blocks)
     (get-block board (- n 1) (rest blocks)))))

(defn get-block-row
  ([board block-number n]
   (let [block (get-block board block-number)]
     (get-block-row board block n block))) 
  ([board block n rows-left]
   "Get the nth row of block from board"
   (if (= 0 n)
     (first rows-left)
     (get-block-row board block (- n 1) (rest rows-left)))))

(defn get-column[board n]
  "Get the nth column from board"
  (let [group
        (cond
          (<= n 3) (first board)
          (<= n 6) (second board)
          (<= n 9) (second (rest board))
          :else "invalid!")]
    (map first group)))

(defn print-board[board]
  (print-block board 0)
  (print-block board 1)
  (print-block board 2))

(defn print-block [board block-number]
  (println (get-block-row board block-number 0))
  (println (get-block-row board block-number 1))
  (println (get-block-row board block-number 2)))

