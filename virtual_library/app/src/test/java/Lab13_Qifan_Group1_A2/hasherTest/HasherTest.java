package Lab13_Qifan_Group1_A2.hasherTest;

import Lab13_Qifan_Group1_A2.hasher.Hasher;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import static org.junit.Assert.*;

public class HasherTest {
    private final String DIGEST_ALGORITHM = "MD5";
    private final String password = "1234";

    @Test
    public void testGetHexString() throws NoSuchAlgorithmException {
        MessageDigest messageDigestFirst = MessageDigest.getInstance(DIGEST_ALGORITHM);
        messageDigestFirst.update(password.getBytes());
        byte[] outputFirst = messageDigestFirst.digest();
        String stringFirst = Hasher.getHexString(outputFirst);

        MessageDigest messageDigestSecond = MessageDigest.getInstance(DIGEST_ALGORITHM);
        messageDigestSecond.update(password.getBytes());
        byte[] outputSecond = messageDigestSecond.digest();
        String stringSecond = Hasher.getHexString(outputSecond);

        assertEquals(stringFirst, stringSecond);
    }

    @Test
    public void testCreateHash() {
        String firstHash = Hasher.createHash(password);
        String secondHash = Hasher.createHash(password);

        assertNotNull(firstHash);
        assertNotNull(secondHash);
        assertEquals(firstHash, secondHash);

        String differentHash = Hasher.createHash("54trtrsfsfsd");
        assertNotNull(differentHash);
        assertNotEquals(firstHash, differentHash);
        assertNotEquals(secondHash, differentHash);
    }

    @Test
    public void testCompareHash() {
        String firstHash = Hasher.createHash(password);
        String secondHash = Hasher.createHash(password);

        assertNotNull(firstHash);
        assertNotNull(secondHash);
        assertTrue(Hasher.compareHash(firstHash, secondHash));

        String differentHash = Hasher.createHash("54trtrsfsfsd");
        assertNotNull(differentHash);
        assertFalse(Hasher.compareHash(firstHash, differentHash));
        assertFalse(Hasher.compareHash(secondHash, differentHash));
    }
}
