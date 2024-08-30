package dev.thomasrba.um.entity;

import lombok.*;

@Getter
@Setter
@Builder
public class newPasswordRequest {
    public String password;
    public String newPassword;
    public String confirmNewPassword;
}
