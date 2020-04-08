package server;

import com.github.entropyfeng.mydb.common.CommonException;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;

public class TestInvoke {


    @Test(expected = NullPointerException.class)
    public void testNullPointer() {
        Student student = new Student();
        Method method = null;
        try {
            method = Student.class.getMethod("sayNull");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (method != null) {
            try {
                method.invoke(student);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                deal(e.getTargetException().toString());

            }
        }

    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnsupportedOperation() {
        Student student = new Student();
        Method method = null;
        try {
            method = Student.class.getMethod("sayOperation");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (method != null) {
            try {
                method.invoke(student);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {

                deal(e.getTargetException().toString());

            }
        }

    }

    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElement() {
        Student student = new Student();
        Method method = null;
        try {
            method = Student.class.getMethod("sayElement");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (method != null) {
            try {
                method.invoke(student);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                deal(e.getTargetException().toString());

            }
        }
    }

    private void deal(String string) {

        switch (string) {
            case CommonException.UNSUPPORTED_OPERATION:
                throw new UnsupportedOperationException();
            case CommonException.NO_SUCH_ELEMENT:
                throw new NoSuchElementException();
            case CommonException.NULL_POINTER:
                throw new NullPointerException();
            default:
                throw new RuntimeException(string);
        }
    }

    static class Student {
        public void sayNull() {
            throw new NullPointerException();
        }

        public void sayOperation() {
            throw new UnsupportedOperationException();
        }

        public void sayElement() {
            throw new NoSuchElementException();
        }

    }
}
