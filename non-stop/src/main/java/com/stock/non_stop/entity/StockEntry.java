package com.stock.non_stop.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class StockEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate entryDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int outQuantity;
    private int leftInStock;

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getOutQuantity() {
        return outQuantity;
    }

    public void setOutQuantity(int outQuantity) {
        this.outQuantity = outQuantity;
    }

    public int getLeftInStock() {
        return leftInStock;
    }

    public void setLeftInStock(int leftInStock) {
        this.leftInStock = leftInStock;
    }
}