package tutorial.application;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;

public class ImagesUtils {

    /**
     * Generate Image element out of file path
     *
     * @param imagePath
     *     relative path to an image file
     * @return Image element based on the resource found from the path
     * @throws IOException
     *     File not found or could not not be read
     */
    public static Image generateImage(String imagePath) throws IOException {
        return generateImage(getBytesFromFile(imagePath));
    }

    /**
     * Read file into byte array
     *
     * @param imagePath
     *     path to a file
     * @return byte array out of file
     * @throws IOException
     *     File not found or could not be read
     */
    public static byte[] getBytesFromFile(String imagePath) throws IOException {
        File file = new File(imagePath);
        return Files.readAllBytes(file.toPath());
    }

    /**
     * Generate an Image element out of a byte array
     *
     * @param bytes
     *     Byte array of an image
     * @return Image element from bytes
     */
    public static Image generateImage(byte[] bytes) {
        Objects.requireNonNull(bytes);
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        StreamResource sr = new StreamResource("user", () -> bis);
        Image image = new Image(sr, "profile-picture");
        return image;
    }
}
