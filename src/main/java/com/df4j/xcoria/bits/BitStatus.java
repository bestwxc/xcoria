package com.df4j.xcoria.bits;

import com.df4j.xcoria.exception.XcoriaException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BitStatus {

    private final Map<String, Integer> keyMap = new HashMap<String, Integer>();
    private final String[] keys;
    // byte[0]  byte[1]
    // i: 76543210
    // 000100100 00000011
    private final byte[] statusArray;

    /**
     * 初始化时，传入状态的key
     *
     * @param keys 状态key
     */
    public BitStatus(final String... keys) {
        int size = keys.length;
        this.keys = new String[size];
        System.arraycopy(keys, 0, this.keys, 0, size);
        for (int i = 0; i < size; i++) {
            keyMap.put(keys[i], i);
        }
        if (keyMap.size() != size) {
            throw new XcoriaException("存在重复的key");
        }
        int numOfBytes = size % 8 == 0 ? size / 8 : size / 8 + 1;
        this.statusArray = new byte[numOfBytes];
        Arrays.fill(this.statusArray, (byte) 0);
    }

    /**
     * 设置key
     *
     * @param key  状态key
     * @param flag 状态
     */
    public void setStatus(final String key, boolean flag) {
        checkKey(key);
        int i = keyMap.get(key);
        int bitIndex = i % 8;
        int byteIndex = i / 8;
        if (flag) {
            statusArray[byteIndex] |= (byte) (1 << bitIndex);
        } else {
            statusArray[byteIndex] &= (byte) ~(1 << bitIndex);
        }
    }

    /**
     * 获取对应的状态
     *
     * @param key 状态key
     * @return 状态是否设置
     */
    public boolean getStatus(final String key) {
        checkKey(key);
        int i = keyMap.get(key);
        int bitIndex = i % 8;
        int byteIndex = i / 8;
        byte b = statusArray[byteIndex];
        return (b & (1 << bitIndex)) != 0;
    }

    /**
     * 校验是否存在key
     *
     * @param key 状态key
     */
    private void checkKey(final String key) {
        if (!keyMap.containsKey(key)) {
            throw new XcoriaException("无对应key:" + key);
        }
    }

    /**
     * 将状态转换为stream
     *
     * @return 状态entry的stream
     */
    public Stream<BitEntry> stream() {
        return IntStream.range(0, statusArray.length)
                .boxed()
                .map(Integer::byteValue)
                .flatMap(x -> {
                    byte b = statusArray[x];
                    return IntStream.range(0, 8)
                            .boxed()
                            .map(Integer::byteValue)
                            .map(y -> {
                                int keyIndex = x * 8 + y;
                                String key = keys[keyIndex];
                                byte f = (byte) (b & (1 << y));
                                boolean flag = (f != 0);
                                return BitEntry.of(key, flag, keyIndex);
                            });
                });
    }

    public static class BitEntry {
        private final String key;
        private final boolean flag;
        private final int index;

        public BitEntry(String key, boolean flag, int index) {
            this.key = key;
            this.flag = flag;
            this.index = index;
        }

        public static BitEntry of(final String key, final boolean flag, final int index) {
            return new BitEntry(key, flag, index);
        }

        public String getKey() {
            return key;
        }

        public boolean isFlag() {
            return flag;
        }

        public int getIndex() {
            return index;
        }

        @Override
        public String toString() {
            return String.format("bit: index: %d, flag: %b, key: %s", index, flag, key);
        }
    }
}
