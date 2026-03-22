package com.stock.non_stop.repository;

import com.stock.non_stop.entity.StockEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<StockEntry, Long> {

    // Optional: find entries by date
    List<StockEntry> findByEntryDate(LocalDate entryDate);

    // Optional: filter by product id
    List<StockEntry> findByProductId(Long productId);
}
