package dto.Users.Controller;

import lombok.Builder;

@Builder
public record UserLogoPassControllerDTO(String login, String oldPass, String newPass) {
}
