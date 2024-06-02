package entity.enums;

public class PublicType {
    public static PublicType ISPUBLIC = new PublicType(true);
    public static PublicType NOTPUBLIC = new PublicType(false);
    private final boolean type;
    PublicType(boolean type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PublicType{" +
                "type=" + type +
                '}';
    }
}
