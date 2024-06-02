package dto.Users.Controller;

import lombok.Builder;

@Builder
public record UserConstFieldsControllerDTO(long idUser, boolean isAdmin) {
}
