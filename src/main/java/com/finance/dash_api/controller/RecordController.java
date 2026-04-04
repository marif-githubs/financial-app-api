package com.finance.dash_api.controller;

import com.finance.dash_api.entity.Record;
import com.finance.dash_api.entity.RecordType;
import com.finance.dash_api.service.RecordService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public Record create(@RequestBody Record record,
                                  @RequestParam UUID userId) {
        return service.createRecord(record, userId);
    }

    @GetMapping
    public List<Record> getAll(
            @RequestParam(required = false) RecordType type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        LocalDate start = startDate != null ? LocalDate.parse(startDate) : null;
        LocalDate end = endDate != null ? LocalDate.parse(endDate) : null;

        return service.filterRecords(type, category, start, end);
    }

    @PutMapping("/{id}")
    public Record update(@PathVariable UUID id,
                                  @RequestBody Record record) {
        return service.updateRecord(id, record);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        service.deleteRecord(id);
        return "Record deleted";
    }
}