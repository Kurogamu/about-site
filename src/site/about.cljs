(ns site.about
  (:require
    [site.util :refer [kebab-wrap]]
    [clojure.string :refer [join]]
    [reagent.core :as r]))

(defn recap []
  [:div
   {:class "card"}
   [:div
    {:class "card-title"}
    [:h3 "Things done"]]
   [:div
    {:class "card-content"}
    "I have lived in Sweden, Finland, Japan and Canada; studied music, 3D sculpting, a few languages and computer science; worked as a bartender, waiter and software developer; layouted a yearbook and a student magazine; played in a handful of metal/punk bands; written a bunch of songs; knitted a couple of sweaters and many small things; designed and 3D-printed child-proofing attachments for furniture; written a few recipes; assembled and programmed half a dozen ergonomic keyboards; collected LEGO sets; and become a father of two."]])

(defn computer []
  [:div
   {:class "card"}
   [:div
    {:class "card-title"}
    [:h3 "Computers"]]
   [:div
    {:class "card-content"}
    "While I've used keyboards since I learned to read, my passion for languages, math and technology really came together as I began studying computer science. I was particularly inspired by algorithms and datastructures, which led me into combinatorial optimization. My thesis brought me into work in fintech and later in the energy industry. As a full-stack developer I have learned to architect, design and write software while aware of scalability and cost."]])

(defn interests []
  [:div
   {:class "card"}
   [:div
    {:class "card-title"}
    [:h3 "Interests"]]
   [:div
    {:class "card-content"}
    "I have an unsustainable amount of hobbies, but in short: I like obscure music, technology, table-top role-playing games, knitting, horror fiction and custom-built keyboards."]])

(defn links []
  [:div
   {:class "card"}
   [:div
    {:class "card-title"}
    [:h3 "Contact"]]
   [:div
    {:class "card-content"}
    [:div
     {:class "detailed-list-section"}
     [:span {:class "label"} "E-mail"]
     [:span
      {:class "link"}
      [:a
       {:href "mailto:daniel@beretta.nu"}
       "daniel@beretta.nu"]]]
    [:div
     {:class "detailed-list-section"}
     [:span {:class "label"} "E-mail (alt)"]
     [:span
      {:class "link"}
      [:a
       {:href "mailto:ahlbom.daniel@gmail.com"}
       "ahlbom.daniel@gmail.com"]]]
    [:div
     {:class "detailed-list-section"}
     [:span {:class "label"} "GitHub"]
     [:span
      {:class "link"}
      [:a
       {:href "https://github.com/kurogamu"}
       "kurogamu"]]]
    [:div
     {:class "detailed-list-section"}
     [:span {:class "label"} "LinkedIn"]
     [:span
      {:class "link"}
      [:a
       {:href "https://www.linkedin.com/in/daniel-beretta/"}
       "daniel-beretta"]]]]])

(defn about []
  [:div
   {:class "main-section"}
   [computer]
   [links]
   [interests]
   [recap]])
