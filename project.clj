(defproject clojure-elasticsearch-examples "0.1.0-SNAPSHOT"
  :description "Elastic search with Clojure fun"
  :url "perks.github.io"
  :dependencies [[org.clojure/data.json "1.0.0"]
                 [org.elasticsearch/elasticsearch "7.6.0"]
                 [org.elasticsearch.client/elasticsearch-rest-high-level-client "7.6.0"]
                 [org.clojure/clojure "1.10.1"]
                 [org.clojure/tools.logging "0.4.0"]
                 [org.slf4j/slf4j-log4j12 "1.7.1"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jmdk/jmxtools
                                                    com.sun.jmx/jmxri]]
                 [org.testcontainers/testcontainers "1.13.0"]
                 [org.testcontainers/elasticsearch "1.13.0"]
                 [tick "0.4.23-alpha"]]
  :repl-options {:init-ns clojure-elasticsearch-examples.elastic-tutorial}
  :main ^:skip-aot clojure-elasticsearch-examples.elastic-tutorial
  :target-path "target/%s"
  :plugins [[lein-cljfmt "0.6.0"]]
  :profiles {:uberjar {:aot :all}})
