package com.xu.drools.web;


import com.xu.drools.bean.BaseBean;
import com.xu.drools.bean.Person;
import com.xu.drools.service.RuleTableService;
import com.xu.drools.service.RulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "/rules")
@RestController
public class RulesController {
    @Autowired
    private RulesService rulesService;
    @Autowired
    private RuleTableService ruleTableService;

    @RequestMapping(value = "/getRule", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getRule(@RequestParam Integer id) {
        return rulesService.getRulesWrite(id);
    }

    @RequestMapping(value = "/getRule2", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getRule2(@RequestParam Integer id) {
        Person p = new Person(11,"","");
        p.setAge(18);
        return rulesService.getRulesWrite(id, p);
    }


    @RequestMapping(value = "/getRule3", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getRule3(@RequestParam Integer id, @RequestParam String name) {
        try {
            Class<?> classType = Class.forName("com.xu.drools.bean." + name);
            BaseBean o = (BaseBean) classType.newInstance();
            return rulesService.getRulesWrite(id, (Person) o);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping(value = "/getRule4", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getRule4(HttpServletRequest request) {
        System.out.println(request.getSession().getServletContext().getRealPath(""));
        ruleTableService.getRuleTable();
        return "";
    }

    @RequestMapping(value = "/getRule5", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getRule5(HttpServletRequest request) {
        Person p = new Person(14,"","");
        p.setAge(18);
        p.setName("Tony");
        return ruleTableService.getRuleTable2(p);
    }
}

