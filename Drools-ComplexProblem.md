## Drools-Example

使用Drools来解决一些复杂的逻辑问题。

### MingDrink

- problem

    小明喝汽水问题:

    1元钱一瓶汽水，喝完后两个空瓶换一瓶汽水，问：小明有20元钱，最多可以喝到几瓶汽水？
- rule

    ```
        package com.xu.drools

        import com.xu.drools.bean.XiaoMing;


        // 小明喝汽水问题
        // 1元钱一瓶汽水，喝完后两个空瓶换一瓶汽水，问：小明有20元钱，最多可以喝到几瓶汽水？
        // 规则1：1元钱一瓶汽水
        // 规则2：两个空瓶换一瓶汽水

        //规则1：1元钱一瓶汽水。有钱就买水，空瓶+1，钱-1，喝水+1；
        rule "rule1"
            salience 3
            when
                $m:XiaoMing(money>0);
            then
                System.out.println("有钱即可喝水，钱："+$m.getMoney());
                $m.setBottle($m.getBottle()+1);
                $m.setMoney($m.getMoney()-1);
                $m.setDrink($m.getDrink()+1);
                update($m)
        end

        //规则2：两个空瓶换一瓶汽水。有空瓶就换钱，空瓶-2，钱+1；
        rule "rule2"
            salience 2
            when
                $m:XiaoMing(bottle>=2);
            then
                System.out.println("有瓶子就换钱，瓶子："+$m.getBottle());
                $m.setBottle($m.getBottle()-2);
                $m.setMoney($m.getMoney()+1);
                update($m)
        end

        //规则3，打印已经喝掉的数量
        rule "rule3"
            salience 1
            when
                $m:XiaoMing();
            then
                System.out.println("总共喝掉："+$m.getDrink());
        end
    ```

### WorldProblem

- problem

    猜对错问题:
    在一次数学竞赛中，获得前三名的同学是A，B，C. 老师对他们说：“祝贺你们，请你们猜一猜名次。”
     * 甲：“A是第二，C是第三.”
     * 乙：“A是第一，B是第三.”
     * 丙：“B是第二，A是第三.”
 
    `感觉有更好的方法，但是写不出规则来0.0`
- rule

    ```
        package com.xu.drools

        import com.xu.drools.bean.Student;
        import com.xu.drools.bean.World

        rule "find jia1"
            salience 2
        when
                $jia1 : Student(name == "jia",word1=="A",word2=="C",desc1=="2",desc2!="3")
            then
                insert( new World($jia1.getDesc1(),0,$jia1.getDesc2()) );
        end

        rule "find jia2"
            salience 2
            when
                $jia2 : Student(name == "jia",word1=="A",word2=="C",desc1!="2",desc2=="3")
            then
                insert( new World($jia2.getDesc1(),0,$jia2.getDesc2()) );
        end

        rule "find yi1"
            salience 3
            when
                $yi1 : Student(name == "yi",word1=="A",word2=="B",desc1=="1",desc2!="3");
                $w : World(a==$yi1.getDesc1())
            then
                $w.setB($yi1.getDesc2());
                update($w)
        end

        rule "find yi2"
            salience 3
            when
                $yi2 : Student(name == "yi",word1=="A",word2=="B",desc1=="1",desc2!="3")
                $w : World(a==$yi2.getDesc1())
            then
                $w.setB($yi2.getDesc2());
                update($w)
        end


        rule "find bing1"
            salience 4
            when
                $bing1 : Student(name == "bing",word1=="A",word2=="B",desc1=="3",desc2!="2")
                $w : World(a==$bing1.getDesc1(),b==$bing1.getDesc2())
            then
                System.out.println("-------"+$w.toString());
        end


        rule "find bing2"
            salience 4
            when
                $bing2 : Student(name == "bing",word1=="A",word2=="B",desc1!="3",desc2=="2")
                $w : World(a==$bing2.getDesc1(),b==$bing2.getDesc2())
            then
                System.out.println("-------"+$w.toString());
        end
    ```

### GolferProblem

- problem

    高尔夫球员站位问题:

    已知有四个高尔夫球员，他们的名字是Fred,Joe,Bob,Tom;
     * 今天他们分别穿着红色，蓝色，橙色，以及格子衣服，并且他们按照从左往右的顺序站成一排。
     * 我们将最左边的位置定为1，最右边的位置定为4，中间依次是2,3位置。
     * 现在我们了解的情况是：
     * 1.高尔夫球员Fred,目前不知道他的位置和衣服颜色
     * 2.Fred右边紧挨着的球员穿蓝色衣服
     * 3.Joe排在第2个位置
     * 4.Bob穿着格子短裤
     * 5.Tom没有排在第1位或第4位，也没有穿橙色衣服
     * 请问,这四名球员的位置和衣服颜色。

- rule

    ```
        package com.xu.drools

        import com.xu.drools.bean.Golfer;

        rule "find solution"
            when
                //1.高尔夫球员Fred,目前不知道他的位置和衣服颜色
                $fred : Golfer( name == "Fred" )

                //3.Joe排在第2个位置
                $joe : Golfer( name == "Joe",
                        position == 2,
                        position != $fred.position,
                        color != $fred.color )

                //4.Bob穿着格子短裤
                $bob : Golfer( name == "Bob",
                        position != $fred.position,
                        position != $joe.position,
                        color == "plaid",
                        color != $fred.color,
                        color != $joe.color )

                //5.Tom没有排在第1位或第4位，也没有穿橙色衣服
                $tom : Golfer( name == "Tom",
                        position != 1,
                        position != 4,
                        position != $fred.position,
                        position != $joe.position,
                        position != $bob.position,
                        color != "orange",
                        color != $fred.color,
                        color != $joe.color,
                        color != $bob.color )

                //2.Fred右边紧挨着的球员穿蓝色衣服
                Golfer( position == ( $fred.position + 1 ),
                            color == "blue",
                            this in ( $joe, $bob, $tom ) )

            then
                System.out.println( "Fred " + $fred.getPosition() + " " + $fred.getColor() );
                System.out.println( "Joe " + $joe.getPosition() + " " + $joe.getColor() );
                System.out.println( "Bob " + $bob.getPosition() + " " + $bob.getColor() );
                System.out.println( "Tom " + $tom.getPosition() + " " + $tom.getColor() );
        end
    ```