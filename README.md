Inter Bank Funds Transfers API
===================================================================
`ibft-api` is a small financial application for carrying out simple banking transactions like debit/credit of an account.

### Scope
This api does not provide authentication/authorization for endpoints and uses an in-memory database H2 with some preloaded scripts.
The scripts are located in /resources/data.sql and these will run when the application is run.


### Execute Tests
```bash
mvn test
```

### Package into an executable jar
```bash
mvn package -DskipTests
```
### Run project from command line
```bash
java -jar ibft-api-0.0.1-SNAPSHOT.jar
```


### Running `ibft-api`
`ibft-api` is a `Spring Boot` application that runs on port 8080. 
 
Some example [`curl`] commands:

```bash
curl --location --request GET 'http://localhost:8080/accounts/111/balance'
```
```bash
curl --location --request GET 'http://localhost:8080/accounts/111/ministatement'
```
```bash
curl --location --request POST 'http://localhost:8080/accounts/transferfunds' \
--header 'Content-Type: application/json' \
--data-raw '{
    "sender-account-id":"222",
    "receiver-account-id":"111",
    "transaction-amount":"5"
}'
```



