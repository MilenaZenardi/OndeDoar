package com.ondedoar.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class ValidationUtils {
    public static List<String> getErrorMessages(BindingResult bindingResult) {
        List<String> errorMessages = new ArrayList<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            String errorMessage = error.getDefaultMessage();
            errorMessages.add(removeSquareBrackets(errorMessage));
        }
        return errorMessages;
    }

    private static String removeSquareBrackets(String message) {
        if (message != null && message.startsWith("[") && message.endsWith("]")) {
            return message.substring(1, message.length() - 1);
        }
        return message;
    }
}