#!/bin/sh
echo Open Grafana at http://localhost:3000/
kubectl -n istio-system port-forward deployment/grafana 3001:3000