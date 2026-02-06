package io.github.sympol.pure.asserts;

/**
 * Exception thrown when a UUID is nil (all zeros).
 */
public class UUIDIsNilException extends AssertionException {

    private UUIDIsNilException(String message, String field) {
        super(field, message);
    }

    @Override
    public AssertionErrorType type() {
        return AssertionErrorType.MISSING_MANDATORY_VALUE;
    }

    /**
     * @return A new builder for this exception.
     */
    public static UUIDIsNilExceptionBuilder builder() {
        return new UUIDIsNilExceptionBuilder();
    }

    /**
     * Builder for {@link UUIDIsNilException}.
     */
    public static class UUIDIsNilExceptionBuilder {
        private String field;

        /**
         * Set the field name.
         * 
         * @param field field name
         * @return the builder
         */
        public UUIDIsNilExceptionBuilder field(String field) {
            this.field = field;
            return this;
        }

        /**
         * Build the exception.
         * 
         * @return the exception
         */
        public UUIDIsNilException build() {
            return new UUIDIsNilException(
                    String.format("UUID for field '%s' is nil (00000000-0000-0000-0000-000000000000)", field),
                    field);
        }
    }
}