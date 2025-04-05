package com.example.server.api.server.enumaration;

import lombok.Getter;

@Getter
public enum Status {
    UP("SERVER_UP"),
    DOWN("SERVER_DOWN");

    private final String status;

    Status(String status) {
        this.status = status;
    }
}
