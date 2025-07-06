/**
 * Utility class for handling file upload operations in the application.
 * This class provides methods to manage file uploads and determines the correct
 * upload directory dynamically based on the application's runtime location.
 * 
 * The upload location is set to a directory named 'images' in the project's root directory.
 * The path is determined at runtime to ensure portability across different environments.
 * 
 * @author Dinh Cong Phuc - CE190770
 */
package utils;

import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileIOUtil {
    private static final String PROJECT_ROOT_PATH;
    
    // Static initializer block to set up the UPLOAD_LOCATION
    static {
        // Get the path to the directory containing the compiled class files
        // 1. Get the ProtectionDomain of this class
        // 2. Get the CodeSource from the ProtectionDomain
        // 3. Get the URL of the CodeSource (where the class was loaded from)
        // 4. Convert the URL to a filesystem path
        String classPath = FileIOUtil.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath();
                
        // Navigate up the directory structure to reach the project root
        // The number of getParentFile() calls depends on your project structure:
        // - From target/classes (where compiled classes are)
        // - Up to target
        // - Up to project root (AE2CGroceryStore)
        // - getParent() converts to String and removes the trailing slash
        String rootPath = new File(classPath)
                .getParentFile()  // 1. target/classes
                .getParentFile()  // 2. target
                .getParentFile()  // 3. AE2CGroceryStore (project root)
                .getParent();     // 4. Convert to String and clean path
                
        // Set the upload location to the project root (Note: Windows already append trailing \ to path)
        PROJECT_ROOT_PATH = rootPath + File.separator;
    }
    
    /**
     * Returns the absolute path to the upload directory.
     * This is a convenience method that returns the pre-calculated UPLOAD_LOCATION.
     * 
     * @return String representing the absolute path to the upload directory
     */
    public static String getRootPath() {
        return PROJECT_ROOT_PATH;
    }

    /**
     * Saves an uploaded file to the specified output location.
     * This method handles the file transfer from the uploaded part to the target file.
     * 
     * @param input  The Part object representing the uploaded file
     * @param output The target File where the uploaded content should be saved
     * @throws IOException If an I/O error occurs during file transfer
     */
    public static void fileUploader(Part input, File output) throws IOException {
        // Using try-with-resources to ensure proper resource cleanup
        // The OutputStream and InputStream will be automatically closed when done
        try ( OutputStream os = new FileOutputStream(output);
                InputStream is = input.getInputStream()) {

            byte[] buffer = new byte[1024];
            int byteRead;
            while ((byteRead = is.read(buffer)) > 0) {
                os.write(buffer, 0, byteRead);
            }
        }
    }
            
}
