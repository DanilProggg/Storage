package com.kridan.storage.auth.adapter.in.http;

import lombok.Data;

@Data
public class CredentialsDto {
    private String login;
    private String password;
}
