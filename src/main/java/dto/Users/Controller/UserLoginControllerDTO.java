package dto.Users.Controller;

import lombok.Builder;

@Builder
public record UserLoginControllerDTO(String login, String password) {
}
