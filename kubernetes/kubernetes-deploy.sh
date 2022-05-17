#!/bin/sh
if [ -z "$DOCKER_ACCOUNT" ]; then
    DOCKER_ACCOUNT=mika2147
fi;

kubectl create deployment apache --image=docker.io/$DOCKER_ACCOUNT/microservice-kubernetes-apache:latest --port=80
kubectl expose deployment/apache --type="LoadBalancer" --port 80

kubectl create deployment category --image=docker.io/$DOCKER_ACCOUNT/microservice-kubernetes-category:latest --port=8080
kubectl expose deployment/category --type="LoadBalancer" --port 8081

kubectl create deployment product --image=docker.io/$DOCKER_ACCOUNT/microservice-kubernetes-product:latest --port=8080
kubectl expose deployment/product --type="LoadBalancer" --port 8082

kubectl create deployment cpdatabase --image=docker.io/$DOCKER_ACCOUNT/microservice-kubernetes-database:latest --port=8080
kubectl expose deployment/cpdatabase --type="LoadBalancer" --port 3306