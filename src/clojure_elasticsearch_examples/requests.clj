(ns clojure-elasticsearch-examples.requests
  (:import (org.elasticsearch.action.get GetRequest)
           (org.elasticsearch.action.index IndexRequest)
           (org.elasticsearch.common.xcontent XContentType)))

(defn get-request
  [index id]
  (GetRequest. index id))

(defn index-request ^IndexRequest
  [^String index ^String id ^String json]
  (-> (IndexRequest. index)
      (.id id)
      (.source json XContentType/JSON)))
