package org.learningequality.androidapp;

import android.app.Activity;
import android.app.FragmentBreadCrumbs;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import java.io.File;


/**
 * An activity representing a list of Nodes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link NodeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link NodeListFragment} and the item details
 * (if present) is a {@link NodeDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link NodeListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class NodeListActivity extends Activity
        implements NodeListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_list);

        if (findViewById(R.id.node_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            NodeListFragment fragment = new NodeListFragment();
            getFragmentManager().beginTransaction()
                                .replace(R.id.node_list_container, fragment)
                                .commit();

            // boilerplate for initializing the breadcrumbs
            FragmentBreadCrumbs crumbs = (FragmentBreadCrumbs) findViewById(R.id.breadcrumbs);
            crumbs.setActivity(this);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Callback method from {@link NodeListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     * @param file
     */
    @Override
    public void onItemSelected(File file) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.

            Bundle arguments = new Bundle();


            android.app.Fragment fragment;
            int id_to_replace;

             if (file.isDirectory()) {
                fragment = new NodeListFragment();
                arguments.putString(NodeListFragment.ARG_TOPIC_PATH_ID, file.getAbsolutePath());
                id_to_replace = R.id.node_list_container;
             } else {
                 fragment = new NodeDetailFragment();
                 arguments.putString(NodeDetailFragment.ARG_TOPIC_PATH_ID, file.getAbsolutePath());
                 id_to_replace = R.id.node_detail_container;
             }


            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(id_to_replace, fragment)
                    .setBreadCrumbTitle(file.getName())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, NodeDetailActivity.class);
            detailIntent.putExtra(NodeDetailFragment.ARG_TOPIC_PATH_ID, file);
            startActivity(detailIntent);
        }
    }
}
