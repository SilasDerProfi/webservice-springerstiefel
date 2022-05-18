#!/bin/sh
if [ -z "$DOCKER_ACCOUNT" ]; then
    DOCKER_ACCOUNT=mika2147
fi;

arg1="$1"

if [ "$1" == "--no-cache" ]; then
    docker build ../apache --tag=microservice-kubernetes-apache --no-cache
else
    docker build ../apache --tag=microservice-kubernetes-apache
fi

docker tag microservice-kubernetes-apache $DOCKER_ACCOUNT/microservice-kubernetes-apache:latest
docker push $DOCKER_ACCOUNT/microservice-kubernetes-apache

if [ "$1" == "--no-cache" ]; then
    docker build .. --tag=microservice-kubernetes-category -f ../docker/CategoryDockerfile --no-cache
else
    docker build .. --tag=microservice-kubernetes-category -f ../docker/CategoryDockerfile
fi
docker tag microservice-kubernetes-category $DOCKER_ACCOUNT/microservice-kubernetes-category:latest
docker push $DOCKER_ACCOUNT/microservice-kubernetes-category

if [ "$1" == "--no-cache" ]; then
    docker build .. --tag=microservice-kubernetes-product -f ../docker/ProductDockerfile --no-cache
else
    docker build .. --tag=microservice-kubernetes-category -f ../docker/CategoryDockerfile
fi
docker tag microservice-kubernetes-product $DOCKER_ACCOUNT/microservice-kubernetes-product:latest
docker push $DOCKER_ACCOUNT/microservice-kubernetes-product

docker build .. --tag=microservice-kubernetes-database -f ../docker/DockerfileMySQL
docker tag microservice-kubernetes-database $DOCKER_ACCOUNT/microservice-kubernetes-database:latest
docker push $DOCKER_ACCOUNT/microservice-kubernetes-database