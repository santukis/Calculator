package com.frikiplanet.calculator_book.domain;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.google.common.truth.Truth.assertThat;
import static junitparams.JUnitParamsRunner.$;
import static org.mockito.ArgumentMatchers.anyDouble;
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
   private Operation mockedOperation;

   @Before
   public void setUp() throws Exception {
      MockitoAnnotations.initMocks(this);
      Operation operation = new MathOperation();
      calculator = new MathCalculator(mockedExpression, operation);
   }

   @Parameters(value = {"(", "()", "3(", "7+2("})
   @Test
   public void containsParenthesisShouldReturnTrue(String expression) {
      assertThat(calculator.containsParenthesis(expression)).isTrue();
   }

   @Parameters(value = {")", "", "3+2", "3^2.5"})
   @Test
   public void containsParenthesisShouldReturnFalse(String expression) {
      assertThat(calculator.containsParenthesis(expression)).isFalse();
   }

   @Parameters(method = "getParenthesisExpressionData")
   @Test
   public void getParenthesisExpressionShouldReturnExpectedExpression(
           String original, String expected) {
      assertThat(calculator.getParenthesisExpression(original)).isEqualTo(expected);
   }

   private Object[] getParenthesisExpressionData() {
      return $(
              $("(2+", "2+"),
              $("(2x3)", "2x3"),
              $("(2(2+3", "2+3"),
              $("(3+4)(5-7)", "5-7"),
              $("((3-4)x5)", "3-4")
      );
   }

   @Parameters(method = "replaceParenthesisData")
   @Test
   public void replaceParenthesisShouldReturnExpectedExpression(
           String from, String with, String expected) {
      assertThat(calculator.replaceParenthesis(from, with)).isEqualTo(expected);
   }

   private Object[] replaceParenthesisData() {
      return $(
              $("(2+3)", "5", "5"),
              $("3(", "", "3x"),
              $("f(7x3)", "21", "f21"),
              $("3.4/(7-2", "5", "3.4/5"),
              $("(2-3)7", "-1", "-1x7"),
              $("(3+3)/5", "6", "6/5"),
              $("(3+3)r4", "6", "6r4")
      );
   }

   @Parameters(method = "addSymbolData")
   @Test
   public void addSymbolShouldCallAddSymbol(String to, String symbol) {
      calculator.addSymbol(to, symbol);

//      verify(mockedExpression, times(1)).addSymbol(anyString(), anyString());
//      verify(mockedExpression, times(0)).replaceSymbol(anyString(), anyString());
   }

   private Object[] addSymbolData() {
      return $(
              $("", "-"),
              $("2", "+"),
              $("5", "."),
              $("4.3", "f")
      );
   }

   @Parameters(method = "addSymbolData")
   @Test
   public void addSymbolShouldCallAddSymbolInExpression(
           String to, String symbol) {
      //Arrange
      MockExpression mockedExpression = new MockExpression(); //Creamos un test doble de tipo mock
      MathOperation dummyOperation = null; //Creamos un test doble de tipo stub, Dummy según la tipología de Meszaros
      MathCalculator calculator = new MathCalculator(mockedExpression, dummyOperation);

      //Act
      calculator.addSymbol(to, symbol);

      //Assert
//      assertThat(mockedExpression.symbolAdded).isTrue();
//      assertThat(mockedExpression.symbolReplaced).isFalse();
   }

   @Parameters(method = "replaceSymbolData")
   @Test
   public void addSymbolShouldCallReplaceSymbol(
           String to, String symbol) {
      calculator.addSymbol(to, symbol);

//      verify(mockedExpression, times(1)).replaceSymbol(anyString(), anyString());
//      verify(mockedExpression, times(0)).addSymbol(anyString(), anyString());
   }

   @Parameters(method = "replaceSymbolData")
   @Test
   public void addSymbolShouldCallReplaceSymbolInExpression(
           String to, String symbol) {
      //Arrange
      MockExpression mockedExpression = new MockExpression(); //Creamos un test doble de tipo mock
      MathOperation dummyOperation = null; //Creamos un test doble de tipo stub, Dummy según la tipología de Meszaros
      MathCalculator calculator = new MathCalculator(mockedExpression, dummyOperation);

      //Act
      calculator.addSymbol(to, symbol);

      //Assert
//      assertThat(mockedExpression.symbolAdded).isFalse();
//      assertThat(mockedExpression.symbolReplaced).isTrue();
   }

   private Object[] replaceSymbolData() {
      return $(
              $("-", "+"),
              $("-5+", "x"),
              $("3x4/", "f"),
              $("3sqrt", "x"),
              $("3.", "/")
      );
   }

   @Test
   public void removeSymbolShouldCallRemoveSymbol() {
      calculator.removeSymbol("");
    //  verify(mockedExpression, times(1)).removeSymbol("");
   }

   @Parameters(method = "calculateData")
   @Test
   public void calculateShouldReturnExpectedExpression(
           String from, String expected) {
      Expression expression = new MathExpression();
      MathOperation operation = new MathOperation();
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
      when(mockedExpression.tokenize(from)).thenReturn(tokens);
      assertThat(calculator.resolve(from)).isEqualTo(expected);
   }

   private Object[] resolveData() {
      return $(
              $("", new String[] {""}, ""),
              $("-", new String[] {"-"}, "0"),
              $("-5", new String[] {"-5"}, "-5"),
              $("3.4", new String[] {"3.4"}, "3.4"),
              $("3-2", new String[] {"3", "-2"}, "1"),
              $("3+2", new String[] {"3", "+", "2"}, "5"),
              $("f3", new String[] {"f", "3"}, "6"),
              $("2f3", new String[] {"2", "f", "3"}, "12"),
              $("9/r9", new String[] {"9", "/", "r", "9"}, "3"),
              $("3^f3", new String[]{"3", "^", "f", "3"}, "729"),
              $("3--4", new String[] {"3", "-", "-4"}, "7")
      );
   }

   @Parameters(method = "resolveAdditionData")
   @Test
   public void resolveShouldCallXTimesAdditionMethod(
           String from, String[] tokens, int times) {
      MathCalculator calculator = new MathCalculator(mockedExpression, mockedOperation);
      when(mockedExpression.tokenize(from)).thenReturn(tokens);
      calculator.resolve(from);
   //   verify(mockedOperation, times(times)).addition(anyDouble(),anyDouble());
   }

   private Object[] resolveAdditionData() {
      return $(
              $("2+2", new String[] {"2", "+", "2"}, 2),
              $("", new String[] {""}, 0),
              $("2", new String[] {"2"}, 1),
              $("2+2x3-5", new String[] {"2", "+", "2", "x", "3", "-5"}, 3)
      );
   }

   @Parameters(method = "resolveSubtractionData")
   @Test
   public void resolveShouldCallXTimesSubtractionMethod(
           String from, String[] tokens, int times) {
      MathCalculator calculator = new MathCalculator(mockedExpression, mockedOperation);
      when(mockedExpression.tokenize(from)).thenReturn(tokens);
      calculator.resolve(from);
 //     verify(mockedOperation, times(times)).subtraction(anyDouble(),anyDouble());
   }

   private Object[] resolveSubtractionData() {
      return $(
              $("", new String[] {""}, 0),
              $("-2", new String[] {"-2"}, 0),
              $("2--5", new String[] {"2", "-", "-5"}, 1)
      );
   }

   @Parameters(method = "resolveMultiplicationData")
   @Test
   public void resolveShouldCallXTimesMultiplicationMethod(
           String from, String[] tokens, int times) {
      MathCalculator calculator = new MathCalculator(mockedExpression, mockedOperation);
      when(mockedExpression.tokenize(from)).thenReturn(tokens);
      calculator.resolve(from);
 //     verify(mockedOperation, times(times)).multiplication(anyDouble(),anyDouble());
   }

   private Object[] resolveMultiplicationData() {
      return $(
              $("", new String[] {""}, 0),
              $("2", new String[] {"2"}, 0),
              $("2x5", new String[] {"2", "x", "5"}, 1),
              $("2x5+7x3", new String[] {"2", "x", "5", "+", "7", "x", "3"}, 2)
      );
   }

   @Parameters(method = "resolveDivisionData")
   @Test
   public void resolveShouldCallXTimesDivisionMethod(
           String from, String[] tokens, int times) {
      MathCalculator calculator = new MathCalculator(mockedExpression, mockedOperation);
      when(mockedExpression.tokenize(from)).thenReturn(tokens);
      calculator.resolve(from);
 //     verify(mockedOperation, times(times)).division(anyDouble(),anyDouble());
   }

   private Object[] resolveDivisionData() {
      return $(
              $("", new String[] {""}, 0),
              $("2", new String[] {"2"}, 0),
              $("2/5", new String[] {"2", "/", "5"}, 1),
              $("2/5+7/3", new String[] {"2", "/", "5", "+", "7", "/", "3"}, 2)
      );
   }

   @Parameters(method = "resolveFactorialData")
   @Test
   public void resolveShouldCallXTimesFactorialMethod(
           String from, String[] tokens, int times) {
      MathCalculator calculator = new MathCalculator(mockedExpression, mockedOperation);
      when(mockedExpression.tokenize(from)).thenReturn(tokens);
      calculator.resolve(from);
  //    verify(mockedOperation, times(times)).factorial(anyDouble());
   }

   private Object[] resolveFactorialData() {
      return $(
              $("", new String[] {""}, 0),
              $("2", new String[] {"2"}, 0),
              $("2f5", new String[] {"2", "f", "5"}, 1),
              $("2f5+7f3", new String[] {"2", "f", "5", "+", "7", "f", "3"}, 2)
      );
   }

   @Parameters(method = "resolveSquareRootData")
   @Test
   public void resolveShouldCallXTimesSquareRootMethod(
           String from, String[] tokens, int times) {
      MathCalculator calculator = new MathCalculator(mockedExpression, mockedOperation);
      when(mockedExpression.tokenize(from)).thenReturn(tokens);
      calculator.resolve(from);
 //     verify(mockedOperation, times(times)).squareRoot(anyDouble());
   }

   private Object[] resolveSquareRootData() {
      return $(
              $("", new String[] {""}, 0),
              $("2", new String[] {"2"}, 0),
              $("2r5", new String[] {"2", "r", "5"}, 1),
              $("2r5+7r3", new String[] {"2", "r", "5", "+", "7", "r", "3"}, 2)
      );
   }

   @Test(expected = ExpressionException.class)
   public void resolveShouldThrowWhenTokenizeThrows() {
      when(mockedExpression.tokenize(anyString())).thenThrow(ExpressionException.class);
      calculator.resolve("Invalid String");
   }
}
