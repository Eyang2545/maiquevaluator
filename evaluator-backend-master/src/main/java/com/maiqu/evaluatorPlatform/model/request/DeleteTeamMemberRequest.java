package com.maiqu.evaluatorPlatform.model.request;

import lombok.Data;

import java.io.Serializable;


@Data
public class DeleteTeamMemberRequest implements Serializable {
    private Long id;
}
