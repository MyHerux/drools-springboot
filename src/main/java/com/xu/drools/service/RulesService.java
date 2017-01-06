package com.xu.drools.service;


import com.xu.drools.bean.Message;
import com.xu.drools.bean.Rules;
import com.xu.drools.dao.RulesDao;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RulesService {

    @Autowired
    private RulesDao rulesDao;

    public String getRulesWrite(Integer id) {
        String rules = "";
        Rules ru = rulesDao.getById(id);
        if (ru != null && ru.getRules() != null) {
            rules = ru.getRules();
        }

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

        Message message = new Message();
        message.setStatus("0");
        ksession.insert(message);
        ksession.fireAllRules();
        return "ok";
    }
}
