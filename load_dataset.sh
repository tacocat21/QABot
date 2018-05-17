#!/bin/bash 

curl -H "Content-Type: application/json" -XPOST "localhost:9200/qas/_doc/_bulk?pretty&refresh" --data-binary "@dataset/stem_classified.json"
