package com.finance.dash_api.controller;

import com.finance.dash_api.DTO.RecordDTO;
import com.finance.dash_api.POJO.ApiResponse;
import com.finance.dash_api.POJO.RecordId;
import com.finance.dash_api.entity.Record;
import com.finance.dash_api.entity.RecordType;
import com.finance.dash_api.service.RecordService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/records")
public class RecordController {

    private final RecordService service;

    public RecordController(RecordService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RecordId> create(@RequestBody RecordDTO record,
                                        @RequestParam UUID userId) {
        UUID recordId = service.createRecord(record, userId);

        return new ApiResponse<>("Success", "Record Created", new RecordId(userId, recordId));

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Page<RecordDTO>> getAll(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false, defaultValue = "0") int pageNum,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        LocalDateTime start = startDate != null ? LocalDateTime.parse(startDate) : null;
        LocalDateTime end = endDate != null ? LocalDateTime.parse(endDate) : null;


        Page<RecordDTO> page = service.filterRecords(type, category, start, end, pageNum, size);

        return new ApiResponse<>("Success","List of records found. Page:"+pageNum+" size:"+size, page);
    }

    @PutMapping("/{recordId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<RecordId> update(@PathVariable UUID recordId,
                                        @RequestBody RecordDTO record) {
        RecordDTO updatedRecord = service.updateRecord(recordId, record);

        return new ApiResponse<>("Success", "Record updated", new RecordId(updatedRecord.getUserId(), updatedRecord.getId()));
    }

    @DeleteMapping("/{recordId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<RecordId> delete(@PathVariable UUID recordId) {

        Record deletedRecord = service.deleteRecord(recordId);

        return new ApiResponse<>("Success", "Record Deleted", new RecordId( deletedRecord.getUser().getId(), deletedRecord.getId()));
    }
}