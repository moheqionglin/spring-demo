package com.moheqionglin.tomcat;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.EmptyResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.coyote.http11.Http11NioProtocol;

import java.io.File;
import java.security.GeneralSecurityException;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-10 10:10
 */
public class TomcatBootstrap {
    /**
     *
     *<Server port="8005" shutdown="SHUTDOWN">
     *   org.apache.catalina.core.JasperListener | JreMemoryLeakPreventionListener
     *   | ServerLifecycleListener | GlobalResourcesLifecycleListener
     *   <Listener className="org.apache.catalina.core.AprLifecycleListener" SSLEngine="on" />
     *
     *   <GlobalNamingResources>
     *     <Resource name="UserDatabase" auth="Container"
     *               type="org.apache.catalina.UserDatabase"
     *               description="User database that can be updated and saved"
     *               factory="org.apache.catalina.users.MemoryUserDatabaseFactory"
     *               pathname="conf/tomcat-users.xml" />
     *   </GlobalNamingResources>
     *
     *   <!-- 在<Server>中只能有一个<Service>元素，它表示服务 -->
     *   <Service name="Catalina">
     *      <!-- 在<Service>中可以有N个<Connector>元素，它表示连接，不同的Connector针对不同的协议，我们只需关心处理HTTP协议的元素 -->
     *      <!-- port表示端口号，默认值为8080，修改为80以后在访问项目时就不用再给出端口号了，因为80是HTTP默认端口 -->
     *     <Connector port="80" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" />
     *     <Connector port="8009" protocol="AJP/1.3" redirectPort="8443" />
     *
     *     <!-- 在<Service>中只能有一<Engine>元素，它是处理引擎，用户最终连接最终是由Connector导入的Engine处理的 -->
     *     <Engine name="Catalina" defaultHost="localhost">
     *       <Realm className="org.apache.catalina.realm.UserDatabaseRealm"
     *              resourceName="UserDatabase"/>
     *       <!-- 在<Engine>中可以有N个<Host>元素，每个<Host>元素表示一个虚拟主机，每个主机都有自己的主机名name和项目目录appBase -->
     *       <Host name="localhost"  appBase="webapps" unpackWARs="true" autoDeploy="true"
     *             xmlValidation="false" xmlNamespaceAware="false">
     *             <!-- 在<Host>中可以有N个<Context>元素，其中path指定的是项目虚拟路径，可以随意给出，docBase指定你的项目的真实存放的路径。指定后，访问时就不再通过项目名访问资源，而是通过path的值访问  -->
     *             <Context path="" docBase="D:\JAVA\tomcat6\我的主页"/><!--配置为此虚拟主机（即某个网站）的主页-->
     *       </Host>
     *
     *       <!--定义了另一个虚拟主机及其此主机的相对路径，相当于可以将搜狐和新浪的网站在同一台服务器中运行-->
     *       <Host name="localhost2"  appBase="webapps2"
     *             unpackWARs="true" autoDeploy="true"
     *             xmlValidation="false" xmlNamespaceAware="false">
     *       </Host>
     *     </Engine>
     *
     *   </Service>
     *
     * </Server>
     *
     *
     *                                            1             N       1        N
     *                                         +----------+    +---------+    +---------+
     *                                    +--->+ Engine   +--->+Host     |--->+ Context |
     *     1               N          1   |    +----------+    +---------+    +---------+
     * +---------+         +----------+   |
     * | Server  +-------->+  Service +---+         N
     * +---------+         +----------+   |    +----------+
     *                                    +--->+ Connector|
     *                                         +----------+
     * */
    public static void main(String[] args) {
        String baseDir =  getProjectDir() + "/tomcat-base-dir";
        startTomcat(8080, "/mohe", baseDir, true);


    }

    private static String getProjectDir(){
        String path = TomcatBootstrap.class.getClassLoader().getResource("./").getPath();
        return path.substring(0, path.lastIndexOf("/target/classes/"));
    }

    private static void startTomcat(int port, String contextPath, String baseDir, boolean https) {
        Tomcat tomcat = new Tomcat();


        Connector connector = https ? createHttpsConnector(port) : createHttpConnector(port);
        tomcat.getService().addConnector(connector);
        tomcat.setConnector(connector);


        tomcat.getHost().setAutoDeploy(false);
        tomcat.setBaseDir(baseDir);

        StandardContext context = (StandardContext) tomcat.addWebapp(contextPath, baseDir);
        context.setParentClassLoader(TomcatBootstrap.class.getClassLoader());
        context.setUseRelativeRedirects(false);

        //支持 @WebServlet(name = "test3", urlPatterns = {"/test3/*"})
        configureResources(context);
        //servlet
        addServlet(tomcat, contextPath, context);

        //tomcat 启动jar扫描设置为跳过所有，避免与框架结合出现 jar file not found exception
//        System.setProperty("tomcat.util.scan.StandardJarScanFilter.jarsToSkip", "\\,*");
        try {
            tomcat.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
        System.out.println("tomcat start success");
        tomcat.getServer().await();

    }

    private static Connector createHttpsConnector(int port) {
        if(!KeyStoreGenerate.exists()){
            try {
                KeyStoreGenerate.generate();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }
        final String DEFAULT_PROTOCOL = "org.apache.coyote.http11.Http11NioProtocol";
        Connector connector = new Connector(DEFAULT_PROTOCOL);
        connector.setPort(port);

        Http11NioProtocol protocol = (Http11NioProtocol)connector.getProtocolHandler();
        protocol.setKeystorePass("123456");
        protocol.setKeystoreFile(KeyStoreGenerate.filePath);
        protocol.setKeyAlias(KeyStoreGenerate.alias);
        protocol.setSSLEnabled(true);

        return connector;

    }

    private static Connector createHttpConnector(int port) {
        Connector connector = new Connector();
        connector.setPort(port);
        return connector;
    }

    private static void addServlet(Tomcat tomcat, String contextPath, StandardContext context) {
        tomcat.addServlet(contextPath, "userServlet", UserServlet.class.getName());
        context.addServletMappingDecoded("/user/*", "userServlet");
    }

    private static void configureResources(StandardContext context) {
        String WORK_HOME = System.getProperty("user.dir");
        File classesDir = new File(TomcatBootstrap.class.getClassLoader().getResource("./").getPath());
        File jarDir = new File(WORK_HOME, "lib");
        WebResourceRoot resources = new StandardRoot(context);
        if (classesDir.exists()) {//IDEA 环境
            resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", classesDir.getAbsolutePath(), "/"));
            System.out.println("Resources added: [classes]");
        } else if (jarDir.exists()) {//打包后的 java -jar启动情况下
            resources.addJarResources(new DirResourceSet(resources, "/WEB-INF/lib", jarDir.getAbsolutePath(), "/"));
            System.out.println("Resources added: [jar]");
        } else {//异常
            resources.addPreResources(new EmptyResourceSet(resources));
            System.out.println("Resources added: [empty]");
        }
        context.setResources(resources);
    }


}