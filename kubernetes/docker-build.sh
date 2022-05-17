#!/bin/sh
if [ -z "$DOCKER_ACCOUNT" ]; then
    DOCKER_ACCOUNT=mika2147
fi;

docker build ../apache --tag=microservice-kubernetes-apache
docker tag microservice-kubernetes-apache $DOCKER_ACCOUNT/microservice-kubernetes-apache:latest
docker push $DOCKER_ACCOUNT/microservice-kubernetes-apache

docker build .. --tag=microservice-kubernetes-category -f ../docker/CategoryDockerfile
docker tag microservice-kubernetes-category $DOCKER_ACCOUNT/microservice-kubernetes-category:latest
docker push $DOCKER_ACCOUNT/microservice-kubernetes-category

docker build .. --tag=microservice-kubernetes-product -f ../docker/ProductDockerfile
docker tag microservice-kubernetes-product $DOCKER_ACCOUNT/microservice-kubernetes-product:latest
docker push $DOCKER_ACCOUNT/microservice-kubernetes-product

docker build .. --tag=microservice-kubernetes-database -f ../docker/DockerfileMySQL
docker tag microservice-kubernetes-database $DOCKER_ACCOUNT/microservice-kubernetes-database:latest
docker push $DOCKER_ACCOUNT/microservice-kubernetes-database