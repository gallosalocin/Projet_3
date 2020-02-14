package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class) public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule = new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least one item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(ITEMS_COUNT));
        // When : perform a click on a delete icon
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).perform(actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(ITEMS_COUNT - 1));
    }

    /**
     * When we click on an item, the item is displayed on the details activity
     */
    @Test
    public void myNeighboursList_clickOnNeighbour_shouldDisplayDetailsActivity() {
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).perform(actionOnItemAtPosition(2, click()));
        onView(withId(R.id.details_content)).check(matches(isDisplayed()));
    }

    /**
     * When we click on an item, it's corresponding name should be displayed
     */
    @Test
    public void myNeighboursList_onDetailsActivity_shouldDisplayRightName() {
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).perform(actionOnItemAtPosition(3, click()));
        onView(withId(R.id.tv_name_photo)).check(matches(withText("Vincent")));
    }

    /**
     * We ensure that our recyclerview of favorite tab is displaying only favorite items
     */
    @Test
    public void myNeighboursList_onFavoritesTab_shouldDisplayOnlyFavoritesNeighbours() {
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).perform(swipeLeft());
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(0));
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).perform(swipeRight());
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).perform(actionOnItemAtPosition(4, click()));
        onView(withId(R.id.fab_favorite)).perform(click());
        onView(withId(R.id.tb_details)).perform(pressBack());
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).perform(swipeLeft());
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(withItemCount(1));
    }
}