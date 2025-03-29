(ns site.graphic
  (:require
    [clojure.string :refer [join]]
    [reagent.core :as r]
    [reagent.dom :as d]))

(def branch-min-angle (* 2 (/ js/Math.PI 3)) )

(def branch-range (* 2 (/ js/Math.PI 3)))

(def levels 10)

(def child-size 0.9)

(defn set-width [context width]
  (set! (.-lineWidth context) width))

(defn set-fill-style [context style]
  (set! (.-fillStyle context) style))

(defn set-stroke-style [context style]
  (set! (.-strokeStyle context) style))

(defn polar [origin point]
  (let [px (- (first point) (first origin))
        py (- (second point) (second origin))]
    [(Math/hypot py px)
     (Math/atan2 py px)]))

(defn child-angle [parent-angle]
  (-> (* (js/Math.random) branch-range)
      (+ branch-min-angle)
      (+ parent-angle)
      (rem (* 2 js/Math.PI))))

(defn create-node [base base-parent]
  (let [[parent-length parent-angle] (polar base base-parent)
        angle (child-angle parent-angle)
        length (* child-size parent-length)]
    [(-> (Math/cos angle)
         (* 1.1)
         (* length)
         (+ (first base)))
     (-> (Math/sin angle)
         (* length)
         (+ (second base)))]))

(defn insert-node [node node-parent level]
  (if (> level levels)
    [node]
    (let [child-node #(create-node node node-parent)]
      [node
         (insert-node (child-node) node (inc level))
         (insert-node (child-node) node (inc level))])))

(defn create-tree-nodes [origin]
  (let [trunk-length (* 1.3 (/ (second origin) levels))
        second-node [(* 0.98 (first origin)) (- (second origin) trunk-length)]]
    [origin (insert-node second-node origin 1)]))

(defn draw-node [context node level]
  (let [[x y] (first node)
        children (rest node)]
    (if (> (count children) 0)
      (doall
        (for [child children]
          (doto context
            (.beginPath)
            (set-width (* 5 (Math/pow 0.9 level)))
            (set-stroke-style "#101010")
            (.moveTo x y)
            (.lineTo (ffirst child) (second (first child)))
            (.stroke)
            (draw-node child (inc level)))))
      (doto context
        (.beginPath)
        (set-fill-style "#aa808090")
        (.arc x y 5 0 (* 2 js/Math.PI))
        (.fill)))))

(defn draw [canvas]
  (let [ctx (.getContext canvas "2d")
        w (.-clientWidth canvas)
        h (.-clientHeight canvas)
        nodes (create-tree-nodes [(* w 0.618) h])]
    (.clearRect ctx 0 0 w h)
    (draw-node ctx nodes 0)))

;; picks up a lot from
;; https://github.com/reagent-project/reagent-cookbook/tree/master/recipes/canvas-fills-div
(def window-width (r/atom nil))

(defn on-window-resize [ evt ]
  (reset! window-width (.-innerWidth js/window)))

(.addEventListener js/window "resize" on-window-resize)

(defn canvas-elem [wrap-ref]
  (r/create-class
    {:component-did-update
     #(some-> @wrap-ref .-firstChild draw)

     :reagent-render
     (fn []
       @window-width
       [:canvas (if-let [node @wrap-ref]
                  {:width (.-clientWidth node)
                   :height (.-clientHeight node)})])}))

(defn graphic []
  (let [wrap-ref (r/atom nil)]
    (fn []
      [:div
       {:ref #(reset! wrap-ref %)
        :class "backdrop canvas-wrapper"
        :on-click #(some-> @wrap-ref .-firstChild draw)}
       [canvas-elem wrap-ref]])))
