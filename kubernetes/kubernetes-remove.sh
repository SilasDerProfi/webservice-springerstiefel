#!/bin/sh
arg1="$1"

if [ "$1" == "--start" ]; then
    minikube start --memory 8192
fi
kubectl delete service apache category product cpdatabase webshop
kubectl delete deployments apache category product cpdatabase webshop

if [ "$1" == "--stop" ]; then
    minikube stop
fi