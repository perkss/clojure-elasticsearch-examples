(ns clojure-elasticsearch-examples.elastic-tutorial-test
  (:require [clojure.test :refer :all]
            [clojure.tools.logging :as log]
            [clojure-elasticsearch-examples.elastic-tutorial :refer :all]
            [clojure-elasticsearch-examples.requests :as requests]
            [clojure.data.json :as json]
            [clojure.walk :as walk])
  (:import
    (org.elasticsearch.client RequestOptions)
    (org.testcontainers.containers.wait.strategy Wait)
    (org.testcontainers.elasticsearch ElasticsearchContainer)))

(deftest example-kafka-integration-test
  (testing "Fire up test containers Elasticsearch and then index and get message"
    (let
      [elastic-container (ElasticsearchContainer. "docker.elastic.co/elasticsearch/elasticsearch-oss:7.6.0")
       _ (.waitingFor elastic-container (Wait/forLogMessage ".*started.*" 1))
       _ (.start elastic-container)
       address (.getContainerIpAddress elastic-container)
       port (.getMappedPort elastic-container 9200)
       index "posts"
       doc-id "1"
       doc-payload {:user    "perkss"
                    :message "Clojure and Elasticsearch"}
       rest-client (rest-client address port)
       request (requests/index-request index doc-id (json/write-str
                                                      doc-payload))
       _ (execute rest-client request RequestOptions/DEFAULT)
       get-response (execute-get rest-client (requests/get-request index doc-id) RequestOptions/DEFAULT)
       parsed-source (walk/keywordize-keys (json/read-str (.getSourceAsString get-response)))]
      (log/infof "Elastic started %s:%s with mapped result %s" address port parsed-source)
      (is (= doc-payload parsed-source)))))