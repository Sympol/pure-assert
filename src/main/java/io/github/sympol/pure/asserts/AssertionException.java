package io.github.sympol.pure.asserts;

import java.util.Map;

/**
 * Base exception for all assertion failures in the pure-assert library.
 * Provides structured error information including field name, error type, and
 * parameters.
 */
public abstract class AssertionException extends RuntimeException {

    private final String field;

    /**
     * @param field   the name of the field that failed validation
     * @param message the error message
     */
    protected AssertionException(String field, String message) {
        super(message);
        this.field = field;
    }

    /**
     * @return the type of assertion error
     */
    public abstract AssertionErrorType type();

    /**
     * @return the name of the field that failed validation
     */
    public String field() {
        return field;
    }

    /**
     * @return additional parameters providing context about the error
     */
    public Map<String, String> parameters() {
        return Map.of();
    }
}