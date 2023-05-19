package com.facundocenteno.springboot.app.products.controller;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.facundocenteno.springboot.app.products.models.entity.Product;
import com.facundocenteno.springboot.app.products.models.service.IProductService;

@RestController
public class ProductController {

	@Autowired
	private Environment env;
	
	@Value("${server.port}")
	private Integer port;
	
	@Autowired
	private IProductService productService;
	
	@GetMapping("/products")
	public List<Product> getAll(){
		return productService.findAll().stream().map(product -> {
			//product.setPort(Integer.parseInt(env.getProperty("server.port")));
			product.setPort(port);
			return product;
		}).collect(Collectors.toList());
		
	}
	
	@GetMapping("/products/{id}")
	public Product getById(@PathVariable Long id) {
		Product product = productService.findById(id);
		//product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		product.setPort(port);
		return product;
	}
}