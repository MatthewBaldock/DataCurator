package com.example.nbmb.datacurator;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DetectWifiTest {
    @Rule
    public  ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    @Rule
    public  ActivityTestRule<DetectedNetworks> detectedNetworksActivityTestRule = new ActivityTestRule<DetectedNetworks>(DetectedNetworks.class);
    @Before
    public void init()
    {
        activityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
        detectedNetworksActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
    }
    @Test
    public void detect() throws Exception {
        int initial = detectedNetworksActivityTestRule.getActivity().wifiCount;
        onView(withId(R.id.button4)).perform(click());
        assert(initial < detectedNetworksActivityTestRule.getActivity().wifiCount);
    }
}
