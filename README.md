# activiti工作流引擎的使用
呕心沥血研究了几天工作流引擎

从最基本的安装插件画图  到  项目的使用：

1、安装插件：必须用eclipse（兼容更好），我用的idea2020-1版本的无法安装bpmn插件。
    eclipse安装插件过程：（1）插件下载地址：http://www.activiti.org/designer/archived/activiti-designer-5.18.0.zip
                        （2）打开Eclipse，选择菜单Help -> Install New Software -> Add Repository 
                            Name： Activiti BPMN 2.0 designer
                            Location: 刚刚下载的压缩包
                         之后就是next  next。。。
                         
2、用eclipse建activiti项目：插件安装好后就有建activiti项目的选项

3、使用刚刚建的项目画bpmn流程图： 在刚刚建的项目的src/main/resources/diagrams下新建Activiti Diagram，在此页面上画流程图

4、导出bpmn格式和png格式的图片：导出的地方没有找到快捷键是ctrl+o,png和bpmn都是springboot项目中要用的

5、之后springboot中的配置可以下载以上代码，配置过程中遇到的各种报错已被我百度解决，还有报错欢迎指出


再说说数据库的问题：
1、activiti6.0默认会生成28张表，其中我感觉比较重要（核心）的是以act_hi_开头和以act_ru_开头的表，这两类表一类保存的是流程的历史数据，一类报存的是流程正在运行中的数据

2、说几点代码中的问题：
（1）identityService.setAuthenticatedUserId("3");设置的是该流程启动人（谁启动的该流程会记录在act_hi_procinst表的START_USER_ID_字段）
（2）taskService.addComment("12506","10001","批准");  流程中每个节点都可以设置评论三个参数分别为act_ru_task的id_、act_ru_task的proc_inst_id_,、评论的内容，并会记录该信息在act_hi_comment表
（3）runtimeService.startProcessInstanceByKey("leave",map);启动一个流程参数为bpmn画的图的id,第二个参数为一个hashMap类型的数据map的key是在画bpmn过程中设置的参数（${key}的格式动态设置的），value就是我们要设置的数据
（4）taskService.complete("10006",map);每个节点完成的方法，参数一是任务id即act_ru_task的id_，参数二也是一个hashMap可以设置bpmn图中的动态参数，常用的是动态设置任务节点指派给谁去做.....
    
