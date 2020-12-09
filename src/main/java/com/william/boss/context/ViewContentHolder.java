package com.william.boss.context;

import com.william.boss.vo.KeyValue;

/**
 * 缓存视图名称及对应生成的html
 * @author john
 */
@SuppressWarnings("unused")
public class ViewContentHolder {
    private static final ThreadLocal<KeyValue<String, String>> CONTENT = new ThreadLocal<>();

    public static void set(KeyValue<String, String> val) {
        CONTENT.set(val);
    }
    public static KeyValue<String, String> get() {
        return CONTENT.get();
    }

    public static String getHtml() {
        return CONTENT.get() == null ? null : CONTENT.get().getValue();
    }

    public static void remove() {
        CONTENT.remove();
    }

}
