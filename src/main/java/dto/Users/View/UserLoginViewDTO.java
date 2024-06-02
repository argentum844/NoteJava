package dto.Users.View;

import lombok.Builder;

@Builder
public record UserLoginViewDTO(String login, String password) {
}
