# mockend

the mock backend for frontend applications

# install

`curl -L -O https://github.com/AdamBien/mockend/releases/download/[LATEST_RELEASE]/mockend-runner.jar`

e.g. for the v0.0.1:

`curl -L -O https://github.com/AdamBien/mockend/releases/download/v0.0.1/mockend-runner.jar`

# run

`java -jar target/mockend-runner.jar`

# API

Checkout: `http://localhost:8080/q/swagger-ui/`

All requests can be delayed for the time defined in the header: `delay-in-ms`

## Create Read Update Delete (CRUD)

### creation with generated id
request: `curl -XPOST "http://localhost:8080/crud" -H  "Content-Type: application/json" -d "{\"message\":\"hello, mockend\"}"`
response: 
```
access-control-allow-credentials: false 
access-control-allow-origin: http://localhost:8080 
content-length: 0 
location: http://localhost:8080/crud/1618928997430
```

### fetch an existing object

request: `curl http://localhost:8080/crud/1618928997430`
response: 
```json
{"message":"hello, mockend","id":"1618928997430"}
```

### fetch an existing object with a delay

request with a response delayed for 1000 ms:

`curl -H'delay-in-ms:1000' http://localhost:8080/crud/1618928997430`

### delete a record

request:

`curl -XDELETE http://localhost:8080/crud/1618928997430`

response:

`HTTP/1.1 204 No Content``

### create an object with id

request:

`curl -X PUT "http://localhost:8080/crud/42" -H  "Content-Type: application/json" -d "{\"message\":\"hello,mockend\"}"`

response: 

```
HTTP/1.1 201 Created
Content-Length: 0
Location: http://localhost:8080/crud/42
```

subsequent `PUT`with the same id:

request:

`curl -X PUT "http://localhost:8080/crud/42" -H  "Content-Type: application/json" -d "{\"message\":\"hello,mockend\"}"`

response:

`HTTP/1.1 204 No Content`



# build

`mvn package`
