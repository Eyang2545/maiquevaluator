package com.maiqu.evaluatorPlatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maiqu.evaluatorPlatform.model.entity.Team;
import com.maiqu.evaluatorPlatform.model.update.TeamUpdateRequest;

/**
 * @author ht
 */
public interface TeamService extends IService<Team> {

    Long addTeam(Long evaluatorId, Team team);

    boolean updateTeam(TeamUpdateRequest teamUpdateRequest);
}
