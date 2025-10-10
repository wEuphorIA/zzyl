package com.zzyl.nursing.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzyl.nursing.domain.Contract;
import com.zzyl.nursing.service.IContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 @author Euphoria
 @version 1.0
 @description: TODO
 @date 2025/10/10 下午8:48 */
@Component
@Slf4j
public class ContractJob {

    @Resource
    private IContractService contractService;

    @Scheduled(cron = "0 * * * * ?")
    public void updateContractStatusJob() {
        System.out.println("kaishi");
        // 把状态为0的全部都扫出来
        LambdaQueryWrapper<Contract> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Contract::getStatus, 0)
                .le(Contract::getStartDate, LocalDateTime.now())
                .ge(Contract::getEndDate, LocalDateTime.now());
        List<Contract> list = contractService.list(lambdaQueryWrapper);
        log.info("{}", list);
        if (list != null) {
            list.forEach(c -> c.setStatus(1));
        }
        contractService.saveOrUpdateBatch(list);

        // 扫描状态为1的
        LambdaQueryWrapper<Contract> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Contract::getStatus, 1)
                .le(Contract::getEndDate, LocalDateTime.now());
        List<Contract> list1 = contractService.list(lambdaQueryWrapper1);
        log.info("{}", list1);
        if (list1 != null) {
            list1.forEach(c -> c.setStatus(2));
        }
        contractService.saveOrUpdateBatch(list1);
    }

}
