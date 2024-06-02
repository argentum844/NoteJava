package entity;


import java.time.LocalDate;

public class Sessions {
    private long idSession;
    private long idUser;
    private LocalDate dateStart;
    private LocalDate dateEnd;

    Sessions(long idSession, long idUser, LocalDate dateStart, LocalDate dateEnd) {
        this.idSession = idSession;
        this.idUser = idUser;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public long getIdSession() {
        return idSession;
    }

    public void setIdSession(long idSession) {
        this.idSession = idSession;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public String toString() {
        return "Sessions{" +
                "idSession=" + idSession +
                ", idUser=" + idUser +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                '}';
    }
}
