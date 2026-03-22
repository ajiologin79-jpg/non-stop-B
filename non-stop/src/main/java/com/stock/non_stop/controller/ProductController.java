package com.stock.non_stop.controller;

import com.stock.non_stop.dto.ProductDTO;
import com.stock.non_stop.entity.Product;
import com.stock.non_stop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductRepository repo;

    @GetMapping
    public List<Product> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Product create(@RequestBody ProductDTO dto) {

        Product p = new Product();
        p.setName(dto.getName());
        p.setTotalQuantity(dto.getTotalQuantity());

        return repo.save(p);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody ProductDTO dto) {

        Product p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        p.setName(dto.getName());
        p.setTotalQuantity(dto.getTotalQuantity());

        return repo.save(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
