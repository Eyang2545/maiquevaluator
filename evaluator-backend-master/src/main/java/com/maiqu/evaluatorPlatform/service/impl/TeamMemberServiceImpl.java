package com.maiqu.evaluatorPlatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maiqu.evaluatorPlatform.mapper.EvaluatorMapper;
import com.maiqu.evaluatorPlatform.mapper.TeamMemberMapper;
import com.maiqu.evaluatorPlatform.model.entity.Evaluator;
import com.maiqu.evaluatorPlatform.model.entity.TeamMember;
import com.maiqu.evaluatorPlatform.model.vo.TeamMemberVO;
import com.maiqu.evaluatorPlatform.service.EvaluatorService;
import com.maiqu.evaluatorPlatform.service.TeamMemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author ht
 */

@Service
@Slf4j
public class TeamMemberServiceImpl extends ServiceImpl<TeamMemberMapper, TeamMember> implements TeamMemberService {

    @Resource
    private TeamMemberMapper teamMemberMapper;

    @Override
    public List<TeamMemberVO> getMembersByTeamId(Long teamId) {
        QueryWrapper<TeamMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teamId", teamId);
        List<TeamMember> teamMembers = teamMemberMapper.selectList(queryWrapper);

        List<TeamMemberVO> teamMemberVOList = teamMembers.stream().map(teamMember -> teamMember.toVO()).collect(Collectors.toList());

        return teamMemberVOList;
    }


}
