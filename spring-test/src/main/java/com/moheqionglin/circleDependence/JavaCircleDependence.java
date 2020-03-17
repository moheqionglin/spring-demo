package com.moheqionglin.circleDependence;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-04 13:58
 *
 *  构造方法依赖   字段依赖 排列组合四种情况
 *
 * A的构造方法中依赖了B的实例对象，同时B的构造方法中依赖了A的实例对象
 * A的构造方法中依赖了B的实例对象，同时B的某个field或者setter需要A的实例对象，以及反之
 * A的某个field或者setter依赖了B的实例对象，同时B的某个field或者setter依赖了A的实例对象，以及反之
 *
 *
 */
public class JavaCircleDependence {

    public static void main(String[] args) {
        new JavaCircleDependence().circleDependence1();
    }


    /*
    * java.lang.StackOverflowError
    * */
    public void circleDependence(){
        A a = new A();
        System.out.println(a.b);
    }


    class A {
        B b;
        public A(){
            b = new B();
        }
    }

    class B{
        A a;
        public B(){
            a = new A();
        }
    }


    /*
     * java.lang.StackOverflowError
     * */
    public void circleDependence1(){
        A1 a = new A1();
        System.out.println(a.b);
    }

    class A1 {
        B1 b = new B1();
        public A1(){
        }
    }

    class B1{
        A1 a = new A1();
        public B1(){
        }
    }
}