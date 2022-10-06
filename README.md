# Crypto Investment Advisor

Recommendation system for crypto investment. 
Basic service is based on data in csv files with prices for each crypto.

## Start the application as Docker container

1. Build the app with
`mvn clean install`
2. Run the app with params `docker run -d -p 8000:8000 -e DATA_BASE_DIR=[data_base_dir] --name crypto-investment-advisor com.crypto/crypto-investment-advisor/[version]`

e.g. `docker run -d -p 8000:8000 -e DATA_BASE_DIR=[/opt/crypto-investment-advisor] --name crypto-investment-advisor com.crypto/crypto-investment-advisor/0.0.1-SNAPSHOT`

## Start the application in IDE
 1. Pass `DATA_BASE_DIR` (the path to base directory containing csv data) as an environment variable
 2. Start `CryptoInvestmentAdvisorApplication`

## Api Documentation
Find the Api Swagger documentation on
`http://[host]:8080/swagger-ui/index.html#`




