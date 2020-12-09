package com.william.boss.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class KeyValue<K, V> {
    private K key;
    private V value;
}
