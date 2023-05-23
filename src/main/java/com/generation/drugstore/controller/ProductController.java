package com.generation.drugstore.controller;

import com.generation.drugstore.model.Product;
import com.generation.drugstore.repository.CategoryRepository;
import com.generation.drugstore.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Product>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(productRepository.findAllByNameContainingIgnoreCase(name));
    }

    @PostMapping
    public ResponseEntity<Product> post(@Valid @RequestBody Product product) {
        if (categoryRepository.existsById(product.getCategory().getId()))
            return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(product));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping
    public ResponseEntity<Product> put(@Valid @RequestBody Product product) {
        if (productRepository.existsById(product.getId())) {
            if (categoryRepository.existsById(product.getCategory().getId()))
                return ResponseEntity.ok(productRepository.save(product));
            else
                return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(resp -> {
                    productRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}/orlaboratory/{laboratory}")
    public ResponseEntity<List<Product>> getByNameOrLab(@PathVariable String name, @PathVariable String laboratory) {
        return ResponseEntity.ok(productRepository.findAllByNameOrLaboratory(name, laboratory));

    }

    @GetMapping("/name/{name}/andlaboratory/{laboratory}")
    public ResponseEntity<List<Product>> getByNameAndLab(@PathVariable String name, @PathVariable String laboratory) {
        return ResponseEntity.ok(productRepository.findAllByNameAndLaboratory(name, laboratory));
    }

    @GetMapping("/start_price/{start}/end_price/{end}")
    public ResponseEntity<List<Product>> getByPrice(@PathVariable BigDecimal start, @PathVariable BigDecimal end) {
        return ResponseEntity.ok(productRepository.findAllByPriceBetween(start, end));
    }


}
