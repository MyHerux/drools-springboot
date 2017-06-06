# drools-spring-boot

规则引擎Drools与SpringBoot的使用

## SpringBoot与Drools

#### SpringBoot项目
    略
#### Drools
- 依赖
```
        <drools.version>6.5.0.Final</drools.version>
        <!--Drools-->
        <dependency>
            <groupId>org.kie</groupId>
            <artifactId>kie-api</artifactId>
            <version>${drools.version}</version>
        </dependency>
        <dependency>
            <groupId>org.drools</groupId>
            <artifactId>drools-core</artifactId>
            <version>${drools.version}</version>
        </dependency>
        <dependency>
            <groupId>org.drools</groupId>
            <artifactId>drools-compiler</artifactId>
            <version>${drools.version}</version>
        </dependency>
        <dependency>
            <groupId>org.drools</groupId>
            <artifactId>drools-decisiontables</artifactId>
            <version>${drools.version}</version>
        </dependency>
        <dependency>
            <groupId>org.drools</groupId>
            <artifactId>drools-templates</artifactId>
            <version>${drools.version}</version>
        </dependency>
```


## Drools

   - 规则
   [Drools-Language](https://github.com/MyHerux/drools-springboot/blob/master/Drools-Language.md)

   - Rule示例
   [Drools-Example](https://github.com/MyHerux/drools-springboot/blob/master/Drools-Example.md)
   
   - Drools动态规则与决策表
   [Drools-Use](https://github.com/MyHerux/drools-springboot/blob/master/Drools-Use.md)

   - Drools解决复杂逻辑问题
   [Drools-ComplexProblem](https://github.com/MyHerux/drools-springboot/blob/master/Drools-ComplexProblem.md)
