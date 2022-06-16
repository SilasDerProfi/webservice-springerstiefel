package hska.iwi.eShopMaster.model.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import hska.iwi.eShopMaster.model.api.ProductApiConnector;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;

public class ProductApiConnectorImpl implements ProductApiConnector{
    public static final String PRODUCTS_BASE_URL = "http://product.default.svc.cluster.local:8082/products";
    private ObjectMapper objectMapper;

    public ProductApiConnectorImpl(){
        objectMapper = new ObjectMapper();
    }

    public List<Product> getObjectList() {
        RestTemplate restTemplate = new RestTemplate();
        String prodResourceUrl = PRODUCTS_BASE_URL;
        ResponseEntity<String> result = restTemplate.exchange(prodResourceUrl, HttpMethod.GET, null, String.class);
        List<Product> products;
        if(result.getStatusCode().equals(HttpStatus.OK)){
            try {
                products = objectMapper.readValue(result.getBody(), new TypeReference<List<Product>>() {});
            } catch (Exception e) {
                e.printStackTrace();
                products = new ArrayList<Product>();
            }
        }else{
            products = new ArrayList<Product>();
        }
        
        return products;
    }

    public List<Product> getProductListByCriteria(String searchDescription, Double searchMinPrice,
            Double searchMaxPrice) {
                RestTemplate restTemplate = new RestTemplate();
                String prodResourceUrl = PRODUCTS_BASE_URL + "?" + "description=" + searchDescription + "&minPrice=" + searchMinPrice + "&maxPrice=" + searchMaxPrice;

                ResponseEntity<String> result = restTemplate.exchange(prodResourceUrl, HttpMethod.GET, null, String.class);
                List<Product> products;
                if(result.getStatusCode().equals(HttpStatus.OK)){
                    try {
                        products = objectMapper.readValue(result.getBody(), new TypeReference<List<Product>>() {});
                    } catch (Exception e) {
                        e.printStackTrace();
                        products = new ArrayList<Product>();
                    }
                }else{
                    products = new ArrayList<Product>();
                }
                
                return products;
    }

    public void saveObject(Product product) {
        RestTemplate restTemplate = new RestTemplate();
        String prodResourceUrl = PRODUCTS_BASE_URL + "?" + "name=" + product.getName() + "&categoryId=" + product.getCategory().getId() + "&price=" + product.getPrice() + "&details=" + product.getDetails();
        ResponseEntity<String> result = restTemplate.exchange(prodResourceUrl, HttpMethod.POST, null, String.class);
        
    }

    public Product getObjectById(int id) {
        RestTemplate restTemplate = new RestTemplate();
        String prodResourceUrl = PRODUCTS_BASE_URL + "/" + id;
        ResponseEntity<String> result = restTemplate.exchange(prodResourceUrl, HttpMethod.GET, null, String.class);
        
        if(result.getStatusCode().equals(HttpStatus.OK)){
            try {
                return objectMapper.readValue(result.getBody(), new TypeReference<Product>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void deleteById(int id) {
        RestTemplate restTemplate = new RestTemplate();
        String prodResourceUrl = PRODUCTS_BASE_URL + "/" + id;
        ResponseEntity<String> result = restTemplate.exchange(prodResourceUrl, HttpMethod.DELETE, null, String.class);        
    
    }

    public boolean deleteProductsByCategoryId(int categoryId) {
        RestTemplate restTemplate = new RestTemplate();
        String prodResourceUrl = PRODUCTS_BASE_URL + "?categoryId=" + categoryId;
        ResponseEntity<String> result = restTemplate.exchange(prodResourceUrl, HttpMethod.DELETE, null, String.class);        
        return result.getStatusCode().equals(HttpStatus.OK);
    }

    public Product getObjectByName(String name) {
        List<Product> products = this.getObjectList();
        for(Product p : products){
            if(p.getName().equals(name)){
                return p;
            }
        }

        return null;
    }
    
}
