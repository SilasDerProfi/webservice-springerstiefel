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

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(path = "/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository catRepo;

    @PostMapping()
    public @ResponseBody Category addNewCategory(@RequestParam("name") String name) {
        Category c = new Category(name);
        c = catRepo.save(c);
        return c;
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody String deleteCategory(@PathVariable("id") int id) {
        RestTemplate restTemplate = new RestTemplate();
        String prodResourceUrl = "http://productservice:8082/products?categoryId=" + id;
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

    @GetMapping()
    public @ResponseBody Iterable<Category> getAll() {
        return catRepo.findAll();
    }

    @GetMapping(path = "/categoryExists/{id}")
    public @ResponseBody Boolean categoryExists(@PathVariable("id") int id){
        return catRepo.existsById(id);
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody Category getCategory(@PathVariable("id") int id){
        Optional<Category> cat = catRepo.findById(id);
        if(cat.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "category with id " + id + " not found");
        }
        
        return cat.get();

    }  

}
