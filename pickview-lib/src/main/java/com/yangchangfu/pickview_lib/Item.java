package com.yangchangfu.pickview_lib;

import java.util.List;

/**
 * Created by apple on 16/4/26.
 */
public class Item {

    public String name;
    public List<Item> items;

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", items=" + items +
                '}';
    }
}