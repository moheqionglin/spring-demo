package com.moheqionglin.classLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-02 14:57
 */
public class MoheClassLoader extends ClassLoader {

    private String classLoaderName = null;
    private String loadPath = "";

    public MoheClassLoader(String classLoaderName, String loadPath) {
        //默认的父 类加载器 为 APPClassLoader
        super();
        this.classLoaderName = classLoaderName;
        this.loadPath = loadPath;
    }

    @Override
    public Class findClass(String name) {
        byte[] b = loadClassData(name);
        System.out.println("MoheClassLoader load class " + name);
        return defineClass(name, b, 0, b.length);
    }

    private byte[] loadClassData(String name) {
        // load the class data from the connection
        String abstractFilePath = loadPath + "/" + name.replace(".", "/") + ".class";
        try {
            FileInputStream fin = new FileInputStream(new File(abstractFilePath));
            byte classByte[] = new byte[fin.available()];
            fin.read(classByte);
            return classByte;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}