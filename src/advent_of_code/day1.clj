(ns advent-of-code.day1
  (:require [clojure.string :as str]))

(def diffs (->> (slurp "input/day_1")
                str/split-lines
                (map #(Integer/parseInt %))))

(def freqs (reductions + 0 (cycle diffs)))

(def first-repeated (reduce (fn [seen? nxt]
                              (if (seen? nxt)
                                (reduced nxt)
                                (conj seen? nxt)))
                            #{} freqs))
