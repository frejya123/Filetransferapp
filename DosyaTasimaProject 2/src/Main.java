import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.Arrays;

public class Main extends JFrame implements ActionListener {
    JTextField sourceDirTextField;
    JTextField destDirTextField;
    JCheckBox allFilesCheckBox, txtCheckBox, pdfCheckBox, pngCheckBox;
    JRadioButton encryptRadioButton, compressRadioButton, hideRadioButton;
    JButton moveButton;

    public Main() {
        setTitle("FILE MOVER APP");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 1));

        JLabel sourceDirLabel = new JLabel("Source Directory:");
        sourceDirTextField = new JTextField();

        JLabel destDirLabel = new JLabel("Target Directory:");
        destDirTextField = new JTextField();

        allFilesCheckBox = new JCheckBox("All Files");
        txtCheckBox = new JCheckBox("Only txt");
        pdfCheckBox = new JCheckBox("Only pdf");
        pngCheckBox = new JCheckBox("Only png");

        encryptRadioButton = new JRadioButton("Encrypt");
        compressRadioButton = new JRadioButton("Compress");
        hideRadioButton = new JRadioButton("Hide");

        moveButton = new JButton("MOVE");
        moveButton.addActionListener(this);

        add(sourceDirLabel);
        add(sourceDirTextField);
        add(destDirLabel);
        add(destDirTextField);
        add(allFilesCheckBox);
        add(txtCheckBox);
        add(pdfCheckBox);
        add(pngCheckBox);
        add(encryptRadioButton);
        add(compressRadioButton);
        add(hideRadioButton);
        add(moveButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main main = new Main();
            main.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String sourceDir = sourceDirTextField.getText();
        String destDir = destDirTextField.getText();
        boolean isEncrypt = encryptRadioButton.isSelected();
        boolean isCompress = compressRadioButton.isSelected();
        boolean isHide = hideRadioButton.isSelected();

        File sourceDirectory = new File(sourceDir);
        File destDirectory = new File(destDir);

        if (!sourceDirectory.isDirectory() || !destDirectory.isDirectory()) {
            JOptionPane.showMessageDialog(this, "Wrong Path!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        FileFilter fileFilter = this::filterFiles;
        File[] filesToMove = sourceDirectory.listFiles(fileFilter);

        if (filesToMove == null || filesToMove.length == 0) {
            JOptionPane.showMessageDialog(this, "Could not found!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (File file : filesToMove) {
            try {
                String fileName = file.getName();
                //String fileExtension = getFileExtension(fileName);
                String encryptedFileName = isEncrypt ? encryptFileName(fileName) : fileName;
                String compressedFileName = isCompress ? compressFileName(encryptedFileName) : encryptedFileName;
                String hiddenFileName = isHide ? hideFileName(compressedFileName) : compressedFileName;

                File destFile = new File(destDir + File.separator + hiddenFileName);

                if (!destFile.exists()) {
                    Path sourcePath = file.toPath();
                    Path destPath = destFile.toPath();

                    Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
                    JOptionPane.showMessageDialog(this, fileName + " moved successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, fileName + " already exists in the target directory!", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, " An error occurred while moving files!", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    boolean filterFiles(File file) {
        if (allFilesCheckBox.isSelected()) {
            return true;
        }

        String fileName = file.getName();
        String fileExtension = getFileExtension(fileName);

        if (txtCheckBox.isSelected() && fileExtension.equals("txt")) {
            return true;
        }

        if (pdfCheckBox.isSelected() && fileExtension.equals("pdf")) {
            return true;
        }

        if (pngCheckBox.isSelected() && fileExtension.equals("png")) {
            return true;
        }

        return false;
    }

    String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        } else {
            return "";
        }
    }

    String encryptFileName(String fileName) {
        if (fileName.isEmpty())
            return "";
        else
        try {
            String secretKey = "secretKey";
            byte[] key = secretKey.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);

            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] encryptedBytes = cipher.doFinal(fileName.getBytes("UTF-8"));
            return bytesToHex(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    String hideFileName(String fileName) throws Exception {
        if (fileName == null || fileName.isEmpty())
            throw new IllegalArgumentException("File name cannot be null or empty.");
        return "." + fileName;
    }
    

    String compressFileName(String fileName) {
        // optional for now
        return fileName + ".zip";
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }
}
