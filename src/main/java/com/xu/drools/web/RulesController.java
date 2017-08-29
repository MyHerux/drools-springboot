package com.xu.drools.web;


import com.google.gson.Gson;
import com.xu.drools.bean.Person;
import com.xu.drools.common.exception.JsonResponse;
import com.xu.drools.dao.RulesDao;
import com.xu.drools.service.RuleTableService;
import com.xu.drools.service.RulesService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Drools的基本用法，动态规则+决策表
 */
@RequestMapping(value = "/rules")
@RestController
public class RulesController {

    @Autowired
    private RulesService rulesService;
    @Autowired
    private RuleTableService ruleTableService;
    @Autowired
    private RulesDao rulesDao;

    @ApiOperation(value = "验证规则是否合法",notes = verify)
    @RequestMapping(value = "/verify", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JsonResponse ruleVerify(@RequestParam(value = "rule") String rule,
                                   @RequestParam(value = "data") String json) {
        KieSession kieSession = rulesService.getKieSession(rule);
        Gson gson = new Gson();
        Person person = gson.fromJson(json, Person.class);
        kieSession.insert(person);
        int rules=kieSession.fireAllRules();
        System.out.println(rules);
        kieSession.dispose();
        return new JsonResponse(person);
    }


    @ApiOperation(value = "添加规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rule", dataType = "String", required = true, value = "规则"),
            @ApiImplicitParam(name = "name", dataType = "String", required = true, value = "规则名称")})
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JsonResponse ruleAdd(@RequestParam String rule, @RequestParam String name) {
        rulesDao.setRule(name, rule);
        return new JsonResponse("");
    }

    @ApiOperation(value = "规则列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JsonResponse getRuleList() {
        return new JsonResponse(rulesDao.getRuleList());
    }


    @ApiOperation(value = "规则结果")
    @RequestMapping(value = "/result", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JsonResponse getResult(@RequestParam("id") Integer id, @RequestParam("json") String json) {
        Gson gson = new Gson();
        Person person = gson.fromJson(json, Person.class);
        return new JsonResponse(rulesService.getRulesWrite(id, person));
    }

    @ApiOperation(value = "决策表转换")
    @ApiImplicitParam(name = "file", dataType = "MultipartFile", required = true, value = "决策表xls")
    @RequestMapping(value = "/getRuleXls", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JsonResponse getRuleXls(@RequestParam(value = "file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        String rule = ruleTableService.getRuleTable(inputStream);
        try {
            KieSession kieSession = rulesService.getKieSession(rule);
            kieSession.insert(new Person());
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResponse(e);
        }
        return new JsonResponse(rule);
    }

    @ApiOperation(value = "删除规则")
    @ApiImplicitParam(name = "id", dataType = "Integer", required = true, value = "规则编号")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JsonResponse delete(@RequestParam Integer id) {
        return new JsonResponse(rulesDao.deleteRule(id));
    }

    @ApiOperation(value = "修改规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rule", dataType = "String", required = true, value = "规则"),
            @ApiImplicitParam(name = "id", dataType = "Integer", required = true, value = "规则id"),
            @ApiImplicitParam(name = "name", dataType = "String", required = true, value = "规则名称")})
    @RequestMapping(value = "/ruleUpdate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JsonResponse ruleUpdate(@RequestParam String rule, @RequestParam Integer id, @RequestParam String name) {
        return new JsonResponse(rulesDao.updateRule(id, name, rule));
    }

    private final static String verify="规则：package com.xu.drools;\n" +
            "import com.xu.drools.bean.Person;\n" +
            "rule \"2\"\n" +
            "\twhen\n" +
            "        $p : Person(age < 30);\n" +
            "    then\n" +
            "\t\tSystem.out.println(\"hello, young xu2!\");\n" +
            "\t\t$p.setDesc(\"young \"+$p.getName());\n" +
            "\t\tretract($p)\n" +
            "end"+"   数据实体：{\n" +
            "    \"age\":18,\n" +
            "    \"name\":\"xu\",\n" +
            "    \"desc\":\"帅\"\n" +
            "}";
}

