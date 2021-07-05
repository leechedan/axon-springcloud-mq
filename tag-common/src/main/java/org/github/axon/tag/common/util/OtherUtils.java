package org.github.axon.tag.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.util.UUID;

/**
 * 未分类的工具
 *
 * @author ve
 * @date 2020/1/5 14:36
 */
@Slf4j
public enum OtherUtils {
    ;
    static final char[] chars = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
            'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
            'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm',
            'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D',
            'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M'};

    public static String getGUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String rendomCode(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(chars[RandomUtils.nextInt(0, chars.length)]);
        }
        return sb.toString();
    }

}
