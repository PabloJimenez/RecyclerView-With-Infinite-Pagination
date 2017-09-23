package com.conectors.innova.recyclerviewpagination.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moi-chan on 21/09/17.
 */

public class Item {

    private String title;

    public Item() {

    }

    public Item(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Creating 10 dummy content for list.
     * @param itemCount
     * @return
     */
    public static List<Item> createItems(int itemCount) {
        List<Item> items = new ArrayList<>();
        for ( int i = 0; i < 10; i++) {
            Item item = new Item("Item " + (itemCount == 0 ?
                    (itemCount + 1 + i) : (itemCount + i)));
            items.add(item);
        }
        return items;
    }

}
