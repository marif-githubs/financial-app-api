package com.finance.dash_api.service;

import com.finance.dash_api.POJO.CustomException;
import com.finance.dash_api.entity.*;
import com.finance.dash_api.entity.Record;
import com.finance.dash_api.repository.RecordRepository;
import com.finance.dash_api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RecordService {

    private final RecordRepository recordRepo;
    private final UserRepository userRepo;

    public RecordService(RecordRepository recordRepo, UserRepository userRepo) {
        this.recordRepo = recordRepo;
        this.userRepo = userRepo;
    }

    public UUID createRecord(Record record, UUID userId) {

        Record createdRecord;
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        record.setUser(user);

        try {

            createdRecord = recordRepo.save(record);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return createdRecord.getId();
    }

    public Record updateRecord(UUID recordId, Record newRecordDetail) {

        Record record;
        Record existingRecordDetail = recordRepo.findById(recordId)
                .orElseThrow(() -> new CustomException("Record not found", HttpStatus.NOT_FOUND));

        if (newRecordDetail.getAmount() != null && !newRecordDetail.getAmount().equals(existingRecordDetail.getAmount())) {
            throw new CustomException("Amount Cannot be Change", HttpStatus.METHOD_NOT_ALLOWED);
        }
        if (newRecordDetail.getType() != null && newRecordDetail.getType().equals(existingRecordDetail.getType())) {
            throw new CustomException("Type Cannot be Change", HttpStatus.METHOD_NOT_ALLOWED);
        }
        if (newRecordDetail.getCategory() != null && !existingRecordDetail.getCategory().equals(newRecordDetail.getCategory())) {
            existingRecordDetail.setCategory(newRecordDetail.getCategory());
        }
        if (newRecordDetail.getNotes() != null && !existingRecordDetail.getNotes().equals(newRecordDetail.getNotes())) {
            existingRecordDetail.setNotes(newRecordDetail.getNotes());
        }

        try {
            record = recordRepo.save(existingRecordDetail);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return record;
    }

    public Record deleteRecord(UUID recordId) {

        Record record = recordRepo.findById(recordId)
                .orElseThrow(() -> new CustomException("Record not found", HttpStatus.NOT_FOUND));

        try {

            recordRepo.deleteById(recordId);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return record;
    }

    public List<Record> filterRecords(RecordType type, String category, LocalDateTime start, LocalDateTime end) {

        List<Record> records ;

        try {

            if (type != null && category != null) {
                records = recordRepo.findByTypeAndCategory(type, category);
            } else if (type != null) {
                records = recordRepo.findByType(type);
            } else if (category != null) {
                records = recordRepo.findByCategory(category);
            } else if (start != null && end != null) {
                records = recordRepo.findByCreationDateBetween(start, end);
            } else {
                records = recordRepo.findAll();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (records.isEmpty()){
            throw new CustomException("No Record Found",HttpStatus.NOT_FOUND);
        }

        return records;
    }
}