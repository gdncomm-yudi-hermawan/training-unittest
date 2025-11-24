package com.gdn.training;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("String Helper Test")
class StringHelperTest {
    // Use Arrays.asList (allows null elements) instead of List.of which throws NPE on null
    List<String> input = Arrays.asList("gdn", "jakarta", null, "indonesia");
    List<String> expectedOutput = List.of("GDN", "JAKARTA", "INDONESIA");

    @Test
    @DisplayName("toUpperCase converts non-null strings and drops nulls")
    void toUpperCase_converts_and_skips_nulls() {
        assertEquals(expectedOutput, StringHelper.toUpperCase(input));
    }

    @Test
    @DisplayName("toUpperCase returns empty list for empty input")
    void toUpperCase_emptyInput_returnsEmptyList() {
        assertTrue(StringHelper.toUpperCase(List.of()).isEmpty());
    }

    @Test
    @DisplayName("toUpperCase throws NullPointerException for null input")
    void toUpperCase_nullInput_throwsNPE() {
        assertThrows(NullPointerException.class, () -> StringHelper.toUpperCase(null));
    }
}