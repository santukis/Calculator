package com.frikiplanet.calculator_book.presentation.activities;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.frikiplanet.calculator_book.domain.MathCalculator;
import com.frikiplanet.calculator_book.domain.MathExpression;
import com.frikiplanet.calculator_book.R;
import com.frikiplanet.calculator_book.domain.MathOperation;
import com.frikiplanet.calculator_book.presentation.presenter.CalculatorPresenter;
import com.frikiplanet.calculator_book.presentation.views.CalculatorView;

import java.util.Arrays;
import java.util.List;

public class MathCalculatorActivity extends AppCompatActivity implements
        TextWatcher, Animator.AnimatorListener, CalculatorView {

   private LinearLayout screenCanvas;
   private TextView operationsText;
   private TextSwitcher resultText;
   private Button clearButton;
   private Button removeLastButton;
   private Button equalButton;
   private List<Button> buttons;

   private CalculatorPresenter presenter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_math_calculator);

      initializeViewComponents();
      initializeViewListeners();
   }

   private void initializeViewComponents() {
      screenCanvas = findViewById(R.id.result_screen);
      operationsText = findViewById(R.id.operations);
      resultText = findViewById(R.id.result);
      clearButton = findViewById(R.id.bt_clear);
      removeLastButton = findViewById(R.id.bt_remove_last);
      equalButton = findViewById(R.id.bt_equal);

      buttons = Arrays.asList(findViewById(R.id.bt_parenthesis_start), findViewById(R.id.bt_parenthesis_end), findViewById(R.id.bt_parenthesis_end), findViewById(R.id.bt_addition),
              findViewById(R.id.bt_subtraction), findViewById(R.id.bt_multiplication), findViewById(R.id.bt_division), findViewById(R.id.bt_exponentiation),
              findViewById(R.id.bt_square_root), findViewById(R.id.bt_factorial), findViewById(R.id.bt0), findViewById(R.id.bt00), findViewById(R.id.bt1),
              findViewById(R.id.bt2), findViewById(R.id.bt3), findViewById(R.id.bt4), findViewById(R.id.bt5), findViewById(R.id.bt6), findViewById(R.id.bt7),
              findViewById(R.id.bt8), findViewById(R.id.bt9), findViewById(R.id.bt_dot));

      setPresenter(new CalculatorPresenter(this, new MathCalculator(new MathExpression(), new MathOperation())));
   }

   private void initializeViewListeners() {
      operationsText.addTextChangedListener(this);

      resultText.setFactory(() -> {
         TextView resultView = new TextView(MathCalculatorActivity.this);
         resultView.setGravity(Gravity.CENTER);
         resultView.setTextSize(30);
         return resultView;
      });

      resultText.setInAnimation(this, R.anim.result_anim);

      clearButton.setOnClickListener(v -> {
         if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            circularRevealCard(screenCanvas);
         else
            presenter.clearScreen();
      });

      removeLastButton.setOnClickListener(v -> {
         String expression = operationsText.getText().toString();
         presenter.removeSymbol(expression);
      });

      equalButton.setOnClickListener(v -> {
         resultText.setText(((TextView) resultText.getCurrentView()).getText());
         ((TextView) resultText.getCurrentView()).setTextColor(ContextCompat.getColor(MathCalculatorActivity.this, R.color.colorAccent));
      });

      for (final Button button : buttons) {
         button.setOnClickListener(v -> {
                    String expression = operationsText.getText().toString();
                    presenter.addSymbol(expression, button.getTag().toString());
                 }
         );
      }
   }

   @TargetApi(Build.VERSION_CODES.LOLLIPOP)
   private void circularRevealCard(View view) {
      float finalRadius = Math.max(view.getWidth(), view.getHeight());

      Animator circularReveal =
              ViewAnimationUtils.createCircularReveal(
                      view,
                      0,
                      view.getHeight(),
                      0,
                      finalRadius * 1.5f);

      circularReveal.setDuration(500);
      circularReveal.addListener(this);

      circularReveal.start();
   }

   @Override
   public void beforeTextChanged(CharSequence s, int start, int count, int after) {

   }

   @Override
   public void onTextChanged(CharSequence s, int start, int before, int count) {

   }

   @Override
   public void afterTextChanged(Editable s) {
      presenter.calculate(s.toString());
   }

   @Override
   public void onAnimationStart(Animator animation) {
      screenCanvas.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
   }

   @Override
   public void onAnimationEnd(Animator animation) {
      screenCanvas.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
      presenter.clearScreen();
   }

   @Override
   public void onAnimationCancel(Animator animation) {

   }

   @Override
   public void onAnimationRepeat(Animator animation) {

   }

   @Override
   public void setPresenter(CalculatorPresenter presenter) {
      this.presenter = presenter;
   }

   @Override
   public void showOperations(String operations) {
      this.operationsText.setText(operations);
   }

   @Override
   public void showResult(String result) {
      ((TextView) resultText.getCurrentView()).setTextColor(ContextCompat.getColor(this, R.color.secondaryText));
      resultText.setCurrentText(result);
   }

   @Override
   public void showError() {
      resultText.setText(getString(R.string.wrong_operation));
      ((TextView) resultText.getCurrentView()).setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
   }
}