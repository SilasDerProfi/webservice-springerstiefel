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

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping(path = "/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository catRepo;

    private static final String PRODUCTS_BASE_URL = "http://product.default.svc.cluster.local:8082/products";

    @PostMapping()
    public @ResponseBody Category addNewCategory(@RequestParam("name") String name, HttpServletResponse response) {
        response.setHeader("Pod-Name", System.getenv("HOSTNAME"));

        Category c = new Category(name);
        c = catRepo.save(c);
        return c;
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody String deleteCategory(@PathVariable("id") int id, HttpServletResponse response) {
        response.setHeader("Pod-Name", System.getenv("HOSTNAME"));

        RestTemplate restTemplate = new RestTemplate();
        String prodResourceUrl = PRODUCTS_BASE_URL + "?categoryId=" + id;
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
    public @ResponseBody Iterable<Category> getAll(HttpServletResponse response) {
        response.setHeader("Pod-Name", System.getenv("HOSTNAME"));

        return catRepo.findAll();
    }

    @GetMapping(path = "/categoryExists/{id}")
    public @ResponseBody Boolean categoryExists(@PathVariable("id") int id, HttpServletResponse response){
        response.setHeader("Pod-Name", System.getenv("HOSTNAME"));

        return catRepo.existsById(id);
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody Category getCategory(@PathVariable("id") int id, HttpServletResponse response){
        response.setHeader("Pod-Name", System.getenv("HOSTNAME"));

        Optional<Category> cat = catRepo.findById(id);
        if(cat.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "category with id " + id + " not found");
        }
        
        return cat.get();

    }  

}
