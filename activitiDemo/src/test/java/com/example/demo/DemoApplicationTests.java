package com.example.demo;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import java.util.HashMap;

@SpringBootTest
class DemoApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplicationTests.class);

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private IdentityService identityService;

    @Resource
    private TaskService taskService;

    @Test
    void startLeave() {
        //开启一个请假流程
        identityService.setAuthenticatedUserId("3");
        ProcessInstance leave = runtimeService.startProcessInstanceByKey("leave");
        String processDefinitionId = leave.getProcessDefinitionId();   //流程定义id
        LOGGER.info("processDefinitionId = " + processDefinitionId);
        String id = leave.getId(); //流程实例id
        LOGGER.info("id = " + id);
    }

    @Test
    void leave() {
        HashMap<String,Object> vars = new HashMap();
        vars.put("day",2);
        identityService.setAuthenticatedUserId("1");
        taskService.addComment("10006","10001","我要请假2天");  //act_ru_task的id_ ,
        taskService.complete("10006",vars);
    }

    @Test
    void leader() {
        identityService.setAuthenticatedUserId("2");
        taskService.addComment("12506","10001","批准");  //act_ru_task的id_ ,
        taskService.complete("12506");
    }
}
