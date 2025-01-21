package com.task.library.management.GlobalException;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
	private final Map<String, Object> additionalDetails;
}
