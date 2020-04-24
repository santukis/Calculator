package com.frikiplanet.calculator_book.presentation;

public interface CalculatorView {

    void setPresenter(CalculatorPresenter presenter);

    void showOperations(String operations);

    void showResult(String result);

    void showError();
}