package com.example.nbmb.datacurator;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListView;

import com.example.nbmb.datacurator.database.DisableDataHelper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SaveWifiTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    @Rule
    public  ActivityTestRule<SavedNetworks> savedNetworksActivityTestRule = new ActivityTestRule<SavedNetworks>(SavedNetworks.class);
    @Before
    public void init()
    {
        activityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
        savedNetworksActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
    }
    @Test
    public void detect() throws Exception {

        DisableDataHelper helper = new DisableDataHelper(savedNetworksActivityTestRule.getActivity().context);
        String item = "TEST";
        int initial = savedNetworksActivityTestRule.getActivity().savedList.length;
        helper.addNetwork(item,"somepassword","WPA");
        onView(withId(R.id.button3)).perform(click());
        assert(initial < savedNetworksActivityTestRule.getActivity().savedList.length);
        assertEquals(item ,savedNetworksActivityTestRule.getActivity().savedList[0]);
        pressBack();
        helper.removeNetworkItem(item);
        onView(withId(R.id.button3)).perform(click());
        assert(initial == savedNetworksActivityTestRule.getActivity().savedList.length);
    }
}
