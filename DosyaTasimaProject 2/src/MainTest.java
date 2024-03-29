import static org.junit.Assert.*;
import java.io.File;

import org.junit.*;

public class MainTest { // True, False, Equals, NotNull, NotEquals
    private Main main;

    @Before
    public void setUp() {
        main = new Main();
    }

    // Assertion 1: Pdf validity
    @Test
    public void testPdfFileFilter() {
        main.pdfCheckBox.setSelected(true); 
        assertTrue("Should allow PDF files", main.filterFiles(new File("document.pdf")));
        assertFalse("Should not allow non-PDF files", main.filterFiles(new File("image.jpg")));
    }

    // Assertion 2: Check if all files filter works
    @Test
    public void testAllFilesFilter() {
        main.allFilesCheckBox.setSelected(true);
        assertTrue(main.filterFiles(new File("test.txt")));
    }

    // Assertion 3: Check if txt file filter works
    @Test
    public void testTxtFileFilter() {
        main.txtCheckBox.setSelected(true);
        assertTrue(main.filterFiles(new File("test.txt")));
    }

    // Assertion 4: Extension extraction correctness
    @Test
    public void testFileExtensionExtraction() {
        String extension = main.getFileExtension("test.txt");
        assertEquals("txt", extension);
    }

    // Assertion 5: Encryption of file name
    @Test
    public void testEncryptFileName() {
        String encryptedName = main.encryptFileName("test");
        assertNotNull(encryptedName);
        assertFalse(encryptedName.isEmpty());
    }

    // Assertion 6: Regex check
    @Test
    public void testFileNameRegexMatch() {
        String encryptedName = main.encryptFileName("testfile");
        String regex = "^[0-9A-Fa-f]+$";
        assertTrue(encryptedName.matches(regex));
    }

    // Assertion 7 : No file extensions test
    @Test
    public void testNoFileFilterSelected() {
        main.allFilesCheckBox.setSelected(false);
        main.txtCheckBox.setSelected(false);
        main.pdfCheckBox.setSelected(false);
        main.pngCheckBox.setSelected(false);
        assertFalse("Should not allow files when no filter is selected", main.filterFiles(new File("random.file")));
    }

    // Assertion 8: Is empty string
    @Test
    public void testReturnIsEmptyString() {
        String encryptedName = main.encryptFileName("");
        assertTrue(encryptedName.isEmpty());
    }

    // Assertion 9: File name after encryption
    @Test
    public void testEncryptedFileNameIsNotEquals() {
        String encryptedName = main.encryptFileName("testfile");
        assertNotEquals("testfile", encryptedName);
    }

    // Assertion 10: File name after hiding
    @Test
    public void testHiddenFileNameBeginsWithDot() throws Exception {
        main.hideRadioButton.setSelected(true);
        String hiddenFileName = main.hideFileName("testfile");
        assertEquals('.', hiddenFileName.charAt(0));
    }
}
@Test
public void testEncryptedFileNameNotNull() {
    String encryptedName = main.encryptFileName("testfile");
    assertNotNull(encryptedName);
}
@Test
public void testEncryptedFileNameLength() {
    String encryptedName = main.encryptFileName("testfile");
    assertEquals("Length of encrypted file name should be same as original", "testfile".length(), encryptedName.length());
}
@Test
public void testEncryptedFileNameNotEqualsOriginal() {
    String encryptedName = main.encryptFileName("testfile");
    assertNotEquals("Encrypted file name should not be same as original", "testfile", encryptedName);
}
@Test
public void testHiddenFileNameNotNull() {
    main.hideRadioButton.setSelected(true);
    String hiddenFileName = main.hideFileName("testfile");
    assertNotNull(hiddenFileName);
}
@Test
public void testHiddenFileNameStartsWithOriginal() {
    main.hideRadioButton.setSelected(true);
    String hiddenFileName = main.hideFileName("testfile");
    assertTrue("Hidden file name should start with original", hiddenFileName.startsWith("testfile"));
}
@Test
public void testHiddenFileNameNotEqualsOriginal() {
    main.hideRadioButton.setSelected(true);
    String hiddenFileName = main.hideFileName("testfile");
    assertNotEquals("Hidden file name should not be same as original", "testfile", hiddenFileName);
}
@Test
public void testFileExtensionExtractionNotNull() {
    String extension = main.getFileExtension("test.txt");
    assertNotNull(extension);
}
@Test
public void testSameInputSameOutputForEncryption() {
    String input = "testfile";
    String encryptedName1 = main.encryptFileName(input);
    String encryptedName2 = main.encryptFileName(input);
    assertEquals("Encryption should produce same output for same input", encryptedName1, encryptedName2);
}
@Test
public void testHiddenFileNameNotEqualsOriginal() {
    main.hideRadioButton.setSelected(true);
    String hiddenFileName = main.hideFileName("testfile");
    assertNotEquals("Hidden file name should not be same as original", "testfile", hiddenFileName);
}
@Test
public void testFileExtensionExtraction() {
    String extension = main.getFileExtension("test.txt");
    assertEquals("txt", extension);
}
