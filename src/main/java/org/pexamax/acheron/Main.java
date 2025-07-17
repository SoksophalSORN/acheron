package org.pexamax.acheron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
        // if user is logged in
        //     retrieve user data from database
        //     return user object
        // If not, prompt for username, fetch user
        //     If user does not exist, sign em up
        //         prompt for email
        //              if email exists in database
        //                  prompt user to input a new one
        //         prompt for password 
        //         register user into db
        //         return user object
        //     else
        //         prompt for password
        //         User user = User.login(username, password)
        //         if login successful, return user
        //         else
        //            prompt for password again, maximum 3 times
        //            if 3 times already, exit program
        // Display app menu
		SpringApplication.run(Main.class, args);
	}

}
