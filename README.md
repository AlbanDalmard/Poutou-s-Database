# Launching scalardb

```
sudo docker-compose up -d
java -jar scalardb-schema-loader-3.9.6.jar --config database.properties --schema-file schema.json --coordinator
```

## Scalardb commands

```
./gradlew run --args="GetCustomerInfo id"
./gradlew run --args="AddCustomer id name city number transactions"
```

The AddCustomer still raises an error, but the GetCustomerInfo works fine.

## Launch GUI commands

```
cd ./demo
mvn spring-boot:run
```
If you have error on the building of Spring, then run Java on the DemoApplication.java file in src/main/java/com/example/demo.
