package com.example.nbmb.datacurator;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.nbmb.datacurator.database.DisableDataHelper;
import com.example.nbmb.datacurator.service.WifiDisableService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DisableWifiTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    @Rule
    public  ActivityTestRule<WifiDisable> wifiActivityTestRule = new ActivityTestRule<WifiDisable>(WifiDisable.class);
    @Before
    public void init()
    {
        activityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
        wifiActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
    }
    @Test
    public void detect() throws Exception {
        WifiManager wifi =(WifiManager)wifiActivityTestRule.getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.duration)).perform(replaceText("5"),closeSoftKeyboard());
        onView(withId(R.id.toggleButton)).perform(click());
        assert(wifi.isWifiEnabled() == false);
        Thread.sleep(6000);
        assert(wifi.isWifiEnabled() == true);
        pressBack();
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.duration)).perform(replaceText("10"),closeSoftKeyboard());
        onView(withId(R.id.toggleButton)).perform(click());
        assert(wifi.isWifiEnabled() == false);
        onView(withId(R.id.toggleButton)).perform(click());
        assert(wifi.isWifiEnabled() == true);
    }
}
