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
   public void tokenizeShouldReturnExpectedExpression(
           String original, Object[] expected) {
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
}