#!/bin/sh
minikube start --memory 8192
kubectl apply -f infrastructure.yaml
kubectl apply -f microservices.yaml