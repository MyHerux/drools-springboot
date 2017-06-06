package com.xu.drools.rule.complexProblem;

import com.xu.drools.bean.Student;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * 使用kmodule的方式调用drools
 * /resources/META-INF/kmodule.xml
 * 猜对错问题
 */
public class WorldProblem {

    /**
     * 在一次数学竞赛中，获得前三名的同学是A，B，C. 老师对他们说：“祝贺你们，请你们猜一猜名次。”
     * 甲：“A是第二，C是第三.”
     * 乙：“A是第一，B是第三.”
     * 丙：“B是第二，A是第三.”
     * 感觉有更好的方法，但是写不出规则来0.0
     */
    public static void main(final String[] args) {
        KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
        System.out.println(kc.verify().getMessages().toString());
        execute(kc);
    }

    private static void execute(KieContainer kc) {
        KieSession ksession = kc.newKieSession("worldKS");
        String[] names = new String[]{"jia", "yi", "bing"};
        String[] word1 = new String[]{"A", "B", "C"};
        String[] word2 = new String[]{"A", "B", "C"};
        int[] desc1 = new int[]{1, 2, 3};
        int[] desc2 = new int[]{1, 2, 3};
        for (String n : names) {
            for (String w1 : word1) {
                for (String w2 : word2) {
                    for (int d1 : desc1) {
                        for (int d2 : desc2) {
                            Student student = new Student(n, w1, w2, d1, d2);
                            ksession.insert(student);
                        }
                    }
                }
            }
        }

        ksession.fireAllRules();
        ksession.dispose();
    }
}
