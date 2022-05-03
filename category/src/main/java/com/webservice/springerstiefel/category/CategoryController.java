package com.webservice.springerstiefel.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpHeaders;
import java.util.Optional;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {
    @Autowired
    private CategoryRepository catRepo;

    @PostMapping(path = "/addCat")
    public @ResponseBody Category addNewCategory(@RequestParam String name) {
        Category c = new Category(name);
        c = catRepo.save(c);
        return c;
    }

    @DeleteMapping(path = "/deleteCat")
    public @ResponseBody String deleteCategory(@RequestParam int id) {
        RestTemplate restTemplate = new RestTemplate();
        String prodResourceUrl = "http://productservice:8082/product/deleteProdByCat?categoryId=" + id;
        ResponseEntity<String> result = restTemplate.exchange(prodResourceUrl, HttpMethod.DELETE, null, String.class);
        

        if(!catRepo.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "category with id " + id + " does not exist");
        }
       
        try{
            catRepo.deleteById(id);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "delete failed");
        }
        
        
        return "success";
    }

    @GetMapping(path="/allCats")
    public @ResponseBody Iterable<Category> getAll() {
        return catRepo.findAll();
    }

    @GetMapping(path = "/categoryExists")
    public @ResponseBody Boolean categoryExists(@RequestParam int id){
        return catRepo.existsById(id);
    }

    @GetMapping()
    public @ResponseBody Category getCategory(@RequestParam int id){
        Optional<Category> cat = catRepo.findById(id);
        if(cat.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "category with id " + id + " not found");
        }
        
        return cat.get();

    }  

}
