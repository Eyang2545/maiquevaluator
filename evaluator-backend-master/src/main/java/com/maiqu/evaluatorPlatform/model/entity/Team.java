package com.maiqu.evaluatorPlatform.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.maiqu.evaluatorPlatform.model.common.Shared;
import lombok.Data;

import java.util.Date;

/**
 * @author ht
 */
@Data
@TableName(value ="team")
public class Team extends Shared {
    /**
     * 测评师id
     */
    protected Long evaluatorId;

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
     * 终止时间
     */
    protected Date endTime;

    /**
     * 队伍名称
     */
    protected String teamName;



}
