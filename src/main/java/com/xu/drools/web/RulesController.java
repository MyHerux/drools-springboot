package com.xu.drools.web;


import com.xu.drools.bean.Rules;
import com.xu.drools.dao.RulesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/rules")
@RestController
public class RulesController {
    @Autowired
    private RulesDao rulesDao;

    @RequestMapping(value = "/getRule", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getRule(@RequestParam Integer id) {
        Rules rules = rulesDao.getById(id);
        return rules.getRules();
    }
}
