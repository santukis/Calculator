package com.frikiplanet.calculator_book;

public interface CalculatorPresenter {

    void addSymbol(String expression, String character);

    void removeSymbol(String expression);

    void clearScreen();

    void calculate(String expression);
}
