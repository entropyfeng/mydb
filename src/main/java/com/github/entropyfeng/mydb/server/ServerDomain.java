package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.core.obj.ListObject;
import com.github.entropyfeng.mydb.core.obj.SetObject;
import com.github.entropyfeng.mydb.core.obj.ValuesObject;
import com.github.entropyfeng.mydb.server.command.ValuesCommand;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * @author entropyfeng
 */
public class ServerDomain {

    private ValuesObject valuesObject;

    private ListObject listObject;

    private SetObject setObject;

    private ConcurrentLinkedDeque<ValuesExe> valuesQueue;

    private ConcurrentLinkedDeque<Runnable> listQueue;


}
