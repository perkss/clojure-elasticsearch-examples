(ns clojure-elasticsearch-examples.get-requests
  (:import (org.elasticsearch.action.get GetRequest)))

(defn get-request
  [index id]
  (GetRequest. index id))
