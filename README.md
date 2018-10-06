# Realtime Statistics


Rest API for realtime statistics of last 60 seconds


Restful API that calculates realtime statistics from the last 60 seconds. API contains three endpoints:

POST /transactions: to register a new transaction (unique input of this application).
GET /statistics: returns the statistic based of the transactions of the last 60 seconds.
DELETE /transactions: to delete all the transactions.

### SpringBoot application created using maven build tool

- Compile with command "mvn clean install"
- Run Tests with "mvn clean test"
- Run with command "mvn spring-boot:run"
- Transactions are saved in PriorityBlockingQueue in sorted order.
- Spring scheduler is used to clear the transactions from the cache that are older than 60 seconds.
- Transaction cache will keep updating based on the scheduler timing and remove old entries.
- Statistics service is created to get statistics for fixed duration of 60 seconds.
- Statistics calculated on the collection using DoubleSummaryStatics.
- GlobalExceptionHandler is created to handle the exceptions. 
- Unit Tests of controller.
