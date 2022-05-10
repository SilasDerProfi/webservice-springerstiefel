package com.webservice.springerstiefel.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
    @Autowired
    private ProductRepository prodRepo;

    @PostMapping()
    public @ResponseBody Product addNewProduct(@RequestParam String name, @RequestParam int categoryId,
            @RequestParam double price, @RequestParam(defaultValue = "") String details) {
        RestTemplate restTemplate = new RestTemplate();
        String prodResourceUrl = "http://categoryservice:8081/categories/categoryExists/" + categoryId;
        ResponseEntity<Boolean> result = restTemplate.exchange(prodResourceUrl, HttpMethod.GET, null, Boolean.class);

        if (!result.getBody())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "category with id " + categoryId + " does not exist");

        Product p = new Product(name, price, categoryId, details);
        p = prodRepo.save(p);
        return p;
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody String deleteProduct(@PathVariable int id) {
        prodRepo.deleteById(id);
        return "success";
    }

    @DeleteMapping()
    public @ResponseBody Iterable<Product> deleteProductByCatId(@RequestParam(defaultValue = "-1") int categoryId) {
        if(categoryId == -1){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "You need to set a categoryId");
        }
        
        Iterable<Product> products = prodRepo.findAll();
        for (Product p : products) {
            if (p.getCategoryId() == categoryId) {
                prodRepo.delete(p);
            }
        }
        return products;
    }

    @GetMapping()
    public @ResponseBody Iterable<Product> getAll(
    		@RequestParam(defaultValue="") String description,
    		@RequestParam(defaultValue="0") int minPrice,
    		@RequestParam(defaultValue="" + Integer.MAX_VALUE) int maxPrice) {
    	List<Product> products = new ArrayList<>();
    	
    	for (var p : prodRepo.findAll()) {
    		if (p.getPrice() > maxPrice)
    			continue;
    		if (p.getPrice() < minPrice)
    			continue;
    		if (!p.getDetails().contains(description))
    			continue;
    		products.add(p);
    	}
    	
        return products;
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody Product getProduct(@PathVariable("id") int id) {
        Optional<Product> product = prodRepo.findById(id);

        if(product.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + id + " does not exist");
        }

        return product.get();
    }
}
