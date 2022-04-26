package com.webservice.springerstiefel.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {
    @Autowired
    private CategoryRepository catRepo;

    @PostMapping(path = "/addCat")
    public @ResponseBody String addNewCategory(@RequestParam String name) {
        Category c = new Category(name);
        catRepo.save(c);
        return "success";
    }

    @PostMapping(path = "/deleteCat")
    public @ResponseBody String deleteCategory(@RequestParam int id) {
        catRepo.deleteById(id);
        RestTemplate restTemplate = new RestTemplate();
        String prodResourceUrl = "http://localhost:8082/product/deleteProdByCat?categoryId=" + id;
        ResponseEntity<String> result = restTemplate.exchange(prodResourceUrl, HttpMethod.POST, null, String.class);
        return "success";
    }

    @GetMapping(path="/allCats")
    public @ResponseBody Iterable<Category> getAll() {
        return catRepo.findAll();
    }
}
