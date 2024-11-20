package com.akhil.uber_backend.uber_ride;

import com.akhil.uber_backend.uber_ride.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UberRideApplicationTests {

	@Autowired
	private EmailSenderService emailSenderService;


	@Test
	void contextLoads() {
		emailSenderService.sendEmail(
				"rajanakhil07@gmail.com",
				"Test Email",
				"Hi! This is testing email"
		);

		String[] batchEmail = {
				"rajanakhil07@gmail.com",
				"me.priyapd@gmail.com"
		};

		emailSenderService.sendEmail(batchEmail, "Test Email", "Hi this is testing email");
	}

}