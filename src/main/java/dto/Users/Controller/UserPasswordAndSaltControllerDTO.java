package dto.Users.Controller;

import lombok.Builder;

@Builder
public record UserPasswordAndSaltControllerDTO(String userPassword, String salt) {
}
