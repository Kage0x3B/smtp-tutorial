package de.syscy.smtptutorial.util;

import lombok.experimental.UtilityClass;

import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class PasswordUtil {
	private static final int PASSWORD_LENGTH = 6;
	private static final int CHARACTER_LIMIT_LEFT = 97;
	private static final int CHARACTER_LIMIT_RIGHT = 122;

	public static String generatePassword() {
		StringBuilder buffer = new StringBuilder(PASSWORD_LENGTH);

		for(int i = 0; i < PASSWORD_LENGTH; i++) {
			int randomCharacterCode = ThreadLocalRandom.current().nextInt(CHARACTER_LIMIT_LEFT, CHARACTER_LIMIT_RIGHT + 1);
			buffer.append((char) randomCharacterCode);
		}

		return buffer.toString();
	}
}