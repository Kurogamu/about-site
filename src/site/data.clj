(ns site.data
  (:require [clojure.java.io :as io]))

(defmacro static-resource [path]
  (slurp (io/resource path)))
