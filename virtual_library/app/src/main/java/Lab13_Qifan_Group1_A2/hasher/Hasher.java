package Lab13_Qifan_Group1_A2.hasher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    private static final String DIGEST_ALGORITHM = "MD5";
    private static final String HEX_FORMAT = "%02x";

    public static String getHexString(byte[] hashedPassword) {
        StringBuilder builder = new StringBuilder();

        for (Byte b : hashedPassword) {
            builder.append(String.format(HEX_FORMAT, b));
        }

        return builder.toString();
    }

    public static String createHash(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(DIGEST_ALGORITHM);
            messageDigest.update(password.getBytes());
            return getHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean compareHash(String hash1, String hash2) {
        return hash1.equals(hash2);
    }
}
