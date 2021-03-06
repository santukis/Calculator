package com.frikiplanet.calculator_book.domain;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.google.common.truth.Truth.assertThat;
import static junitparams.JUnitParamsRunner.$;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class MathCalculatorTest {

   private MathCalculator calculator;

   @Mock
   private Expression mockedExpression;

   @Mock
   private Resolver mockedResolver;

   @Before
   public void setUp() {
      MockitoAnnotations.openMocks(this);
      Resolver resolver = new MathResolver();
      calculator = new MathCalculator(mockedExpression, resolver);
   }

   @Parameters(method = "addSymbolData")
   @Test
   public void addSymbolShouldCallAddSymbolInExpression(String to, String symbol) {
      MockExpression mockExpression = new MockExpression();
      calculator = new MathCalculator(mockExpression, null);

      calculator.addSymbol(to, symbol);

      assertThat(mockExpression.added).isTrue();
      assertThat(mockExpression.replaced).isFalse();
   }

   @Parameters(method = "addSymbolData")
   @Test
   public void addSymbolShouldCallAddSymbol(String to, String symbol) {
      when(mockedExpression.read(anyString())).thenReturn(to);

      calculator.addSymbol(to, symbol);

      verify(mockedExpression, times(1)).addSymbol(to, symbol);
      verify(mockedExpression, times(0)).replaceSymbol(to, symbol);
   }

   private Object[] addSymbolData() {
      return $(
              $("", "-"),
              $("2", "+"),
              $("5", "."),
              $("4.3", "f")
      );
   }

   @Parameters(method = "replaceSymbolData")
   @Test
   public void addSymbolShouldCallReplaceSymbol(String to, String symbol, String expected) {
      MockExpression mockedExpression = new MockExpression();
      calculator = new MathCalculator(mockedExpression, null);

      calculator.addSymbol(to, symbol);

      assertThat(mockedExpression.added).isFalse();
      assertThat(mockedExpression.replaced).isTrue();
   }

   @Parameters(method = "replaceSymbolData")
   @Test
   public void addSymbolShouldReplaceSymbol(String to, String symbol, String expected) {
      calculator = new MathCalculator(new MathExpression(), null);

      String result = calculator.addSymbol(to, symbol);

      assertThat(result).isEqualTo(expected);
   }

   @Test @Parameters(method = "replaceSymbolData")
   public void addSymbolShouldCallReplaceSymbolWithMockito(String to, String symbol, String expected) {
      when(mockedExpression.read(to)).thenReturn(to);
      calculator.addSymbol(to, symbol);
      verify(mockedExpression, times(1)).replaceSymbol(to, symbol);
      verify(mockedExpression, times(0)).addSymbol(to, symbol);
   }

   private Object[] replaceSymbolData() {
      return $(
              $("+", "+", " + "),
              $("-", "-", " - "),
              $("x", "x", " x "),
              $("/", "/", " / "),
              $("^", "^", " ^ "),
              $("-", "+", " + "),
              $("-5+", "x", " - 5 x "),
              $("3x4/", "x", "3 x 4 x "),
              $("3-", "+", "3 + ")
      );
   }

   @Parameters(method = "removeSymbolData")
   @Test
   public void removeSymbolShouldRemoveLastSymbol(String to, String expected) {
      calculator = new MathCalculator(new MathExpression(), null);
      String result = calculator.removeSymbol(to);

      assertThat(result).isEqualTo(expected);
   }

   private Object[] removeSymbolData() {
      return $(
              $("", ""),
              $("+", ""),
              $("-", ""),
              $("x", ""),
              $("/", ""),
              $("^", ""),
              $("-", ""),
              $("-5+", " - 5"),
              $("3r(", "3"),
              $("4.5x7.6f(", "4.5 x 7.6")

      );
   }

   @Parameters(method = "calculateData")
   @Test
   public void calculateShouldReturnExpectedExpression(
           String from, String expected) {
      Expression expression = new MathExpression();
      MathResolver operation = new MathResolver();
      MathCalculator calculator = new MathCalculator(expression, operation);

      assertThat(calculator.calculate(from)).isEqualTo(expected);
   }

   private Object[] calculateData() {
      return $(
              $("(2+(2x2)", "6"),
              $("sqrt(3x3)(3+2)", "15"),
              $("-9(-3)", "27"),
              $("fact(3+3-1)", "120")
      );
   }

   @Parameters(method = "resolveData")
   @Test
   public void resolveShouldReturnExpectedExpression(
           String from, String[] tokens, String expected) {
      when(mockedExpression.tokenize(from)).thenReturn(new ArrayList<>(Arrays.asList(tokens)));
      assertThat(calculator.resolve(from)).isEqualTo(expected);
   }

   private Object[] resolveData() {
      return $(
              $("", new String[] {""}, ""),
              $("-", new String[] {"-"}, "0"),
              $("-5", new String[] {"-5"}, "-5"),
              $("3.4", new String[] {"3.4"}, "3.4"),
              $("3-2", new String[] {"3", "-", "2"}, "1"),
              $("3+2", new String[] {"3", "+", "2"}, "5"),
              $("f3", new String[] {"f", "3"}, "6"),
              $("2f3", new String[] {"2", "x", "f", "3"}, "12"),
              $("9/r9", new String[] {"9", "/", "r", "9"}, "3"),
              $("3^f3", new String[]{"3", "^", "f", "3"}, "729"),
              $("3--4", new String[] {"3", "-", "-4"}, "7")
      );
   }
}
