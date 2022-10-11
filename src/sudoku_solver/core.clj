(ns sudoku-solver.core)

(defn make-n[]
  (inc (rand-int 10)))

(defn make-row[]
  (list(make-n) (make-n) (make-n)))

(defn block []
  [(make-row) (make-row) (make-row)])

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

(defn get-row [board n]
  "Get the nth row from board"
  )

(defn get-column[board n]
  "Get the nth column from board"
  (let [group
        (cond
          (<= n 3) (first board)
          (<= n 6) (second board)
          (<= n 9) (second (rest board))
          :else "invalid!")]
    (map first group)))


