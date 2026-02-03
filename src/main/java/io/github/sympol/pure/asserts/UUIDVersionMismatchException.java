package io.github.sympol.pure.asserts;

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

    public static UUIDVersionMismatchExceptionBuilder builder() {
        return new UUIDVersionMismatchExceptionBuilder();
    }

    public static class UUIDVersionMismatchExceptionBuilder {
        private String field;
        private int expectedVersion;
        private int actualVersion;

        public UUIDVersionMismatchExceptionBuilder field(String field) {
            this.field = field;
            return this;
        }

        public UUIDVersionMismatchExceptionBuilder expectedVersion(int expectedVersion) {
            this.expectedVersion = expectedVersion;
            return this;
        }

        public UUIDVersionMismatchExceptionBuilder actualVersion(int actualVersion) {
            this.actualVersion = actualVersion;
            return this;
        }

        public UUIDVersionMismatchException build() {
            return new UUIDVersionMismatchException(
                    String.format("UUID version mismatch for field '%s'. Expected: %d, Actual: %d",
                            field, expectedVersion, actualVersion),
                    field, expectedVersion, actualVersion);
        }
    }
}
