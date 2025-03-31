(ns site.resume
  (:require-macros
    [site.data :refer [static-resource]])
  (:require
    [site.util :refer [kebab-wrap]]
    [clojure.string :refer [join]]
    [clojure.edn :as edn]
    [reagent.core :as r]))

;; -------------------------
;; Data

(def data-resume
  (edn/read-string (static-resource "data/resume.edn")))

;; -------------------------
;; View

(defn wrapped-list [{:keys [entries selected description]}]
  [:div.wrapped-list
   [:div.wrapped-list-entries
    (doall
      (for [[entry-key entry] entries]
        [:button
         {:key entry-key
          :class (kebab-wrap
                   "entry-label"
                   (if (= entry-key @selected) "selected"))
          :on-click #(reset! selected entry-key)}
         (:label entry)]))]
   (if (some? @selected)
     [:div.wrapped-list-note
      [:span.label (get-in entries [@selected :label])]
      [:span.note (get-in entries [@selected :notes])]]
     [:div
      {:class "wrapped-list-description"} description])])

(defn collapsible-wrapper [{:keys [collapsed]} & children]
  [:div
   {:class (kebab-wrap "collapsible" (if @collapsed "collapsed"))
    :on-click #(reset! collapsed false)}
   children])

(defn skill [entry]
  [:div
   {:key (:name entry)
    :class "card"}
   [:div.card-title [:h3 (:name entry)]]
   [:div.card-content
    (let [selected (r/atom nil)]
      [wrapped-list
       {:entries (:skill-set entry)
        :selected selected
        :description (:click-note entry)}])]])

(defn employment [entry]
  (let [collapsed (r/atom (< 4 (count (:highlights entry))))]
    [:div
     {:key (:name entry)
      :class "card"}
     [:div.card-title
      [:h3 (:name entry)]
      [:div.card-subtitle
       [:div.job-title (:title entry)]]]
     [:div.card-content
      [collapsible-wrapper
       {:collapsed collapsed}
       [:div
        {:key (join [entry "dur"])
         :class "detailed-list-section job-duration"}
        [:span.time (:time entry)]
        [:span.date (:date entry)]]
       (if-not (clojure.string/blank? (:stack entry))
         [:div
          {:key (join [entry "stack"])
           :class "detailed-list-section"}
          [:span {:class "label"} "Stack"]
          [:span {:class "content"} (join ", " (:stack entry))]])
       (if (count (:fields entry))
         [:div
          {:key (join [entry "fields"])
           :class "detailed-list-section"}
          [:span.label "Fields"]
          [:span.content (join ", " (:fields entry))]])
       (if-not (clojure.string/blank?  (:highlights entry))
         [:div
          {:key (join [entry "stack"])
           :class "detailed-list-section"}
          [:span.label "Responsibilities"]
          [:span.content
           [:ul
            (for [[index highlight] (map-indexed vector (:highlights entry))]
              [:li {:key (join [entry index])} highlight])]]])]]]))

(defn project [entry]
  [:div
   {:key (:name entry)
    :class "card"}
   [:div.card-title
    [:h3 (:name entry)]
    (if (contains? entry :location)
      [:div.card-subtitle (:location entry)])]
   [:div.card-content
    [:div.detailed-list-section (:summary entry)]
    [:div.detailed-list-section
     [:span.label "Duration"]
     [:span.content (:duration entry)]]
    (if-not (clojure.string/blank? (:link-target entry))
     [:div.detailed-list-section
      [:span.label "Link"]
      [:span.link
       [:a
        {:href (:link-target entry)}
        (:link-label entry)]]])
    (if-not (clojure.string/blank? (:fields entry))
      [:div.detailed-list-section
       [:span.label "Fields"]
       [:span.content (join ", " (:fields entry))]])]])

(defn card [entry]
  (case (:type entry)
    :click-list (skill entry)
    :employment (employment entry)
    :project (project entry)))

(defn resume []
  [:div
   {:class "main-section resume"}
   (doall
     (for [[section-key section] data-resume]
       [:div
        {:key section-key
         :class "main-subsection"}
        [:div.main-subsection-header [:h2 (:name section)]]
        [:div.card-container
         (for [entry (:data section)] (card entry))]]))])
