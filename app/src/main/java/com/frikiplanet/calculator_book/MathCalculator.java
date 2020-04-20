package com.frikiplanet.calculator_book;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MathCalculator implements Calculator {

   private Expression expression;
   private MathOperation operation;

   public MathCalculator(Expression expression, MathOperation operation) {
      this.expression = expression;
      this.operation = operation;
   }

   @Override
   public String addSymbol(@NonNull String to, @NonNull String symbol) {

      if(isAnOperator(symbol)) {
         symbol = isAnUnaryOperator(symbol) ? symbol.concat(MathSymbols.PARENTHESIS_START) : symbol;

         if (endsWithOperator(to)) {
            return expression.replaceSymbol(to, symbol);

         }
      }
      return expression.addSymbol(to, symbol);
   }

   @Override
   public String removeSymbol(@NonNull String from) {
      return expression.removeSymbol(from);
   }

   @Override
   public String calculate(@NonNull String from) throws OperationException, ExpressionException {

      from = expression.read(from);

      while (containsParenthesis(from)) {
         String parenthesis = getParenthesisExpression(from);
         from = replaceParenthesis(from, resolve(parenthesis));
      }

      return resolve(from);
   }

   @VisibleForTesting
   boolean containsParenthesis(String expression) {
      return expression.contains(MathSymbols.PARENTHESIS_START);
   }

   @VisibleForTesting
   String getParenthesisExpression(String from) {
      String parenthesisExpression = getRightmostParenthesis(from);
      return removeParenthesis(parenthesisExpression);
   }

   private String getRightmostParenthesis(String from) {
      int START_INDEX = getParenthesisStartIndex(from);
      int END_INDEX = getParenthesisEndIndex(from);

      return from.substring(START_INDEX, END_INDEX);
   }

   private int getParenthesisStartIndex(String from) {
      return from.lastIndexOf(MathSymbols.PARENTHESIS_START);
   }

   private int getParenthesisEndIndex(String from) {
      int index = from.indexOf(MathSymbols.PARENTHESIS_END, getParenthesisStartIndex(from));

      if (index == -1) {
         return from.length();
      }

      return ++index;
   }

   private String removeParenthesis(String from) {
      return from.replace(MathSymbols.PARENTHESIS_START, MathSymbols.EMPTY_STRING)
              .replace(MathSymbols.PARENTHESIS_END, MathSymbols.EMPTY_STRING).trim();
   }

   @VisibleForTesting
   String replaceParenthesis(String from, String with) {
      with = addOperators(from, with);
      return new StringBuilder(from)
              .replace(getParenthesisStartIndex(from), getParenthesisEndIndex(from), with)
              .toString();
   }

   private String addOperators(String expression, String to) {
      to = addOperatorBeforeParenthesis(expression, to);
      to = addOperatorAfterParenthesis(expression, to);
      return to;
   }


   private String addOperatorBeforeParenthesis(String from, String to) {
      try {
         String previousCharacter = String.valueOf(from.charAt(getParenthesisStartIndex(from) - 1));

         if (!isAnOperator(previousCharacter) && !previousCharacter.equals(MathSymbols.PARENTHESIS_START))
            return MathSymbols.MULTIPLICATION.concat(to);

      } catch (IndexOutOfBoundsException exception) {
         return to;
      }

      return to;
   }

   private String addOperatorAfterParenthesis(String from, String to) {
      try {
         String nextCharacter = String.valueOf(from.charAt(getParenthesisEndIndex(from)));

         if (!isAnOperator(nextCharacter) && !nextCharacter.equals(MathSymbols.PARENTHESIS_END))
            return to.concat(MathSymbols.MULTIPLICATION);

      } catch (IndexOutOfBoundsException exception) {
         return to;
      }

      return to;
   }

   @VisibleForTesting
   String resolve(String from) throws OperationException, ExpressionException  {
      if(from.isEmpty()) return "";

      double result = 0;
      String unaryOperator = MathSymbols.NONE;
      String binaryOperator = MathSymbols.NONE;

      String[] symbols = expression.tokenize(from);

      for (String symbol : symbols) {
         if (symbol.isEmpty()) continue;

         if (isAnUnaryOperator(symbol)) {
            unaryOperator = symbol;
            if (binaryOperator.equals(MathSymbols.NONE) && result != 0)
               binaryOperator = MathSymbols.MULTIPLICATION;

         } else if (isABinaryOperator(symbol)) {
            binaryOperator = symbol;

         } else {
            if(unaryOperator.equals(MathSymbols.NONE) && binaryOperator.equals(MathSymbols.NONE))
               result = operation.addition(result, Double.parseDouble(symbol));

            if (!unaryOperator.equals(MathSymbols.NONE)) {
               symbol = String.valueOf(calculateUnaryOperation(Double.parseDouble(symbol), unaryOperator));
               result = binaryOperator.equals(MathSymbols.NONE) ? Double.parseDouble(symbol) : result;
               unaryOperator = MathSymbols.NONE;
            }

            if(!binaryOperator.equals(MathSymbols.NONE)) {
               result = calculateBinaryOperation(result, Double.parseDouble(symbol), binaryOperator);
               binaryOperator = MathSymbols.NONE;
            }
         }
      }

      return getFormattedNumber(result);
   }

   private boolean isAnOperator(String symbol) {
      return isABinaryOperator(symbol) || isAnUnaryOperator(symbol);
   }

   private boolean isABinaryOperator(String symbol) {
      return symbol.equals(MathSymbols.ADDITION) ||
              symbol.equals(MathSymbols.SUBTRACTION) ||
              symbol.equals(MathSymbols.MULTIPLICATION) ||
              symbol.equals(MathSymbols.DIVISION) ||
              symbol.equals(MathSymbols.EXPONENTIATION);
   }

   private boolean isAnUnaryOperator(String symbol) {
      return symbol.equals(MathSymbols.SQUARE_ROOT) ||
              symbol.equals(MathSymbols.FACTORIAL);
   }

   private boolean endsWithOperator(String expression) {
      expression = expression.trim();
      return expression.endsWith(MathSymbols.ADDITION) || expression.endsWith(MathSymbols.SUBTRACTION) ||
              expression.endsWith(MathSymbols.MULTIPLICATION) || expression.endsWith(MathSymbols.DIVISION) ||
              expression.endsWith(MathSymbols.EXPONENTIATION) || expression.endsWith(MathSymbols.SQUARE_ROOT_SCREEN) ||
              expression.endsWith(MathSymbols.FACTORIAL_SCREEN) || expression.endsWith(MathSymbols.DOT);
   }

   private double calculateUnaryOperation(double operand, String operator) {
      double result = 0;
      switch (operator) {
         case MathSymbols.SQUARE_ROOT:
            result = operation.squareRoot(operand);
            break;
         case MathSymbols.FACTORIAL:
            result = operation.factorial(operand);
            break;
      }
      return result;
   }

   private double calculateBinaryOperation(double accumulated, double value, String operator) {
      double result = 0;
      switch (operator) {
         case MathSymbols.ADDITION:
            result = operation.addition(accumulated, value);
            break;
         case MathSymbols.SUBTRACTION:
            result = operation.subtraction(accumulated, value);
            break;
         case MathSymbols.MULTIPLICATION:
            result = operation.multiplication(accumulated, value);
            break;
         case MathSymbols.DIVISION:
            result = operation.division(accumulated, value);
            break;
         case MathSymbols.EXPONENTIATION:
            result = operation.exponentiation(accumulated, value);
            break;
      }
      return accumulated == 0 ? value : result;
   }

   private String getFormattedNumber(double value) {
      DecimalFormatSymbols symbols = new DecimalFormatSymbols();
      symbols.setDecimalSeparator('.');
      DecimalFormat formatter = new DecimalFormat("0", symbols);
      formatter.setMaximumFractionDigits(8);
      return formatter.format(value);
   }
}
