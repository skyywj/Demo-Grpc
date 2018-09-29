注意代码格式化：IDEA
快捷键 windows：ctrl+alt+l   mac：option+command+l（win+alt+l）

此项目为demo-grpc项目:支持grpc，mysql，redis，redisson，事务

学习springboot连接mysql

在此附上grpc的官方文档：
       
        https://grpc.io/docs/quickstart/java.html
        http://doc.oschina.net/grpc?t=61535

将demo项目克隆并添加到新的github项目的步骤：

    1）git clone Demo项目
    2）git remote rm origin
    3) git remote add origin git@github.com:skyywj/Demo-Grpc.git
    4) rm -rf .git
    5) rm -rf .gitignore
    6) git init
    7) git pull origin master --alow-unrelated-histories
    8) git add .
    9) git commit .
    10) git push
    
  
 如果有多余的东西，需要另行处理，比如重新clone一个新的项目后直接删除操作，然后再提交。

gRPC 默认使用 protocol buffers 作为接口定义语言,在项目中采用proto新建仓库的策略以保证接口可以供Android ios等共用，但ios Android编译proto的方式不同

    新建proto项目，并在本项目中引入
        1、新建springboot项目protodemo
        2、删除不必要的文件，目录格式也需要修改
        3、pom.xml 添加依赖包grpc-all(项目中的几个),protobuf-java,jersey-rpc-support,jersey-server
        4、pom.xml 添加build
        5、新建test.proto文件，并写好接口
        6、编译 mvn compile
        7、安装 mvn install(安装到本地~/.m2/resposity下)
    接口项目完成操作，接下来是项目具体引入
        1、~/.m2/settings.xml配置需要有，这个自己百度配置
        2、引入本地依赖，说明一下具体
                <dependency>
                //groupid为项目名
                    <groupId>test-proto</groupId>
                //artifactId为install后:repository后，版本名前的路径,以.连接的路径 如com.abc
                    <artifactId>protodemo</artifactId>
                    <version>0.0.1-SNAPSHOT</version>
                </dependency>
        3、mvn compile 成功即可。
        4、引入springboot标准grpc依赖包grpc-spring-boot-starter、jersey-rpc-support、grpc-netty、grpc-protobuf
        5、写grpc层。使用@GRpcService，代码逻辑尽量放到service层，不要让grpc层太臃肿(第二次说了)
        
完成以上之后，让我头疼的事情是grpc层的测试
    
    网上有说使用mock grpc进行单元测试的，但我看到有好多东西现在已经不支持，具体没试过，还请大佬轻批。
        https://www.scienjus.com/mock-grpc-in-java-unit-test/
    本项目 采用的是netty channal的方式  （具体请自己查阅）
    
    测试时请先运行HrProsApplication.java,然后再去跑grpc的单元测试，否则报refuse connect 127.0.0.1.6565的错误。

日志系统采用的是logback，添加logback.xml 和lognet依赖

事务使用@Transational注解
