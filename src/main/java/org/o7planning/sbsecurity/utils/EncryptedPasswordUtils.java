package org.o7planning.sbsecurity.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptedPasswordUtils {

	// Encrypt Password with BCryptPasswordEncoder
	private static String encryptedPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

	public static void main(String[] args) {
		String password = "123";
		String encryptedPassword = encryptedPassword(password);

		System.out.println("Encrypted Password: " + encryptedPassword);
	}

}