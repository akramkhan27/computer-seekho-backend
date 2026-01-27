package com.computerseekho.api.repository;

import com.computerseekho.api.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findByStudentId(Integer studentId);

    List<Payment> findByCourseId(Integer courseId);

    List<Payment> findByBatchId(Integer batchId);

    Optional<Payment> findByEnquiryId(Integer enquiryId);
}