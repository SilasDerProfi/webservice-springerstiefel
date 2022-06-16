package hska.iwi.eShopMaster.model.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hska.iwi.eShopMaster.model.api.CategoryApiConnector;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;

public class CategoryApiConnectorImpl implements CategoryApiConnector{
    public static final String CATEGORY_BASE_URL = "http://category.default.svc.cluster.local:8081/categories";;
    private ObjectMapper objectMapper;

    public CategoryApiConnectorImpl(){
        objectMapper = new ObjectMapper();
    }

    public List<Category> getObjectList(){
        RestTemplate restTemplate = new RestTemplate();
        String prodResourceUrl = CATEGORY_BASE_URL;
        ResponseEntity<String> result = restTemplate.exchange(prodResourceUrl, HttpMethod.GET, null, String.class);
        List<Category> categories;
        if(result.getStatusCode().equals(HttpStatus.OK)){
            try {
                categories = objectMapper.readValue(result.getBody(), new TypeReference<List<Category>>() {});
            } catch (Exception e) {
                e.printStackTrace();
                categories = new ArrayList<Category>();
            }
        }else{
            categories = new ArrayList<Category>();
        }
        
        return categories;
    }

    public Category getObjectById(int id) {
        RestTemplate restTemplate = new RestTemplate();
        String prodResourceUrl = CATEGORY_BASE_URL + "/" + id;
        ResponseEntity<String> result = restTemplate.exchange(prodResourceUrl, HttpMethod.GET, null, String.class);
        
        if(result.getStatusCode().equals(HttpStatus.OK)){
            try {
                return objectMapper.readValue(result.getBody(), new TypeReference<Category>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public Category getObjectByName(String name) {
        List<Category> categories = this.getObjectList();
        for(Category c : categories){
            if(c.getName().equals(name)){
                return c;
            }
        }

        return null;
    }

    public void saveObject(Category cat) {
        RestTemplate restTemplate = new RestTemplate();
        String prodResourceUrl = CATEGORY_BASE_URL + "?name=" + cat.getName();
        ResponseEntity<String> result = restTemplate.exchange(prodResourceUrl, HttpMethod.POST, null, String.class);
    }

    public void deleteById(int id) {
        RestTemplate restTemplate = new RestTemplate();
        String prodResourceUrl = CATEGORY_BASE_URL + "/" + id;
        ResponseEntity<String> result = restTemplate.exchange(prodResourceUrl, HttpMethod.DELETE, null, String.class);        
    }
    
}
