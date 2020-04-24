package com.frikiplanet.calculator_book;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.frikiplanet.calculator_book.presentation.MathCalculatorActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MathCalculatorEspressoRecord {

    @Rule
    public ActivityTestRule<MathCalculatorActivity> mActivityTestRule = new ActivityTestRule<>(MathCalculatorActivity.class);

    @Test
    public void mathCalculatorEspressoRecord() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.bt1), withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.four_row),
                                        childAtPosition(
                                                withId(R.id.calculator),
                                                3)),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.bt2), withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.four_row),
                                        childAtPosition(
                                                withId(R.id.calculator),
                                                3)),
                                1),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.bt_exponentiation), withText("^"),
                        childAtPosition(
                                allOf(withId(R.id.second_row),
                                        childAtPosition(
                                                withId(R.id.calculator),
                                                1)),
                                3),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.bt3), withText("3"),
                        childAtPosition(
                                allOf(withId(R.id.four_row),
                                        childAtPosition(
                                                withId(R.id.calculator),
                                                3)),
                                2),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.bt_parenthesis_start), withText("("),
                        childAtPosition(
                                allOf(withId(R.id.first_row),
                                        childAtPosition(
                                                withId(R.id.calculator),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.bt6), withText("6"),
                        childAtPosition(
                                allOf(withId(R.id.third_row),
                                        childAtPosition(
                                                withId(R.id.calculator),
                                                2)),
                                2),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.bt6), withText("6"),
                        childAtPosition(
                                allOf(withId(R.id.third_row),
                                        childAtPosition(
                                                withId(R.id.calculator),
                                                2)),
                                2),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.bt6), withText("6"),
                        childAtPosition(
                                allOf(withId(R.id.third_row),
                                        childAtPosition(
                                                withId(R.id.calculator),
                                                2)),
                                2),
                        isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.bt_subtraction), withText("−"),
                        childAtPosition(
                                allOf(withId(R.id.third_row),
                                        childAtPosition(
                                                withId(R.id.calculator),
                                                2)),
                                4),
                        isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.bt_parenthesis_start), withText("("),
                        childAtPosition(
                                allOf(withId(R.id.first_row),
                                        childAtPosition(
                                                withId(R.id.calculator),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton10.perform(click());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.bt5), withText("5"),
                        childAtPosition(
                                allOf(withId(R.id.third_row),
                                        childAtPosition(
                                                withId(R.id.calculator),
                                                2)),
                                1),
                        isDisplayed()));
        appCompatButton11.perform(click());

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.bt_multiplication), withText("✕"),
                        childAtPosition(
                                allOf(withId(R.id.second_row),
                                        childAtPosition(
                                                withId(R.id.calculator),
                                                1)),
                                4),
                        isDisplayed()));
        appCompatButton12.perform(click());

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(R.id.bt3), withText("3"),
                        childAtPosition(
                                allOf(withId(R.id.four_row),
                                        childAtPosition(
                                                withId(R.id.calculator),
                                                3)),
                                2),
                        isDisplayed()));
        appCompatButton13.perform(click());

        ViewInteraction appCompatButton14 = onView(
                allOf(withId(R.id.bt_parenthesis_end), withText(")"),
                        childAtPosition(
                                allOf(withId(R.id.first_row),
                                        childAtPosition(
                                                withId(R.id.calculator),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatButton14.perform(click());

        ViewInteraction appCompatButton15 = onView(
                allOf(withId(R.id.bt_addition), withText("+"),
                        childAtPosition(
                                allOf(withId(R.id.four_row),
                                        childAtPosition(
                                                withId(R.id.calculator),
                                                3)),
                                4),
                        isDisplayed()));
        appCompatButton15.perform(click());

        ViewInteraction appCompatButton16 = onView(
                allOf(withId(R.id.bt1), withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.four_row),
                                        childAtPosition(
                                                withId(R.id.calculator),
                                                3)),
                                0),
                        isDisplayed()));
        appCompatButton16.perform(click());

        ViewInteraction appCompatButton17 = onView(
                allOf(withId(R.id.bt0), withText("0"),
                        childAtPosition(
                                allOf(withId(R.id.five_row),
                                        childAtPosition(
                                                withId(R.id.calculator),
                                                4)),
                                0),
                        isDisplayed()));
        appCompatButton17.perform(click());

        ViewInteraction appCompatButton18 = onView(
                allOf(withId(R.id.bt_parenthesis_end), withText(")"),
                        childAtPosition(
                                allOf(withId(R.id.first_row),
                                        childAtPosition(
                                                withId(R.id.calculator),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatButton18.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
