#!/bin/sh
kubectl -n istio-system port-forward deployment/istio-ingressgateway 80:80