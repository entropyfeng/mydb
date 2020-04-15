package base;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

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
    @SuppressWarnings("rawtypes")
    public void testReturnStrings() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method= TestReflection.class.getDeclaredMethod("returnZeroStrings");
       Object res= method.invoke(this);
       Assert.assertEquals(Collection.class,method.getReturnType());
       Assert.assertEquals(0,((Collection)res).size());
    }

    @Test
    @SuppressWarnings({"unchecked","rawtypes"})
    public void testReturnNullZeroStrings() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method= TestReflection.class.getDeclaredMethod("returnNullZeroStrings");
        Object res=method.invoke(this);
        StringBuilder stringBuilder=new StringBuilder();

       ((Collection)res).forEach(object -> stringBuilder.append((String) object));
       Assert.assertEquals("0123456789",stringBuilder.toString());
    }


}
