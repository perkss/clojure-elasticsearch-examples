(ns clojure-elasticsearch-examples.elastic-tutorial
  (:require
    [clojure.tools.logging :as log]
    [clojure.data.json :as json]
    [clojure-elasticsearch-examples.requests :as requests]
    [tick.alpha.api :as t]
    [tick.core :as tick])
  (:gen-class)
  (:import (org.elasticsearch.client RestHighLevelClient RequestOptions RestClient)
           (org.apache.http HttpHost)
           (org.elasticsearch.action ActionListener)
           (org.elasticsearch.action.index IndexRequest IndexResponse)
           (org.elasticsearch.action.get GetRequest GetResponse)))

(defn execute ^IndexResponse
  [^RestHighLevelClient client
   ^IndexRequest request
   ^RequestOptions request-options]
  (.index client request request-options))

(defn execute-get ^GetResponse
  [^RestHighLevelClient client
   ^GetRequest request
   ^RequestOptions request-options]
  (.get client request request-options))

(defn action-listener
  []
  (reify ActionListener
    (onResponse [_ response]
      (log/infof "Callback: Got response %s" response))
    (onFailure [_ exception]
      (log/error "Failed: " exception))))

(defn execute-async
  [^RestHighLevelClient client
   ^IndexRequest request
   ^RequestOptions request-options

   ^ActionListener listener]
  (.indexAsync client request request-options listener))

(defn rest-client ^RestHighLevelClient
  ([^String host ^Integer port] (rest-client host port "http"))
  ([^String host ^Integer port ^String scheme]
   (-> (RestHighLevelClient. (RestClient/builder (into-array HttpHost [(HttpHost. host port scheme)]))))))

(defn -main [& args]
  (log/infof "Example Elastic Search App Running!")
  (let [index "posts"
        client (rest-client "localhost" 9200)
        request-1 (requests/index-request index "1" (json/write-str
                                                      {:user      "kimchy"
                                                       :post-date (t/format (tick/now))
                                                       :message   "trying out Elasticsearch"}))
        request-2 (requests/index-request index "2" (json/write-str
                                                      {:user      "perkss"
                                                       :post-date (t/format (tick/now))
                                                       :message   "Clojure and Elasticsearch"}))
        response (execute client request-1 RequestOptions/DEFAULT)
        response-2 (execute-async client request-2 RequestOptions/DEFAULT (action-listener))]
    (log/infof "Result of indexing document synchronous is %s" response)
    (log/infof "Result of indexing document asynchronous is %s" response-2)
    (log/infof "Get request doc 1 response is %s" (execute-get client (requests/get-request index "1") RequestOptions/DEFAULT))
    (log/infof "Get request doc 2 response is %s" (execute-get client (requests/get-request index "2") RequestOptions/DEFAULT))))
