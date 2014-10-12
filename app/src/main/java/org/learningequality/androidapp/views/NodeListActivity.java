package org.learningequality.androidapp.views;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import org.learningequality.androidapp.R;

import java.io.File;

public class NodeListActivity extends Activity
        implements NodeListFragment.OnNodeSelectedListener {

    int id = 0;
    static int menu_width = 200;

    private boolean mTwoPane;

    private RelativeLayout contentlayout;
    private RelativeLayout root_list;

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
            contentlayout = (RelativeLayout)findViewById(R.id.content_layout);
            root_list = (RelativeLayout)findViewById(R.id.root_layout);
            addNodeList();
   //     }
    }

    //int root_layout_id = 1;
    float mPreviousX = 0;
    float moving;
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                //float s = (int)(x - mPreviousX);
                //root_list.getX()
                moving += x - mPreviousX;
                root_list.setX(moving);
        }
        mPreviousX = x;
        return true;
    }

    @Override
    public void onNodeSelected(int fragID, File file) {
        //clear backstack. do we really need backstack all the fragments? since we have such a menu system
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        if(file.isDirectory()) {
            //first, remove what ever is displayed in the content layout
            Fragment Contentlayoutfrag = getFragmentManager().findFragmentById(R.id.node_detail_container);
            if(Contentlayoutfrag != null) {
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .remove(Contentlayoutfrag).commit();
            }
            //second, add new list
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

            //update the contentlayout position
            contentlayout.setX(menu_width*id);

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
                menu_width, RelativeLayout.LayoutParams.MATCH_PARENT);
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
       // root_list = newRelativeLayout;

        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                menu_width, RelativeLayout.LayoutParams.MATCH_PARENT);
        rlp.addRule(RelativeLayout.RIGHT_OF, id-1);
        newRelativeLayout.setLayoutParams(rlp);

        RelativeLayout rootLayout = (RelativeLayout)findViewById(R.id.root_layout);
        rootLayout.addView(newRelativeLayout);

        NodeListFragment newListFragment = new NodeListFragment();
        getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(newRelativeLayout.getId(), newListFragment).commit();
    }

}