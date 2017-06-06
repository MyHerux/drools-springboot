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

    /**
     * 小明喝汽水问题
     *
     * 1元钱一瓶汽水，喝完后两个空瓶换一瓶汽水，问：小明有20元钱，最多可以喝到几瓶汽水？
     */
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
