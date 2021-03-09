package com.example.demo;


import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;


@Configuration
public class ActivitiConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiConfig.class);

    private final DataSource dataSource;

    private final PlatformTransactionManager platformTransactionManager;

    protected String databaseSchemaUpdate = ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE;

    @Autowired
    public ActivitiConfig(DataSource dataSource, PlatformTransactionManager platformTransactionManager) {
        this.dataSource = dataSource;
        this.platformTransactionManager = platformTransactionManager;
    }

    /**
     * 创建配置文件，并自动扫描processes包下的bpmn(流程定义文件)
     * @return
     */
    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration() {
        SpringProcessEngineConfiguration spec = new SpringProcessEngineConfiguration();
        spec.setDataSource(dataSource);// 数据源
        spec.setTransactionManager(platformTransactionManager);// 事务管理
        spec.setDatabaseSchemaUpdate(databaseSchemaUpdate);// 数据库更新策略
        String deploymentName = "曹伟测试 - 流程自动部署";
        spec.setDeploymentName(deploymentName);// 部署名称
        Resource[] resources = null;
        try {
            // 启动自动部署流程
            resources = new PathMatchingResourcePatternResolver().getResources("classpath*:processes/*.bpmn");
        } catch (IOException e) {
            LOGGER.error(e.toString());
        }
        spec.setDeploymentResources(resources);
        return spec;
    }

    /**
     * 创建流程引擎
     * @return
     */
    @Bean
    public ProcessEngineFactoryBean processEngine() {
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration(springProcessEngineConfiguration());
        return processEngineFactoryBean;
    }



}
