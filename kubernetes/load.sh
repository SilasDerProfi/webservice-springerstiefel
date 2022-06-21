#!/bin/bash
for i in `seq 1 10000`;
do
    curl --cookie "JSESSIONID=0C3904B1C94F1A46612E2F2BFF1189A7" -s -o /dev/null -I -w "%{http_code}" http://localhost/EShop-1.0.0/listAllProducts.action
    echo
done
