(ns site.core
  (:require
    [site.state :refer [app-state emit tabs]]
    [site.util :refer [kebab-wrap]]
    [site.graphic :refer [graphic]]
    [site.resume :refer [resume]]
    [site.about :refer [about]]
    [site.notes :refer [notes]]
    [reagent.core :as r]
    [reagent.dom :as d]
    ["react" :as react]))

;; -------------------------
;; View

(defn title []
  [:div.page-title [:h1 "daniel beretta"]])

(defn tab-menu []
  (let [button-class #(kebab-wrap
                        "tab-menu-button"
                        (if (= % (:tab @app-state)) "selected"))]
    [:div.tab-menu
     (doall
       (for [[tab-key tab-label] tabs]
       [:button
        {:key tab-key
         :class (button-class tab-key)
         :on-click #(emit [:select-tab tab-key])}
        tab-label]))]))

(defn footer []
  [:div.footer
   [:p
    "This site is built by me with ClojureScript and hosted on DigitalOcean. "
    [:a {:href "https://github.com/Kurogamu/about-site"} "Here's the page source!"]]
   [:p "The bin-tree at page top is randomly generated, click on it to get a new one."]])


(defn app-root []
  [:div {:class "app-root"}
   [:div
    {:class "top-section"}
    [graphic]
    [title]
    [tab-menu]]
   (case (:tab @app-state)
     :resume [resume]
     :about [about]
     :notes [notes]
     [:div {:class "empty"}])
   [footer]])

;; -------------------------
;; Handlers

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [app-root] (.getElementById js/document "app")))

(defn init []
  (mount-root))

(js/document.addEventListener "DOMContentLoaded" init)
