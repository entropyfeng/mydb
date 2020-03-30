package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author entropyfeng
 */
public class TurtleClientProtoEncoder extends MessageToByteEncoder<TurtleProtoBuf.ClientCommand> {

    private static final Logger logger= LoggerFactory.getLogger(TurtleClientProtoEncoder.class);


    @Override
    protected void encode(ChannelHandlerContext ctx, TurtleProtoBuf.ClientCommand msg, ByteBuf out) throws Exception {
        int bodyLen = msg.getSerializedSize();
        int headerLen = computeRawVarint32Size(bodyLen);
        out.ensureWritable(headerLen + bodyLen);
        writeRawVarint32(out, bodyLen);
        out.writeBytes(msg.toByteArray());

        logger.info("do encode");
    }


    /**
     * Writes protobuf varint32 to (@link ByteBuf).
     * @param out to be written to
     * @param value to be written
     */
    static void writeRawVarint32(ByteBuf out, int value) {
        while (true) {
            if ((value & ~0x7F) == 0) {
                out.writeByte(value);
                return;
            } else {
                out.writeByte((value & 0x7F) | 0x80);
                value >>>= 7;
            }
        }
    }

    /**
     * Computes size of protobuf varint32 after encoding.
     * @param value which is to be encoded.
     * @return size of value encoded as protobuf varint32.
     */
    static int computeRawVarint32Size(final int value) {
        if ((value & (0xffffffff <<  7)) == 0) {
            return 1;
        }
        if ((value & (0xffffffff << 14)) == 0) {
            return 2;
        }
        if ((value & (0xffffffff << 21)) == 0) {
            return 3;
        }
        if ((value & (0xffffffff << 28)) == 0) {
            return 4;
        }
        return 5;
    }
}
