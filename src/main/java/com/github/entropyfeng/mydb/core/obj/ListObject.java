package com.github.entropyfeng.mydb.core.obj;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author entropyfeng
 */
public class ListObject extends BaseObject {

    private HashMap<String, LinkedList<TurtleValue>> listMap;

    public ListObject(HashMap<String, LinkedList<TurtleValue>> listMap) {
        super();
        this.listMap = listMap;
    }

    public int size(){
        return listMap.size();
    }




}
