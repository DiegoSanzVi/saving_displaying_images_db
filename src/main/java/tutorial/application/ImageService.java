package tutorial.application;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tutorial.backend.entities.User;
import tutorial.backend.repositories.UserRepository;

@Service
public class ImageService {
    
    @Autowired
    UserRepository userRepository;
    
    public Image generateImage(User user) {
        Long id = user.getId();
        StreamResource sr = new StreamResource("user", () ->  {
            User attached = userRepository.findWithPropertyPictureAttachedById(id);
            return new ByteArrayInputStream(attached.getProfilePicture());
        });
        sr.setContentType("image/png");
        Image image = new Image(sr, "profile-picture");
        return image;

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

}
