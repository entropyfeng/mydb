package base;

import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.Test;

import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class TestReflection {

    public Void returnVoid(){
        return null;
    }

    public void returnvoid(){

    }
    public Collection<String> returnZeroStrings(){
        return new ArrayList<>();
    }
    public Collection<String> returnNullZeroStrings(){
      ArrayList<String> arrayList= new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            arrayList.add(String.valueOf(i));
        }
      return arrayList;
    }

    @Test
    public void testvoid() throws NoSuchMethodException {
        Method method= TestReflection.class.getDeclaredMethod("returnvoid");
        Assert.assertEquals(Void.TYPE,method.getReturnType());
    }

    @Test
    public void testVoid() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method method= TestReflection.class.getDeclaredMethod("returnVoid");
        Assert.assertEquals(Void.class,method.getReturnType());
        Object object= method.invoke(this);
        Assert.assertNull(object);
    }
    @Test
    public void testReturnStrings() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method= TestReflection.class.getDeclaredMethod("returnZeroStrings");
       Object res= method.invoke(this);
       Assert.assertEquals(Collection.class,method.getReturnType());
       Assert.assertEquals(0,((Collection)res).size());
    }

    @Test
    public void testReturnNullZeroStrings() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method= TestReflection.class.getDeclaredMethod("returnNullZeroStrings");
        Object res=method.invoke(this);
       String string= (String) ((Collection)res).stream().reduce((a,b)-> (String)a+(String) b).get();
       Assert.assertEquals("0123456789",string);
    }

}
