(ns site.notes
  (:require
    [reagent.core :as r]
    [clojure.string :refer [join]]))

(defonce state (r/atom {}))

(defn fetch [url callback]
  (-> (js/window.fetch url)
    (.then callback)
    (.catch #(js/console.error "error fetching" %))))

(defn decode-text [encoded]
  (let [decoder (js/TextDecoder. "utf-8")]
    (.decode decoder encoded #js {"stream" true})))

(defn append-read [target read-result]
  (let [encoded (.-value read-result)]
    (if (nil? encoded)
      target
      (->> (decode-text encoded)
           (vector target)
           join))))

(defn read-aux [reader result callback]
  (let [read-callback
        (fn [read-result]
          (let [join-result (append-read result read-result)]
            (if (.-done read-result)
              (callback join-result)
              (read-aux reader join-result callback))))]
    (-> (.read reader)
        (.then read-callback)
        (.catch #(js/console.error "failed to read" %)))))

(defn fetch-note [url]
  (fetch
    url
    (fn [response]
      (read-aux
        (.getReader (.-body response))
        ""
        (fn [result] (swap! state assoc url result))))))

(defn parse-line [line]
  (let [[words-head
         words-tail] (clojure.string/split line #" " 2)]
    (case words-head
      "###" [:h4 words-tail]
      "##" [:h3 words-tail]
      "#" [:h2 words-tail]
      "'''" [:p {:class "quote"} words-tail]
      [:p line])))

(defn parse-note [content]
  (let [lines (re-seq
                #"(?m)^[^\n]*$\n\n"
                (join [content "\n\n"]))]
    (map parse-line lines)))

(defn note [url]
  (let [content (get @state url)]
    (if (nil? content) (fetch-note url))
    [:div
     {:class "card"}
     (if (some? content) (parse-note content) "loading...")]))

(defn notes []
  [:div
   {:class "main-container"}
   (note "notes/250318-tiny-computer.md")])
