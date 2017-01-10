package com.xu.drools.service;


import com.xu.drools.bean.Person;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class RuleTableService {
    /**
     * 默认规则文件所在路径
     */
    private static final String RULES_PATH = "tables";

    public void getRuleTable() {
        //把excel翻译成drl文件
        SpreadsheetCompiler compiler = new SpreadsheetCompiler();
        String drl = compiler.compile(ResourceFactory.newClassPathResource(RULES_PATH + File.separator + "rule.xlsx", "UTF-8"), "rule-table");
        System.out.println(drl);
        Long start = System.currentTimeMillis();
        //执行决策表
        try {
            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession("tablesKiession");
            Person person = new Person();
            person.setName("Tony");
            kSession.insert(person);
            kSession.fireAllRules();
            kSession.dispose();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.out.println("COST:" + String.valueOf(System.currentTimeMillis() - start));
    }

    public String getRuleTable2(Person t) {
        //把excel翻译成drl文件
        SpreadsheetCompiler compiler = new SpreadsheetCompiler();
        String rules = compiler.compile(ResourceFactory.newClassPathResource(RULES_PATH + File.separator + "rule.xlsx", "UTF-8"), "rule-table");
        System.out.println(rules);
        Long start = System.currentTimeMillis();
        //执行决策表
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.write("src/main/resources/rules/rules.drl", rules.getBytes());
        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new IllegalStateException("### errors ###");
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KieBase kieBase = kieContainer.getKieBase();
        KieSession ksession = kieBase.newKieSession();

        ksession.insert(t);
        ksession.fireAllRules();
        System.out.println("COST:" + String.valueOf(System.currentTimeMillis() - start));
        return t.getOut();
    }
}
