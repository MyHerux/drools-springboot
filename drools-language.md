## Drools语法-Language
### 关键词
  - Hard keywords(Cannot use any):

      `true,false,null`
  - Soft keywords(avoid use)

      `lock-on-active date-effective date-expires no-loop auto-focus activation-group agenda-group ruleflow-group entry-point duration package import dialect salience enabled attributes rule extend when then template query declare function global eval not in or and exists forall accumulate collect from action reverse result end over init`

### 注释
  - 单行注释
    ```
    rule "Testing Comments"
    when
        // this is a single line comment
        eval( true ) // this is a comment in the same line of a pattern
    then
        // this is a comment inside a semantic code block
    end
    ```
  - 多行注释
    ```
    rule "Test Multi-line Comments"
    when
        /* this is a multi-line comment
        in the left hand side of a rule */
    eval( true )
    then
        /* and this is a multi-line comment
        in the right hand side of a rule */
    end
    ```

### Language
  - package

      `package`表示一个命名空间.package是必须定义的，必须放在规则文件第一行.
  - import

      `import`语句的工作方式类似于Java中的import语句。您需要为要在规则中使用的任何对象指定完全限定路径和类型名称。
  - global

      `global`用于定义全局变量。

      Rules:
      ```
      global java.util.List myGlobalList;

      rule "Using a global"
      when
        eval( true )
      then
        myGlobalList.add( "Hello World" );
      end
      ```
      Set the global value:
      ```
      List list = new ArrayList();
      KieSession kieSession = kiebase.newKieSession();
      kieSession.setGlobal( "myGlobalList", list );
      ```
  - function

      `function`是一种将语义代码放置在规则源文件中的方法，而不是普通的Java类
      ```
      function String hello(String name) {
        return "Hello "+name+"!";
      }
      import function my.package.Foo.hello
      rule "using a static function"
      when
        eval( true )
      then
        System.out.println( hello( "Bob" ) );
      end
      ```
  - query

    `query`是一种搜索工作内存中与指定条件匹配的事实的简单方法.
    ```
    对所有30岁以上的人的简单查询
    query "people over the age of 30"
        person : Person( age > 30 )
    end

    查询超过x岁的人，以及居住在y的人
    query "people over the age of x"  (int x, String y)
        person : Person( age > x, location == y )
    end

    QueryResults results = ksession.getQueryResults( "people over the age of 30" );
    System.out.println( "we have " + results.size() + " people over the age  of 30" );

    System.out.println( "These people are are over 30:" );

    for ( QueryResultsRow row : results ) {
        Person person = ( Person ) row.get( "person" );
        System.out.println( person.getName() + "\n" );
    }
    ```
  - rule

    `rule`定义规则。`rule "ruleName"`。
#### rule
  一个规则可以包含三个部分：
  - 属性部分：定义当前规则执行的一些属性等，比如是否可被重复执行、过期时间、生效时间等。
    `activation-group` `agenda-group` `auto-focus` `date-effective` `date-expires` `dialect` `duration` `duration-value` `enabled` `lock-on-active` `no-loop` `ruleflow-group` `salience`

    ![规则属性][http://of0qa2hzs.bkt.clouddn.com/1484118982%281%29.jpg]

    - salience

      设置规则执行的优先级，`salience` 属性的值是一个数字，数字越大执行优先级越高，同时它的值可以是一个负数.
      规则的`salience` 默认值为0，所以如果我们不手动设置规则的`salience` 属性，那么它的执行顺序是随机的.
    - no-loop

      在一个规则当中如果条件满足就对`Working Memory`当中的某个Fact 对象进行了修改，比如使用update 将其更新到当前的`Working Memory`当中，这时引擎会再次检查所有的规则是否满足条件，如果满足会再次执行.
    - date-effective

      默认值：N/A

      类型：字符串，包含日期和时间定义。格式：`dd-MMM-yyyy`(25-Sep-2009).

      仅当当前日期和时间在日期有效属性后面时，才能激活规则。
    - date-expires

      默认值：N/A

      类型：字符串，包含日期和时间定义。格式：`dd-MMM-yyyy`(25-Sep-2009).

      如果当前日期和时间在date-expires属性之后，则无法激活规则.
    - enabled
  - 条件部分：即`LHS`，定义当前规则的条件，如`when Message()`; 判断当前workingMemory中是否存在Message对象。
  - 结果部分：即`RHS`，这里可以写普通java代码，即当前规则条件满足后执行的操作，可以直接调用Fact对象的方法来操作应用。
