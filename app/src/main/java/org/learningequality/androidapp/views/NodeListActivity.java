package org.learningequality.androidapp.views;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.RelativeLayout;

import org.learningequality.androidapp.R;

import java.io.File;

public class NodeListActivity extends Activity
        implements NodeListFragment.OnNodeSelectedListener {

    int id = 0;

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_list);

 //       if (findViewById(R.id.node_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
            addNodeList();
   //     }
    }

    @Override
    public void onNodeSelected(int fragID, File file) {
        if(file.isDirectory()) {
            if (fragID == id) {
                addNodeList(file);
            } else {
                //
                for (int i = fragID + 1; i <= id; i++) {
                    Fragment tobeRemoved = getFragmentManager().findFragmentById(i);
                    getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                            .remove(tobeRemoved).commit();
                }
                id = fragID;
                addNodeList(file);
            }
        }else{
            for (int i = fragID + 1; i <= id; i++) {
                Fragment tobeRemoved = getFragmentManager().findFragmentById(i);
                getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .remove(tobeRemoved).commit();
            }
            id = fragID;

            RelativeLayout contentlayout = (RelativeLayout)findViewById(R.id.content_layout);
            contentlayout.setX(200*id);

            Bundle arguments = new Bundle();
            int id_to_replace;
            Fragment fragment = new NodeDetailFragment();
            arguments.putString(NodeDetailFragment.ARG_TOPIC_PATH_ID, file.getAbsolutePath());
            id_to_replace = R.id.node_detail_container;
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(id_to_replace, fragment)
                //    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void addNodeList(File file){
        Bundle arguments = new Bundle();
        RelativeLayout newRelativeLayout = new RelativeLayout(this);
        id++;
        newRelativeLayout.setId(id);

        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                200, RelativeLayout.LayoutParams.MATCH_PARENT);
        rlp.addRule(RelativeLayout.RIGHT_OF, id-1);
        newRelativeLayout.setLayoutParams(rlp);

        RelativeLayout rootLayout = (RelativeLayout)findViewById(R.id.root_layout);
        rootLayout.addView(newRelativeLayout);

        NodeListFragment newListFragment = new NodeListFragment();
        arguments.putString(NodeListFragment.ARG_TOPIC_PATH_ID, file.getAbsolutePath());
        newListFragment.setArguments(arguments);
        getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(newRelativeLayout.getId(), newListFragment).commit();
    }

    public void addNodeList(){
        RelativeLayout newRelativeLayout = new RelativeLayout(this);
        id++;
        newRelativeLayout.setId(id);

        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                200, RelativeLayout.LayoutParams.MATCH_PARENT);
        rlp.addRule(RelativeLayout.RIGHT_OF, id-1);
        newRelativeLayout.setLayoutParams(rlp);

        RelativeLayout rootLayout = (RelativeLayout)findViewById(R.id.root_layout);
        rootLayout.addView(newRelativeLayout);

        NodeListFragment newListFragment = new NodeListFragment();
        getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(newRelativeLayout.getId(), newListFragment).commit();
    }

}