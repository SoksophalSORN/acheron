package org.pexamax.acheron;

import org.pexamax.acheron.entity.User; // For User entity
import org.pexamax.acheron.repository.UserRepository; // For UserRepository

import org.springframework.beans.factory.annotation.Autowired; // For @Autowired annotation
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest; // For @DataJpaTest annotation
import static org.assertj.core.api.Assertions.assertThat; // For assertions

import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest; // Uncomment if you want to use @SpringBootTest for integration tests

@DataJpaTest // loads only JPA-related components and configures H2 automatically
// @SpringBootTest(classes = Main.class) // Integration/Smoke test
class MainTests {

    @Autowired
    private UserRepository userRepo;

	@Test
	void contextLoads() {
	}

    // Testing if the UserRepository is working correctly with H2 database
    void saveAndFindUserMail() {
        User user = new User();
        user.setEmail("nevergonnagiveyouup@gmail.com");
        userRepo.save(user);

        assertThat(userRepo.findByEmail("nevergonnagiveyouup@gmail.com")).isPresent();
    }

}
