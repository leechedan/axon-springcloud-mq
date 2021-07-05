package org.github.axon.tag.common.util;

import cn.hutool.core.bean.BeanUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luhuancheng
 * @since 2021/6/3 11:06 AM
 */
public class ModelMapper {

    public static <T> T map(Object t, Class<T> r) {
        return null == t ? null : BeanUtil.copyProperties(t, r);
    }

    public static <T> List<T> mapToList(List<?> t, Class<T> r) {
        List<T> res = new ArrayList<>(t.size());
        t.forEach(item -> {
            T map = map(item, r);
            res.add(map);
        });
        return res;
    }
}
