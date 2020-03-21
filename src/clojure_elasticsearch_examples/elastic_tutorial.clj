(ns clojure-elasticsearch-examples.elastic-tutorial
  (:require
    [clojure.tools.logging :as log]
    [clojure.data.json :as json]
    [clojure-elasticsearch-examples.get-requests :as get-requests]
    [tick.alpha.api :as t]
    [tick.core :as tick])
  (:gen-class)
  (:import (org.elasticsearch.common.settings Settings$Builder Settings)
           (org.elasticsearch.client RestHighLevelClient RequestOptions RestClient)
           (org.apache.http HttpHost)
           (org.elasticsearch.client.indices CreateIndexRequest)
           (org.elasticsearch.action ActionListener)
           (org.elasticsearch.action.index IndexRequest)
           (org.elasticsearch.common.xcontent XContentType)
           (org.elasticsearch.action.get GetRequest)))

(defn index-request ^IndexRequest
  [^String index ^String id ^String json]
  (-> (IndexRequest. index)
      (.id id)
      (.source json XContentType/JSON)))

; think aobut generic request here
(defn execute
  [^RestHighLevelClient client
   ^IndexRequest request
   ^RequestOptions request-options]
  (.index client request request-options))

(defn execute-get
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
  [
   ^RestHighLevelClient client
   ^IndexRequest request
   ^RequestOptions request-options

   ^ActionListener listener]
  (.indexAsync client request request-options listener))

(defn index-builder
  [name settings]
  (-> (CreateIndexRequest. name)
      ;; (.settings settings) add these back in correctly.
      ))

(defn rest-client ^RestHighLevelClient
  [^String host]
  (-> (RestHighLevelClient. (RestClient/builder (into-array HttpHost [(HttpHost. "localhost", 9200, "http") (HttpHost. "localhost", 9201, "http")])))))

;; Probably should do the .. method as more pure
(defn settings-builder
  [^Integer shards ^Integer replicas]
  (-> (Settings/builder)
      (.put "number_of_shards" shards)
      (.put "number_of_replicas" replicas)
      (.build)))

(defn -main [& args]
  (log/infof "Example Elastic Search App Running!")
  (let [
        index "posts"
        client (rest-client "http://localhost:9200")
        request-1 (index-request index "1" (json/write-str
                                             {:user      "kimchy"
                                              :post-date (t/format (tick/now))
                                              :message   "trying out Elasticsearch"}))
        request-2 (index-request index "2" (json/write-str
                                             {:user      "perkss"
                                              :post-date (t/format (tick/now))
                                              :message   "Clojure and Elasticsearch"}))
        response (execute client request-1 RequestOptions/DEFAULT)
        response-2 (execute-async client request-2 RequestOptions/DEFAULT (action-listener))
        get-response (execute-get client (get-requests/get-request index "1") RequestOptions/DEFAULT)]
    (log/infof "Result of indexing document synchronous is %s" response)
    (log/infof "Result of indexing document asynchronous is %s" response-2)
    (log/infof "Get request response is %s" get-response)))
