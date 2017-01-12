[toc]
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

### Pakage
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

### Function
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

### Query
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

### Rule

  `rule`定义规则。`rule "ruleName"`。

  一个规则可以包含三个部分：属性部分,条件部分：即`LHS`,结果部分：即`RHS`.
  ![](http://of0qa2hzs.bkt.clouddn.com/rule.jpg)

#### 属性部分-Attributes

  定义当前规则执行的一些属性等，比如是否可被重复执行、过期时间、生效时间等。
    `activation-group` `agenda-group` `auto-focus` `date-effective` `date-expires` `dialect` `duration` `duration-value` `enabled` `lock-on-active` `no-loop` `ruleflow-group` `salience`

  ![](http://of0qa2hzs.bkt.clouddn.com/1484118982%281%29.jpg)

  - no-loop

      默认值：false

      类型：Boolean

      在一个规则当中如果条件满足就对`Working Memory`当中的某个Fact对象进行了修改，比如使用update 将其更新到当前的`Working Memory`当中，这时引擎会再次检查所有的规则是否满足条件，如果满足会再次执行.
  - ruleflow-group

      默认值：N/A

      类型：String

      `Ruleflow`是一个Drools功能，可让您控制规则的触发。由相同的规则流组标识汇编的规则仅在其组处于活动状态时触发。将规则划分为一个个的组，然后在规则流当中通过使用`ruleflow-group`属性的值，从而使用对应的规则。
  - lock-on-active

      默认值：false

      类型：Boolean

      当在规则上使用`ruleflow-group` 属性或`agenda-group` 属性的时候，将`lock-on-action` 属性的值设置为true，可能避免因某些Fact 对象被修改而使已经执行过的规则再次被激活执行。可以看出该属性与`no-loop` 属性有相似之处，`no-loop` 属性是为了避免Fact 修改或调用了`insert`、`retract`、`update` 之类而导致规则再次激活执行，这里的`lock-on-action` 属性也是起这个作用，`lock-on-active` 是`no-loop` 的增强版属性，它主要作用在使用`ruleflow-group` 属性或`agenda-group` 属性的时候

  - salience

      默认值：0

      类型：integer

      设置规则执行的优先级，`salience` 属性的值是一个数字，数字越大执行优先级越高，同时它的值可以是一个负数.
      规则的`salience` 默认值为0，所以如果我们不手动设置规则的`salience` 属性，那么它的执行顺序是随机的.

  - agenda-group

      默认值：MAIN

      类型：String

      规则的调用与执行是通过`StatelessSession` 或`StatefulSession` 来实现的，一般的顺序是创建一个`StatelessSession` 或`StatefulSession`，将各种经过编译的规则的package 添加到session当中，接下来将规则当中可能用到的Global对象和Fact对象插入到Session 当中，最后调用fireAllRules 方法来触发、执行规则。在没有调用最后一步`fireAllRules`方法之前，所有的规则及插入的`Fact`对象都存放在一个名叫`Agenda`表的对象当中，这个`Agenda`表中每一个规则及与其匹配相关业务数据叫做`Activation`，在调用`fireAllRules`方法后，这些Activation 会依次执行，这些位于Agenda 表中的Activation 的执行顺序在没有设置相关用来控制顺序的属性时（比如salience 属性），它的执行顺序是随机的，不确定的。`Agenda Group` 是用来在Agenda 的基础之上，对现在的规则进行再次分组，具体的分组方法可以采用为规则添加`agenda-group`属性来实现。`agenda-group` 属性的值也是一个字符串，通过这个字符串，可以将规则分为若干个`Agenda Group`，默认情况下，引擎在调用这些设置了`agenda-group` 属性的规则的时候需要显示的指定某个`Agenda Group` 得到`Focus`（焦点），这样位于该`Agenda Group` 当中的规则才会触发执行，否则将不执行。

  - auto-focus

      默认值：false

      类型：Boolean

      用来在已设置了`agenda-group`的规则上设置该规则是否可以自动独取`Focus`，如果该属性设置为true，那么在引擎执行时，就不需要显示的为某个`Agenda Group`设置`Focus`，否则需要。对于规则的执行的控制，还可以使用`Agenda Filter` 来实现。在Drools 当中，提供了一个名为`org.drools.runtime.rule.AgendaFilter 的Agenda Filter` 接口，用户可以实现该接口，通过规则当中的某些属性来控制规则要不要执行。`org.drools.runtime.rule.AgendaFilter` 接口只有一个方法需要实现，方法体如下： ` public boolean accept(Activation activation);`  在该方法当中提供了一个`Activation` 参数，通过该参数我们可以得到当前正在执行的规则对象或其它一些属性，该方法要返回一个布尔值，该布尔值就决定了要不要执行当前这个规则，返回true 就执行规则，否则就不执行。

  - activation-group

      默认值：N/A

      类型：String

      该属性的作用是将若干个规则划分成一个组，用一个字符串来给这个组命名，这样在执行的时候，具有相同`activation-group`属性的规则中只要有一个会被执行，其它的规则都将不再执行。也就是说，在一组具有相同`activation-group`属性的规则当中，只有一个规则会被执行，其它规则都将不会被执行。当然对于具有相同`activation-group`属性的规则当中究竟哪一个会先执行，则可以用类似`salience`之类属性来实现。

  - dialect

      默认值： 根据package指定

      类型：String，"java" or "mvel"

      `dialect`种类是用于LHS或RHS代码块中的任何代码表达式的语言。目前有两种`dialect`，`Java`和`MVEL`。虽然`dialect`可以在包级别指定，但此属性允许为规则覆盖包定义。

  - date-effective

      默认值：N/A

      类型：字符串，包含日期和时间定义。格式：`dd-MMM-yyyy`(25-Sep-2009).

      仅当当前日期和时间在日期有效属性后面时，才能激活规则。
  - date-expires

      默认值：N/A

      类型：字符串，包含日期和时间定义。格式：`dd-MMM-yyyy`(25-Sep-2009).

      如果当前日期和时间在`date-expires`属性之后，则无法激活规则.
  - enabled

      默认值：false

      类型：String

      表示规则是可用的，如果手工为一个规则添加一个`enabled`属性，并且设置其`enabled`属性值为false，那么引擎就不会执行该规则.

  - duration

      默认值：无

      类型：long

      持续时间指示规则将在指定的持续时间之后触发，如果它仍然是true.

#### 条件部分-LHS

  定义当前规则的条件，如`when Message()`; 判断当前workingMemory中是否存在Message对象。

  `Left Hand Side`（`LHS`）是规则的条件部分的公共名称。它由零个或多个条件元素组成。
  如果LHS为空，它将被认为是一个条件元素，它总是为真，并且当创建一个新的WorkingMemory会话时，它将被激活一次。
  ```
      Conditions / LHS —匹配模式（Patterns）

      没有字段约束的Pattern
      Person()

      有文本字段约束的Pattern
      Person( name == “bob” )

      字段绑定的Pattern
      Person( $name : name == “bob” )
      变量名称可以是任何合法的java变量，$是可选的，可由于区分字段和变量

      Fact绑定的Pattern
      $bob : Person( name == “bob” )字段绑定的Pattern

      变量约束的Pattern
      Person( name == $name )
  ```

  Drools提供了十二种类型比较操作符:
      `>`  `>=`  `<`  `<=`  `==`  `!=`  `contains`  `not contains`  `memberOf`  `not memberOf` `matches` `not matches`

  - contains

      运算符`contains`用于检查作为Collection或elements的字段是否包含指定的值.
      ```
      Cheese( name contains "tilto" )
      Person( fullName contains "Jr" )
      String( this contains "foo" )
      ```
  - not contains

      和`contains`相反

  - memberOf

      运算符`memberOf`用于检查字段是否是集合的成员或元素;该集合必须是一个变量。
      ```
      CheeseCounter( cheese memberOf $matureCheeses )
      ```
  - not memberOf

      和`memberOf`相反

  - matches

      正则表达式匹配，与java不同的是，不用考虑'/'的转义问题
      ```
      Cheese( type matches "(Buffalo)?\\S*Mozarella" )
      ```
  - not matches

      和`matches`相反

  其他条件元素：
  - exists

    存在。检查Working Memory是否存在某物。使用模式`exists`，则规则将只激活最多一次，而不管在工作存储器中存在与存在模式中的条件匹配的数据量

  - not

    不存在，检查工作存储器中是否存在某物。认为“`not`”意味着“`there must be none of...`”。

#### 结果部分-RHS

  这里可以写普通java代码，即当前规则条件满足后执行的操作，可以直接调用Fact对象的方法来操作应用。

  `Right Hand Side`（`RHS`）是规则的结果或动作部分的通用名称;此部分应包含要执行的操作的列表。在规则的RHS中使用命令式或条件式代码是不好的做法;作为一个规则应该是`原子`的性质 - “`when this, then do this`”，而不是“`when this, maybe do this`”。规则的RHS部分也应该保持较小，从而保持声明性和可读性。如果你发现你需要在RHS中的命令式和/或条件代码，那么也许你应该把这个规则分成多个规则。 `RHS`的主要目的是插入，删除或修改工作存储器数据。为了协助，有一些方便的方法可以用来修改工作记忆;而不必首先引用工作内存实例。

  - update

      更新，告诉引擎对象已经改变（已经绑定到LHS上的某个东西），并且规则可能需要重新考虑。
  - insert(new Something())

      插入，往当前`workingMemory`中插入一个新的Fact对象，会触发规则的再次执行，除非使用`no-loop`限定；

  - insertLogical(new Something())

      类似于`insert`，但是当没有更多的facts支持当前触发规则的真实性时，对象将被自动删除。
  - modify

      修改，与`update`语法不同，结果都是更新操作。该语言扩展提供了一种结构化的方法来更新事实。它将更新操作与一些setter调用相结合来更改对象的字段。
  - retract

      删除

一些内置的method。

  - drools.halt()

    调用`drools.halt（）`立即终止规则执行。这是需要将控制权返回到当前会话使用`fireUntilHalt（）`的点。
  - drools.getWorkingMemory()

    返回WorkingMemory对象.
  - drools.setFocus( String s)

    将焦点设置为指定的`agenda group`.
  - drools.getRule().getName()

    从规则的RHS调用，返回规则的名称。
  - drools.getTuple()

    返回与当前执行的规则匹配的`Tuple`，而drools.getActivation（）传递相应的激活。
