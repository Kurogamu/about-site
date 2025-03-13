(ns site.core
  (:require
    [reagent.core :as r]
    [reagent.dom :as d]
    ["react" :as react]))

;; -------------------------
;; Task

;; -------------------------
;; View

(defn app-root [state]
  [:div {:class "app-root"}])

;; -------------------------
;; Handlers

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [app-root] (.getElementById js/document "app")))

(defn init []
  (mount-root))

(js/document.addEventListener "DOMContentLoaded" init)
