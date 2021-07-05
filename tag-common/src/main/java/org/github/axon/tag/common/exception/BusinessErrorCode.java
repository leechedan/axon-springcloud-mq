package org.github.axon.tag.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lee
 * @date 2018/7/25 15:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessErrorCode {
    private static final long serialVersionUID = 1L;
    private String status;
    private String msg;
}
