package com.kridan.storage.auth.application.port.out;

public interface PasswordEncoder {
    String hashPassword(String password);
    boolean checkPassword(String password, String hashedPassword);
}
