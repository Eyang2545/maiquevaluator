package com.maiqu.evaluatorPlatform.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.maiqu.evaluatorPlatform.model.common.Shared;
import com.maiqu.evaluatorPlatform.model.common.User;
import com.maiqu.evaluatorPlatform.model.vo.TeamMemberVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.concurrent.locks.Lock;

/**
 * @author ht
 */
@Data
@TableName(value ="team_member")
public class TeamMember extends User {

    private Long teamId;

    public TeamMemberVO toVO(){
        TeamMemberVO teamMemberVO = new TeamMemberVO();
        BeanUtils.copyProperties(this,teamMemberVO);
        return teamMemberVO;
    }
}
