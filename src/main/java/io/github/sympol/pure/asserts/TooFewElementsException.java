package io.github.sympol.pure.asserts;

import java.util.Map;

/**
 * Exception thrown when a collection has fewer elements than required.
 */
public class TooFewElementsException extends AssertionException {

    private final String minSize;
    private final String currentSize;

    /**
     * @param builder The builder containing exception details.
     */
    public TooFewElementsException(TooFewElementsExceptionBuilder builder) {
        super(builder.field, builder.message());
        minSize = String.valueOf(builder.minSize);
        currentSize = String.valueOf(builder.size);
    }

    /**
     * @return A new builder for this exception.
     */
    public static TooFewElementsExceptionBuilder builder() {
        return new TooFewElementsExceptionBuilder();
    }

    /**
     * Builder for {@link TooFewElementsException}.
     */
    public static class TooFewElementsExceptionBuilder {

        private String field;
        private int minSize;
        private int size;

        /**
         * Set the field name.
         *
         * @param field field name
         * @return the builder
         */
        public TooFewElementsExceptionBuilder field(String field) {
            this.field = field;

            return this;
        }

        /**
         * Set the minimum required size.
         *
         * @param minSize min size
         * @return the builder
         */
        public TooFewElementsExceptionBuilder minSize(int minSize) {
            this.minSize = minSize;

            return this;
        }

        /**
         * Set the actual size encountered.
         *
         * @param size actual size
         * @return the builder
         */
        public TooFewElementsExceptionBuilder size(int size) {
            this.size = size;

            return this;
        }

        private String message() {
            return "Size of collection \"" +
                    field +
                    "\" must be at least " +
                    minSize +
                    " but was " +
                    size;
        }

        /**
         * Build the exception.
         *
         * @return the exception
         */
        public TooFewElementsException build() {
            return new TooFewElementsException(this);
        }
    }

    @Override
    public AssertionErrorType type() {
        return AssertionErrorType.TOO_FEW_ELEMENTS;
    }

    @Override
    public Map<String, String> parameters() {
        return Map.of("minSize", minSize, "currentSize", currentSize);
    }
}
