package com.github.entropyfeng.mydb.core.obj;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author entropyfeng
 */
public class ListObject extends BaseObject {

    private final HashMap<String, LinkedList<TurtleValue>> listMap;

    public ListObject() {
        super();
        this.listMap = new HashMap<>();
    }

    public int size(){
        return listMap.size();
    }




}
