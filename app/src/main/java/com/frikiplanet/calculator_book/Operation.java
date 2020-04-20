package com.frikiplanet.calculator_book;

public interface Operation {

   double addition(double left, double right);

   double subtraction(double left, double right);

   double multiplication(double left, double right);

   double division(double dividend, double divisor);

   double exponentiation(double base, double exponent);

   double squareRoot(double radicand);

   double factorial(double operand);
}