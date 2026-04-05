package com.finance.dash_api.repository;


import com.finance.dash_api.entity.Record;
import com.finance.dash_api.entity.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RecordRepository extends JpaRepository<Record, UUID> {

    List<Record> findByType(RecordType type);

    List<Record> findByCategory(String category);

    List<Record> findByCreationDateBetween(LocalDateTime start, LocalDateTime end);

    List<Record> findByTypeAndCategory(RecordType type, String category);
}