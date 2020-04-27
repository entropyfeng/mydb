package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.config.CommonConfig;
import com.github.entropyfeng.mydb.config.Constant;
import com.github.entropyfeng.mydb.core.domain.ValuesDomain;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.regex.Pattern.compile;


/**
 * @author entropyfeng
 */
public class DumpFactory {


    public static void loadAll() {

    }

    public static ValuesDomain loadValues() {

        String path = CommonConfig.getProperties().getProperty(Constant.BACK_UP_PATH_NAME);
        Pattern backupPattern = compile("^[1-9]+(-hash.dump|-list.dump|-orderSet.dump|-set.dump|-values.dump)$");
        Pattern valuesPattern = compile("-values.dump$");
        Pattern listPattern = compile("-list.dump$");
        Pattern setPattern = compile("-list.dump$");
        Pattern hashPattern = compile("-hash.dump$");
        Pattern orderSetPattern = compile("-orderSet.dump$");

        FilenameFilter filter = (dir, name) -> backupPattern.matcher(name).matches();
        File folder = new File(path);
        //如果不存在文件夹，则新建一个文件夹。
        if (!folder.exists()) {
            folder.mkdir();
        }
        String[] names = folder.list(filter);
        if (names != null) {
            Optional<String> valuesFilename = Arrays.stream(names).filter(s -> valuesPattern.matcher(s).matches()).max(String::compareTo);
            Optional<String> listFileName = Arrays.stream(names).filter(s -> listPattern.matcher(s).matches()).max(String::compareTo);
            Optional<String> setFileName = Arrays.stream(names).filter(s -> setPattern.matcher(s).matches()).max(String::compareTo);
            Optional<String> hashFileName = Arrays.stream(names).filter(s -> hashPattern.matcher(s).matches()).max(String::compareTo);
            Optional<String> orderSetFileName = Arrays.stream(names).filter(s -> orderSetPattern.matcher(s).matches()).max(String::compareTo);

            if (valuesFilename.isPresent()){
                File valuesDump=new File(path+valuesFilename.get());
                try {
                    FileInputStream fileInputStream=new FileInputStream(valuesDump);

                    DataInputStream dataInputStream=new DataInputStream(fileInputStream);
                    ValuesDomain.read(dataInputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (listFileName.isPresent()){
                File listDump=new File(path+listFileName.get());
            }
            if (setFileName.isPresent()){
                File setFileDump=new File(path+setFileName.get());
            }
            if (hashFileName.isPresent()){
                File hashFileDump=new File(path+hashFileName.get());
            }
            if (orderSetFileName.isPresent()){
                File orderSetFileDump=new File(path+orderSetFileName.get());
            }




    
        
        }


        return null;

    }

    public static void main(String[] args) throws Exception {



    }

}
