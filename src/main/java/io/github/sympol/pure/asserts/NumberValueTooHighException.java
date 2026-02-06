package io.github.sympol.pure.asserts;

import java.util.Map;

/**
 * Exception thrown when a numeric value is too high.
 */
public final class NumberValueTooHighException extends AssertionException {

    private final String max;
    private final String value;

    private NumberValueTooHighException(NumberValueTooHighExceptionBuilder builder) {
        super(builder.field, builder.message());
        max = builder.maxValue;
        value = builder.value;
    }

    /**
     * Create a new builder for this exception.
     *
     * @return a new builder
     */
    public static NumberValueTooHighExceptionBuilder builder() {
        return new NumberValueTooHighExceptionBuilder();
    }

    /**
     * Builder for {@link NumberValueTooHighException}.
     */
    public static class NumberValueTooHighExceptionBuilder {

        private String field;
        private String maxValue;
        private String value;

        /**
         * Set the field name.
         *
         * @param field field name
         * @return the builder
         */
        public NumberValueTooHighExceptionBuilder field(String field) {
            this.field = field;

            return this;
        }

        /**
         * Set the maximum allowed value.
         *
         * @param maxValue max value
         * @return the builder
         */
        public NumberValueTooHighExceptionBuilder maxValue(String maxValue) {
            this.maxValue = maxValue;

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
        public NumberValueTooHighExceptionBuilder value(String value) {
            this.value = value;

            return this;
        }

        /**
         * @return The descriptive error message.
         */
        public String message() {
            return "Value of field \"" +
                    field +
                    "\" must be at most " +
                    maxValue +
                    " but was " +
                    value;
        }

        /**
         * Build the exception.
         *
         * @return the exception
         */
        public NumberValueTooHighException build() {
            return new NumberValueTooHighException(this);
        }
    }

    @Override
    public AssertionErrorType type() {
        return AssertionErrorType.NUMBER_VALUE_TOO_HIGH;
    }

    @Override
    public Map<String, String> parameters() {
        return Map.of("max", max, "value", value);
    }
}