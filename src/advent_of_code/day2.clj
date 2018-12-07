(ns advent-of-code.day2
  (:require [clojure.string :as str]))

(def ids (->> (slurp "input/day_2")
              str/split-lines))

(def checksum (->> ids
                   (map (comp clojure.set/map-invert frequencies))
                   (map (fn [{twos 2 threes 3}] [(if twos 1 0) (if threes 1 0)]))
                   (reduce (partial mapv +))
                   (apply *)))

(def trie (reduce (fn [m id] (assoc-in m id id)) {} ids))

(defn n-letter-distance [trie word n]
  (cond
    (< n 0) nil
    (and (empty? word) (string? trie)) [trie]
    :default
    (let [[fst & rst] word]
      (mapcat (fn [[k subtrie]]
                (n-letter-distance subtrie rst (if (= fst k) n (dec n))))
              trie))))


(def common-letters (->> ids
                         (some #(let [matches (n-letter-distance trie % 1)]
                                  (when (> (count matches) 1)
                                    matches)))
                         (apply map list)
                         (filter #(apply = %))
                         (map first)
                         (apply str)))
