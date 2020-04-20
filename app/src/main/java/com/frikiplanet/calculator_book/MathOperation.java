package com.frikiplanet.calculator_book;


public class MathOperation {

   public double addition(Double operand1, Double operand2) throws OperationException {

      throwsIfValuesAreInvalid(operand1, operand2);

      return operand1 + operand2;
   }

   public double subtraction(Double operand1, Double operand2) throws OperationException {

      throwsIfValuesAreInvalid(operand1, operand2);

      return operand1 - operand2;
   }

   public double multiplication(Double operand1, Double operand2) throws OperationException {

      throwsIfValuesAreInvalid(operand1, operand2);

      double result = operand1 * operand2;

      throwsIfValuesAreInvalid(result);

      return result;
   }

   public double division(Double dividend, Double divisor) throws OperationException {

      throwsIfValuesAreInvalid(dividend, divisor);

      double result = dividend / divisor;

      throwsIfValuesAreInvalid(result);

      return result;
   }

   public double exponentiation(Double base, Double exponent) throws OperationException {

      throwsIfValuesAreInvalid(base, exponent);

      if (exponent > Double.MAX_EXPONENT || exponent < Double.MIN_EXPONENT)
         throw new OperationException("Exponent out of scope");

      if ((base == 0 || base == 1) && exponent != 0)
         return base;

      double result = 1;
      boolean expoNegative = exponent < 0;

      if (expoNegative)
         exponent = -exponent;

      while (exponent != 0) {
         result = multiplication(result, base);
         exponent--;
      }

      return expoNegative ? (1 / result) : result;
   }

   public double squareRoot(Double radicand) throws OperationException {

      throwsIfValuesAreInvalid(radicand);

      double aux;
      double squareRoot = radicand / 2;

      if (radicand < 0) {
         throw new OperationException();
      }

      do {
         aux = squareRoot;
         squareRoot = (aux + (radicand / aux)) / 2;

         throwsIfValuesAreInvalid(squareRoot);
      }
      while (aux != squareRoot);

      return squareRoot;

   }

   public double factorial(Double operand) throws OperationException {

      throwsIfValuesAreInvalid(operand);

      if (operand < 0)
         throw new OperationException("result is not valid");

      double result = 1;

      while (operand != 0) {
         result = multiplication(result, operand);
         operand--;
      }

      return result;
   }

   private void throwsIfValuesAreInvalid(Double... values) throws OperationException {
      for (Double value : values) {
         if (value == null || value == Double.MAX_VALUE || Double.isInfinite(value) || Double.isNaN(value))
            throw new OperationException();
      }
   }
}