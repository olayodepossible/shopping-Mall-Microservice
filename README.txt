

How to run the archtecture:


1. Run all the Microservices in IntelliJ, 11 of them. And open Eureka in browser to see which one is running: http://localhost:8761

2. Create Mongo DB (MngoDb compass) for each Microservices (5):
	productServiceDB, customerServiceDB, shoppingServiceCommandDB, shoppingServiceQueryDB, 		orderServiceDB

3. Install Kafka from the internet and navigate to the folder:
	How to start Kafka

	i. Go to terminal and navigate to kafka_2.13 folder

	ii. Start Zookeeper (for managing clusters in kafka) by running:
		sh bin/zookeeper-server-start.sh config/zookeeper.properties

	iii. Start kafka by typing this in another terminal:

		sh bin/kafka-server-start.sh config/server.properties

4. If you want to trace requests, 
download zipkin and navigate to the folder and run this in terminal:

	 java -jar zipkin-server-1.30.3-exec.jar

- Then you may see the entire architecture on the browser:  http://localhost:9411/zipkin 

5. Test the api's from Client Application, or postman.

	
