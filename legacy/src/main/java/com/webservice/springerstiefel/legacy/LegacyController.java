package com.webservice.springerstiefel.legacy;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/legacy")
public class LegacyController {

    @RequestMapping("/test")
  	public String test() {
    	return "Hello";
  	}

}
