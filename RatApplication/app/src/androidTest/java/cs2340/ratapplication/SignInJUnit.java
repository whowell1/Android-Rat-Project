package cs2340.ratapplication;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cs2340.ratapplication.controllers.SignupPage;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)

public class SignInJUnit
{
    @Rule
    public ActivityTestRule<SignupPage> mActivityRule = new ActivityTestRule<>(
            SignupPage.class);
    @Test
    public void noAtSign(){
        onView(withId(R.id.etName)).perform(typeText("wrenhowell.com"));
        onView(withId(R.id.etPassword)).perform(typeText("12345678"));
        onView(withId(R.id.signupBtn)).perform(click());
        closeSoftKeyboard();

        onView(withText("Your email is incorrect"));

    }
    @Test
    public void shortPassword() {
        onView(withId(R.id.etName)).perform(typeText("Ross"));
        closeSoftKeyboard();
        onView(withId(R.id.etPassword)).perform(typeText("3"));
        closeSoftKeyboard();
        onView(withId(R.id.signupBtn)).perform(click());


        onView(withText("Your pass word is too short try again"));


    }
    @Test
    public void success() {

        onView(withId(R.id.etName)).perform(typeText("wrentarohowell@gmail.com"));
        onView(withId(R.id.etPassword)).perform(typeText("58635863"));
        onView(withId(R.id.signupBtn)).perform(click());
        closeSoftKeyboard();

        onView(withText("Login successfully"));



    }


}