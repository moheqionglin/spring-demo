package com.moheqionglin.sqlParser;
class Solution {
    public static void main(String[] args) {
        String s = new Solution().reverseOnlyLetters("a-bC-dEf-ghIj");
        System.out.println(s);

    }
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