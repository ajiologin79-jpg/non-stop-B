package com.stock.non_stop.service;

import com.stock.non_stop.entity.Product;
import com.stock.non_stop.entity.StockEntry;
import com.stock.non_stop.repository.ProductRepository;
import com.stock.non_stop.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StockService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private StockRepository stockRepo;

    public StockEntry createEntry(Long productId, int outQty, LocalDate date) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (outQty > product.getTotalQuantity()) {
            throw new RuntimeException("Out quantity exceeds stock!");
        }

        int leftStock = product.getTotalQuantity() - outQty;

        StockEntry entry = new StockEntry();
        entry.setProduct(product);
        entry.setOutQuantity(outQty);
        entry.setLeftInStock(leftStock);
        entry.setEntryDate(date);

        return stockRepo.save(entry);
    }
}
