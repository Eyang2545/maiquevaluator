package com.maiqu.evaluatorPlatform.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ListRequest implements Serializable {
    private Long teamId;
}
