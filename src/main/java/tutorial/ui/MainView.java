package tutorial.ui;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tutorial.application.ImageService;
import tutorial.backend.entities.User;
import tutorial.backend.repositories.UserRepository;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.Route;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;


@Route
public class MainView extends VerticalLayout{

    private Upload upload;
    private User user;
    private VerticalLayout imageContainer;
    private ComboBox<User> userComboBox;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageService imageService;

    public MainView() {
        add(new H1("User view"));
        add(new H3("Profile picture"));

        userComboBox = new ComboBox<>();
        userComboBox.addValueChangeListener(event-> {
            User selectedUser = event.getValue();
            if ( selectedUser != null){
                user = selectedUser;
                showImage();
            }
        });
        add(userComboBox);

        initImageContainer();
    }

    @PostConstruct
    private void init() {
        List<User> users = userRepository.findAll();

        userComboBox.setItems(users);

        if ( !users.isEmpty()){
            userComboBox.setValue(users.get(0));
        }
        initUploaderImage();
    }

    private void initUploaderImage() {
        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg","image/jpg", "image/png", "image/gif");

        upload.addSucceededListener(event -> {
            String attachmentName = event.getFileName();
            try {
                // The image can be jpg png or gif, but we store it always as png file in this example
                BufferedImage inputImage = ImageIO.read(buffer.getInputStream(attachmentName));
                ByteArrayOutputStream pngContent = new ByteArrayOutputStream();
                ImageIO.write(inputImage, "png", pngContent);
                saveProfilePicture(pngContent.toByteArray());
                showImage();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        add(upload);
    }

    private void saveProfilePicture(byte[] imageBytes) {
        user.setProfilePicture(imageBytes);
        user = userRepository.save(user);
    }

    private void showImage() {
        Image image = imageService.generateImage(user);
        image.setHeight("100%");
        imageContainer.removeAll();
        imageContainer.add(image);
    }

    private void initImageContainer(){
        imageContainer = new VerticalLayout();
        imageContainer.setWidth("200px");
        imageContainer.setHeight("200px");
        imageContainer.getStyle().set("overflow-x", "auto");
        add(imageContainer);
    }
}
