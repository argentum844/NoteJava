package treats.hashed;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordHashed {
    private static final SecureRandom rnd = new SecureRandom();

    public String passwordHash(String password, String salt){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(salt.getBytes());
            byte[] passwordHashed = messageDigest.digest(password.getBytes());
            return bytesToHex(passwordHashed);
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("I fuck your algorithm ", e);
        }
    }
    private String bytesToHex(byte[] bytes){
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b:bytes){
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }
    public String generateSalt(){
        byte[] salt = new byte[30];
        rnd.nextBytes(salt);
        return bytesToHex(salt);
    }

}
