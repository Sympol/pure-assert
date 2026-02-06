package io.github.sympol.pure.asserts;

import java.util.Map;

/**
 * Exception thrown when a numeric value is too low.
 */
public final class NumberValueTooLowException extends AssertionException {

    private final String min;
    private final String value;

    private NumberValueTooLowException(NumberValueTooLowExceptionBuilder builder) {
        super(builder.field, builder.message());
        min = builder.minValue;
        value = builder.value;
    }

    /**
     * Create a new builder for this exception.
     *
     * @return a new builder
     */
    public static NumberValueTooLowExceptionBuilder builder() {
        return new NumberValueTooLowExceptionBuilder();
    }

    /**
     * Builder for {@link NumberValueTooLowException}.
     */
    public static class NumberValueTooLowExceptionBuilder {

        private String field;
        private String minValue;
        private String value;

        /**
         * Set the field name.
         *
         * @param field field name
         * @return the builder
         */
        public NumberValueTooLowExceptionBuilder field(String field) {
            this.field = field;

            return this;
        }

        /**
         * Set the minimum allowed value.
         *
         * @param minValue min value
         * @return the builder
         */
        public NumberValueTooLowExceptionBuilder minValue(String minValue) {
            this.minValue = minValue;

            return this;
        }

        /**
         * Set the actual value.
         *
         * @param value actual value
         * @return the builder
         */
        /**
         * Set the actual value.
         * 
         * @param value actual value
         * @return the builder
         */
        public NumberValueTooLowExceptionBuilder value(String value) {
            this.value = value;

            return this;
        }

        /**
         * @return The descriptive error message.
         */
        public String message() {
            return "Value of field \"" +
                    field +
                    "\" must be at least " +
                    minValue +
                    " but was " +
                    value;
        }

        /**
         * Build the exception.
         *
         * @return the exception
         */
        public NumberValueTooLowException build() {
            return new NumberValueTooLowException(this);
        }
    }

    @Override
    public AssertionErrorType type() {
        return AssertionErrorType.NUMBER_VALUE_TOO_LOW;
    }

    @Override
    public Map<String, String> parameters() {
        return Map.of("min", min, "value", value);
    }
}