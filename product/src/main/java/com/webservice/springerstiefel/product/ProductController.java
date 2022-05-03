package com.webservice.springerstiefel.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
    @Autowired
    private ProductRepository prodRepo;

    @PostMapping(path = "/addProd")
    public @ResponseBody Product addNewProduct(@RequestParam String name, @RequestParam int categoryId,
            @RequestParam double price) {
        RestTemplate restTemplate = new RestTemplate();
        String prodResourceUrl = "http://categoryservice:8081/category/categoryExists?id=" + categoryId;
        ResponseEntity<Boolean> result = restTemplate.exchange(prodResourceUrl, HttpMethod.GET, null, Boolean.class);

        if (!result.getBody())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "category with id " + categoryId + " does not exist");

        Product p = new Product(name, categoryId, price);
        p = prodRepo.save(p);
        return p;
    }

    @DeleteMapping(path = "/deleteProd")
    public @ResponseBody String deleteProduct(@RequestParam int id) {
        prodRepo.deleteById(id);
        return "success";
    }

    @DeleteMapping(path = "/deleteProdByCat")
    public @ResponseBody Iterable<Product> deleteProductByCatId(@RequestParam int categoryId) {
        Iterable<Product> products = prodRepo.findAll();
        for (Product p : products) {
            if (p.getCategoryId() == categoryId) {
                prodRepo.delete(p);
            }
        }
        return products;
    }

    @GetMapping(path = "/allProds")
    public @ResponseBody Iterable<Product> getAll() {
        return prodRepo.findAll();
    }
}
