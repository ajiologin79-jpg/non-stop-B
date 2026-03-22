package com.stock.non_stop.controller;
import com.stock.non_stop.dto.StockEntryDTO;
import com.stock.non_stop.entity.Product;
import com.stock.non_stop.entity.StockEntry;
import com.stock.non_stop.repository.StockRepository;
import com.stock.non_stop.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@RestController
@RequestMapping("/stock")
@CrossOrigin
public class StockController {

    @Autowired
    private StockService service;

    @Autowired
    private StockRepository repo;

    @GetMapping
    public List<StockEntry> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public StockEntry create(@RequestBody StockEntryDTO dto) {

        return service.createEntry(
                dto.getProductId(),
                dto.getOutQuantity(),
                LocalDate.parse(dto.getDate())
        );
    }

    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {

        List<StockEntry> list = repo.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Stock Data");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Date");
        header.createCell(1).setCellValue("Product");
        header.createCell(2).setCellValue("Out Quantity");
        header.createCell(3).setCellValue("Remaining Stock");

        int rowNum = 1;

        for (StockEntry s : list) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(s.getEntryDate().toString());
            row.createCell(1).setCellValue(s.getProduct().getName());
            row.createCell(2).setCellValue(s.getOutQuantity());
            row.createCell(3).setCellValue(s.getLeftInStock());
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=stock.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }

    @PutMapping("/{id}")
    public StockEntry update(@PathVariable Long id, @RequestBody StockEntryDTO dto) {

        StockEntry existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        Product product = existing.getProduct();

        if (dto.getOutQuantity() > product.getTotalQuantity()) {
            throw new RuntimeException("Exceeds stock limit");
        }

        existing.setOutQuantity(dto.getOutQuantity());
        existing.setEntryDate(LocalDate.parse(dto.getDate()));
        existing.setLeftInStock(product.getTotalQuantity() - dto.getOutQuantity());

        return repo.save(existing);
    }
}
