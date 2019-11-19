package com.moheqionglin.alth.leecode;

/**
 *
 * 给定一个字符串 S，返回 “反转后的” 字符串，其中不是字母的字符都保留在原地，而所有字母的位置发生反转。
 * 输入："a-bC-dEf-ghIj"
 * 输出："j-Ih-gfE-dCba"
 *
 *
 *
 *
 * */
public class ReverseOnlyLettersSolution {

    public String reverseOnlyLetters(String s) {
        char[] chars = s.toCharArray();
        int i = 0;
        int j = chars.length - 1;
        while(i < j){
            if(isChar(chars[i]) && isChar(chars[j])){
                swap(chars, i , j);
                i ++;
                j --;
            }else if (!isChar(chars[i]) && i < j){
                i ++;
            }else if (!isChar(chars[j]) && i < j){
                j --;
            }
            
        }
        return new String(chars);
    }
    
    public boolean isChar(char c){
        return (c >='a' && c<='z') ||  (c >='A' && c<='Z');
    }
    
    public void swap(char chars[], int i , int j){
        char s  = chars[i];
        chars[i] = chars[j];
        chars[j] = s;
    }
}

/*
*
* 思路 首尾指针
*   都是 字符 swap
*   不是字符 移动指针
*
* */