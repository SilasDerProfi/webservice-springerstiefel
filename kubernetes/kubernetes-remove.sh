#!/bin/sh
minikube start --memory 8192
kubectl delete service apache category product cpdatabase webshop
kubectl delete deployments apache category product cpdatabase webshop