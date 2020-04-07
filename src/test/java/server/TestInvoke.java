package server;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestInvoke {


    @Test
    public void test(){
        Student student=new Student();
        Method method=null;
        try {
           method= Student.class.getMethod("sayNull");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (method!=null){
            try {
                method.invoke(student);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {

               e.getTargetException();


            }
        }

    }

    static class Student{
        public void sayNull(){
            throw new NullPointerException();
        }
        public void sayOperation(){
            throw new UnsupportedOperationException();
        }
    }
}
