package org.learningequality.androidapp.dummy;

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
     * An array of sample (dummy) items.
     */
    public static List<Node> ITEMS = new ArrayList<Node>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Node> ITEM_MAP = new HashMap<String, Node>();

    static {
        // Add 3 sample items.
        addItem(new Node("1", "Item 1"));
        addItem(new Node("2", "Item 2"));
        addItem(new Node("3", "Item 3"));
    }

    private static void addItem(Node item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Node {
        public String id;
        public String content;

        public Node(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
