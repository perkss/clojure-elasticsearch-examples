(defproject clojure-elasticsearch-examples "0.1.0-SNAPSHOT"
  :description "Elastic search with Clojure fun"
  :url "perks.github.io"
  :dependencies [[io.searchbox/jest "5.3.3"]
                 [org.clojure/clojure "1.10.1"]
                 [org.clojure/tools.logging "0.4.0"]
                 [org.slf4j/slf4j-log4j12 "1.7.1"]
                 [org.elasticsearch/elasticsearch "7.2.0"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jmdk/jmxtools
                                                    com.sun.jmx/jmxri]]]
  :repl-options {:init-ns clojure-elasticsearch-examples.core}
  :main ^:skip-aot clojure-elasticsearch-examples.core
  :target-path "target/%s"
  :plugins [[lein-cljfmt "0.6.0"]]
  :profiles {:uberjar {:aot :all}})
