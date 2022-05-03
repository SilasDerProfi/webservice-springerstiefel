package com.webservice.springerstiefel.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
    @Autowired
    private ProductRepository prodRepo;

    @PostMapping(path = "/addProd")
    public @ResponseBody
    String addNewProduct(@RequestParam String name, @RequestParam int categoryId, @RequestParam double price) {
        //TODO: check if category exists before adding product
        Product p = new Product(name, categoryId, price);
        prodRepo.save(p);
        //TODO: change return type?
        return "success";
    }

    //TODO: Change mapping to delete?
    @DeleteMapping(path = "/deleteProd")
    public @ResponseBody String deleteProduct(@RequestParam int id) {
        prodRepo.deleteById(id);
        //TODO: change return type?
        return "success";
    }

    //TODO: change mapping to delete?
    @DeleteMapping(path = "/deleteProdByCat")
    public @ResponseBody String deleteProductByCatId(@RequestParam int categoryId) {
        Iterable<Product> products = prodRepo.findAll();
        for (Product p : products) {
            if (p.getCategoryId() == categoryId) {
                prodRepo.delete(p);
            }
        }
        //TODO: change return type?
        return "success";
    }

    @GetMapping(path="/allProds")
    public @ResponseBody Iterable<Product> getAll() {
        return prodRepo.findAll();
    }
}
