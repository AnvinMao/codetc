package com.codetc.web.model.vo;

import lombok.Data;

@Data
public class TokenVO {
    private String accessToken;

    private Long expired;
}
