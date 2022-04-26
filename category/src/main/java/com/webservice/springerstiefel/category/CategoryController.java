package com.webservice.springerstiefel.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
        String prodResourceUrl = "http://localhost:8081/deleteProdByCat/" ;
        return "success";
    }

    @GetMapping(path="/allCats")
    public @ResponseBody Iterable<Category> getAll() {
        return catRepo.findAll();
    }
}
