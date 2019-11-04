package de.syscy.smtptutorial.smtp;

import de.syscy.smtptutorial.TutorialBackend;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StatusCode {
	STATUS_200(200),
	STATUS_211(211),
	STATUS_220(220, TutorialBackend.SERVER_DOMAIN_NAME + " Service ready"),
	STATUS_221(221, TutorialBackend.SERVER_DOMAIN_NAME + " Service closing transmission channel"),
	STATUS_250(250, "OK"),
	STATUS_334(334, "Start auth input"),
	STATUS_335(335, "Authentication succeeded"),
	STATUS_354(354, "Start mail input; end with <CRLF>.<CRLF>"),
	STATUS_421(421, TutorialBackend.SERVER_DOMAIN_NAME + " Service not available, closing transmission channel"),
	STATUS_450(450, "Action not taken, mailbox unavailable"),
	STATUS_451(451, "Action aborted, local error in processing"),
	STATUS_452(452, "Action not taken, insufficient system storage"),
	STATUS_500(500, "Syntax error, unrecognized command"),
	STATUS_501(501, "Syntax error in parameters"),
	STATUS_502(502, "Command not implemented"),
	STATUS_503(503, "Bad sequence of commands"),
	STATUS_504(504, "Command parameter not implemented"),
	STATUS_521(521, TutorialBackend.SERVER_DOMAIN_NAME + " does not accept mail"),
	STATUS_530(530, "Access denied"),
	STATUS_535(535, "SMTP authentication unsuccessful"),
	STATUS_550(550, "Action not taken, mailbox unavailable"),
	STATUS_551(551, "User not local"),
	STATUS_552(552, "Action aborted, exceeded storage allocation"),
	STATUS_553(553, "Action not taken, mailbox name not allowed"),
	STATUS_554(554, "Transaction failed");

	private final @Getter int code;
	private final @Getter String defaultMessage;

	StatusCode(int code) {
		this(code, "");
	}
}