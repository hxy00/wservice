package com.emt.epay.controller;


import com.emt.base.entity.ReturnObject;
import com.emt.epay.EpayServerApplication;
import com.emt.epay.server.inter.IEpayOrderDetailSv;
import com.emt.epay.server.inter.IEpayOrderScanSv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by dsj on 2017/3/15.
 */

@RestController
@RequestMapping("/EpayOrder")
@SpringApplicationConfiguration(classes = EpayServerApplication.class)
public class PayAccountController {
    private static Logger logger = LoggerFactory.getLogger(PayAccountController.class);


    @Autowired
    private IEpayOrderDetailSv iEpayOrderDetailSv;

    @Autowired
    private IEpayOrderScanSv iEpayOrderScanSv;


    @RequestMapping("/payList")
    public ReturnObject payList() {
        List<Map<String, Object>> maps = iEpayOrderDetailSv.payList(300);

        int count = null != maps && maps.size() > 0 ? maps.size() : 0;
        logger.debug(maps.toString());
        return new ReturnObject(ReturnObject.SuccessEnum.success, "success", maps, count);
    }


}
