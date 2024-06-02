package dto.Users.Controller;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Date;

@Builder
public record UsersControllerDTO(long idUser, String login, String userName,
                                 String userSurname, boolean isAdmin, LocalDate birthdayDate) {
}
