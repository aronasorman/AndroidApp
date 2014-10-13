package org.learningequality.androidapp.views;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.learningequality.androidapp.R;

import java.io.File;

public class NodeListActivity extends Activity
        implements NodeListFragment.OnNodeSelectedListener {

    int id = 0;
    static int menu_width = 200;
    //int screen_width = 0;

    private boolean mTwoPane;

    private RelativeLayout contentlayout;
    public static CustomTouchLayout root_list;
    private MyObservable observe_id = MyObservable.get_id_tracker();

    //for testing only
    //TextView dispaly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_list);

        if (findViewById(R.id.node_detail_container) != null) {
//            The detail container view will be present only in the
//            large-screen layouts (res/values-large and
//            res/values-sw600dp). If this view is present, then the
//            activity should be in two-pane mode.
            mTwoPane = true;
            contentlayout = (RelativeLayout)findViewById(R.id.content_layout);
            root_list = (CustomTouchLayout)findViewById(R.id.root_layout);

//            Display display = getWindowManager().getDefaultDisplay();
//            Point size = new Point();
//            display.getSize(size);
//            screen_width = size.x;
//
//            dispaly = (TextView)findViewById(R.id.display);
//            dispaly.setText(" | posX: " + root_list.getX()+" width: "+root_list.getWidth());

            initialNodeList();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

//    float mPreviousX = 0;
//    float mCurrentX = 0;
//    float moving;
//    @Override
//    public boolean onTouchEvent(MotionEvent e) {
//        mCurrentX = e.getX();
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                float i = mCurrentX - mPreviousX;
//
////                //for testing only
////                float sum = root_list.getWidth() - root_list.getX() - screen_width;
////                dispaly.setText(" | posX: " + root_list.getX() + " moving: "+ moving
////                        + " | sum: " + sum + " | i: " + i + "      width: "+root_list.getWidth());
//
//                if(root_list.getX() < 0 || i < 0) {
//                    if(root_list.getX() > -menu_width*(id-1) || i > 0) {
//                        moving += i;
//                        if(moving > 0){
//                            root_list.setX(0);
//                        }else if(moving < -menu_width*(id-1)) {
//                            root_list.setX(-menu_width*(id-1));
//                        }else{
//                                root_list.setX(moving);
//                        }
//                    }
//                }
//                break;
//        }
//        mPreviousX = mCurrentX;
//        return true;
//    }

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
                //or remove what ever frags is ahead of current frag
                for (int i = fragID + 1; i <= id; i++) {
                    Fragment tobeRemoved = getFragmentManager().findFragmentById(i);
                    getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                            .remove(tobeRemoved).commit();
                    //remove the layout that contains the frag
                    RelativeLayout deletelayout = (RelativeLayout)findViewById(i);
                    root_list.removeView(deletelayout);
                }
                id = fragID;
                observe_id.set_tracked_id(id);
                addNodeList(file);
            }
        }else{
            //or remove what ever frags is ahead of current frag
            for (int i = fragID + 1; i <= id; i++) {
                Fragment tobeRemoved = getFragmentManager().findFragmentById(i);
                getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .remove(tobeRemoved).commit();
                //remove the layout that contains the frag
                RelativeLayout deletelayout = (RelativeLayout)findViewById(i);
                root_list.removeView(deletelayout);
            }
            id = fragID;
            observe_id.set_tracked_id(id);

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
//        Toast.makeText(this, " | posX: " + root_list.getX()+" width: "+root_list.getWidth(),
//                Toast.LENGTH_SHORT).show();
        Bundle arguments = new Bundle();
        RelativeLayout newRelativeLayout = new RelativeLayout(this);
        id++;
        observe_id.set_tracked_id(id);
        newRelativeLayout.setId(id);
        newRelativeLayout.setBackgroundColor(Color.parseColor("#a2d89e"));

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

    public void initialNodeList(){
        RelativeLayout newRelativeLayout = new RelativeLayout(this);
        id++;
        observe_id.set_tracked_id(id);
        newRelativeLayout.setId(id);
        newRelativeLayout.setBackgroundColor(Color.parseColor("#a2d89e"));

        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                menu_width, RelativeLayout.LayoutParams.MATCH_PARENT);
        rlp.addRule(RelativeLayout.RIGHT_OF, id-1);
        newRelativeLayout.setLayoutParams(rlp);

        RelativeLayout rootLayout = (RelativeLayout)findViewById(R.id.root_layout);
        rootLayout.addView(newRelativeLayout);

        NodeListFragment newListFragment = new NodeListFragment();
        getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(newRelativeLayout.getId(), newListFragment).commit();
    }

}