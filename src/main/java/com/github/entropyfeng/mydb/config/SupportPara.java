package com.github.entropyfeng.mydb.config;

/**
 * @author entropyfeng
 * 支持参数类型
 */
public enum SupportPara {
    /**
     * {@link String}
     */
    STRING((byte) 0),
    /**
     * {@link Integer}
     */
    INTEGER((byte) 1),
    /**
     * {@link Long}
     */
    LONG((byte) 2),
    /**
     * {@link Double}
     */
    DOUBLE((byte) 3),
    /**
     * 使用字符串表示的整数
     */
    NUMBER_INTEGER((byte) 4),

    /**
     * 使用字符串表示的非整数
     */
    NUMBER_DECIMAL((byte) 5),
    /**
     * {@link com.github.entropyfeng.mydb.core.obj.TurtleObject}
     */
    TURTLE_OBJECT((byte) 6),
    /**
     * 集合元素
     */
    COLLECTION((byte) 7);


    private byte type;

    private SupportPara(byte type) {
        this.type = type;
    }

    public byte toType() {
        return type;
    }

    public static SupportPara getSupportParaByType(byte type) throws IllegalArgumentException {
        for (SupportPara o : SupportPara.values()) {
            if (o.type == type) {
                return o;
            }
        }
        throw new IllegalArgumentException("not exists type ->" + type);
    }

}
