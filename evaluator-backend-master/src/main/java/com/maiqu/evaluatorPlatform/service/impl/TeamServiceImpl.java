package com.maiqu.evaluatorPlatform.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maiqu.evaluatorPlatform.common.ErrorCode;
import com.maiqu.evaluatorPlatform.exception.BusinessException;
import com.maiqu.evaluatorPlatform.mapper.EvaluatorMapper;
import com.maiqu.evaluatorPlatform.mapper.TeamMapper;
import com.maiqu.evaluatorPlatform.model.entity.Evaluator;
import com.maiqu.evaluatorPlatform.model.entity.Team;
import com.maiqu.evaluatorPlatform.model.update.TeamUpdateRequest;
import com.maiqu.evaluatorPlatform.service.EvaluatorService;
import com.maiqu.evaluatorPlatform.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;


/**
 * @author ht
 */

@Service
@Slf4j
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

    @Override
    public Long addTeam(Long evaluatorId, Team team) {
        //检验是否合法
        checkLegal(evaluatorId,team);


        //偏移时间，根据项目周期，设置开始和结束时间
        Date endTime = DateUtil.offsetDay(DateUtil.date(),team.getTeamCycle());
        team.setEndTime(endTime);
        team.setEvaluatorId(evaluatorId);

        boolean isSave = save(team);
        if(!isSave){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return team.getId();
    }

    @Override
    public boolean updateTeam(TeamUpdateRequest teamUpdateRequest) {
        if(teamUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }
        if(teamUpdateRequest.getTeamId() == null || teamUpdateRequest.getTeamId()<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"传入的队伍id不合规");
        }

        if(StringUtils.isEmpty(teamUpdateRequest.getDescription())){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"队伍描述为空");
        }

        if(StringUtils.isEmpty(teamUpdateRequest.getTeamName())||teamUpdateRequest.getTeamName().length()>=10){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"队伍名字不合规");
        }

        if(teamUpdateRequest.getTeamNumber()<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"创建队伍的数量小于等于0");
        }

        //时间偏移
        Integer teamCycle = teamUpdateRequest.getTeamCycle();
        teamCycle = Optional.ofNullable(teamCycle).orElse(0);

        if(teamCycle == 0){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }

        Team team = getById(teamUpdateRequest.getTeamId());

        Date createTime = team.getCreateTime();
        DateTime endTime = DateUtil.offsetDay(createTime, teamCycle);

        Team team1 = copyBean(teamUpdateRequest, team, endTime);

        return updateById(team1);
    }

    // 注意！这里不能用BeanUtils进行修改哦！
    private Team copyBean(TeamUpdateRequest teamUpdateRequest,Team team,Date endTime){
        team.setTeamName(teamUpdateRequest.getTeamName());
        team.setTeamCycle(teamUpdateRequest.getTeamCycle());
        team.setDescription(teamUpdateRequest.getDescription());
        team.setTeamNumber(teamUpdateRequest.getTeamNumber());
        team.setEndTime(endTime);
        return team;
    }


    private void checkLegal(Long evaluatorId,Team team){
        if(evaluatorId == null || team == null){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }

        if(StringUtils.isEmpty(team.getTeamName()) || team.getTeamName().length() >= 10){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"队伍不规范");
        }

        if(team.getTeamNumber()<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"创建队伍的数量小于等于0");
        }

        if(team.getTeamCycle()<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"创建队伍的周期太短啦");
        }

        if(StringUtils.isEmpty(team.getDescription())){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }
    }


}
