(defproject clojure-elasticsearch-examples "0.1.0-SNAPSHOT"
  :description "Elastic search with Clojure fun"
  :url "perks.github.io"
  :dependencies [[org.clojure/clojure "1.10.1"]]
  :repl-options {:init-ns clojure-elasticsearch-examples.core}
  :main ^:skip-aot clojure-elasticsearch-examples.core
  :target-path "target/%s"
  :plugins [[lein-cljfmt "0.6.0"]]
  :profiles {:uberjar {:aot :all}})
