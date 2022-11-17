package com.maiqu.evaluatorPlatform.model.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.maiqu.evaluatorPlatform.model.common.User;
import com.maiqu.evaluatorPlatform.model.vo.EvaluatorVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;


/**
 * @author ht
 */
@Data
@TableName(value ="evaluator")
public class Evaluator extends User {



    public EvaluatorVO toVo(){
        EvaluatorVO evaluatorVO = new EvaluatorVO();
        BeanUtils.copyProperties(this,evaluatorVO);
        return evaluatorVO;
    }

}
