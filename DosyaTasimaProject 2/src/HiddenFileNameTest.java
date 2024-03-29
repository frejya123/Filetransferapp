import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HiddenFileNameTest { //19th
    private Main main = new Main();

    @Test
    public void testHiddenFileNameBeginsWithDot() throws Exception {
        main.hideRadioButton.setSelected(true);
        String hiddenFileName = main.hideFileName("testfile");
        assertEquals('.', hiddenFileName.charAt(0));
    }
}