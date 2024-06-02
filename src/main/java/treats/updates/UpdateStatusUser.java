package treats.updates;

import lombok.Builder;

@Builder
public record UpdateStatusUser(boolean isNewLogin, boolean isNewUserName,
                               boolean isNewUserSurname, boolean isNewIsAdmin, boolean isNewBirthdayDate) {
}
