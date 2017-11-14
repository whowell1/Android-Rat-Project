//package cs2340.ratapplication;
//
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//
//import cs2340.ratapplication.controllers.SignupPage;
//
//@RunWith(AndroidJUnit4.class)
//@LargeTest
///**
// * Created by wren on 11/10/17.
// */
//
//public class aSigninTest {
//
//
//    private String mStringToBetyped;
//
//    @Rule
//    public ActivityTestRule<SignupPage> mActivityRule = new ActivityTestRule<>(
//            SignupPage.class);
//
//    @Before
//    public void initValidString() {
//        // Specify a valid string.
//        mStringToBetyped = "Espresso";
//    }
//
//    @Test
//    public void changeText_sameActivity() {
//        // Type text and then press the button.
//        onView(withId(R.id.editTextUserInput))
//                .perform(typeText(mStringToBetyped), closeSoftKeyboard());
//        onView(withId(R.id.changeTextBt)).perform(click());
//
//        // Check that the text was changed.
//        onView(withId(R.id.textToBeChanged))
//                .check(matches(withText(mStringToBetyped)));
//    }
//}
//
//
//
//
//
//
//
//
//
