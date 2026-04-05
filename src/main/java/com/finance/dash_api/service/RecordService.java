package com.finance.dash_api.service;

import com.finance.dash_api.DTO.RecordDTO;
import com.finance.dash_api.Helper.MapToDTO;
import com.finance.dash_api.POJO.CustomException;
import com.finance.dash_api.entity.*;
import com.finance.dash_api.entity.Record;
import com.finance.dash_api.repository.RecordRepository;
import com.finance.dash_api.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RecordService {

    private final RecordRepository recordRepo;
    private final UserRepository userRepo;
    private final MapToDTO mapToDTO;

    public RecordService(RecordRepository recordRepo, UserRepository userRepo, MapToDTO mapToDTO) {
        this.recordRepo = recordRepo;
        this.userRepo = userRepo;
        this.mapToDTO = mapToDTO;
    }

    public UUID createRecord(RecordDTO recordDTO, UUID userId) {

        Record record, createdRecord;
        User user = userRepo.findById(userId).orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        record = mapToDTO.toRecord(recordDTO, user);

        createdRecord = recordRepo.save(record);

        return createdRecord.getId();
    }

    public RecordDTO updateRecord(UUID recordId, RecordDTO newRecordDetail) {

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

        record = recordRepo.save(existingRecordDetail);
        return mapToDTO.toRecordDTO(record);
    }

    public Record deleteRecord(UUID recordId) {

        Record record = recordRepo.findById(recordId).orElseThrow(() -> new CustomException("Record not found", HttpStatus.NOT_FOUND));

        recordRepo.deleteById(recordId);

        return record;
    }

    public Page<RecordDTO> filterRecords(String type, String category, LocalDateTime start, LocalDateTime end,
                                         int pageNum,
                                         int size) {

        RecordType recordType = null;

        if (type != null) {
            try {
                recordType = RecordType.valueOf(type.toUpperCase());
            } catch (Exception e) {
                throw new CustomException("Invalid Record Type", HttpStatus.BAD_REQUEST);
            }
        }

        Pageable pageable = PageRequest.of(pageNum, size);
        Page<Record> recordPage;

        if (recordType != null && category != null) {
            recordPage = recordRepo.findByTypeAndCategory(recordType, category, pageable);

        } else if (recordType != null) {
            recordPage = recordRepo.findByType(recordType, pageable);

        } else if (category != null) {
            recordPage = recordRepo.findByCategory(category, pageable);

        } else if (start != null && end != null) {
            recordPage = recordRepo.findByCreationDateBetween(start, end, pageable);

        } else {
            recordPage = recordRepo.findAll(pageable);
        }

        if (recordPage.isEmpty()) {
            throw new CustomException("No Record Found", HttpStatus.NOT_FOUND);
        }

        return recordPage.map(mapToDTO::toRecordDTO);
    }
}