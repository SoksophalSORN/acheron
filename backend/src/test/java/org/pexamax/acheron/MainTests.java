package org.pexamax.acheron;

// Change to specific package name during prod. 
// Not a good practice to use wildcard imports in production code.
// import org.pexamax.acheron.entity.*;
// import org.pexamax.acheron.repository.*;
// import org.pexamax.acheron.service.*;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = Main.class)
class MainTests {

	@Test
	void contextLoads() {
	}

}
