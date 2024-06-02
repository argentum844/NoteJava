package dto.Users.View;

import lombok.Builder;

@Builder
public record UserUpdateDescriptionViewDTO(long idUser, String login, String userName,
                                           String userSurname, String isAdmin, String birthdayDate){
}