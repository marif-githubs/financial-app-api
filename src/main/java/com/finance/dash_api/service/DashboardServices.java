package com.finance.dash_api.service;

import com.finance.dash_api.entity.RecordType;
import com.finance.dash_api.repository.RecordRepository;
import com.finance.dash_api.DTO.DashboardSummary;
import org.springframework.stereotype.Service;

@Service
public class DashboardServices {

    private final RecordRepository recordRepo;

    public DashboardServices(RecordRepository recordRepo) {
        this.recordRepo = recordRepo;
    }

    public DashboardSummary getSummary() {
        Double income = recordRepo.getTotalByType(RecordType.INCOME);;
        Double expense = recordRepo.getTotalByType(RecordType.EXPENSE);;

        return new DashboardSummary(
                income,
                expense,
                income - expense
        );
    }
}
