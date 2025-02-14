#!/bin/bash
 
export AWS_ACCESS_KEY_ID=testAccessKey
export AWS_SECRET_ACCESS_KEY=testSecretKey
export AWS_DEFAULT_REGION=us-east-1
 
aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name queue-taller-webflux
 
aws --endpoint-url=http://localhost:4566 dynamodb create-table --table-name dynamo-taller-webflux --attribute-definitions AttributeName=id,AttributeType=N --key-schema AttributeName=id,KeyType=HASH --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5