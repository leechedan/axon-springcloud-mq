package org.github.axon.tag.base.domain.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

public class BasePageRequest extends PageRequest
        implements Serializable {

    public BasePageRequest(){
        super(1, 10, Sort.unsorted());
    }

    public BasePageRequest(int page, int size, Sort sort){
        super(page, size, sort);
    }

    public PageRequest page(){
        return this;
    }
}
