package dto.Users.Controller;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserForAdminControllerDTO(long idUser, String login, String userName,
                                        String userSurname, LocalDate birthdayDate) {

}
