package org.learningequality.androidapp.dummy;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample file for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ContentLoad {

    /**
     * An array of sample (dummy) items.
     */
    public List<Node> ITEMS = new ArrayList<Node>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public Map<String, Node> ITEM_MAP = new HashMap<String, Node>();

    private void addItem(Node item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public ContentLoad() {
        File root = Environment.getExternalStorageDirectory();

        populateItems(root);
    }

    public ContentLoad(File root) {
        populateItems(root);
    }

    private void populateItems(File root) {
        for (File f: root.listFiles()) {
            Node n = new Node(f.getName(), f);
            addItem(n);
        }
    }

    /**
     * A dummy item representing a piece of file.
     */
    public static class Node {
        public String id;
        public File file;

        public Node(String id, File file) {
            this.id = id;
            this.file = file;
        }

        @Override
        public String toString() {
            return id;
        }
    }
}
