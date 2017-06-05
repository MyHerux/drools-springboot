package com.xu.drools.rule.complexProblem;

import com.xu.drools.bean.XiaoMing;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * 使用kmodule的方式调用drools
 * /resources/META-INF/kmodule.xml
 * 小明喝水问题
 */
public class MingDrink {

    public static void main(final String[] args) {
        KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
        System.out.println(kc.verify().getMessages().toString());
        execute(kc);
    }

    private static void execute(KieContainer kc) {
        KieSession ksession = kc.newKieSession("mingKS");
        XiaoMing xiaoMing=new XiaoMing();
        xiaoMing.setMoney(50);
        ksession.insert(xiaoMing);
        ksession.fireAllRules();
        ksession.dispose();
    }
}
