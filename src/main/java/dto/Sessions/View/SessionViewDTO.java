package dto.Sessions.View;

import lombok.Builder;

@Builder
public record SessionViewDTO(long idSession, long idUser, String dateStart, String dateEnd) {
}
