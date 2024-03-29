import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class FileExtensionParameterizedTest {

    private String fileName;
    private String expectedExtension;

    public FileExtensionParameterizedTest(String fileName, String expectedExtension) {
        this.fileName = fileName;
        this.expectedExtension = expectedExtension;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][] {
            {"test.txt", "txt"},
            {"archive.zip", "zip"},
            {"image.jpeg", "jpeg"},
            {"no_extension", ""},
            {"double.extension.pdf", "pdf"}
        });
    }

    @Test
    public void testGetFileExtension() {
        Main main = new Main();
        assertEquals(expectedExtension, main.getFileExtension(fileName));
    }
}
