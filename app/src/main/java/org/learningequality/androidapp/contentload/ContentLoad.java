package org.learningequality.androidapp.contentload;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ContentLoad {
    /**
     * An array of sample (Node) items.
     */
    public List<Node> ITEMS = new ArrayList<Node>();

    /**
     * A map of sample (Node) items, by ID.
     */
    public Map<String, Node> ITEM_MAP = new HashMap<String, Node>();

    private void addItem(Node item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    //constructor 1
    public ContentLoad() {
        File root = Environment.getExternalStorageDirectory();

        populateItemMap(root);
    }

    //constructor 2
    public ContentLoad(File root) {
        populateItemMap(root);
    }

    private void populateItemMap(File root) {
//
//        File unicefFolder = root.listFiles(new FilenameFilter() {
//            @Override
//            public boolean accept(File file, String s) {
//                return s.equals("UNICEF");
//            }
//        })[0];
//
//        for (File f : unicefFolder.listFiles()) {
//            Node n = new Node(f.getName(), f);
//            addItem(n);
//        }
        for (File f: root.listFiles()) {
            Node n = new Node(f.getName(), f);
            addItem(n);
        }
    }

    /**
     * A node item representing a piece of content.
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