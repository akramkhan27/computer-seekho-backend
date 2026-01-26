package com.computerseekho.api.service.impl;


import com.computerseekho.api.entity.Batch;
import com.computerseekho.api.repository.BatchRepository;
import com.computerseekho.api.service.BatchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchServiceImpl implements BatchService {

    private final BatchRepository batchRepository;

    public BatchServiceImpl(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    // CREATE
    @Override
    public Batch createBatch(Batch batch) {
        return batchRepository.save(batch);
    }

    // READ ALL
    @Override
    public List<Batch> getAllBatches() {
        return batchRepository.findAll();
    }

    // READ BY ID
    @Override
    public Batch getBatchById(Integer id) {
        return batchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Batch not found"));
    }

    // UPDATE
    @Override
    public Batch updateBatch(Integer id, Batch batch) {

        Batch existing = getBatchById(id);

        existing.setBatchName(batch.getBatchName());
        existing.setBatchStartTime(batch.getBatchStartTime());
        existing.setBatchEndTime(batch.getBatchEndTime());
        existing.setCourse(batch.getCourse());
        existing.setPresentationDate(batch.getPresentationDate());
        existing.setFinalPresentationDate(batch.getFinalPresentationDate());
        existing.setCourseFees(batch.getCourseFees());
        existing.setCourseFeesFrom(batch.getCourseFeesFrom());
        existing.setCourseFeesTo(batch.getCourseFeesTo());
        existing.setBatchIsActive(batch.getBatchIsActive());
        existing.setBatchLogoUrl(batch.getBatchLogoUrl());

        return batchRepository.save(existing);
    }

    // DELETE (Hard delete – can be soft if needed)
    @Override
    public void deleteBatch(Integer id) {
        batchRepository.deleteById(id);
    }

    // SEARCH
    @Override
    public List<Batch> searchBatch(String keyword) {
        return batchRepository.findByBatchNameContainingIgnoreCase(keyword);
    }
}
