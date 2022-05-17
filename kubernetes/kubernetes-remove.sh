#!/bin/sh
minikube start
kubectl delete service apache category product cpdatabase
kubectl delete deployments apache category product cpdatabase 