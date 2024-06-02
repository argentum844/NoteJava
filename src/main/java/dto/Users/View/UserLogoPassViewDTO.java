package dto.Users.View;

import lombok.Builder;

@Builder
public record UserLogoPassViewDTO(String login, String oldPass, String newPass) {
}
