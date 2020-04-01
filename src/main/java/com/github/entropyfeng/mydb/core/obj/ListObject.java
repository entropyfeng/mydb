package com.github.entropyfeng.mydb.core.obj;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
        return listMap==null?0:listMap.size();
    }

    void xxx(String string){
    }
    public int sizeOf(String key){
        final List<TurtleValue> turtleList=listMap.get(key);
        return turtleList==null?0:turtleList.size();
    }





}
