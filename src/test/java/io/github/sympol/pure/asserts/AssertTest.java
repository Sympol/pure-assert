package io.github.sympol.pure.asserts;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AssertTest {

    @Test
    void testNotNull_valid() {
        assertDoesNotThrow(() -> Assert.notNull("field", new Object()));
    }

    @Test
    void testNotNull_invalid() {
        assertThrows(MissingMandatoryValueException.class, () -> Assert.notNull("field", null));
    }

    @Test
    void testNotBlank_valid() {
        assertDoesNotThrow(() -> Assert.notBlank("name", "John"));
    }

    @Test
    void testNotBlank_invalid_blank() {
        assertThrows(MissingMandatoryValueException.class, () -> Assert.notBlank("name", ""));
        assertThrows(MissingMandatoryValueException.class, () -> Assert.notBlank("name", " "));
        assertThrows(MissingMandatoryValueException.class, () -> Assert.notBlank("name", "\t\n"));
    }

    @Test
    void testNotBlank_invalid_null() {
        assertThrows(MissingMandatoryValueException.class, () -> Assert.notBlank("name", null));
    }

    @Test
    void testNotEmpty_collection_valid() {
        Collection<String> col = Arrays.asList("a", "b");
        assertDoesNotThrow(() -> Assert.notEmpty("col", col));
    }

    @Test
    void testNotEmpty_collection_invalid_empty() {
        Collection<String> col = Collections.emptyList();
        assertThrows(MissingMandatoryValueException.class, () -> Assert.notEmpty("col", col));
    }

    @Test
    void testNotEmpty_collection_invalid_null() {
        assertThrows(MissingMandatoryValueException.class, () -> Assert.notEmpty("col", (Collection<?>) null));
    }

    @Test
    void testNotEmpty_map_valid() {
        Map<String, String> map = Map.of("key", "value");
        assertDoesNotThrow(() -> Assert.notEmpty("map", map));
    }

    @Test
    void testNotEmpty_map_invalid_empty() {
        Map<String, String> map = Map.of();
        assertThrows(MissingMandatoryValueException.class, () -> Assert.notEmpty("map", map));
    }

    @Test
    void testNotEmpty_map_invalid_null() {
        assertThrows(MissingMandatoryValueException.class, () -> Assert.notEmpty("map", (Map<?, ?>) null));
    }

    // StringAsserter
    @Test
    void testStringAsserter_notNull_valid() {
        assertDoesNotThrow(() -> Assert.field("name", "Alice").notNull());
    }

    @Test
    void testStringAsserter_notNull_invalid() {
        assertThrows(MissingMandatoryValueException.class, () -> Assert.field("name", (String) null).notNull());
    }

    @Test
    void testStringAsserter_notBlank_valid() {
        assertDoesNotThrow(() -> Assert.field("name", "Bob").notBlank());
    }

    @Test
    void testStringAsserter_notBlank_invalid() {
        assertThrows(MissingMandatoryValueException.class, () -> Assert.field("name", "").notBlank());
        assertThrows(MissingMandatoryValueException.class, () -> Assert.field("name", " ").notBlank());
    }

    @Test
    void testStringAsserter_minLength_valid() {
        assertDoesNotThrow(() -> Assert.field("name", "ABC").minLength(2));
    }

    @Test
    void testStringAsserter_minLength_invalid() {
        assertThrows(StringTooShortException.class, () -> Assert.field("name", "A").minLength(2));
    }

    @Test
    void testStringAsserter_maxLength_valid() {
        assertDoesNotThrow(() -> Assert.field("name", "AB").maxLength(3));
    }

    @Test
    void testStringAsserter_maxLength_invalid() {
        assertThrows(StringTooLongException.class, () -> Assert.field("name", "ABCD").maxLength(3));
    }

    @Test
    void testStringAsserter_matches_valid() {
        Pattern pattern = Pattern.compile("\\d+");
        assertDoesNotThrow(() -> Assert.field("age", "123").matches(pattern, "Must be digits only."));
    }

    @Test
    void testStringAsserter_matches_invalid() {
        Pattern pattern = Pattern.compile("\\d+");
        assertThrows(MissingMandatoryValueException.class,
                () -> Assert.field("age", "abc").matches(pattern, "Must be digits only."));
    }

    // IntegerAsserter
    @Test
    void testIntegerAsserter_positive_valid() {
        assertDoesNotThrow(() -> Assert.field("age", 5).positive());
    }

    @Test
    void testIntegerAsserter_positive_invalid_negative() {
        assertThrows(NumberValueTooLowException.class, () -> Assert.field("age", -1).positive());
    }

    @Test
    void testIntegerAsserter_min_valid() {
        assertDoesNotThrow(() -> Assert.field("score", 10).min(5));
    }

    @Test
    void testIntegerAsserter_min_invalid() {
        assertThrows(NumberValueTooLowException.class, () -> Assert.field("score", 3).min(5));
    }

    @Test
    void testIntegerAsserter_max_valid() {
        assertDoesNotThrow(() -> Assert.field("score", 7).max(10));
    }

    @Test
    void testIntegerAsserter_max_invalid() {
        assertThrows(NumberValueTooHighException.class, () -> Assert.field("score", 15).max(10));
    }

    // InstantAsserter
    @Test
    void testInstantAsserter_inPast_valid() {
        Instant past = Instant.now().minusSeconds(10);
        assertDoesNotThrow(() -> Assert.field("date", past).inPast());
    }

    @Test
    void testInstantAsserter_inPast_invalid_future() {
        Instant future = Instant.now().plusSeconds(10);
        assertThrows(NotBeforeTimeException.class, () -> Assert.field("date", future).inPast());
    }

    // UUIDAsserter
    @Test
    void testUUIDAsserter_isVersion_valid() {
        UUID uuid = UUID.randomUUID(); // version 4
        assertDoesNotThrow(() -> Assert.field("id", uuid).isVersion(4));
    }

    @Test
    void testUUIDAsserter_isVersion_invalid() {
        UUID uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
        assertThrows(UUIDVersionMismatchException.class, () -> Assert.field("id", uuid).isVersion(4));
    }

    // BigDecimalAsserter
    @Test
    void testBigDecimalAsserter_positive_valid() {
        BigDecimal val = new BigDecimal("5.0");
        assertDoesNotThrow(() -> Assert.field("price", val).positive());
    }

    @Test
    void testBigDecimalAsserter_positive_invalid_zero() {
        BigDecimal val = BigDecimal.ZERO;
        assertDoesNotThrow(() -> Assert.field("price", val).positive()); // positive inclut 0
    }

    @Test
    void testBigDecimalAsserter_strictlyPositive_valid() {
        BigDecimal val = new BigDecimal("0.1");
        assertDoesNotThrow(() -> Assert.field("price", val).strictlyPositive());
    }

    @Test
    void testBigDecimalAsserter_strictlyPositive_invalid() {
        BigDecimal val = BigDecimal.ZERO;
        assertThrows(NumberValueTooLowException.class, () -> Assert.field("price", val).strictlyPositive());
    }

    // CollectionAsserter
    @Test
    void testCollectionAsserter_maxSize_valid() {
        Collection<String> col = List.of("a", "b");
        assertDoesNotThrow(() -> Assert.field("list", col).maxSize(3));
    }

    @Test
    void testCollectionAsserter_maxSize_invalid() {
        Collection<String> col = List.of("a", "b", "c", "d");
        assertThrows(TooManyElementsException.class, () -> Assert.field("list", col).maxSize(2));
    }

    @Test
    void testCollectionAsserter_noNullElement_valid() {
        Collection<String> col = List.of("a", "b");
        assertDoesNotThrow(() -> Assert.field("list", col).noNullElement());
    }

    @Test
    void testCollectionAsserter_noNullElement_invalid() {
        Collection<String> col = Arrays.asList("a", null);
        assertThrows(NullElementInCollectionException.class, () -> Assert.field("list", col).noNullElement());
    }

    // DoubleAsserter
    @Test
    void testDoubleAsserter_min_valid() {
        assertDoesNotThrow(() -> Assert.field("val", 10.0).min(5.0));
    }

    @Test
    void testDoubleAsserter_min_invalid() {
        assertThrows(NumberValueTooLowException.class, () -> Assert.field("val", 3.0).min(5.0));
    }

    @Test
    void testDoubleAsserter_max_valid() {
        assertDoesNotThrow(() -> Assert.field("val", 7.0).max(10.0));
    }

    @Test
    void testDoubleAsserter_max_invalid() {
        assertThrows(NumberValueTooHighException.class, () -> Assert.field("val", 15.0).max(10.0));
    }

    @Test
    void testDoubleAsserter_over_valid() {
        assertDoesNotThrow(() -> Assert.field("val", 5.1).over(5.0));
    }

    @Test
    void testDoubleAsserter_over_invalid() {
        assertThrows(NumberValueTooLowException.class, () -> Assert.field("val", 5.0).over(5.0));
    }

    @Test
    void testDoubleAsserter_under_valid() {
        assertDoesNotThrow(() -> Assert.field("val", 4.9).under(5.0));
    }

    @Test
    void testDoubleAsserter_under_invalid() {
        assertThrows(NumberValueTooHighException.class, () -> Assert.field("val", 5.0).under(5.0));
    }

    // FloatAsserter
    @Test
    void testFloatAsserter_min_valid() {
        assertDoesNotThrow(() -> Assert.field("val", 10.0f).min(5.0f));
    }

    @Test
    void testFloatAsserter_min_invalid() {
        assertThrows(NumberValueTooLowException.class, () -> Assert.field("val", 3.0f).min(5.0f));
    }

    // LongAsserter
    @Test
    void testLongAsserter_min_valid() {
        assertDoesNotThrow(() -> Assert.field("val", 100L).min(50L));
    }

    @Test
    void testLongAsserter_min_invalid() {
        assertThrows(NumberValueTooLowException.class, () -> Assert.field("val", 30L).min(50L));
    }

    // ArrayAsserter
    @Test
    void testArrayAsserter_notEmpty_valid() {
        String[] arr = { "a", "b" };
        assertDoesNotThrow(() -> Assert.field("arr", arr).notEmpty());
    }

    @Test
    void testArrayAsserter_notEmpty_invalid() {
        String[] arr = {};
        assertThrows(MissingMandatoryValueException.class, () -> Assert.field("arr", arr).notEmpty());
    }

    @Test
    void testArrayAsserter_maxSize_valid() {
        String[] arr = { "a", "b" };
        assertDoesNotThrow(() -> Assert.field("arr", arr).maxSize(2));
    }

    @Test
    void testArrayAsserter_maxSize_invalid() {
        String[] arr = { "a", "b", "c" };
        assertThrows(TooManyElementsException.class, () -> Assert.field("arr", arr).maxSize(2));
    }

    @Test
    void testArrayAsserter_noNullElement_invalid() {
        String[] arr = { "a", null };
        assertThrows(NullElementInCollectionException.class, () -> Assert.field("arr", arr).noNullElement());
    }

    // UUIDAsserter
    @Test
    void testUUIDAsserter_isNotNil_valid() {
        assertDoesNotThrow(() -> Assert.field("id", UUID.randomUUID()).isNotNil());
    }

    @Test
    void testUUIDAsserter_isNotNil_invalid() {
        UUID nil = UUID.fromString("00000000-0000-0000-0000-000000000000");
        assertThrows(UUIDIsNilException.class, () -> Assert.field("id", nil).isNotNil());
    }

    // InstantAsserter
    @Test
    void testInstantAsserter_after_valid() {
        Instant now = Instant.now();
        Instant future = now.plusSeconds(10);
        assertDoesNotThrow(() -> Assert.field("date", future).after(now));
    }

    @Test
    void testInstantAsserter_after_invalid() {
        Instant now = Instant.now();
        Instant past = now.minusSeconds(10);
        assertThrows(NotAfterTimeException.class, () -> Assert.field("date", past).after(now));
    }

    @Test
    void testInstantAsserter_before_valid() {
        Instant now = Instant.now();
        Instant past = now.minusSeconds(10);
        assertDoesNotThrow(() -> Assert.field("date", past).before(now));
    }

    @Test
    void testInstantAsserter_before_invalid() {
        Instant now = Instant.now();
        Instant future = now.plusSeconds(10);
        assertThrows(NotBeforeTimeException.class, () -> Assert.field("date", future).before(now));
    }

    @Test
    void testInstantAsserter_inFuture_valid() {
        Instant future = Instant.now().plusSeconds(60);
        assertDoesNotThrow(() -> Assert.field("date", future).inFuture());
    }

    @Test
    void testInstantAsserter_inFuture_invalid() {
        Instant past = Instant.now().minusSeconds(60);
        assertThrows(NotAfterTimeException.class, () -> Assert.field("date", past).inFuture());
    }

    // EnumAsserter
    private enum Status { ACTIVE, INACTIVE, PENDING }

    @Test
    void testEnumAsserter_notNull_valid() {
        assertDoesNotThrow(() -> Assert.field("status", Status.ACTIVE).notNull());
    }

    @Test
    void testEnumAsserter_notNull_invalid() {
        assertThrows(MissingMandatoryValueException.class, () -> Assert.field("status", (Status) null).notNull());
    }

    @Test
    void testEnumAsserter_isIn_valid() {
        assertDoesNotThrow(() -> Assert.field("status", Status.ACTIVE).isIn(Status.ACTIVE, Status.INACTIVE));
    }

    @Test
    void testEnumAsserter_isIn_invalid() {
        assertThrows(MissingMandatoryValueException.class,
                () -> Assert.field("status", Status.PENDING).isIn(Status.ACTIVE, Status.INACTIVE));
    }

    @Test
    void testEnumAsserter_isNotIn_valid() {
        assertDoesNotThrow(() -> Assert.field("status", Status.ACTIVE).isNotIn(Status.INACTIVE, Status.PENDING));
    }

    @Test
    void testEnumAsserter_isNotIn_invalid() {
        assertThrows(MissingMandatoryValueException.class,
                () -> Assert.field("status", Status.INACTIVE).isNotIn(Status.ACTIVE, Status.INACTIVE));
    }

    @Test
    void testEnumAsserter_satisfies_valid() {
        assertDoesNotThrow(() -> Assert.field("status", Status.ACTIVE)
                .satisfies(s -> s == Status.ACTIVE, "Must be ACTIVE"));
    }

    @Test
    void testEnumAsserter_satisfies_invalid() {
        assertThrows(MissingMandatoryValueException.class,
                () -> Assert.field("status", Status.INACTIVE)
                        .satisfies(s -> s == Status.ACTIVE, "Must be ACTIVE"));
    }

    @Test
    void testEnumAsserter_chained() {
        assertDoesNotThrow(() -> Assert.field("status", Status.ACTIVE)
                .notNull()
                .isIn(Status.ACTIVE, Status.INACTIVE, Status.PENDING));
    }

    // ByteArrayAsserter
    @Test
    void testByteArrayAsserter_notNull_valid() {
        assertDoesNotThrow(() -> Assert.field("data", new byte[]{1, 2, 3}).notNull());
    }

    @Test
    void testByteArrayAsserter_notNull_invalid() {
        assertThrows(MissingMandatoryValueException.class, () -> Assert.field("data", (byte[]) null).notNull());
    }

    @Test
    void testByteArrayAsserter_notEmpty_valid() {
        assertDoesNotThrow(() -> Assert.field("data", new byte[]{1}).notEmpty());
    }

    @Test
    void testByteArrayAsserter_notEmpty_invalid() {
        assertThrows(MissingMandatoryValueException.class, () -> Assert.field("data", new byte[]{}).notEmpty());
    }

    @Test
    void testByteArrayAsserter_maxSize_valid() {
        assertDoesNotThrow(() -> Assert.field("data", new byte[]{1, 2}).maxSize(3));
    }

    @Test
    void testByteArrayAsserter_maxSize_invalid() {
        assertThrows(TooManyElementsException.class, () -> Assert.field("data", new byte[]{1, 2, 3}).maxSize(2));
    }

    @Test
    void testByteArrayAsserter_satisfies_valid() {
        assertDoesNotThrow(() -> Assert.field("data", new byte[]{1, 2, 3})
                .satisfies(d -> d.length >= 2, "Must have at least 2 bytes"));
    }

    @Test
    void testByteArrayAsserter_satisfies_invalid() {
        assertThrows(MissingMandatoryValueException.class,
                () -> Assert.field("data", new byte[]{1})
                        .satisfies(d -> d.length >= 2, "Must have at least 2 bytes"));
    }

    @Test
    void testByteArrayAsserter_chained() {
        assertDoesNotThrow(() -> Assert.field("data", new byte[]{1, 2, 3})
                .notNull()
                .notEmpty()
                .maxSize(10));
    }

    // LocalDateTimeAsserter
    @Test
    void testLocalDateTimeAsserter_notNull_valid() {
        assertDoesNotThrow(() -> Assert.field("date", LocalDateTime.now()).notNull());
    }

    @Test
    void testLocalDateTimeAsserter_notNull_invalid() {
        assertThrows(MissingMandatoryValueException.class,
                () -> Assert.field("date", (LocalDateTime) null).notNull());
    }

    @Test
    void testLocalDateTimeAsserter_inPast_valid() {
        LocalDateTime past = LocalDateTime.now().minusDays(1);
        assertDoesNotThrow(() -> Assert.field("date", past).inPast());
    }

    @Test
    void testLocalDateTimeAsserter_inPast_invalid() {
        LocalDateTime future = LocalDateTime.now().plusDays(1);
        assertThrows(NotBeforeTimeException.class, () -> Assert.field("date", future).inPast());
    }

    @Test
    void testLocalDateTimeAsserter_inFuture_valid() {
        LocalDateTime future = LocalDateTime.now().plusDays(1);
        assertDoesNotThrow(() -> Assert.field("date", future).inFuture());
    }

    @Test
    void testLocalDateTimeAsserter_inFuture_invalid() {
        LocalDateTime past = LocalDateTime.now().minusDays(1);
        assertThrows(NotAfterTimeException.class, () -> Assert.field("date", past).inFuture());
    }

    @Test
    void testLocalDateTimeAsserter_after_valid() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime later = now.plusHours(1);
        assertDoesNotThrow(() -> Assert.field("date", later).after(now));
    }

    @Test
    void testLocalDateTimeAsserter_after_invalid() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime before = now.minusHours(1);
        assertThrows(NotAfterTimeException.class, () -> Assert.field("date", before).after(now));
    }

    @Test
    void testLocalDateTimeAsserter_before_valid() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime before = now.minusHours(1);
        assertDoesNotThrow(() -> Assert.field("date", before).before(now));
    }

    @Test
    void testLocalDateTimeAsserter_before_invalid() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime later = now.plusHours(1);
        assertThrows(NotBeforeTimeException.class, () -> Assert.field("date", later).before(now));
    }

    @Test
    void testLocalDateTimeAsserter_satisfies_valid() {
        LocalDateTime now = LocalDateTime.now();
        assertDoesNotThrow(() -> Assert.field("date", now)
                .satisfies(d -> d.isAfter(LocalDateTime.now().minusDays(1)), "Must be recent"));
    }

    @Test
    void testLocalDateTimeAsserter_satisfies_invalid() {
        LocalDateTime old = LocalDateTime.now().minusDays(10);
        assertThrows(MissingMandatoryValueException.class,
                () -> Assert.field("date", old)
                        .satisfies(d -> d.isAfter(LocalDateTime.now().minusDays(1)), "Must be recent"));
    }

    @Test
    void testLocalDateTimeAsserter_chained() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime later = now.plusHours(1);
        assertDoesNotThrow(() -> Assert.field("date", later)
                .notNull()
                .inFuture()
                .after(now));
    }

    // isGreaterThanOrEqualTo / isLessThanOrEqualTo
    @Test
    void testIntegerAsserter_isGreaterThanOrEqualTo_valid() {
        assertDoesNotThrow(() -> Assert.field("age", 18).isGreaterThanOrEqualTo(18));
    }

    @Test
    void testIntegerAsserter_isGreaterThanOrEqualTo_invalid() {
        assertThrows(NumberValueTooLowException.class, () -> Assert.field("age", 17).isGreaterThanOrEqualTo(18));
    }

    @Test
    void testIntegerAsserter_isLessThanOrEqualTo_valid() {
        assertDoesNotThrow(() -> Assert.field("age", 18).isLessThanOrEqualTo(18));
    }

    @Test
    void testIntegerAsserter_isLessThanOrEqualTo_invalid() {
        assertThrows(NumberValueTooHighException.class, () -> Assert.field("age", 19).isLessThanOrEqualTo(18));
    }

    @Test
    void testLongAsserter_isGreaterThanOrEqualTo_valid() {
        assertDoesNotThrow(() -> Assert.field("val", 100L).isGreaterThanOrEqualTo(100L));
    }

    @Test
    void testLongAsserter_isGreaterThanOrEqualTo_invalid() {
        assertThrows(NumberValueTooLowException.class, () -> Assert.field("val", 99L).isGreaterThanOrEqualTo(100L));
    }

    @Test
    void testDoubleAsserter_isGreaterThanOrEqualTo_valid() {
        assertDoesNotThrow(() -> Assert.field("val", 5.0).isGreaterThanOrEqualTo(5.0));
    }

    @Test
    void testDoubleAsserter_isGreaterThanOrEqualTo_invalid() {
        assertThrows(NumberValueTooLowException.class, () -> Assert.field("val", 4.9).isGreaterThanOrEqualTo(5.0));
    }

    @Test
    void testBigDecimalAsserter_isGreaterThanOrEqualTo_valid() {
        BigDecimal val = new BigDecimal("10.5");
        assertDoesNotThrow(() -> Assert.field("price", val).isGreaterThanOrEqualTo(new BigDecimal("10.5")));
    }

    @Test
    void testBigDecimalAsserter_isGreaterThanOrEqualTo_invalid() {
        BigDecimal val = new BigDecimal("9.9");
        assertThrows(NumberValueTooLowException.class, () -> Assert.field("price", val).isGreaterThanOrEqualTo(new BigDecimal("10")));
    }

    @Test
    void testBigDecimalAsserter_isLessThanOrEqualTo_valid() {
        BigDecimal val = new BigDecimal("10.5");
        assertDoesNotThrow(() -> Assert.field("price", val).isLessThanOrEqualTo(new BigDecimal("10.5")));
    }

    @Test
    void testBigDecimalAsserter_isLessThanOrEqualTo_invalid() {
        BigDecimal val = new BigDecimal("10.6");
        assertThrows(NumberValueTooHighException.class, () -> Assert.field("price", val).isLessThanOrEqualTo(new BigDecimal("10.5")));
    }

    // toField() - alias for value()
    @Test
    void testStringAsserter_toField() {
        String result = Assert.field("name", "Alice").notBlank().toField();
        assertEquals("Alice", result);
    }

    @Test
    void testIntegerAsserter_toField() {
        Integer result = Assert.field("age", 25).min(0).toField();
        assertEquals(25, result);
    }

    @Test
    void testCollectionAsserter_toField() {
        Collection<String> col = List.of("a", "b");
        Collection<String> result = Assert.field("list", col).notEmpty().toField();
        assertEquals(col, result);
    }

    // withMessage() - custom error messages
    @Test
    void testStringAsserter_withMessage_notBlank() {
        MissingMandatoryValueException ex = assertThrows(MissingMandatoryValueException.class,
                () -> Assert.field("name", "").withMessage("Le nom ne peut pas être vide").notBlank());
        assertEquals("Le nom ne peut pas être vide", ex.getMessage());
    }

    @Test
    void testStringAsserter_withMessage_minLength() {
        MissingMandatoryValueException ex = assertThrows(MissingMandatoryValueException.class,
                () -> Assert.field("code", "AB").withMessage("Le code doit avoir au moins 3 caractères").minLength(3));
        assertEquals("Le code doit avoir au moins 3 caractères", ex.getMessage());
    }

    @Test
    void testStringAsserter_withMessage_maxLength() {
        MissingMandatoryValueException ex = assertThrows(MissingMandatoryValueException.class,
                () -> Assert.field("code", "ABCDE").withMessage("Le code ne peut pas dépasser 3 caractères").maxLength(3));
        assertEquals("Le code ne peut pas dépasser 3 caractères", ex.getMessage());
    }

    @Test
    void testStringAsserter_withMessage_resets_after_success() {
        String result = Assert.field("name", "Alice")
                .withMessage("custom message")
                .notBlank()
                .value();
        assertEquals("Alice", result);
    }

    @Test
    void testIntegerAsserter_withMessage_min() {
        MissingMandatoryValueException ex = assertThrows(MissingMandatoryValueException.class,
                () -> Assert.field("age", 5).withMessage("L'âge doit être au moins 18 ans").min(18));
        assertEquals("L'âge doit être au moins 18 ans", ex.getMessage());
    }

    @Test
    void testIntegerAsserter_withMessage_max() {
        MissingMandatoryValueException ex = assertThrows(MissingMandatoryValueException.class,
                () -> Assert.field("age", 200).withMessage("L'âge ne peut pas dépasser 120 ans").max(120));
        assertEquals("L'âge ne peut pas dépasser 120 ans", ex.getMessage());
    }

    @Test
    void testLongAsserter_withMessage_min() {
        MissingMandatoryValueException ex = assertThrows(MissingMandatoryValueException.class,
                () -> Assert.field("val", 5L).withMessage("La valeur minimale est 10").min(10L));
        assertEquals("La valeur minimale est 10", ex.getMessage());
    }

    @Test
    void testDoubleAsserter_withMessage_min() {
        MissingMandatoryValueException ex = assertThrows(MissingMandatoryValueException.class,
                () -> Assert.field("rate", 0.5).withMessage("Le taux minimum est 0.8").min(0.8));
        assertEquals("Le taux minimum est 0.8", ex.getMessage());
    }

    @Test
    void testBigDecimalAsserter_withMessage_min() {
        BigDecimal val = new BigDecimal("5.0");
        MissingMandatoryValueException ex = assertThrows(MissingMandatoryValueException.class,
                () -> Assert.field("price", val).withMessage("Le prix minimum est 10").min(new BigDecimal("10")));
        assertEquals("Le prix minimum est 10", ex.getMessage());
    }

}