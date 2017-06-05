## Drools应用

### 动态规则

#### 动态获取KieSession
```
    public KieSession getKieSession(String rules) {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.write("src/main/resources/rules/rules.drl", rules.getBytes());
        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new BusinessException(300003,results.getMessages().toString(),4);
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KieBase kieBase = kieContainer.getKieBase();

        return kieBase.newKieSession();
    }
```
#### 激活规则
```
    KieSession kieSession = rulesService.getKieSession(rule);
    Gson gson = new Gson();
    Person person = gson.fromJson(json, Person.class);
    kieSession.insert(person);
    kieSession.fireAllRules();
    kieSession.dispose();
```

### 决策表

#### 将文件翻译为drl文件
```
    public String getRuleTable() {
        //把excel翻译成drl文件
        SpreadsheetCompiler compiler = new SpreadsheetCompiler();
        String rules = compiler.compile(ResourceFactory.newClassPathResource(RULES_PATH + File.separator + "rule.xlsx", "UTF-8"), "rule-table");
        System.out.println(rules);
        return rules;
    }
```
#### 将文件流翻译为drl文件
```
    public String getRuleTable(InputStream inputStream) {
        //把excel翻译成drl文件
        SpreadsheetCompiler compiler = new SpreadsheetCompiler();
        String rules = compiler.compile(ResourceFactory.newInputStreamResource(inputStream, "UTF-8"), "rule-table");
        logger.info("get rule from xls:" + rules);
        return rules;
    }
```