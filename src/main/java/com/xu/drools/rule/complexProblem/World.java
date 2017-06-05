package com.xu.drools.rule.complexProblem;

import com.xu.drools.bean.Student;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * Created by xu on 2017/6/5.
 */
public class World {

    public static void main(final String[] args) {
        KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
        System.out.println(kc.verify().getMessages().toString());
        execute(kc);
    }

    private static void execute(KieContainer kc) {
        KieSession ksession = kc.newKieSession("worldKS");
        String[] names = new String[]{"jia", "yi", "bing", "ding", "wu"};
        int[] area = new int[]{1, 2, 3, 4, 5};

        Student jia=new Student("jia","europe=3","america=2");

        ksession.fireAllRules();
        ksession.dispose();
    }
}
