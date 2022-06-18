#!/bin/sh
if [ -z "$DOCKER_ACCOUNT" ]; then
    DOCKER_ACCOUNT=felixleh
fi;

arg1="$1"

if [ "$1" == "--no-cache" ]; then
    docker build ../apache --tag=microservice-kubernetes-apache --no-cache
else
    docker build ../apache --tag=microservice-kubernetes-apache
fi

docker tag microservice-kubernetes-apache $DOCKER_ACCOUNT/microservice-kubernetes-apache:latest
docker push $DOCKER_ACCOUNT/microservice-kubernetes-apache

if [ "$1" == "--no-cache" ] || [ "$1" == "--category" ]; then
    docker build .. --tag=microservice-kubernetes-category -f ../docker/CategoryDockerfile --no-cache
else
    docker build .. --tag=microservice-kubernetes-category -f ../docker/CategoryDockerfile
fi
docker tag microservice-kubernetes-category $DOCKER_ACCOUNT/microservice-kubernetes-category:latest
docker push $DOCKER_ACCOUNT/microservice-kubernetes-category

if [ "$1" == "--no-cache" ] || [ "$1" == "--product" ]; then
    docker build .. --tag=microservice-kubernetes-product -f ../docker/ProductDockerfile --no-cache
else
    docker build .. --tag=microservice-kubernetes-product -f ../docker/ProductDockerfile
fi
docker tag microservice-kubernetes-product $DOCKER_ACCOUNT/microservice-kubernetes-product:latest
docker push $DOCKER_ACCOUNT/microservice-kubernetes-product

if [ "$1" == "--no-cache" ] || [ "$1" == "--webshop" ]; then
    docker build .. --tag=microservice-kubernetes-webshop -f ../docker/WebshopDockerfile --no-cache
else
    docker build .. --tag=microservice-kubernetes-webshop -f ../docker/WebshopDockerfile
fi
docker tag microservice-kubernetes-webshop $DOCKER_ACCOUNT/microservice-kubernetes-webshop:latest
docker push $DOCKER_ACCOUNT/microservice-kubernetes-webshop

if [ "$1" == "--no-cache" ] || [ "$1" == "--database" ]; then
    docker build .. --tag=microservice-kubernetes-database -f ../docker/DockerfileMySQL --no-cache
else
    docker build .. --tag=microservice-kubernetes-database -f ../docker/DockerfileMySQL
fi
docker tag microservice-kubernetes-database $DOCKER_ACCOUNT/microservice-kubernetes-database:latest
docker push $DOCKER_ACCOUNT/microservice-kubernetes-database