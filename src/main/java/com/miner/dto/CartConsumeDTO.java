package com.miner.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * Created by hushangjie on 2017/10/11.
 */
@Data
@Accessors(chain = true)
public class CartConsumeDTO {
    @NotNull(message = "该字段不能为空")
    private String url;
    @NotNull(message = "该字段不能为空")
    private String code;
    @NotNull(message = "该字段不能为空")
    private int type;
}
