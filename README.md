# mockend

the "mock backend" for frontend Single Page Applications. 

mockend was used to implement the ["unidirectional dataflow"](https://adambien.blog/roller/abien/entry/unidirectional_data_flow_example_with) and asynchronous data fetching in the https://github.com/adambien/bce.design template.

# install

`curl -L -O https://github.com/AdamBien/mockend/releases/download/[LATEST_RELEASE]/mockend-runner.jar`

e.g. for the v0.0.1:

`curl -L -O https://github.com/AdamBien/mockend/releases/download/v0.0.1/mockend-runner.jar`

# run

Install Java 11+ first, then run:

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

`HTTP/1.1 204 No Content`

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

## echo

request:

`curl -X POST "http://localhost:8080/echo" -H"Content-Type: text/plain" -d "hello, mockend"`

response:

```
HTTP/1.1 200 OK
Content-Length: 19
Content-Type: text/plain;charset=UTF-8

echo:hello, mockend
```

request with a response delayed for 1000 ms:

`curl -i -X POST "http://localhost:8080/echo"  -H'delay-in-ms:1000' -H"Content-Type: text/plain" -d "hello, mockend"`

## status

The `/statuses` API returns responses with status codes passed as `status` header and a `+` in the body, e.g.

response:

`curl -H"status: 500" http://localhost:8080/statuses`
response:

```
HTTP/1.1 500 Internal Server Error
Content-Length: 1

+
```

`POST`, `PUT`, `GET`, `DELETE`, `OPTIONS` requests are supported.

# build

`mvn package`

# references

This repository contains code extracted from: https://github.com/AdamBien/snail and https://github.com/AdamBien/statustest

mockend was created to test: https://github.com/AdamBien/bce.design and also used at: https://airhacks.live workshops