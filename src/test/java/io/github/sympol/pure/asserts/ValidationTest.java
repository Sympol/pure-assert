package io.github.sympol.pure.asserts;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {

    @Test
    void testRequireNonNull_valid() {
        String val = "test";
        assertEquals(val, Validation.requireNonNull(val, "field"));
    }

    @Test
    void testRequireNonNull_invalid() {
        assertThrows(AssertionException.class, () -> Validation.requireNonNull(null, "field"));
    }

    @Test
    void testRequireNonEmpty_string_valid() {
        String val = "test";
        assertEquals(val, Validation.requireNonEmpty(val, "field"));
    }

    @Test
    void testRequireNonEmpty_string_invalid() {
        assertThrows(AssertionException.class, () -> Validation.requireNonEmpty("", "field"));
        assertThrows(AssertionException.class, () -> Validation.requireNonEmpty("  ", "field"));
        assertThrows(AssertionException.class, () -> Validation.requireNonEmpty((String) null, "field"));
    }

    @Test
    void testRequireNonEmpty_collection_valid() {
        List<String> list = List.of("a");
        assertEquals(list, Validation.requireNonEmpty(list, "list"));
    }

    @Test
    void testRequireNonEmpty_collection_invalid() {
        assertThrows(AssertionException.class, () -> Validation.requireNonEmpty(Collections.emptyList(), "list"));
        assertThrows(AssertionException.class, () -> Validation.requireNonEmpty((Collection<?>) null, "list"));
    }

    @Test
    void testRequirePositive_int_valid() {
        assertEquals(10, Validation.requirePositive(10, "val"));
    }

    @Test
    void testRequirePositive_int_invalid() {
        assertThrows(AssertionException.class, () -> Validation.requirePositive(0, "val"));
        assertThrows(AssertionException.class, () -> Validation.requirePositive(-1, "val"));
    }

    @Test
    void testRequireValidDateOfBirth_valid() {
        LocalDate past = LocalDate.now().minusYears(20);
        assertEquals(past, Validation.requireValidDateOfBirth(past));
    }

    @Test
    void testRequireValidDateOfBirth_invalid() {
        LocalDate future = LocalDate.now().plusDays(1);
        assertThrows(AssertionException.class, () -> Validation.requireValidDateOfBirth(future));
    }

    @Test
    void testRequireValidUrl_valid() {
        String url = "https://example.com";
        assertEquals(url, Validation.requireValidUrl(url));
    }

    @Test
    void testRequireValidUrl_invalid() {
        assertThrows(AssertionException.class, () -> Validation.requireValidUrl("invalid-url"));
    }
}
