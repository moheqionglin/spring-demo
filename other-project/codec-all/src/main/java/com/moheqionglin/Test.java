package com.moheqionglin;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-06-05 11:19
 */
public class Test {
    public static void main(String[] args) {
        Test test = new Test();
        long start = System.currentTimeMillis();
        test.testHardCopyBytes();
        System.out.println(System.currentTimeMillis() - start);


        start = System.currentTimeMillis();
        test.testArrayCopyBytes();
        System.out.println(System.currentTimeMillis() - start);


    }

    public void testHardCopyBytes(){
        byte[] bytes = new byte[0x5000000]; /*~83mb buffer*/
        byte[] out = new byte[bytes.length];
        for(int i = 0; i < out.length; i++)
        {
            out[i] = bytes[i];
        }
    }

    public void testArrayCopyBytes(){
        byte[] bytes = new byte[0x5000000]; /*~83mb buffer*/
        byte[] out = new byte[bytes.length];
        System.arraycopy(bytes, 0, out, 0, out.length);
    }
}