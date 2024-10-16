package com.service.documentation.exception;

/**
 * Custom exception class `ApiException` that extends `RuntimeException`.
 * This class represents an exception with an associated error code.
 */
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 4829382930029382039L;  // Unique identifier for serialization

    private int errorCode;  // The error code associated with the exception

    /**
     * Constructor for creating an instance of `ApiException`.
     *
     * @param errorCode The error code associated with the exception
     * @param message   The error message
     */
    public ApiException(int errorCode, String message) {
        super(message);  // Call the superclass constructor to set the error message
        this.errorCode = errorCode;  // Set the error code
    }

    /**
     * Constructor for creating an instance of `ApiException` with a cause.
     *
     * @param errorCode The error code associated with the exception
     * @param message   The error message
     * @param cause     The underlying cause of the exception
     */
    public ApiException(int errorCode, String message, Throwable cause) {
        super(message, cause);  // Call the superclass constructor with an additional cause parameter
        this.errorCode = errorCode;  // Set the error code
    }

    /**
     * Get the error code associated with the exception.
     *
     * @return The error code
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Convert the `ApiException` instance to a JSON-like string representation.
     *
     * @return A JSON-like string representation of the exception
     */
    @Override
    public String toString() {
        return "{\"errorCode\": " + errorCode + ", \"message\": \"" + getMessage() + "\" }";
    }
}
