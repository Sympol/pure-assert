package io.github.sympol.pure.asserts;

import java.time.Instant;

/**
 * Exception thrown when a time value is not after the expected value.
 */
public class NotAfterTimeException extends AssertionException {

    private NotAfterTimeException(String field, String message) {
        super(field, message);
    }

    @Override
    public AssertionErrorType type() {
        return AssertionErrorType.NOT_AFTER_TIME;
    }

    /**
     * Start building a strictly not after exception.
     *
     * @return a builder
     */
    public static NotAfterTimeExceptionValueBuilder strictlyNotAfter() {
        return new NotAfterTimeExceptionBuilder("must be strictly after");
    }

    /**
     * Start building a not after exception.
     *
     * @return a builder
     */
    public static NotAfterTimeExceptionValueBuilder notAfter() {
        return new NotAfterTimeExceptionBuilder("must be after");
    }

    /**
     * Builder for {@link NotAfterTimeException}.
     */
    public static final class NotAfterTimeExceptionBuilder
            implements NotAfterTimeExceptionValueBuilder, NotAfterTimeExceptionFieldBuilder,
            NotAfterTimeExceptionOtherBuilder {

        private final String hint;
        private Instant value;
        private String field;
        private Instant other;

        private NotAfterTimeExceptionBuilder(String hint) {
            this.hint = hint;
        }

        @Override
        public NotAfterTimeExceptionFieldBuilder value(Instant value) {
            this.value = value;

            return this;
        }

        @Override
        public NotAfterTimeExceptionOtherBuilder field(String field) {
            this.field = field;

            return this;
        }

        @Override
        public NotAfterTimeException other(Instant other) {
            this.other = other;

            return build();
        }

        private NotAfterTimeException build() {
            return new NotAfterTimeException(field, message());
        }

        private String message() {
            return "Time %s in \"%s\" %s %s but wasn't".formatted(value, field, hint, other);
        }
    }

    /**
     * Interface for setting the value.
     */
    public interface NotAfterTimeExceptionValueBuilder {
        /**
         * Set the actual value.
         *
         * @param value actual value
         * @return the builder
         */
        NotAfterTimeExceptionFieldBuilder value(Instant value);
    }

    /**
     * Interface for setting the field name.
     */
    public interface NotAfterTimeExceptionFieldBuilder {
        /**
         * Set the field name.
         *
         * @param field field name
         * @return the builder
         */
        NotAfterTimeExceptionOtherBuilder field(String field);
    }

    /**
     * Interface for setting the other value and building.
     */
    public interface NotAfterTimeExceptionOtherBuilder {
        /**
         * Set the other value and return the exception.
         *
         * @param other other value
         * @return the exception
         */
        NotAfterTimeException other(Instant other);
    }
}