package org.learningequality.androidapp.views;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.learningequality.androidapp.contentload.ContentLoad;

import java.io.File;

/**
 * A simple {@link android.app.ListFragment} subclass.
 *
 */
public class NodeListFragment extends ListFragment{
    OnNodeSelectedListener mListener;

    private ContentLoad myNode;

    public static final String ARG_TOPIC_PATH_ID = "org.fle.android.root_directory_path";

//    float mPreviousX = 0;
//    float mCurrentX = 0;
//    float moving;
//    static int menu_width = 200;
//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        Toast.makeText(getActivity(), "elieli", Toast.LENGTH_LONG).show();
//        mCurrentX = motionEvent.getX();
//        switch (motionEvent.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                float i = mCurrentX - mPreviousX;
//
////                //for testing only
////                float sum = root_list.getWidth() - root_list.getX() - screen_width;
////                dispaly.setText(" | posX: " + root_list.getX() + " moving: "+ moving
////                        + " | sum: " + sum + " | i: " + i + "      width: "+root_list.getWidth());
//                RelativeLayout root_list = (RelativeLayout)getActivity().findViewById(R.id.root_layout);
//                if(root_list.getX() < 0 || i < 0) {
//                    if(root_list.getX() > -menu_width*(2-1) || i > 0) {
//                        moving += i;
//                        if(moving > 0){
//                            root_list.setX(0);
//                        }else if(moving < -menu_width*(2-1)) {
//                            root_list.setX(-menu_width*(2-1));
//                        }else{
//                            root_list.setX(moving);
//                        }
//                    }
//                }
//                break;
//        }
//        mPreviousX = mCurrentX;
//        return true;
//    }

    public interface OnNodeSelectedListener{
        public void onNodeSelected(int fragID, File file);
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            mListener = (OnNodeSelectedListener) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement OnNodeSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        if (args != null && args.containsKey(ARG_TOPIC_PATH_ID)) {
            myNode = new ContentLoad(new File(getArguments().getString(ARG_TOPIC_PATH_ID)));
        } else {
            myNode = new ContentLoad();
        }

        setListAdapter(new ArrayAdapter<ContentLoad.Node>(getActivity(),
                android.R.layout.simple_list_item_activated_1, myNode.ITEMS));
    }

    @Override
    public  void onListItemClick(ListView l, View v, int position, long id){
        //add the info you want to send to the host activity

        //send the info to the host activity
        l.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        l.setItemChecked(position, true);
        mListener.onNodeSelected(this.getId(), myNode.ITEMS.get(position).file);
    }

    public NodeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPause(){
        super.onPause();
    }

}
