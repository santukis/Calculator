package com.frikiplanet.calculator_book.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.google.common.truth.Truth.assertThat;
import static junitparams.JUnitParamsRunner.$;

@RunWith(JUnitParamsRunner.class)
public class MathExpressionTest {

   private MathExpression expression;

   @Before
   public void setUp() {
      expression = new MathExpression();
   }

   @Parameters(method = "readExpressionData")
   @Test
    public void readShouldReturnExpectedExpression(
            String original, String expected) {
      String result = expression.read(original);
      assertThat(result).isEqualTo(expected);
    }

    private Object[] readExpressionData() {
      return $(
              $(" ", ""),
              $("", ""),
              $("fact", "f"),
              $("sqrt", "r"),
              $("(3 + 2)", "(3+2)"),
              $("fact (3 + 2) /       3 + 2", "f(3+2)/3+2")

      );
    }

   @Parameters(method = "writeExpressionData")
   @Test
   public void writeShouldReturnExpectedExpression(
           String original, String expected) {
      String result = expression.write(original);
      assertThat(result).isEqualTo(expected);
   }

   private Object[] writeExpressionData() {
      return $(
              $("f", " fact "),
              $("r", " sqrt "),
              $("(r", " sqrt "),
              $("(f", " fact "),
              $("3.2", "3.2"),
              $("3+2.5", "3 + 2.5"),
              $("3-2.5", "3 - 2.5"),
              $("3^2.5", "3 ^ 2.5"),
              $("3x2.5", "3 x 2.5"),
              $("3/2.5", "3 / 2.5"),
              $("3f(2.5", "3 fact (2.5"),
              $("3r(2.5", "3 sqrt (2.5"),
              $("2.5(3", "2.5 (3"),
              $("3)2.5", "3) 2.5")

      );
   }

   @Parameters(method = "tokenizeExpressionData")
   @Test
   public void tokenizeShouldReturnExpectedExpression(String original, Object[] expected) {
      List<String> result = expression.tokenize(original);
      assertThat(result).isEqualTo(Arrays.asList(expected));
   }

   private Object[] tokenizeExpressionData() {
      return new Object[]{
              new Object[]{"", new Object[]{""}},
              new Object[]{"1", new Object[]{"1"}},
              new Object[]{"12+", new Object[]{"12", "+"}},
              new Object[]{"12-", new Object[]{"12", "-"}},
              new Object[]{"12x", new Object[]{"12", "x"}},
              new Object[]{"12/", new Object[]{"12", "/"}},
              new Object[]{"12^", new Object[]{"12", "^"}},
              new Object[]{"12r", new Object[]{"12", "r"}},
              new Object[]{"12f", new Object[]{"12", "f"}},
              new Object[]{"-5", new Object[]{"-", "5"}},
              new Object[]{"3.4+5", new Object[]{"3.4", "+", "5"}},
              new Object[]{"3.4-5", new Object[]{"3.4", "-", "5"}},
              new Object[]{"3.4x5", new Object[]{"3.4", "x", "5"}},
              new Object[]{"3.4/5", new Object[]{"3.4", "/", "5"}},
              new Object[]{"3.4^5", new Object[]{"3.4", "^", "5"}},
              new Object[]{"3.4r5", new Object[]{"3.4", "r", "5"}},
              new Object[]{"3.4f5", new Object[]{"3.4", "f", "5"}},
              new Object[] {"-2-1", new Object[]{"-", "2", "-", "1"}}
      };
   }

   @Parameters(method = "normalizeExpressionData")
   @Test
   public void normalizeShouldReturnExpectedExpression(String original, String expected) {
       String result = expression.normalize(original);
       assertThat(result).isEqualTo(expected);
   }

   private Object[] normalizeExpressionData() {
       return new Object[] {
               new Object[] {"", ""},
               new Object[] {"1", "1"},
               new Object[] {"2.3", "2.3"},
               new Object[] {"+", "+"},
               new Object[] {"x", "x"},
               new Object[] {"/", "/"},
               new Object[] {"^", "^"},
               new Object[] {"f", "f"},
               new Object[] {"r", "r"},
               new Object[] {"4(", "4x("},
               new Object[] {"4x(", "4x("},
               new Object[] {"4-(", "4-("},
               new Object[] {"4+(", "4+("},
               new Object[] {"4/(", "4/("},
               new Object[] {"4^(", "4^("},
               new Object[] {"r(", "r("},
               new Object[] {"f(", "f("},
               new Object[] {"4.(", "4.x("},
               new Object[] {"4r", "4xr"},
               new Object[] {"4.r", "4.xr"},
               new Object[] {"4f", "4xf"},
               new Object[] {"4.f", "4.xf"},
               new Object[] {"4r(", "4xr("},
               new Object[] {"4f(", "4xf("},
               new Object[] {"4f(3+5(", "4xf(3+5x("},
               new Object[] {"3.3r(3x5(4.5r(", "3.3xr(3x5x(4.5xr("},
               new Object[] {")", ")"},
               new Object[] {")+", ")+"},
               new Object[] {")-", ")-"},
               new Object[] {")x", ")x"},
               new Object[] {")/", ")/"},
               new Object[] {")^", ")^"},
               new Object[] {")4", ")x4"},
               new Object[] {")r", ")xr"},
               new Object[] {")f", ")xf"},
               new Object[] {")4f", ")x4xf"},
               new Object[] {").4f", ")x.4xf"},
               new Object[] {").4r", ")x.4xr"},
               new Object[] {").4r", ")x.4xr"},
               new Object[] {")4r", ")x4xr"},
               new Object[] {"(4+3)(r45f2)4r(7.2+45)", "(4+3)x(r45xf2)x4xr(7.2+45)"},
       };
   }
}