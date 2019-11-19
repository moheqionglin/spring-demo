package com.moheqionglin.alth.leecode;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-14 15:34
 */
public class ReverseYuanYin {
    public static void main(String[] args) {
        System.out.println(new ReverseYuanYin().reverseVowels("leetcode"));
    }
    public String reverseVowels(String s) {
        char as[] = s.toCharArray();
        int i = 0;
        int j = as.length - 1;

        while(i < j){
            if(isYuan(as[i]) && isYuan(as[j])){
                swap(as, i++, j--);
            }else if(!isYuan(as[i])){
                i ++;
            }else if (!isYuan(as[j])){
                j --;
            }
        }
        return new String(as);

    }
    public void swap(char as[], int i, int j){
        char tmp = as[i];
        as[i] = as[j];
        as[j] = tmp;
    }
    public boolean isYuan(char c){
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
                c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
    }
}