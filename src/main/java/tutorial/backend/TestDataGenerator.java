package tutorial.backend;

import java.io.IOException;

import org.jfairy.Fairy;
import org.jfairy.producer.person.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tutorial.application.ImagesUtils;
import tutorial.backend.entities.User;
import tutorial.backend.repositories.UserRepository;

@Component
public class TestDataGenerator {
	private static Logger logger = LoggerFactory.getLogger(TestDataGenerator.class);

	private final UserRepository userRepository;
	private final Fairy fairy = Fairy.create();

	public final String USERS_IMAGES_PATH = "src/main/resources/images/";
	public final String[] imgNames = {"user.png","user1.png","user2.png","user3.png"};
	@Autowired
	public TestDataGenerator(UserRepository userRepository) {
		this.userRepository = userRepository;
		createUsers(20);
	}


	private void createUsers(int n){
		logger.info("Creating users");

		for ( int i = 0; i < n; i++) {
			Person person = fairy.person();
			User user = new User();
			user.setName(person.firstName());
			user.setSurname(person.lastName());

			try {
				String imagePath = USERS_IMAGES_PATH + imgNames[i%imgNames.length];
				user.setProfilePicture(ImagesUtils.getBytesFromFile(imagePath));
			} catch (IOException e) {
				logger.error("It was not possible to set the image of the user!");
			}
			user.setEmail(person.email());
			userRepository.save(user);
		}
	}
}
