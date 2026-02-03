package io.github.sympol.pure.asserts;

import java.util.Map;

/**
 * Exception thrown when a string is shorter than required.
 */
public final class StringTooShortException extends AssertionException {

    private final String minLength;
    private final String currentLength;

    private StringTooShortException(StringTooShortExceptionBuilder builder) {
        super(builder.field, builder.message());
        minLength = String.valueOf(builder.minLength);
        currentLength = String.valueOf(builder.value.length());
    }

    /**
     * Create a new builder for this exception.
     *
     * @return a new builder
     */
    public static StringTooShortExceptionBuilder builder() {
        return new StringTooShortExceptionBuilder();
    }

    /**
     * Builder for {@link StringTooShortException}.
     */
    static final class StringTooShortExceptionBuilder {

        private String value;
        private int minLength;
        private String field;

        private StringTooShortExceptionBuilder() {
        }

        /**
         * Set the field name.
         *
         * @param field field name
         * @return the builder
         */
        StringTooShortExceptionBuilder field(String field) {
            this.field = field;

            return this;
        }

        /**
         * Set the actual value.
         *
         * @param value actual value
         * @return the builder
         */
        StringTooShortExceptionBuilder value(String value) {
            this.value = value;

            return this;
        }

        /**
         * Set the minimum expected length.
         *
         * @param minLength min length
         * @return the builder
         */
        StringTooShortExceptionBuilder minLength(int minLength) {
            this.minLength = minLength;

            return this;
        }

        private String message() {
            return "The value \"%s\" in field \"%s\" must be at least %d long but was only %d".formatted(value, field,
                    minLength, value.length());
        }

        /**
         * Build the exception.
         *
         * @return the exception
         */
        public StringTooShortException build() {
            return new StringTooShortException(this);
        }
    }

    @Override
    public AssertionErrorType type() {
        return AssertionErrorType.STRING_TOO_SHORT;
    }

    @Override
    public Map<String, String> parameters() {
        return Map.of("minLength", minLength, "currentLength", currentLength);
    }
}