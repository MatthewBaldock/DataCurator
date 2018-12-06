package com.example.nbmb.datacurator;

import android.app.NotificationManager;
import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.nbmb.datacurator.data_alerts.DataUsage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AlertDataTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    @Rule
    public  ActivityTestRule<DataAlertActivity> dataAlertActivityActivityTestRule = new ActivityTestRule<DataAlertActivity>(DataAlertActivity.class);

    @Before
    public void init()
    {
        activityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
        dataAlertActivityActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
    }
    @Test
    public void detect() throws Exception {
        NotificationManager notificationManager = dataAlertActivityActivityTestRule.getActivity().getApplicationContext().getSystemService(NotificationManager.class);
        DataUsage du = new DataUsage(dataAlertActivityActivityTestRule.getActivity().getApplicationContext());
        onView(withId(R.id.button2)).perform(click());
        onView(withId(R.id.numBytesEditText)).perform(replaceText("5"),closeSoftKeyboard());
        onView(withId(R.id.numTimeEditText)).perform(replaceText("5"),closeSoftKeyboard());
        onView(withId(R.id.enableAlertSwitch)).perform(click());
        assert(notificationManager.areNotificationsEnabled() == true);
        Thread.sleep(6000);
        assert(notificationManager.getActiveNotifications().length > 0);
        assert(du.getDataUsage() > 0);
    }
}
