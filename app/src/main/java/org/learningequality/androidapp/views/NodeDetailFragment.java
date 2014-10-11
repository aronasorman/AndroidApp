package org.learningequality.androidapp.views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.learningequality.androidapp.R;

import java.io.File;

/**
 * A fragment representing a single Node detail screen.
 * This fragment is either contained in a {@link NodeListActivity}
 * in two-pane mode (on tablets) or a {@link NodeDetailActivity}
 * on handsets.
 */
public class NodeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_TOPIC_PATH_ID = "org.fle.android_app.node_detail.topic_path";

    private File mFile;

    /**
     * The dummy file this fragment is presenting.
     */
//    private ContentLoad.Node mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NodeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String filePath = getArguments().getString(ARG_TOPIC_PATH_ID);
        mFile = new File(filePath);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_node_detail, container, false);

        TextView tv = (TextView) rootView.findViewById(R.id.node_detail);
        tv.setText(mFile.getName());

        return rootView;
    }
}
