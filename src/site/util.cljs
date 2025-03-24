(ns site.util)

(defn kebab-wrap [root & suffixes]
  (conj
    (->> suffixes
        (filter some?)
        (map #(clojure.string/join "-" [root %])))
    root))

