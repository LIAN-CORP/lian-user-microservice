package com.lian.marketing.usermicroservice.domain.mock;

import com.lian.marketing.usermicroservice.domain.model.User;

import java.time.LocalDate;
import java.util.UUID;

public class DomainMocks {
    private DomainMocks() {}

    public static User userMock(boolean isAdult) {
        LocalDate birthday = isAdult ? LocalDate.of(2004, 3, 18) : LocalDate.now();

        return new User(
                UUID.randomUUID(),
                "Erick",
                "Chaparro",
                "erick@gmail.com",
                "12345abc",
                birthday,
                false
        );
    }
}
