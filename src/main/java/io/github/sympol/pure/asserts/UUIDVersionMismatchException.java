package io.github.sympol.pure.asserts;

/**
 * Exception thrown when a UUID has an incorrect version.
 */
public class UUIDVersionMismatchException extends AssertionException {
    private final int expectedVersion;
    private final int actualVersion;

    private UUIDVersionMismatchException(String message, String field, int expectedVersion, int actualVersion) {
        super(field, message);
        this.expectedVersion = expectedVersion;
        this.actualVersion = actualVersion;
    }

    @Override
    public AssertionErrorType type() {
        return AssertionErrorType.MISSING_MANDATORY_VALUE; // Ou un type spécifique si existant
    }

    /**
     * @return A new builder for this exception.
     */
    public static UUIDVersionMismatchExceptionBuilder builder() {
        return new UUIDVersionMismatchExceptionBuilder();
    }

    /**
     * Builder for {@link UUIDVersionMismatchException}.
     */
    public static class UUIDVersionMismatchExceptionBuilder {
        private String field;
        private int expectedVersion;
        private int actualVersion;

        /**
         * Set the field name.
         * 
         * @param field field name
         * @return the builder
         */
        public UUIDVersionMismatchExceptionBuilder field(String field) {
            this.field = field;
            return this;
        }

        /**
         * Set the expected version.
         * 
         * @param expectedVersion expected version
         * @return the builder
         */
        public UUIDVersionMismatchExceptionBuilder expectedVersion(int expectedVersion) {
            this.expectedVersion = expectedVersion;
            return this;
        }

        /**
         * Set the actual version found.
         * 
         * @param actualVersion actual version
         * @return the builder
         */
        public UUIDVersionMismatchExceptionBuilder actualVersion(int actualVersion) {
            this.actualVersion = actualVersion;
            return this;
        }

        /**
         * Build the exception.
         * 
         * @return the exception
         */
        public UUIDVersionMismatchException build() {
            return new UUIDVersionMismatchException(
                    String.format("UUID version mismatch for field '%s'. Expected: %d, Actual: %d",
                            field, expectedVersion, actualVersion),
                    field, expectedVersion, actualVersion);
        }
    }
}
