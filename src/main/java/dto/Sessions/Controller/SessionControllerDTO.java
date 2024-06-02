package dto.Sessions.Controller;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SessionControllerDTO(long idSession, long idUser, LocalDate dateStart, LocalDate dateEnd) {
}
