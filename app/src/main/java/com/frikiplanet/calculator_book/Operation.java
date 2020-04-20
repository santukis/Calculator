package com.frikiplanet.calculator_book;

public interface Operation {

   double addition(double operand1, double operand2);

   double subtraction(double operand1, double operand2);

   double multiplication(double operand1, double operand2);

   double division(double dividend, double divisor);

   double exponentiation(double base, double exponent);

   double squareRoot(double radicand);

   double factorial(double operand);
}
