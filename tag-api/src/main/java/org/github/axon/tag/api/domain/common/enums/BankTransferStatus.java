package org.github.axon.tag.api.domain.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BankTransferStatus {

    STARTED(1, "处理"),
    FAILED(2, "失败"),
    COMPLETED(3, "成功");

    private Integer code;

    private String value;



}
