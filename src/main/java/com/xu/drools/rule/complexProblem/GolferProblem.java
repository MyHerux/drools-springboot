package com.xu.drools.rule.complexProblem;

import com.xu.drools.bean.Golfer;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * 使用kmodule的方式调用drools
 * /resources/META-INF/kmodule.xml
 * 高尔夫球员站位问题
 */
public class GolferProblem {

    /**
     * 已知有四个高尔夫球员，他们的名字是Fred,Joe,Bob,Tom;
     * 今天他们分别穿着红色，蓝色，橙色，以及格子衣服，并且他们按照从左往右的顺序站成一排。
     * 我们将最左边的位置定为1，最右边的位置定为4，中间依次是2,3位置。
     * 现在我们了解的情况是：
     * 1.高尔夫球员Fred,目前不知道他的位置和衣服颜色
     * 2.Fred右边紧挨着的球员穿蓝色衣服
     * 3.Joe排在第2个位置
     * 4.Bob穿着格子短裤
     * 5.Tom没有排在第1位或第4位，也没有穿橙色衣服
     * 请问,这四名球员的位置和衣服颜色。
     */
    public static void main(final String[] args) {
        KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
        System.out.println(kc.verify().getMessages().toString());
        execute(kc);
    }

    private static void execute(KieContainer kc) {
        KieSession ksession = kc.newKieSession("mingKS");
        String[] names = new String[]{"Fred", "Joe", "Bob", "Tom"};
        String[] colors = new String[]{"red", "blue", "plaid", "orange"};
        int[] positions = new int[]{1, 2, 3, 4};

        for (String name : names) {
            for (String color : colors) {
                for (int position : positions) {
                    ksession.insert(new Golfer(name, color, position));
                }
            }
        }
        ksession.fireAllRules();
        ksession.dispose();
    }
}
