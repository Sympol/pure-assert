package io.github.sympol.pure.asserts;

import java.util.Map;

/**
 * Exception thrown when a collection has more elements than allowed.
 */
public class TooManyElementsException extends AssertionException {

    private final String maxSize;
    private final String currentSize;

    /**
     * @param builder The builder containing exception details.
     */
    public TooManyElementsException(TooManyElementsExceptionBuilder builder) {
        super(builder.field, builder.message());
        maxSize = String.valueOf(builder.maxSize);
        currentSize = String.valueOf(builder.size);
    }

    /**
     * @return A new builder for this exception.
     */
    public static TooManyElementsExceptionBuilder builder() {
        return new TooManyElementsExceptionBuilder();
    }

    /**
     * Builder for {@link TooManyElementsException}.
     */
    public static class TooManyElementsExceptionBuilder {

        private String field;
        private int maxSize;
        private int size;

        /**
         * Set the field name.
         * 
         * @param field field name
         * @return the builder
         */
        public TooManyElementsExceptionBuilder field(String field) {
            this.field = field;

            return this;
        }

        /**
         * Set the maximum allowed size.
         * 
         * @param maxSize max size
         * @return the builder
         */
        public TooManyElementsExceptionBuilder maxSize(int maxSize) {
            this.maxSize = maxSize;

            return this;
        }

        /**
         * Set the actual size encountered.
         * 
         * @param size actual size
         * @return the builder
         */
        public TooManyElementsExceptionBuilder size(int size) {
            this.size = size;

            return this;
        }

        private String message() {
            return "Size of collection \"" +
                    field +
                    "\" must be at most " +
                    maxSize +
                    " but was " +
                    size;
        }

        /**
         * Build the exception.
         * 
         * @return the exception
         */
        public TooManyElementsException build() {
            return new TooManyElementsException(this);
        }
    }

    @Override
    public AssertionErrorType type() {
        return AssertionErrorType.TOO_MANY_ELEMENTS;
    }

    @Override
    public Map<String, String> parameters() {
        return Map.of("maxSize", maxSize, "currentSize", currentSize);
    }
}