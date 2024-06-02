package dto.Users.Controller;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserInsertControllerDTO(String login, String userPassword, String userName,
                                      String userSurname, boolean isAdmin, LocalDate birthdayDate) {
}
