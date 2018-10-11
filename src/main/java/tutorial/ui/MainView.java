package tutorial.ui;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tutorial.application.ImagesUtils;
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


@Route("")
public class MainView extends VerticalLayout{

    private Upload upload;
    private User user;
    private VerticalLayout imageContainer;
    private ComboBox<User> userComboBox;

    @Autowired
    private UserRepository userRepository;

    public MainView() {
        add(new H1("User view"));
        add(new H3("Profile picture"));

        userComboBox = new ComboBox<>();
        userComboBox.addValueChangeListener(event-> {
            User selectedUser = event.getValue();
            if ( selectedUser != null){
                user = selectedUser;
                showImage(user.getProfilePicture());
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
            byte[] imageBytes;
            try {
                imageBytes = IOUtils.toByteArray(buffer.getInputStream(attachmentName));
                showImage(imageBytes);
                saveProfilePicture(imageBytes);
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

    private void showImage(byte[] imageBytes) {
        Image image = ImagesUtils.generateImage(imageBytes);
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
