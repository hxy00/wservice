package com.emt.task;

import com.emt.task.service.IBPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class bPayTask
{

    private  final String _keyPrefix = "emt_task_stack_";
    private final  int _count = 100;
    @Autowired
    private IBPayService bPayService;

    @Autowired
    private LoadBalancerClient client;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;


    Logger logger = LoggerFactory.getLogger(bPayTask.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public void reportCurrentTime() {

        if (stringRedisTemplate.opsForList().size(_keyPrefix+"aaa") > 0)
        {
            String _redisStr = stringRedisTemplate.opsForList().leftPop(_keyPrefix + "aaa");
            logger.info(" ------------------  read  value --->  {}-----------------------------", _redisStr);
             String _return =  bPayService.buy(_redisStr);
             logger.info("------------buy  :  {}", _return);
            logger.info("--------- list size ： {}", stringRedisTemplate.opsForList().size(_keyPrefix + "aaa"));
        }else
        {
            logger.info("--------------------read over !  ----------------");
        }
    }

    public void insertStack(){
      if ( stringRedisTemplate.opsForList().size(_keyPrefix+"aaa") < _count)
      {
          Date date = new Date();
          String time = dateFormat.format(date);
          logger.info("-------------- Write list................{}  ----------------", time);
          String s = UUID.randomUUID().toString();
          stringRedisTemplate.opsForList().rightPush(_keyPrefix + "aaa", s);
          logger.info("--------- list size ： {}", stringRedisTemplate.opsForList().size(_keyPrefix + "aaa"));
      }else
      {
        logger.info("----------  List Is Full , Please  wait....... ------------------- ");
      }
    }

}
