package com.xu.drools.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.internal.io.ResourceFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

@Service
public class RuleTableService {
    private static Logger logger = LogManager.getLogger();
    /**
     * 默认规则文件所在路径
     */
    private static final String RULES_PATH = "tables";

    public String getRuleTable() {
        //把excel翻译成drl文件
        SpreadsheetCompiler compiler = new SpreadsheetCompiler();
        String rules = compiler.compile(ResourceFactory.newClassPathResource(RULES_PATH + File.separator + "rule.xlsx", "UTF-8"), "rule-table");
        System.out.println(rules);
        return rules;
    }

    /**
     * 通过决策表文件流获取rule
     *
     * @param inputStream 决策表文件流
     * @return rule
     */
    public String getRuleTable(InputStream inputStream) {
        //把excel翻译成drl文件
        SpreadsheetCompiler compiler = new SpreadsheetCompiler();
        String rules = compiler.compile(ResourceFactory.newInputStreamResource(inputStream, "UTF-8"), "rule-table");
        logger.info("get rule from xls:" + rules);
        return rules;
    }
}
