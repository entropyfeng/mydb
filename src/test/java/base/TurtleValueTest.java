package base;

import com.github.entropyfeng.mydb.common.TurtleValueType;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;
import com.github.entropyfeng.mydb.common.exception.TurtleValueOutBoundsException;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TurtleValueTest {

    @Test
    public void testString(){
        String string="10086";
        TurtleValue turtleValue=new TurtleValue(string);
        turtleValue.append("11");
        Assert.assertEquals("append","1008611",turtleValue.toObject());
        turtleValue.append("#$$!$$%");
        Assert.assertEquals("append","1008611#$$!$$%",turtleValue.toObject());
    }

    @Test
    public void testBigInteger(){

        String string=String.valueOf(Integer.MAX_VALUE);
        BigInteger bigInteger=new BigInteger(string);
        TurtleValue turtleValue=new TurtleValue(bigInteger);
        turtleValue.increment(Integer.MIN_VALUE+1);
        Assert.assertEquals(BigInteger.valueOf(0),turtleValue.toObject());

        turtleValue.increment(Long.MAX_VALUE);
        Assert.assertEquals(BigInteger.valueOf(Long.MAX_VALUE),turtleValue.toObject());

        turtleValue.increment(Long.MIN_VALUE+1);
        Assert.assertEquals(BigInteger.valueOf(0),turtleValue.toObject());


        BigDecimal bigDecimal=new BigDecimal("0.10086");
        turtleValue.increment(bigDecimal);
        Assert.assertEquals(bigDecimal,turtleValue.toObject());

        Assert.assertEquals(bigDecimal.toPlainString(),((BigDecimal)turtleValue.toObject()).toPlainString());

        BigInteger bigInteger1=new BigInteger("+100861111111");
        TurtleValue turtleValue1=new TurtleValue(bigInteger1);
        Assert.assertEquals(new BigInteger("100861111111"),turtleValue1.toObject());
    }


    @Test
    public void testInt(){

        TurtleValue turtleValue=new TurtleValue(1);
        turtleValue.increment(-1);
        Assert.assertEquals(0,turtleValue.toObject());

        turtleValue.increment(1L);
        turtleValue.increment(-1L);
        Assert.assertEquals(0L,turtleValue.toObject());


        turtleValue.increment(new BigInteger("9876543210"));
        turtleValue.increment(new BigInteger("-9876543210"));
        Assert.assertEquals(new BigInteger("0"),turtleValue.toObject());

        turtleValue.increment(0.611);
        Assert.assertEquals(new BigDecimal("0.611"),turtleValue.toObject());


    }


    @Test
    public void testLong(){
        TurtleValue turtleValue=new TurtleValue(100086L);
        turtleValue.increment(-100086L);
        Assert.assertEquals(0L,turtleValue.toObject());
        Assert.assertNotEquals(0,turtleValue.toObject());

        turtleValue.increment(new BigInteger("123456789"));
        turtleValue.increment(new BigInteger("-123456789"));
        Assert.assertEquals(BigInteger.ZERO,turtleValue.toObject());

        turtleValue.increment(0.0);
        Assert.assertEquals(TurtleValueType.NUMBER_DECIMAL,turtleValue.getType());
        Assert.assertEquals(new BigDecimal("0.0"),turtleValue.toObject());
        Assert.assertNotEquals(new BigDecimal("0.00000"),turtleValue.toObject());
    }

    @Test
    public void testBigDecimal(){
        try {
          new BigDecimal("qewqrr");
        }catch (NumberFormatException e){
            Assert.assertEquals(e.getClass(),NumberFormatException.class);
        }
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < 1000000; i++) {
            stringBuilder.append("9");
        }

        BigDecimal bigDecimal=new BigDecimal(stringBuilder.toString());
        TurtleValue turtleValue=new TurtleValue(bigDecimal);
        turtleValue.increment(new BigDecimal("-"+stringBuilder.toString()));
        Assert.assertEquals(BigDecimal.ZERO,turtleValue.toObject());

    }

    @Test
    public void  testTooLongString(){

        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < Integer.MAX_VALUE/3; i++) {
            stringBuilder.append("中");
        }
        TurtleValue turtleValue=null;
        try {
            turtleValue=  new TurtleValue(stringBuilder.toString());
        }catch (TurtleValueOutBoundsException e){

          Assert.assertTrue(true);
        }
        Assert.assertNull(turtleValue);
    }
}
