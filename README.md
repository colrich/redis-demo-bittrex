A simple demo of caching using Spring and Redis and the Bittrex API.
To use:
1. open manifest.yml and place valid Bittrex API keys in the BITTREX\_API\_KEY and BITTREX\_SECRET\_KEY fields. Also update redis service name
2. build and push to Cloud Foundry; use PWS at https://run.pivotal.io if you don't otherwise have access to PCF
3. create and bind a Redis instance i.e. cf create-service p-redis shared-vm my-redis-instance && cf bind-service my-app my-redis-instance
4. restart app - cf restart my-app
5. visit a market summary page at https://my-app.cfapps.io/summary/btc-eth

Incorporates code from https://github.com/platelminto/java-bittrex
