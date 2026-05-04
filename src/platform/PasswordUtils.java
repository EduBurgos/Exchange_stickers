package platform;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Utility class for secure password hashing using PBKDF2WithHmacSHA256.
 * Passwords are stored as: "PBKDF2:iterations:saltBase64:hashBase64"
 */
public class PasswordUtils {

    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    /**
     * Hashes a plain-text password.
     * @param password the plain-text password
     * @return the stored hash string in format "PBKDF2:iterations:salt:hash"
     */
    public static String hashPassword(String password) {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        byte[] hash = pbkdf2(password.toCharArray(), salt);
        return "PBKDF2:" + ITERATIONS + ":"
                + Base64.getEncoder().encodeToString(salt) + ":"
                + Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Verifies a plain-text password against a stored hash.
     * @param password the plain-text password to verify
     * @param stored   the stored hash string
     * @return true if the password matches
     */
    public static boolean checkPassword(String password, String stored) {
        if (stored == null) return false;
        String[] parts = stored.split(":");
        if (parts.length != 4 || !parts[0].equals("PBKDF2")) return false;
        byte[] salt = Base64.getDecoder().decode(parts[2]);
        byte[] expectedHash = Base64.getDecoder().decode(parts[3]);
        byte[] actualHash = pbkdf2(password.toCharArray(), salt);
        return slowEquals(expectedHash, actualHash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            return factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /** Constant-time comparison to prevent timing attacks */
    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++) diff |= a[i] ^ b[i];
        return diff == 0;
    }
}
