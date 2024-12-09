# PriceProvider
Service tha provides a REST API for calculating a given product's price for a given amount

Assumption:

 * Single discount policy can be applied for single product UUID
 * Discount on one product UUID does not affect product with different UUID
 * Price cannot be negative
 * Base prices are not changing live
 * Base prices are stored in the file src/main/resources/basePrices.json
 * There is no need to provide database with prices hashmap should be sufficient


## Prerequisites
- Jdk 21
- Maven 3.8+

### To build project run:
mvn clean install

#### You can configure pricing policies with file:
src/main/resources/application.yml

###### Prices and product names and UUID are stored in file:
src/main/resources/basePrices.json
