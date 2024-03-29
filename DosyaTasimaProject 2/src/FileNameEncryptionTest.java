import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class FileNameEncryptionTest { //9th
    private Main main = new Main();

    @Test
    public void testEncryptFileName() {
        String encryptedName = main.encryptFileName("test");
        assertNotNull(encryptedName);
        assertFalse(encryptedName.isEmpty());
    }
}