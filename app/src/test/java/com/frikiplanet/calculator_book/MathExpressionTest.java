package com.frikiplanet.calculator_book;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.google.common.truth.Truth.assertThat;
import static junitparams.JUnitParamsRunner.$;

@RunWith(JUnitParamsRunner.class)
public class MathExpressionTest {

   private MathExpression controller;

   @Before
   public void setUp() throws Exception {
      controller = new MathExpression();
   }

   @Parameters(method = "readExpressionData")
   @Test
    public void readShouldReturnExpectedExpression(
            String original, String expected) {
      String result = controller.read(original);
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
      String result = controller.write(original);
      assertThat(result).isEqualTo(expected);
   }

   private Object[] writeExpressionData() {
      return $(
              $("f", " fact "),
              $("r", " sqrt "),
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

   @Parameters(method = "addSymbolExpressionData")
   @Test
   public void addSymbolShouldReturnExpectedExpression(
           String original, String symbol, String expected) {
      String result = controller.addSymbol(original, symbol);
      assertThat(result).isEqualTo(expected);
   }

   private Object[] addSymbolExpressionData() {
      return $(
              $("3", "2", "32"),
              $("3.", "2", "3.2"),
              $("3", "+", "3 + "),
              $("3-", "3", "3 - 3"),
              $("4(", "2", "4 (2"),
              $("3.2)", "/", "3.2) / "),
              $("f(", "7", " fact (7"),
              $("r(3x", "5", " sqrt (3 x 5")

      );
   }

   @Parameters(method = "addInvalidSymbolInput")
   @Test(expected = ExpressionException.class)
   public void addSymbolShouldThrowWhenSymbolIsInvalid(String symbol) {
       controller.addSymbol("", symbol);
   }

   private Object[] addInvalidSymbolInput() {
      return $(
              $("&"),
              $("E"),
              $("e"),
              $("*"),
              $("{"),
              $("X")

      );
   }

   @Parameters(method = "removeSymbolExpressionData")
   @Test
   public void removeSymbolShouldReturnExpectedExpression(
           String original, String expected) {
      String result = controller.removeSymbol(original);
      assertThat(result).isEqualTo(expected);
   }

   private Object[] removeSymbolExpressionData() {
      return $(
              $("32", "3"),
              $("3.", "3"),
              $("3", ""),
              $("3-", "3"),
              $("4(2", "4 ("),
              $("3.2)", "3.2"),
              $("f(7", " fact ("),
              $("r(3x"," sqrt (3")

      );
   }

   @Parameters(method = "replaceSymbolExpressionData")
   @Test
   public void replaceSymbolShouldReturnExpectedExpression(
           String original, String symbol, String expected) {
      String result = controller.replaceSymbol(original, symbol);
      assertThat(result).isEqualTo(expected);
   }

   private Object[] replaceSymbolExpressionData() {
      return $(
              $("x", "/", " / "),
              $("32+", "-", "32 - "),
              $("3.", "x", "3 x "),
              $("4.5/", "+", "4.5 + "),
              $("3f", "r", "3 sqrt "),
              $("3^", "/", "3 / ")

      );
   }

   @Parameters(method = "tokenizeExpressionData")
   @Test
   public void tokenizeShouldReturnExpectedExpression(
           String original, Object[] expected) {
      String[] result = controller.tokenize(original);
      assertThat(result).isEqualTo(expected);
   }

   private Object[] tokenizeExpressionData() {
      return new Object[]{
              new Object[]{"", new Object[]{""}},
              new Object[]{"1", new Object[]{"1"}},
              new Object[]{"12+", new Object[]{"12", "+"}},
              new Object[]{"3.4/5", new Object[]{"3.4", "/", "5"}},
              new Object[]{"0.5r", new Object[] {"0.5", "r"}},
              new Object[]{"3+f", new Object[]{"3", "+", "f"}},
              new Object[]{"4^3", new Object[]{"4", "^", "3"}},
              new Object[]{"6/8", new Object[]{"6", "/", "8"}},
              new Object[] {"-2-1", new Object[]{"-2", "-1"}}};
   }
}