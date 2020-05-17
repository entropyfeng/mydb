package base;

import org.junit.Test;

import java.util.ArrayList;


public class HashTest {



    @Test
    public void test7(){
        hand(new ArrayList<>(),3,0);
    }

    public void hand(ArrayList<Integer> integers,int size,int i){
        if (integers.size()==size){
            integers.forEach(System.out::print);
            System.out.println();
        }else {
            for (int j = 0; j < 3; j++) {
                integers.add(j);
                hand(integers,size,i++);
                integers.remove(integers.size()-1);
            }
        }
    }
    


    @Test
    public void test(){
        System.out.println("tt".hashCode());
        System.out.println("uU".hashCode());
        System.out.println("v6".hashCode());

        System.out.println("tttt".hashCode());
        System.out.println("ttuU".hashCode());
        System.out.println("ttttttuU".hashCode());
        System.out.println("ttuUtttt".hashCode());
    }

}
