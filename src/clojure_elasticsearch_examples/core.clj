(ns clojure-elasticsearch-examples.core
  (:require
    [clojure.tools.logging :as log])
  (:gen-class)
  (:import (io.searchbox.client JestClientFactory)
           (io.searchbox.client.config HttpClientConfig$Builder)
           (io.searchbox.indices CreateIndex$Builder)
           (org.elasticsearch.common.settings Settings$Builder Settings)))

(defn index-builder
  [name settings]
  (-> (CreateIndex$Builder. name)
      ;; (.settings settings) add these back in correctly.
      (.build) ))

(defn http-client-config
  [^String host]
  (-> (HttpClientConfig$Builder. host)
      (.build)))

;; Probably should do the .. method as more pure
(defn settings-builder
  [^Integer shards ^Integer replicas]
  (-> (Settings/builder)
      (.put "number_of_shards" shards)
      (.put "number_of_replicas" replicas)
      (.build)))

(defn -main [& args]
  (log/infof "Example Elastic Search App Running!")
  (let [factory (JestClientFactory.)
        http-client-config (http-client-config "http://localhost:9200")
        _ (.setHttpClientConfig factory http-client-config)
        client (.getObject factory)
        result (do (.execute client (index-builder "repayments" (settings-builder 3 1))))]
    (log/infof "Result of creating index is %s" (.getJsonString result))))
