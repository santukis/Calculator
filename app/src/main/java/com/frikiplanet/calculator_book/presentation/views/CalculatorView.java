package com.frikiplanet.calculator_book.presentation.views;

import com.frikiplanet.calculator_book.presentation.presenter.CalculatorPresenter;

public interface CalculatorView {

    void setPresenter(CalculatorPresenter presenter);

    void showOperations(String operations);

    void showResult(String result);

    void showError();
}