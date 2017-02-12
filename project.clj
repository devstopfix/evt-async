(defproject evrythng.api "0.4.0"
  :description "EVERYTHNG Client API using core.async"
  :url "https://github.com/devstopfix/evt-async"
  :license {:name "MIT License"
            :url "https://en.wikipedia.org/wiki/MIT_License"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha14"]
                 [org.clojure/core.async "0.2.395"]
                 [clj-http "2.1.0"]
                 [cheshire "5.6.1"]]
  :source-paths ["src/clj" "src/cljc"]
  :test-paths ["test"])