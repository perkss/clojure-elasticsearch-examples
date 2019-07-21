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