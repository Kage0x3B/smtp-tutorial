package de.syscy.smtptutorial.smtp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SessionState {
	INITIAL("initial"),
	GREETING("greeting"),
	AUTH_USERNAME("authentication username"),
	AUTH_PASSWORD("authentication password"),
	FROM("sender/from"),
	RCPT("receivers/rcpt"),
	DATA("data");

	private final @Getter String name;
}