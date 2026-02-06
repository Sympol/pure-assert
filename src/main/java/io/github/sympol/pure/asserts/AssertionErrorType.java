package io.github.sympol.pure.asserts;

/**
 * Enumeration of assertion error types used by {@link AssertionException}.
 */
public enum AssertionErrorType {
    /** A mandatory value is missing, null, blank, or empty. */
    MISSING_MANDATORY_VALUE,
    /** A time value is not after the expected time. */
    NOT_AFTER_TIME,
    /** A time value is not before the expected time. */
    NOT_BEFORE_TIME,
    /** A collection contains a null element. */
    NULL_ELEMENT_IN_COLLECTION,
    /** A numeric value is too high. */
    NUMBER_VALUE_TOO_HIGH,
    /** A numeric value is too low. */
    NUMBER_VALUE_TOO_LOW,
    /** A string is longer than allowed. */
    STRING_TOO_LONG,
    /** A string is shorter than required. */
    STRING_TOO_SHORT,
    /** A collection has too many elements. */
    TOO_MANY_ELEMENTS
}