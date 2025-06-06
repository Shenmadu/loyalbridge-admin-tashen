package org.example.repository;

import org.example.entity.ConversionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConversionLogRepository extends JpaRepository<ConversionLog, Long> {
    List<ConversionLog> findByUserId(Long userId);
    List<ConversionLog> findByPartnerId(Long partnerId);
    List<ConversionLog> findByConversionDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
