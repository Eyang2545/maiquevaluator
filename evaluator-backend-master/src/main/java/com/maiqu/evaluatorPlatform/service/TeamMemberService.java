package com.maiqu.evaluatorPlatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maiqu.evaluatorPlatform.model.entity.TeamMember;
import com.maiqu.evaluatorPlatform.model.vo.TeamMemberVO;

import java.util.List;

/**
 * @author ht
 */
public interface TeamMemberService extends IService<TeamMember> {
    List<TeamMemberVO> getMembersByTeamId(Long teamId);
}
