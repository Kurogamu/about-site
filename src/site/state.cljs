(ns site.state
  (:require
    [reagent.core :as r]))

;; -------------------------
;; Constants

(def tabs
  {;:notes "notes"
   :resume "resume"
   :about "about"})

;; -------------------------
;; Model

(def default-state
  {:tab :about})

(defonce app-state (r/atom default-state))

(defn shared-handler [state [event value]]
  (case event
    :select-tab (assoc state :tab value)
    state))

(defn emit
  ([msg] (r/rswap! app-state shared-handler msg))
  ([msg handler] (r/rswap! app-state handler msg)))
