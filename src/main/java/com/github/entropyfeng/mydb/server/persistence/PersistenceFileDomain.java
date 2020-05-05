package com.github.entropyfeng.mydb.server.persistence;


import java.io.File;

/**
 * @author entropyfeng
 */
public class PersistenceFileDomain {

    File valuesDumpFile;
    File listDumpFile;
    File hashDumpFile;
    File setDumpFile;
    File orderSetDumpFile;

    public PersistenceFileDomain(){

    }


    public File getValuesDumpFile() {
        return valuesDumpFile;
    }

    public File getListDumpFile() {
        return listDumpFile;
    }

    public File getHashDumpFile() {
        return hashDumpFile;
    }

    public File getSetDumpFile() {
        return setDumpFile;
    }

    public File getOrderSetDumpFile() {
        return orderSetDumpFile;
    }



    public void setValuesDumpFile(File valuesDumpFile) {
        this.valuesDumpFile = valuesDumpFile;
    }

    public void setListDumpFile(File listDumpFile) {
        this.listDumpFile = listDumpFile;
    }

    public void setHashDumpFile(File hashDumpFile) {
        this.hashDumpFile = hashDumpFile;
    }

    public void setSetDumpFile(File setDumpFile) {
        this.setDumpFile = setDumpFile;
    }

    public void setOrderSetDumpFile(File orderSetDumpFile) {
        this.orderSetDumpFile = orderSetDumpFile;
    }
}
