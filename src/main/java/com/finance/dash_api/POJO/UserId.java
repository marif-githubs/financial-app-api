package com.finance.dash_api.POJO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserId {

    private UUID userId;

    public UserId(UUID userId) {
        this.userId = userId;
    }
}
