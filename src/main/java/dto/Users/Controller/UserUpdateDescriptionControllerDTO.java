package dto.Users.Controller;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserUpdateDescriptionControllerDTO(long idUser, String login, String userName,
                                                 String userSurname, boolean isAdmin, LocalDate birthdayDate){
}
