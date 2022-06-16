#!/bin/sh
arg1="$1"

if [ "$1" == "--start" ]; then
    minikube start --memory 8192
fi
kubectl apply -f infrastructure.yaml
kubectl apply -f microservices.yaml