package com.webservice.springerstiefel.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
    @Autowired
    private ProductRepository prodRepo;

    @PostMapping(path = "/addProd")
    public @ResponseBody
    String addNewProduct(@RequestParam String name, @RequestParam int categoryId, @RequestParam double price) {
        Product p = new Product(name, categoryId, price);
        prodRepo.save(p);
        return "success";
    }

    @PostMapping(path = "/deleteProd")
    public @ResponseBody String deleteProduct(@RequestParam int id) {
        prodRepo.deleteById(id);
        return "success";
    }

    @GetMapping(path="/allProds")
    public @ResponseBody Iterable<Product> getAll() {
        return prodRepo.findAll();
    }
}
