(ns site.core
  (:require
    [site.state :refer [app-state emit tabs]]
    [site.util :refer [kebab-wrap]]
    [site.resume :refer [resume]]
    [site.about :refer [about]]
    [site.notes :refer [notes]]
    [reagent.core :as r]
    [reagent.dom :as d]
    ["react" :as react]))

;; -------------------------
;; View

(defn title []
  [:div
   {:class "page-title"}
   [:h1 "daniel beretta"]])

(defn tab-menu []
  (let [button-class #(kebab-wrap
                        "tab-menu-button"
                        (if (= % (:tab @app-state)) "selected"))]
    [:div
     {:class "tab-menu"}
     (doall
       (for [[tab-key tab-label] tabs]
       [:button
        {:key tab-key
         :class (button-class tab-key)
         :on-click #(emit [:select-tab tab-key])}
        tab-label]))]))


(defn app-root []
  [:div {:class "app-root"}
   [:div
    {:class "top-section"}
    [title]
    [tab-menu]]
   (case (:tab @app-state)
     :resume [resume]
     :about [about]
     :notes [notes]
     [:div {:class "empty"}])])

;; -------------------------
;; Handlers

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [app-root] (.getElementById js/document "app")))

(defn init []
  (mount-root))

(js/document.addEventListener "DOMContentLoaded" init)
