package entity.enums;

public class AdminType {
    public static AdminType ISADMIN = new AdminType(true);
    public static AdminType NOTADMIN = new AdminType(false);
    private final boolean type;
    AdminType(boolean type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AdminType{" +
                "type=" + type +
                '}';
    }
}

