package io.github.sympol.pure.asserts;

/**
 * Exception levée lorsqu'une valeur requise est null ou invalide.
 */
public class RequiredValueException extends AssertionException {

    private final Object invalidValue;

    /**
     * @param valueName    Name of the field that failed validation.
     * @param invalidValue The value that was found invalid.
     * @param message      Descriptive error message.
     */
    public RequiredValueException(String valueName, Object invalidValue, String message) {
        super(valueName, message);
        this.invalidValue = invalidValue;
    }

    /**
     * @return The name of the value that was required.
     */
    public String getValueName() {
        return field();
    }

    /**
     * @return The actual invalid value encountered.
     */
    public Object getInvalidValue() {
        return invalidValue;
    }

    @Override
    public AssertionErrorType type() {
        return AssertionErrorType.MISSING_MANDATORY_VALUE;
    }

    /**
     * Creates a new exception for a null value.
     * 
     * @param valueName Name of the field.
     * @return A new RequiredValueException.
     */
    public static RequiredValueException forNullValue(String valueName) {
        return new RequiredValueException(
                valueName, null,
                String.format("The required value '%s' cannot be null.", valueName));
    }

    /**
     * Creates a new exception for an empty value.
     * 
     * @param valueName Name of the field.
     * @return A new RequiredValueException.
     */
    public static RequiredValueException forEmptyValue(String valueName) {
        return new RequiredValueException(
                valueName, "",
                String.format("The required value '%s' cannot be empty.", valueName));
    }

    /**
     * Creates a new exception for a negative value.
     * 
     * @param valueName    Name of the field.
     * @param invalidValue The negative value encountered.
     * @return A new RequiredValueException.
     */
    public static RequiredValueException forNegativeValue(String valueName, Number invalidValue) {
        return new RequiredValueException(
                valueName, invalidValue,
                String.format("The required value '%s' cannot be negative. Invalid value: %s", valueName,
                        invalidValue));
    }

    /**
     * Creates a new exception for an invalid value with a custom reason.
     * 
     * @param valueName    Name of the field.
     * @param invalidValue The value that was found invalid.
     * @param reason       The reason why the value is invalid.
     * @return A new RequiredValueException.
     */
    public static RequiredValueException forInvalidValue(String valueName, Object invalidValue, String reason) {
        return new RequiredValueException(
                valueName, invalidValue,
                String.format("The required value '%s' is invalid: %s. Invalid value: %s", valueName, reason,
                        invalidValue));
    }
}
