package io.github.sympol.pure.asserts;

/**
 * Exception levée lorsqu'une valeur requise est null ou invalide.
 */
public class RequiredValueException extends AssertionException {

    private final Object invalidValue;

    public RequiredValueException(String valueName, Object invalidValue, String message) {
        super(valueName, message);
        this.invalidValue = invalidValue;
    }

    public String getValueName() {
        return field();
    }

    public Object getInvalidValue() {
        return invalidValue;
    }

    @Override
    public AssertionErrorType type() {
        return AssertionErrorType.MISSING_MANDATORY_VALUE;
    }

    public static RequiredValueException forNullValue(String valueName) {
        return new RequiredValueException(
                valueName, null,
                String.format("The required value '%s' cannot be null.", valueName));
    }

    public static RequiredValueException forEmptyValue(String valueName) {
        return new RequiredValueException(
                valueName, "",
                String.format("The required value '%s' cannot be empty.", valueName));
    }

    public static RequiredValueException forNegativeValue(String valueName, Number invalidValue) {
        return new RequiredValueException(
                valueName, invalidValue,
                String.format("The required value '%s' cannot be negative. Invalid value: %s", valueName,
                        invalidValue));
    }

    public static RequiredValueException forInvalidValue(String valueName, Object invalidValue, String reason) {
        return new RequiredValueException(
                valueName, invalidValue,
                String.format("The required value '%s' is invalid: %s. Invalid value: %s", valueName, reason,
                        invalidValue));
    }
}
