package com.github.entropyfeng.mydb.core.obj;

import com.github.entropyfeng.mydb.common.CommonConstant;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.SupportValue;
import com.github.entropyfeng.mydb.expection.OutOfBoundException;
import com.github.entropyfeng.mydb.util.BytesUtil;
import com.github.entropyfeng.mydb.util.CommonUtil;
import com.google.protobuf.ByteString;
import com.google.protobuf.DoubleValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import static com.github.entropyfeng.mydb.core.SupportValue.LONG;
import static java.lang.Enum.valueOf;

/**
 * @author entropyfeng
 */
public class TurtleBaseValue {
   private int type;
   private byte[] values;

  public TurtleBaseValue(TurtleProtoBuf.TurtleValue value){

     final TurtleProtoBuf.TurtleParaType paraType=value.getTurtleParaType();
     final ByteString byteString=value.getValues();
     switch (paraType){
        case STRING:{
           this.type= TurtleProtoBuf.TurtleParaType.STRING_VALUE;
           this.values=byteString.toByteArray();
           break;
        }
        case LONG:{
           this.type= TurtleProtoBuf.TurtleParaType.LONG_VALUE;
           this.values=byteString.toByteArray();
           break;
        }
        case INTEGER:{
           this.type= TurtleProtoBuf.TurtleParaType.INTEGER_VALUE;
           this.values=byteString.toByteArray();
           break;
        }
        case DOUBLE:{
           this.type= TurtleProtoBuf.TurtleParaType.DOUBLE_VALUE;
           this.values=byteString.toByteArray();
           break;
        }
        case NUMBER_INTEGER:{
           this.type= TurtleProtoBuf.TurtleParaType.NUMBER_INTEGER_VALUE;
           this.values=byteString.toByteArray();
           break;
        }
        case NUMBER_DECIMAL:{
           this.type= TurtleProtoBuf.TurtleParaType.NUMBER_DECIMAL_VALUE;
           this.values=byteString.toByteArray();
           break;
        }
        default:throw new UnsupportedOperationException("unSupport paraType"+paraType.toString());
     }



  }

  private TurtleBaseValue(byte[] bytes,int type){
     this.type=type;
     this.values=bytes;
  }

   public TurtleBaseValue(int value) {
      this(BytesUtil.allocate4(value), TurtleProtoBuf.TurtleParaType.INTEGER_VALUE);
   }

   public TurtleBaseValue(double value) {
      this(BytesUtil.allocate8(value), TurtleProtoBuf.TurtleParaType.DOUBLE_VALUE);
   }


   public TurtleBaseValue(long value) {
      this(BytesUtil.allocate8(value), TurtleProtoBuf.TurtleParaType.LONG_VALUE);
   }

   public TurtleBaseValue(BigInteger value) {
      this(value.toByteArray(), TurtleProtoBuf.TurtleParaType.NUMBER_INTEGER_VALUE);
   }

   public TurtleBaseValue(BigDecimal value) {
      this(value.toPlainString().getBytes(), TurtleProtoBuf.TurtleParaType.NUMBER_DECIMAL_VALUE);
   }

}
