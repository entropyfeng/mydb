package base;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;


public class HashTest {


    @Test
    public void test() {
     ArrayList<String> paras=new ArrayList<>();
     paras.add("tt");
     paras.add("uU");
     paras.add("v6");
     ArrayList<String> res=new ArrayList<>();
     construct(new ArrayList<>(),3,0,res,paras);
     res.forEach(s -> Assert.assertEquals(-863286400,s.hashCode()));
    }
    public void construct(ArrayList<String> tempStrings, int size, int i, ArrayList<String> res, ArrayList<String> paras) {
        if (tempStrings.size() == size) {
            res.add(String.join("", tempStrings));
        } else {
            for (int j = 0; j < 3; j++) {
                tempStrings.add(paras.get(j));
                construct(tempStrings, size, i++, res, paras);
                tempStrings.remove(tempStrings.size() - 1);
            }
        }
    }
}
