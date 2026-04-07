package com.finance.dash_api.config;

import com.finance.dash_api.entity.*;
import com.finance.dash_api.entity.Record;
import com.finance.dash_api.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(UserRepository userRepo, RecordRepository recordRepo) {
        return args -> {

            if (userRepo.count() == 0) {

                User admin = new User("Admin", "admin@test.com", "1234@Admin", UserRole.ADMIN, true);
                User analyst = new User("Analyst", "analyst@test.com", "1234@Analyst", UserRole.ANALYST, true);
                User viewer = new User("viewer", "viewer@test.com", "1234@Viewer", UserRole.VIEWER, true);

                userRepo.save(admin);
                userRepo.save(analyst);
                userRepo.save(viewer);

                Record r1 = new Record(5000.0, RecordType.INCOME, "Salary", "Monthly salary", admin);
                Record r2 = new Record(2000.0, RecordType.EXPENSE, "Food", "Groceries", admin);
                Record r3 = new Record(7000.0, RecordType.EXPENSE, "Travel", "Travel Expense", admin);

                recordRepo.save(r1);
                recordRepo.save(r2);
                recordRepo.save(r3);
            }
        };
    }
}