 
# lagom-unmanaged-service-example

A tiny project show how to use external service in Lagom

### What Is an Unmanaged Service?
An unmanaged service is basically an external service that exposes some data over HTTP. Any external REST API that will be used in a Lagom-based service is an unmanaged service.
  
In this example we connect our Lagom service to a public API that provide list of countries include some information about countries.

##### This API is available from below link:

http://services.groupkt.com/country/get/all

We have an external service that connect to this web service and get country list, so our other Lagom services can consume data from this external service.

Here we have CountriesService that do the job for us. this service inject external service, fetch the result via invoking its functions, do some improvement on result and returns list of country that just contains name and code.

##### after running project on your local host, you can test api through below address: 

http://localhost:9000/api/country-list
