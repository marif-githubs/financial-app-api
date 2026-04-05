package com.finance.dash_api.DTO;

import com.finance.dash_api.entity.RecordType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class RecordDTO {

    private UUID id;

    @NotNull
    @Min(value = 1)
    private Double amount;

    private RecordType type;

    @Size(max = 12)
    private String category;

    private LocalDateTime creationDate;

    @Size(max = 30)
    private String notes;

    private UUID userId;

    private String userName;

}