# My Retail
This is an application built for a target case study.  Some of the components used to build this application

* Spring Boot
* Gson
* Lombok
* Spring Data Mongodb
* Mockito (Testing)
* Embedded Mongo (Testing)

## Prerequisites
You will need to have mongo running somewhere. If you have docker you can run

    docker run -p 27017:27017 mongo

This will bring up a container and expose `localhost:27017` for a mongo connection.  By default the application will look for mongo running here.  If you do not have docker you can find installation instructions [here](https://docs.mongodb.com/manual/installation/).

## Running the Project

Clone the project

    git clone git@github.com:ppelayo/myretail.git

#### Java/Maven
Build the project by running

    mvn clean install

This will trigger the tests and package the application into a runnable jar.  Once the packaging has completed you can navigate to the target folder and run the jar.

    java -jar ./target/myretail-0.0.1-SNAPSHOT.jar

The spring boot application will come up on port 8080.

You can overwrite the default mongo location by setting `spring.data.mongodb.host` and `spring.data.mongodb.port` as java options.

    java -Dspring.data.mongodb.host=<hostname> -Dspring.data.mongodb.port=<port> -jar ./target/myretail-0.0.1-SNAPSHOT.jar


#### Docker
The project also has a public docker image on [dockerhub](https://hub.docker.com/r/ppdocx/myretail-target).  You can run the project by running

    docker -p 8080:8080 run ppdocx/myretail-target java -Dspring.data.mongodb.host=<hostname> -Dspring.data.mongodb.port=<port> -jar /app/myretail/target/myretail-0.0.1-SNAPSHOT.jar

Note, you will most likely have to set the hostname and port in this scenario. With containers localhost will refer to the localhost inside of the container


#### Endpoints
##### GET /products/{id}

    curl localhost:8080/products/13860428

##### PUT /products/{id}

    curl -X PUT -d @product.json -H "Content-Type: application/json" localhost:8080/products/13860429


##### Sample Product.json
    {
      "id": 13860429,
      "name": "SpongeBob SquarePants: SpongeBob's Frozen Face-off",
      "current_price": {
        "value": 10.99,
        "currency_code": "YEN"
      }
    }

### Testing
There are several unit tests that test individual class logic.  There is also also a test class called `MyRetailApplicationIntegrationTest` that bootstraps a mongo repository as well as the entire spring application.  The test then makes requests against the bootstrapped application to confirm the full happy path works properly.




