package GorkemKoc.reportingApplication.core.utilities.file;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class FileToBase64{

    public static void main(String[] args) {
        String filePath = "report.jpg";

        try {
            String base64String = encodeFileToBase64(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String encodeFileToBase64(String filePath) throws Exception {
        Path path = Paths.get(filePath);
        byte[] fileContent = Files.readAllBytes(path);

        return Base64.getEncoder().encodeToString(fileContent);
    }
}
