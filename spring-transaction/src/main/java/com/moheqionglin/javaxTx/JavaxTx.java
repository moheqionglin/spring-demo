package com.moheqionglin.javaxTx;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-07-30 15:36
 * https://blog.csdn.net/baidu_19338587/article/details/54963058
 *JTA transactionManager
 * https://www.ibm.com/developerworks/cn/java/j-lo-jta/
 * https://www.cnblogs.com/yixianyixian/p/8372832.html
 *
 *
 * JDBC 事务
 * 如果应用程序中直接使用JDBC来进行持久化，DataSourceTransactionManager会为你处理事务边界。为了使用DataSourceTransactionManager，你需要使用如下的XML将其装配到应用程序的上下文定义中：
 *
 *     <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
 *         <property name="dataSource" ref="dataSource" />
 *     </bean>
 *
 *
 * Hibernate事务
 * 如果应用程序的持久化是通过Hibernate实习的，那么你需要使用HibernateTransactionManager。对于Hibernate3，需要在Spring上下文定义中添加如下的<bean>声明：
 *
 *     <bean id="transactionManager" class="org.springframework.orm.hibernate3.HibernateTransactionManager">
 *         <property name="sessionFactory" ref="sessionFactory" />
 *     </bean>
 *
 * Java持久化API事务（JPA）
 * Hibernate多年来一直是事实上的Java持久化标准，但是现在Java持久化API作为真正的Java持久化标准进入大家的视野。如果你计划使用JPA的话，那你需要使用Spring的JpaTransactionManager来处理事务。你需要在Spring中这样配置JpaTransactionManager：
 *
 *     <bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
 *         <property name="sessionFactory" ref="sessionFactory" />
 *     </bean>
 *
 * JTA 事务（分布式事务才会使用）
 * 如果你没有使用以上所述的事务管理，或者是跨越了多个事务管理源（比如两个或者是多个不同的数据源），你就需要使用JtaTransactionManager：
 *
 *     <bean id="transactionManager" class="org.springframework.transaction.jta.JtaTransactionManager">
 *         <property name="transactionManagerName" value="java:/TransactionManager" />
 *     </bean>
 */
public class JavaxTx {

}