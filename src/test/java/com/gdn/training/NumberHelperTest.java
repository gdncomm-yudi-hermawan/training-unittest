package com.gdn.training;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Number Helper Test")
class NumberHelperTest {

  @Test
  @DisplayName("is odd test")
  public void isOddTest() {
    assertFalse(NumberHelper.isOdd(2));
    assertTrue(NumberHelper.isOdd(3));
  }
}