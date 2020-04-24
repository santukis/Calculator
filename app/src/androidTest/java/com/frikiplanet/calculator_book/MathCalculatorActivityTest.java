package com.frikiplanet.calculator_book;

import android.view.View;
import android.widget.TextView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.rule.ActivityTestRule;

import com.frikiplanet.calculator_book.presentation.MathCalculatorActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(JUnitParamsRunner.class)
public class MathCalculatorActivityTest {

   @Rule
   public ActivityTestRule<MathCalculatorActivity> activityRule =
           new ActivityTestRule<>(MathCalculatorActivity.class);

   @Parameters(method = "getValidOperandButtonData")
   @Test
   public void onClickButtonShouldAddTagValueToOperationsViews(int buttonId, String tagValue) {

      //Click on Operand or Operator button
      onView(withId(buttonId))
              .perform(click());

      //Check if operations view is showing the button tag value
      onView(withId(R.id.operations))
              .check(matches(withText(tagValue)));
   }

   private static Object[] getValidOperandButtonData() {
      return new Object[]{
              new Object[]{R.id.bt1, "1"},
              new Object[]{R.id.bt2, "2"},
              new Object[]{R.id.bt3, "3"},
              new Object[]{R.id.bt4, "4"},
              new Object[]{R.id.bt5, "5"},
              new Object[]{R.id.bt6, "6"},
              new Object[]{R.id.bt7, "7"},
              new Object[]{R.id.bt8, "8"},
              new Object[]{R.id.bt9, "9"},
              new Object[]{R.id.bt0, "0"},
              new Object[]{R.id.bt00, "00"},
              new Object[]{R.id.bt_addition, " + "},
              new Object[]{R.id.bt_subtraction, " - "},
              new Object[]{R.id.bt_multiplication, " x "},
              new Object[]{R.id.bt_division, " / "},
              new Object[]{R.id.bt_exponentiation, " ^ "},
              new Object[]{R.id.bt_factorial, " fact ("},
              new Object[]{R.id.bt_square_root, " sqrt ("},
              new Object[]{R.id.bt_dot, "."},
              new Object[]{R.id.bt_parenthesis_start, " ("},
              new Object[]{R.id.bt_parenthesis_end, ") "}
      };
   }

   @Parameters(method = "getRemoveLastButtonData")
   @Test
   public void onRemoveLastButtonClickShouldRemoveLastSymbolInOperationsView
           (String original, String result) {

      onView(withId(R.id.operations))
              .perform(setText(original));

      onView(withId(R.id.bt_remove_last))
              .perform(click());

      onView(withId(R.id.operations))
              .check(matches(withText(result)));
   }

   private static Object[] getRemoveLastButtonData() {
      return new Object[]{
              new Object[]{"2+2", "2 + "},
              new Object[] {"2", ""},
              new Object[] {"2.", "2"},
              new Object[] {"3x3(3", "3 x 3 ("}
      };
   }

   @Parameters(method = "getClearButtonData")
   @Test
   public void onClearButtonClickShouldClearOperationsViews
           (String original) {

      onView(withId(R.id.operations))
              .perform(setText(original));

      onView(withId(R.id.bt_clear))
              .perform(click());

      onView(withId(R.id.operations))
              .check(matches(withText("")));
   }

   private static Object[] getClearButtonData() {
      return new Object[]{
              new Object[]{"2+2"},
              new Object[] {"2"},
              new Object[] {"2."},
              new Object[] {"3x3(3"}
      };
   }

   @Parameters(method = "getValidOperationsData")
   @Test
   public void onOperationsViewChangedShouldUpdateResultsView(String operations, String result) {

      onView(withId(R.id.operations))
              .perform(setText(operations));

      onView(allOf(withParent(withId(R.id.result)), isCompletelyDisplayed()))
              .check(matches(withText(result)));
   }

   private static Object[] getValidOperationsData() {
      return new Object[]{
              new Object[]{"2+2", "4"},
              new Object[]{"2.2-2", "0.2"},
              new Object[]{"sqrt(9)", "3"},
              new Object[]{"5/2", "2.5"},
              new Object[] {"3+(4x3)", "15"}
      };
   }

   public static ViewAction setText(final String value) {
      return new ViewAction() {
         @SuppressWarnings("unchecked")
         @Override
         public Matcher<View> getConstraints() {
            return allOf(isDisplayed(), isAssignableFrom(TextView.class));
         }

         @Override
         public void perform(UiController uiController, View view) {
            ((TextView) view).setText(value);
         }

         @Override
         public String getDescription() {
            return "replace text";
         }
      };
   }
}