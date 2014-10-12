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
public class NodeListFragment extends ListFragment {
    OnNodeSelectedListener mListener;

    private ContentLoad myNode;

    public static final String ARG_TOPIC_PATH_ID = "org.fle.android.root_directory_path";

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
