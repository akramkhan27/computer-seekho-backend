package com.computerseekho.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.computerseekho.api.entity.Batch;
import com.computerseekho.api.service.BatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batches")
public class BatchController {

    private final BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    // ================= CREATE =================
    @PostMapping
    public ResponseEntity<Batch> createBatch(@RequestBody Batch batch) {
        return ResponseEntity.ok(batchService.createBatch(batch));
    }

    // ================= READ ALL =================
    @GetMapping
    public ResponseEntity<List<Batch>> getAllBatches() {
        return ResponseEntity.ok(batchService.getAllBatches());
    }

    // ================= READ BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<Batch> getBatchById(@PathVariable Integer id) {
        return ResponseEntity.ok(batchService.getBatchById(id));
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public ResponseEntity<Batch> updateBatch(
            @PathVariable Integer id,
            @RequestBody Batch batch) {

        return ResponseEntity.ok(batchService.updateBatch(id, batch));
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBatch(@PathVariable Integer id) {
        batchService.deleteBatch(id);
        return ResponseEntity.noContent().build();
    }

    // ================= SEARCH =================
    @GetMapping("/search")
    public ResponseEntity<List<Batch>> searchBatch(
            @RequestParam String keyword) {

        return ResponseEntity.ok(batchService.searchBatch(keyword));
    }
}