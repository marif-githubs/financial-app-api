package com.finance.dash_api.POJO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RecordId {

    private final UUID userId;

    private final UUID recordId;

    public RecordId(UUID userId, UUID recordId) {
        this.userId = userId;
        this.recordId = recordId;
    }
}
