## Drools-Example

### Honest Politician

- 使用kmodule的方式调用drools
    创建文件：/resources/META-INF/kmodule.xml
    ```
    <?xml version="1.0" encoding="UTF-8"?>
    <kmodule xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns="http://www.drools.org/xsd/kmodule">
        <kbase name="HonestPoliticianKB" packages="com.xu.drools.rule.honestpolitician">
            <ksession name="HonestPoliticianKS"/>
        </kbase>
    </kmodule>
    ```
- rule
    ```
    package com.xu.drools                 //package指定命名空间:com.xu.drools

    import com.xu.drools.bean.Politician; //import要使用的对象:Politician
    import com.xu.drools.bean.Hope;       //import要使用的对象:Hope

    rule "We have an honest Politician"   //rule,定义规则:We have an honest Politician
        salience 10                       //salience，属性部分，定义优先级:10
        when
            exists( Politician( honest == true ) ) //exists,条件部分-LHS，定义当前规则的条件：存在honest==true的Politician
        then
            insertLogical( new Hope() );  //insertLogical,结果部分-RHS，定义规则满足后执行的操作：往当前workingMemory中插入一个新的Hope对象
    end                                   //规则结束

    rule "Hope Lives"
        salience 10
        when
            exists( Hope() )
        then
            System.out.println("Hurrah!!! Democracy Lives");
    end

    rule "Hope is Dead"
        when
            not( Hope() )                 //not,不存在：不存在Hope对象
        then
            System.out.println( "We are all Doomed!!! Democracy is Dead" );
    end

    rule "Corrupt the Honest"
        when
            politician : Politician( honest == true )
            exists( Hope() )
        then
            System.out.println( "I'm an evil corporation and I have corrupted " + politician.getName() );
            modify( politician ) {        //modify,修改：修改politician的Honest为false
                setHonest( false )
            }
    end
    ```
- 数据的输入

    ```
     public static void main(final String[] args) {
        KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
        System.out.println(kc.verify().getMessages().toString());
        execute( kc );
    }

    public static void execute( KieContainer kc ) {
        KieSession ksession = kc.newKieSession("HonestPoliticianKS");

        final Politician p1 = new Politician( "President of Umpa Lumpa", true );
        final Politician p2 = new Politician( "Prime Minster of Cheeseland", true );
        final Politician p3 = new Politician( "Tsar of Pringapopaloo", true );
        final Politician p4 = new Politician( "Omnipotence Om", true );

        ksession.insert( p1 );
        ksession.insert( p2 );
        ksession.insert( p3 );
        ksession.insert( p4 );

        ksession.fireAllRules();

        ksession.dispose();
    }
    ```
- rule的执行顺序

    http://cdn.heroxu.com/20190829156706732481281.png

    ![](http://cdn.heroxu.com/20190829156706732481281.png)

- 执行结果
    ```
    Hurrah!!! Democracy Lives
    I'm an evil corporation and I have corrupted President of Umpa Lumpa
    I'm an evil corporation and I have corrupted Prime Minster of Cheeseland
    I'm an evil corporation and I have corrupted Tsar of Pringapopaloo
    I'm an evil corporation and I have corrupted Omnipotence Om
    We are all Doomed!!! Democracy is Dead
    ```
