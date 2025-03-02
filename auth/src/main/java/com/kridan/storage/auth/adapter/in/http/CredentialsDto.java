package com.kridan.storage.auth.adapter.in.http;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "User`s Credentials")
public class CredentialsDto {
    @Schema(description = "Уникальное имя пользователя", example = "ivan-zolo")
    private String login;
    @Schema(description = "Пароль пользователя", example = "ivan-pw")
    private String password;
}
