package dto.Users.View;

import lombok.Builder;

import java.util.Objects;

@Builder
public record UserInsertViewDTO(long idUser, String login, String userName, String userPassword,
                                String userSurname, String isAdmin, String birthdayDate) {

}
