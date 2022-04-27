package com.moheqionglin;

/**
 * http://www.eclipse.org/aspectj/
 * https://github.com/eclipse/org.aspectj
 * https://blog.csdn.net/d_o_n_g2/article/details/85046536
 * https://www.jianshu.com/p/3c5b09f6f563
 */
public class HelloWorld {

    public void sayChinese(){
        System.out.println("说中国话");
    }

    public static void main(String[] args) {
        new HelloWorld().sayChinese();
    }
}
