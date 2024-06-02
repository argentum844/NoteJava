package entity;

public class Rules {
    private long idRules;
    private String textRules;

    Rules(long idRules, String textRules) {
        this.idRules = idRules;
        this.textRules = textRules;
    }

    public long getIdRules() {
        return idRules;
    }

    public void setIdRules(long idRules) {
        this.idRules = idRules;
    }

    public String getTextRules() {
        return textRules;
    }

    public void setTextRules(String textRules) {
        this.textRules = textRules;
    }

    @Override
    public String toString() {
        return "Rules{" +
                "idRules=" + idRules +
                ", textRules='" + textRules + '\'' +
                '}';
    }
}
