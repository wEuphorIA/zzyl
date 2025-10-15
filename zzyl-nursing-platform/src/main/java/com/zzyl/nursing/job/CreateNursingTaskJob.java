package com.zzyl.nursing.job;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zzyl.nursing.domain.Elder;
import com.zzyl.nursing.service.IElderService;
import com.zzyl.nursing.service.INursingTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CreateNursingTaskJob {

    @Autowired
    private IElderService elderService;

    @Autowired
    private INursingTaskService nursingTaskService;

    public void createNursingTaskJob() {
        //查询所有老人
        List<Elder> elderList = elderService.list(Wrappers.<Elder>lambdaQuery().eq(Elder::getStatus, 1));
        elderList.forEach(elder -> nursingTaskService.createMonthTask(elder) );
        log.info("创建月任务成功");
    }
}
