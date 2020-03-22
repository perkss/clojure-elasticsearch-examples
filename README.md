# clojure-elasticsearch-examples

A Clojure project showing how to use the Elastic Search API. 
Fun times!!

## Getting Started
Now using `docker-compose` we can start and expose elastic search on port `9200` and also start `kibana` and navigate to 
its UI at `http://localhost:5601`.

By running the command in the root directory `docker-compose up -d`

If you have errors please check the logs `docker-compose logs` or `docker logs [container-id]`, I saw `exit code 78` and 
had to increase the `docker daemons` memory size. 

## Building our app into a Docker image

Now lets get ready to build our docker image by building an uberjar first and then creating a docker image from our 
`DockerFile`. This `DockerFile` is fancy as it will create our `Uberjar` and then build our docker image. 

    $ docker build -t elasticsearch-example .

Now its going to run the image we just created with the tag name `elasticsearch-example`

    $ docker run -it elasticsearch-example 
    
## Running Test Containers

[Testcontainers](https://www.testcontainers.org/) make it easy to run tests which use Docker containers. In this example we can 
simply run `lein test` and as long as Docker instance is running then it will fire up Elasticsearch and run our applicaiton test 
against it. Simple.

## Rest High Level Client Tutorial

Create the RestHighLevelClient in Clojure using Java interop.
```clojure
(defn rest-client ^RestHighLevelClient
  ([^String host ^Integer port] (rest-client host port "http"))
  ([^String host ^Integer port ^String scheme]
   (-> (RestHighLevelClient. (RestClient/builder (into-array HttpHost [(HttpHost. host port scheme)]))))))
```

Create a IndexRequest document to be indexed by the rest client.
```clojure
(requests/index-request index "2" (json/write-str
                                                      {:user      "perkss"
                                                       :post-date (t/format (tick/now))
                                                       :message   "Clojure and Elasticsearch"}))
```

Function defined to execute the document IndexRequest
```clojure
(defn execute ^IndexResponse
  [^RestHighLevelClient client
   ^IndexRequest request
   ^RequestOptions request-options]
  (.index client request request-options))
```