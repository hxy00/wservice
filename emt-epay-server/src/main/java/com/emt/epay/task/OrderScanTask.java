package com.emt.epay.task;

import com.emt.epay.server.inter.IEpayOrderQuerySv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018-03-28.
 */
@Component
public class OrderScanTask {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IEpayOrderQuerySv iEpayOrderQuerySv;

    @Scheduled(cron = "0 0/1 * * * ?") //每分钟执行一次
    public void statusCheck() {
        logger.info("每分钟执行一次，开始……");
        //statusTask.healthCheck();
        iEpayOrderQuerySv.queryOrderState(200);

        logger.info("每分钟执行一次，结束。");
    }

//    @Scheduled(fixedRate = 20000)
//    public void testTasks() {
//        logger.info("每20秒执行一次。开始……");
//        //statusTask.healthCheck();
//        logger.info("每20秒执行一次。结束。");
//    }
}
