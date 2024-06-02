package dto.Users.View;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserForAdminViewDTO(long idUser, String login, String userName,
                                  String userSurname, String birthdayDate) {

}
