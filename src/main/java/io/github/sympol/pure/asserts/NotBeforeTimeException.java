package io.github.sympol.pure.asserts;

import java.time.Instant;

/**
 * Exception thrown when a time value is not before the expected value.
 */
public final class NotBeforeTimeException extends AssertionException {

    private NotBeforeTimeException(String field, String message) {
        super(field, message);
    }

    @Override
    public AssertionErrorType type() {
        return AssertionErrorType.NOT_BEFORE_TIME;
    }

    /**
     * Start building a strictly not before exception.
     *
     * @return a builder
     */
    public static NotBeforeTimeExceptionValueBuilder strictlyNotBefore() {
        return new NotBeforeTimeExceptionBuilder("must be strictly before");
    }

    /**
     * Start building a not before exception.
     *
     * @return a builder
     */
    public static NotBeforeTimeExceptionValueBuilder notBefore() {
        return new NotBeforeTimeExceptionBuilder("must be before");
    }

    /**
     * Builder for {@link NotBeforeTimeException}.
     */
    public static final class NotBeforeTimeExceptionBuilder
            implements NotBeforeTimeExceptionValueBuilder, NotBeforeTimeExceptionFieldBuilder,
            NotBeforeTimeExceptionOtherBuilder {

        private final String hint;
        private Instant value;
        private String field;
        private Instant other;

        private NotBeforeTimeExceptionBuilder(String hint) {
            this.hint = hint;
        }

        @Override
        public NotBeforeTimeExceptionFieldBuilder value(Instant value) {
            this.value = value;

            return this;
        }

        @Override
        public NotBeforeTimeExceptionOtherBuilder field(String field) {
            this.field = field;

            return this;
        }

        @Override
        public NotBeforeTimeException other(Instant other) {
            this.other = other;

            return build();
        }

        private NotBeforeTimeException build() {
            return new NotBeforeTimeException(field, message());
        }

        private String message() {
            return "Time %s in \"%s\" %s %s but wasn't".formatted(value, field, hint, other);
        }
    }

    /**
     * Interface for setting the value.
     */
    public interface NotBeforeTimeExceptionValueBuilder {
        /**
         * Set the actual value.
         *
         * @param value actual value
         * @return the builder
         */
        NotBeforeTimeExceptionFieldBuilder value(Instant value);
    }

    /**
     * Interface for setting the field name.
     */
    public interface NotBeforeTimeExceptionFieldBuilder {
        /**
         * Set the field name.
         *
         * @param field field name
         * @return the builder
         */
        NotBeforeTimeExceptionOtherBuilder field(String field);
    }

    /**
     * Interface for setting the other value and building.
     */
    public interface NotBeforeTimeExceptionOtherBuilder {
        /**
         * Set the other value and return the exception.
         *
         * @param other other value
         * @return the exception
         */
        NotBeforeTimeException other(Instant other);
    }
}