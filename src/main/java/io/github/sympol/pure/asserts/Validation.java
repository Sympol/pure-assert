package io.github.sympol.pure.asserts;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Utility class for static validations.
 *
 * @deprecated Use {@link Assert} for a more expressive fluent API.
 */
@Deprecated
public class Validation {

    private Validation() {
        throw new AssertionError("This class cannot be instantiated.");
    }

    // --- Validation de nullité ---
    /**
     * Ensures that the specified value is not null.
     * 
     * @param <T>       type of the value
     * @param value     the value to check
     * @param valueName the name of the value for the error message
     * @return the value if it is not null
     */
    public static <T> T requireNonNull(T value, String valueName) {
        Assert.notNull(valueName, value);
        return value;
    }

    // --- Validation de chaîne non vide ---
    /**
     * Ensures that the specified string is not empty or blank.
     * 
     * @param value     the string to check
     * @param valueName the name of the value for the error message
     * @return the string if it is not empty
     */
    public static String requireNonEmpty(String value, String valueName) {
        Assert.field(valueName, value).notBlank();
        return value;
    }

    // --- Validation de collection non vide ---
    /**
     * Ensures that the specified collection is not empty.
     * 
     * @param <T>            type of the collection
     * @param collection     the collection to check
     * @param collectionName the name of the collection for the error message
     * @return the collection if it is not empty
     */
    public static <T extends Collection<?>> T requireNonEmpty(T collection, String collectionName) {
        Assert.notEmpty(collectionName, collection);
        return collection;
    }

    // --- Validation de map non vide ---
    public static <T extends Map<?, ?>> T requireNonEmpty(T map, String mapName) {
        Assert.notEmpty(mapName, map);
        return map;
    }

    // --- Validation de nombre positif ---
    /**
     * Ensures that the specified integer is strictly positive.
     * 
     * @param value     the value to check
     * @param valueName the name of the value for the error message
     * @return the value if it is positive
     */
    public static int requirePositive(int value, String valueName) {
        Assert.field(valueName, value).strictlyPositive();
        return value;
    }

    public static long requirePositive(long value, String valueName) {
        Assert.field(valueName, value).strictlyPositive();
        return value;
    }

    // --- Validation de condition personnalisée ---
    /**
     * Ensures that the specified value satisfies a custom condition.
     * 
     * @param <T>          type of the value
     * @param value        the value to check
     * @param valueName    the name of the value for the error message
     * @param condition    the condition to test
     * @param errorMessage the error message if the condition fails
     * @return the value if it satisfies the condition
     */
    public static <T> T requireValid(T value, String valueName, Predicate<T> condition, String errorMessage) {
        if (value == null || !condition.test(value)) {
            throw RequiredValueException.forInvalidValue(valueName, value, errorMessage);
        }
        return value;
    }

    public static LocalDate requireValidDateOfBirth(LocalDate date) {
        Assert.field("dateOfBirth", date).inPast();
        return date;
    }

    public static String requireValidUrl(String url) {
        Assert.field("url", url).url();
        return url;
    }
}
