#!/bin/sh
if [ -z "$DOCKER_ACCOUNT" ]; then
    DOCKER_ACCOUNT=felixleh
fi;

arg1="$1"

if [ "$1" == "--no-cache" ] || [ "$1" == "--product" ]; then
    docker build .. --tag=microservice-kubernetes-product -f ../docker/ProductDockerfile --no-cache
else
    docker build .. --tag=microservice-kubernetes-product -f ../docker/ProductDockerfile
fi
docker tag microservice-kubernetes-product $DOCKER_ACCOUNT/microservice-kubernetes-product:latest
docker push $DOCKER_ACCOUNT/microservice-kubernetes-product
