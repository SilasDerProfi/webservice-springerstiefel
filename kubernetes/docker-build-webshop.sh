#!/bin/sh
if [ -z "$DOCKER_ACCOUNT" ]; then
    DOCKER_ACCOUNT=felixleh
fi;

arg1="$1"

if [ "$1" == "--no-cache" ] || [ "$1" == "--webshop" ]; then
    docker build .. --tag=microservice-kubernetes-webshop -f ../docker/WebshopDockerfile --no-cache
else
    docker build .. --tag=microservice-kubernetes-webshop -f ../docker/WebshopDockerfile
fi
docker tag microservice-kubernetes-webshop $DOCKER_ACCOUNT/microservice-kubernetes-webshop:latest
docker push $DOCKER_ACCOUNT/microservice-kubernetes-webshop
