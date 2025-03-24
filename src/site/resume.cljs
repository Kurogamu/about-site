(ns site.resume
  (:require
    [site.util :refer [kebab-wrap]]
    [clojure.string :refer [join]]
    [reagent.core :as r]))

;; -------------------------
;; Data
(def data-skills
  [{:name "Spoken Languages"
    :click-note "Click language for details."
    :skill-set
    {:english
     {:label "English"
      :notes "Professional proficiency."}
     :swedish
     {:label "Swedish"
      :notes "Professional proficiency."}
     :japanese
     {:label "Japanese"
      :notes "Fluent in text and conversation."}
     :italian
     {:label "Italian"
      :notes "Conversational, intermediate."}
     :russian
     {:label "Russian"
      :notes "Conversational, intermediate."}
     :french
     {:label "French"
      :notes "Beginner level."}}}
   {:name "Programming Languages"
    :click-note "Click language for details."
    :skill-set
    {:python
     {:label "Python"
      :notes ">2 years professional experience, scripting since 2009."}
     :java
     {:label "Java"
      :notes ">4 years professional experience."}
     :ruby
     {:label "Ruby"
      :notes ">4 years professional experience."}
     :javascript
     {:label "JavaScript"
      :notes "7 years professional experience, in various frameworks."}
     :clojure
     {:label "Clojure(-Script)"
      :notes "1 year building sideprojects."}}}
   {:name "Programming Languages"
    :click-note "Click language for details."
    :skill-set
    {:django
     {:label "Django"
      :notes ">2 years professional experience."}
     :sql
     {:label "SQL"
      :notes ">7 years professional experience in MSSQL, PostgreSQL."}
     :rails
     {:label "Rails"
      :notes ">4 years professional experience."}
     :react
     {:label "React"
      :notes "5 years professional experience."}
     :typescript
     {:label "TypeScript"
      :notes "5 years professional experience."}
     :jquery
     {:label "jQuery"
      :notes "3 years professional experience."}}}])

(def data-employments
  [{:name "Helicon"
    :time "2 years 6 months"
    :date "April 2022 - October 2024"
    :title "Senior Software Developer"
    :stack ["Python" "Django" "React" "TypeScript" "PostgreSQL" "Redis" "RabbitMQ" "AWS (Athena" "Timeseries" "Lambda" "Glue" "QuickSight)" "KMS" "Docker" "Kubernetes" "Keycloak" "Webpack" "Yocto Linux" "Terraform" "GitLab"]
    :fields ["Energy Industry" "Internet of Things (IoT)" "Optimization" "Cloud Solutions" "Extract Transform Load (ETL)" "Agile" "Software as a Service (SaaS)" "Infrastructure as a Service (IaaS)" "Full-Stack Development (Frontend" "Backend" "Deployment)" "Continuous Integration and Deployment (CI/CD)" "Infrastructure as Code (IaC)"]
    :highlights
    ["Improved raw data read performance by over 100x in data extraction service" 
     "Re-wrote and optimized OPA authorization evalutation speed by 1000x" 
     "Designed and implemented RBAC authorization in micro-service architecture with minimal service dependencies" 
     "Automated setup of project with Django/TypeScript/PostgreSQL stack in Docker, reducing on-boarding from 3 days to 3 hours" 
     "Designed and implemented serverless ETL pipeline in AWS using Pandas, S3, Lambda, Athena and QuickSight" 
     "Implemented automated test runs in GitLab CI" 
     "Implemented custom energy management UI in React/TypeScript, including charts and an animated flow diagram" 
     "Implemented multiple micro-services running Django, Nameko, RabbitMQ" 
     "Implemented multiple APIs for energy management systems" 
     "Configured multiple services in AWS, K8s, Terraform, Docker Compose" 
     "Scripted build and provisioning of bespoke Linux distribution for online edge devices" 
     "Worked agile in teams of 1-10 people in hybrid environment using GitLab, JIRA, Confluence and Notion" 
     "Lead a mentoring effort for junior colleagues"]}
   {:name "Coupa"
    :time "4 years 9 months"
    :date "July 2017 - March 2022"
    :title "Senior Software Engineer"
    :stack ["Java" "MSSQL" "Elasticsearch" "Ruby" "Rails" "Docker" "Redis" "PostgreSQL" "React" "TypeScript" "Webpack" "Babel" "GitHub" "Jenkins"]
    :fields ["Financial technology" "Auctions" "Optimization" "Web Applications" "Categorization" "Agile" "SaaS" "Frontend Development" "Backend Development" "Unit Testing"]
    :highlights
    ["Implemented a complex auction dashboard in Java/JavaScript monitoring real-time data with 100 000's of data points"
     "Implemented a custom hybrid SQL/blob storage format that increased categorization speed by over 100x"
     "Implemented package management in a large Java project, making dependencies consistent and automated in development and CI/CD builds"
     "Implemented Elasticsearch to complement existing custom SQL interface, reducing search times by 100x"
     "Automated and containerized build of a large semi-monolithic project, making development possible in Windows, MacOS and Linux"
     "Built a prototype customizable dashboard feature, presenting graphs from complex datasets"
     "Designed, delivered and maintained multiple features written in several languages and stacks in parallel, primarily Java, Ruby and React"
     "Started delivering production code on 15 year-old code base within weeks of joining project"
     "Started delivering production code on 10 year-old code base within days of joining project"
     "Implemented secure encryption to provide FedRAMP compliance"
     "Wrote end-to-end and unit tests on multiple stacks"
     "Collaborated in Agile teams of 5-10 people across three continents using Git, JIRA and Confluence"]}])

(def data-projects
  [{:name "Personal site"
    :duration "1 week"
    :summary "Website including resume, posts and links. Lets me not depend on other sites to share work experience."
    :fields "ClojureScript, static hosting"}
   {:name "Bord"
    :duration "3 months"
    :summary
    "Replaces spreadsheets: data is typed and passed into map/filter/reduce functions to enable scalable calculations."
    :fields
    "ClojureScript, IndexedDB, UX/UI design"}
   {:name "Antecknat"
    :duration "1 month"
    :summary
    "Note-taking web-app deployed in AWS"
    :fields
    "ClojureScript, DNS, TLS, IaC, AWS (Route53, S3, Cognito, Organizations, CloudFormation, CloudDeploy, CloudCommit)"}
   {:name "Kloss"
    :duration "3 months"
    :summary
    "Student project making a rust-based microkernel for X86 hardware"
    :fields
    "CPU architecture, low-level programming, Rust, Assembly, kernel development"}])

(def data-education
  [{:name "Certified Developer - Associate"
    :duration "Jan 2024 - February 2024"
    :location "AWS"
    :summary "Validation of technical proficiency in developing cloud-based applications in AWS"
    :fields "Cloud architecture (serverless, microservices, API design), AWS (Route53, CloudDeploy, CloudFormation, CloudWatch, ECS, EKS, EC2, DynamoDB, Lambda, Step Functions, SAM, SQS, SNS, EventBridge, S3), databases, security, networks"}
   {:name "Computer Science Bsc."
    :duration "August 2014 - June 2017"
    :location "Uppsala University"
    :summary "Elected courses in mathematics and combinatorial optimization"
    :fields "Algorithms, datastructures, compilers, security, databases, low-level parallelism, semantics, networks, combinatorial optimization, linear programming, multi-variable calculus, linear algebra, statistics, probability, formal logic"}])

;; -------------------------
;; View

(defn wrapped-list [{:keys [entries selected description]}]
  [:div
   {:class "wrapped-list"}
   [:div
    {:class "wrapped-list-entries"}
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
     [:div
      {:class "wrapped-list-note"}
      [:span {:class "label"} (get-in entries [@selected :label])]
      [:span {:class "note"} (get-in entries [@selected :notes])]]
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
   [:div {:class "card-title"} [:h3 (:name entry)]]
   [:div
    {:class "card-content"}
    (let [selected (r/atom nil)]
      [wrapped-list
       {:entries (:skill-set entry)
        :selected selected
        :description (:click-note entry)}])]])

(defn employment [entry]
  (let [collapsed (r/atom true)]
    [:div
     {:key (:name entry)
      :class "card"}
     [:div
      {:class "card-title"}
      [:h3 (:name entry)]
     [:div
      {:class "card-subtitle"}
      [:div {:class "job-title"} (:title entry)]]]
     [:div
      {:class "card-content"}
      [collapsible-wrapper
       {:collapsed collapsed}
       [:div
        {:key (join [entry "dur"])
         :class "detailed-list-section job-duration"}
        [:span {:class "time"} (:time entry)]
        [:span {:class "date"} (:date entry)]]
       [:div
        {:key (join [entry "stack"])
         :class "detailed-list-section"}
        [:span {:class "label"} "Stack"]
        [:span {:class "content"} (join ", " (:stack entry))]]
       [:div
        {:key (join [entry "stack"])
         :class "detailed-list-section"}
        [:span {:class "label"} "Fields"]
        [:span {:class "content"} (join ", " (:fields entry))]]
       [:div
        {:key (join [entry "stack"])
         :class "detailed-list-section"}
        [:span {:class "label"} "Responsibilities"]
        [:span
         {:class "content"}
         [:ul
          (for [[index highlight] (map-indexed vector (:highlights entry))]
            [:li {:key (join [entry index])} highlight])]]]]]]))

(defn project [entry]
  [:div
   {:key (:name entry)
    :class "card"}
   [:div
    {:class "card-title"}
    [:h3 (:name entry)]
    (if (contains? entry :location)
      [:div {:class "card-subtitle"} (:location entry)])]
   [:div
    {:class "card-content"}
    [:div
     {:class "detailed-list-section"}
     (:summary entry)]
    [:div
     {:class "detailed-list-section"}
     [:span {:class "label"} "Duration"]
     [:span {:class "content"} (:duration entry)]]
    [:div
     {:class "detailed-list-section"}
     [:span {:class "label"} "Fields"]
     [:span {:class "content"} (:fields entry)]]]])

(defn resume []
  [:div
   {:class "main-section resume"}
   [:div
    {:class "main-subsection"}
    [:div {:class "main-subsection-header"} [:h2 "Skills"]]
    [:div
     {:class "card-container"}
     (for [entry data-skills] (skill entry))]]
   [:div
    {:class "main-subsection"}
    [:div {:class "main-subsection-header"} [:h2 "Education"]]
    [:div
     {:class "card-container"}
     (for [entry data-education] (project entry))]]
   [:div
    {:class "main-subsection"}
    [:div {:class "main-subsection-header"} [:h2 "Work"]]
    [:div
     {:class "card-container"}
     (for [entry data-employments] (employment entry))]]
   [:div
    {:class "main-subsection"}
    [:div {:class "main-subsection-header"} [:h2 "Projects"]]
    [:div
     {:class "card-container"}
     (for [entry data-projects] (project entry))]]])
