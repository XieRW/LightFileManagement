package com.xrw.springCloudAlibaba.config.tree;

import cn.hutool.core.lang.tree.TreeNodeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @program: LightFileManagement
 * @description: hutool工具包树工具treenode设置
 * @author: xearin 1429382875@qq.com
 * @create: 2021-11-22 15:57
 **/
@Component
public class MyTreeNodeConfig {

    @Bean
    public TreeNodeConfig treeNodeConfig(){
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setIdKey("pId");
        return treeNodeConfig;
    }
}
