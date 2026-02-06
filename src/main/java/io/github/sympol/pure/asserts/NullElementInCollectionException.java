package io.github.sympol.pure.asserts;

/**
 * Exception thrown when a collection contains a null element.
 */
public class NullElementInCollectionException extends AssertionException {

    /**
     * @param field name of the collection field
     */
    public NullElementInCollectionException(String field) {
        super(field, message(field));
    }

    private static String message(String field) {
        return "The field \"" + field + "\" contains a null element";
    }

    @Override
    public AssertionErrorType type() {
        return AssertionErrorType.NULL_ELEMENT_IN_COLLECTION;
    }
}