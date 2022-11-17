package com.maiqu.evaluatorPlatform.model.update;

import com.maiqu.evaluatorPlatform.model.common.Shared;
import lombok.Data;

import java.util.Date;

/**
 * @author ht
 */
@Data
public class TeamUpdateRequest {

    protected Long teamId;

    /**
     * 队伍数量
     */
    protected Integer teamNumber;

    /**
     * 描述
     */
    protected String description;

    /**
     * 队伍周期
     */
    protected Integer teamCycle;

    /**
     * 队伍名称
     */
    protected String teamName;
}
