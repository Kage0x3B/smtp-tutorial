package de.syscy.smtptutorial.smtp;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
public class SMTPResponse {
	private final StatusCode statusCode;
	private final String[] responseLines;

	private SMTPResponse(StatusCode statusCode, String... responseLines) {
		this.statusCode = statusCode;

		if(responseLines == null || responseLines.length < 1) {
			responseLines = new String[] { statusCode.getDefaultMessage() };
		}

		this.responseLines = responseLines;
	}

	public List<String> toResponseList() {
		List<String> responseLineList = new LinkedList<>();

		for(int i = 0; i < responseLines.length; i++) {
			responseLineList.add(statusCode.getCode() + (i == responseLines.length - 1 ? " " : "-") + responseLines[i]);
		}

		return responseLineList;
	}

	public static SMTPResponse create(StatusCode statusCode, String... responseLines) {
		return new SMTPResponse(statusCode, responseLines);
	}

	public static SMTPResponse ok(String... responseLines) {
		return new SMTPResponse(StatusCode.STATUS_250, responseLines);
	}

	public static SMTPResponse commandUnrecognizedError(String... responseLines) {
		return new SMTPResponse(StatusCode.STATUS_500, responseLines);
	}

	public static SMTPResponse parameterSyntaxError(String... responseLines) {
		return new SMTPResponse(StatusCode.STATUS_501, responseLines);
	}

	public static SMTPResponse notImplemented(String... responseLines) {
		return new SMTPResponse(StatusCode.STATUS_502, responseLines);
	}

	public static SMTPResponse notImplementedParameter(String... responseLines) {
		return new SMTPResponse(StatusCode.STATUS_504, responseLines);
	}

	public static SMTPResponse notAuthenticated() {
		return new SMTPResponse(StatusCode.STATUS_530, "User not authenticated");
	}

	public static SMTPResponse invalidState(String... responseLines) {
		return new SMTPResponse(StatusCode.STATUS_503, responseLines);
	}
}