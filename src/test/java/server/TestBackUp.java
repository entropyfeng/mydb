package server;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

public class TestBackUp {

    @Test
    public void test()throws Exception{


        File file=new File("./values.dump");
        System.out.println(file.length());
        file.createNewFile();
        System.out.println(file.length());
        FileOutputStream fileOutputStream=new FileOutputStream(file);


    }
}
