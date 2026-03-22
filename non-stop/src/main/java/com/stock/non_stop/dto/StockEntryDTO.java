package com.stock.non_stop.dto;

public class StockEntryDTO {

    private Long productId;
    private int outQuantity;
    private String date;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getOutQuantity() {
        return outQuantity;
    }

    public void setOutQuantity(int outQuantity) {
        this.outQuantity = outQuantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
