package io.github.sympol.pure.asserts;

/**
 * Exception thrown when a mandatory value is missing, null, blank, or empty.
 */
public class MissingMandatoryValueException extends AssertionException {

    private MissingMandatoryValueException(final String field, final String message) {
        super(field, message);
    }

    /**
     * Creates an exception for a blank value.
     * 
     * @param field name of the field
     * @return a new MissingMandatoryValueException
     */
    public static MissingMandatoryValueException forBlankValue(String field) {
        return new MissingMandatoryValueException(field, defaultMessage(field, "blank"));
    }

    /**
     * Creates an exception for a null value.
     * 
     * @param field name of the field
     * @return a new MissingMandatoryValueException
     */
    public static MissingMandatoryValueException forNullValue(String field) {
        return new MissingMandatoryValueException(field, defaultMessage(field, "null"));
    }

    /**
     * Creates an exception for an empty value.
     * 
     * @param field name of the field
     * @return a new MissingMandatoryValueException
     */
    public static MissingMandatoryValueException forEmptyValue(String field) {
        return new MissingMandatoryValueException(field, defaultMessage(field, "empty"));
    }

    /**
     * Creates an exception for a bad value with a custom message.
     * 
     * @param field   name of the field
     * @param message custom error message
     * @return a new MissingMandatoryValueException
     */
    public static MissingMandatoryValueException forBadValue(String field, String message) {
        return new MissingMandatoryValueException(field, message);
    }

    private static String defaultMessage(String field, String reason) {
        return new StringBuilder()
                .append("The field \"")
                .append(field)
                .append("\" is mandatory and wasn't set")
                .append(" (")
                .append(reason)
                .append(")")
                .toString();
    }

    @Override
    public AssertionErrorType type() {
        return AssertionErrorType.MISSING_MANDATORY_VALUE;
    }
}
