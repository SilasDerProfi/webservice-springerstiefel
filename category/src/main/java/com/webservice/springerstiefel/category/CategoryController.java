package com.webservice.springerstiefel.category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {

    @RequestMapping("/test")
  	public Category test() {
    	return new Category("Example");
  	}

}
