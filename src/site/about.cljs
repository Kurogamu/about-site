(ns site.about
  (:require-macros
    [site.data :refer [static-resource]])
  (:require
    [site.util :refer [kebab-wrap]]
    [clojure.string :refer [join]]
    [clojure.edn :as edn]
    [reagent.core :as r]))

;; -------------------------
;; Data

(def data-about
  (edn/read-string (static-resource "data/about.edn")))

;; -------------------------
;; View

(defn text-card [entry]
  [:div.card-content (:data entry)])

(defn list-card [entry]
  [:div.card-content
   (doall
     (for [list-entry (:data entry)]
       [:div
        {:key (:label list-entry)
         :class "detailed-list-section"}
        [:span.label (:label list-entry)]
        [:span.link
         [:a
          {:href (:link-target list-entry)}
          (:link-label list-entry)]]]))])

(defn card [[entry-key entry]]
  [:div
   {:key entry-key
    :class "card"}
   [:div.card-title [:h3 (:name entry)]]
   (case (:type entry)
     :text-block (text-card entry)
     :list (list-card entry))])

(defn about []
  [:div.main-section
   (doall
     (for [card-entry data-about]
       (card card-entry)))])
