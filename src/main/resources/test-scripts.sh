#!/bin/bash

curl localhost:8080/products/13860428 | jq '.'
curl -X PUT -d @tmp.json -H "Content-Type: application/json" localhost:8080/products/13860429 | jq '.'