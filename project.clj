(defproject ring-sse/ring-sse "0.1.0-SNAPSHOT"
  :description "Ring (Spec 1.4+) Server-Sent Events handler and helpers."
  :url "https://github.com/bobby/ring-sse"
  :license {:name "The MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.3.443"]
                 [ring/ring-core "1.6.1" :scope "provided"]]
  :profiles {:dev {:dependencies [[ring/ring-mock "0.3.0"]]}})
