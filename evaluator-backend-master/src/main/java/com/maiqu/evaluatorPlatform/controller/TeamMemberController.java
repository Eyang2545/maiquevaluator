package com.maiqu.evaluatorPlatform.controller;


import cn.hutool.core.date.DateTime;
import com.maiqu.evaluatorPlatform.common.BaseResponse;
import com.maiqu.evaluatorPlatform.common.ErrorCode;
import com.maiqu.evaluatorPlatform.common.ResultUtils;
import com.maiqu.evaluatorPlatform.exception.BusinessException;
import com.maiqu.evaluatorPlatform.model.User;
import com.maiqu.evaluatorPlatform.model.entity.TeamMember;
import com.maiqu.evaluatorPlatform.model.request.DeleteTeamMemberRequest;
import com.maiqu.evaluatorPlatform.model.request.ListRequest;
import com.maiqu.evaluatorPlatform.model.vo.TeamMemberVO;
import com.maiqu.evaluatorPlatform.service.TeamMemberService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author ht
 */
@Api(tags = "团队成员模块")
@RestController
@RequestMapping("/api/teamMember")
@CrossOrigin
public class TeamMemberController {
    @Resource
    private TeamMemberService teamMemberService;



    @PostMapping("/add")
    public BaseResponse<Long> addTeamMember(@RequestBody TeamMember teamMember) {
        if (teamMember==null){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"对象为空");
        }
        String phone = teamMember.getPhone();
        String username = teamMember.getUsername();
        String userAccount = teamMember.getUserAccount();
//        Integer gender = teamMember.getGender();
//        Integer age = teamMember.getAge();
        if (StringUtils.isAnyBlank(phone,username,userAccount)){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"参数为空");
        }
        if (teamMember.getTeamId()==null||teamMember.getAge()==null){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"参数为空");
        }
        teamMember.setCreateTime(DateTime.now());
        teamMember.setUpdateTime(DateTime.now());
        teamMemberService.save(teamMember);
        return ResultUtils.success(teamMember.getTeamId());
    }

    @PostMapping("/list")
    public BaseResponse<List<TeamMemberVO>> list(@RequestBody ListRequest listRequest) {
        if(listRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"对象为空");
        }
        Long teamId = listRequest.getTeamId();
        if (teamId==null){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"参数为空");
        }
        List<TeamMemberVO> membersByTeamId = teamMemberService.getMembersByTeamId(teamId);

        return ResultUtils.success(membersByTeamId);
    }


    @PostMapping("/delete")
    public BaseResponse<Long> deleteTeamMember(@RequestBody DeleteTeamMemberRequest deleteTeamMemberRequest) {
        if(deleteTeamMemberRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"对象为空");
        }
        Long id =deleteTeamMemberRequest.getId();
        if (id==null){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"参数为空");
        }
        if (!teamMemberService.removeById(id)){
            ResultUtils.error(4,"删除失败","删除失败");
        }
        return ResultUtils.success(id);
    }


}
