package io.github.sympol.pure.asserts;

import java.util.Map;

/**
 * Exception thrown when a string is longer than allowed.
 */
public final class StringTooLongException extends AssertionException {

    private final String maxLength;
    private final String currentLength;

    private StringTooLongException(StringTooLongExceptionBuilder builder) {
        super(builder.field, builder.message());
        maxLength = String.valueOf(builder.maxLength);
        currentLength = String.valueOf(builder.value.length());
    }

    public static StringTooLongExceptionBuilder builder() {
        return new StringTooLongExceptionBuilder();
    }

    /**
     * Builder for {@link StringTooLongException}.
     */
    static final class StringTooLongExceptionBuilder {

        private String value;
        private int maxLength;
        private String field;

        private StringTooLongExceptionBuilder() {
        }

        /**
         * Set the field name.
         * 
         * @param field field name
         * @return the builder
         */
        StringTooLongExceptionBuilder field(String field) {
            this.field = field;

            return this;
        }

        /**
         * Set the actual value.
         * 
         * @param value actual value
         * @return the builder
         */
        StringTooLongExceptionBuilder value(String value) {
            this.value = value;

            return this;
        }

        /**
         * Set the maximum allowed length.
         * 
         * @param maxLength max length
         * @return the builder
         */
        StringTooLongExceptionBuilder maxLength(int maxLength) {
            this.maxLength = maxLength;

            return this;
        }

        private String message() {
            return "The value \"%s\" in field \"%s\" must be at most %d long but was %d".formatted(value, field,
                    maxLength, value.length());
        }

        /**
         * Build the exception.
         * 
         * @return the exception
         */
        public StringTooLongException build() {
            return new StringTooLongException(this);
        }
    }

    @Override
    public AssertionErrorType type() {
        return AssertionErrorType.STRING_TOO_LONG;
    }

    @Override
    public Map<String, String> parameters() {
        return Map.of("maxLength", maxLength, "currentLength", currentLength);
    }
}
