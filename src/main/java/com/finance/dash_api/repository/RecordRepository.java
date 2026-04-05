package com.finance.dash_api.repository;


import com.finance.dash_api.entity.Record;
import com.finance.dash_api.entity.RecordType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.UUID;

public interface RecordRepository extends JpaRepository<Record, UUID> {

    Page<Record> findByType(RecordType type, Pageable pageable);

    Page<Record> findByCategory(String category, Pageable pageable);

    Page<Record> findByCreationDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Record> findByTypeAndCategory(RecordType type, String category, Pageable pageable);

    @Query("SELECT COALESCE(SUM(r.amount), 0) FROM Record r WHERE r.type = :type")
    Double getTotalByType(RecordType type);
}