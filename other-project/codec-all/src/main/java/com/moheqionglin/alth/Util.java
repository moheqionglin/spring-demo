package com.moheqionglin.alth;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

/**
 * @author wanli.zhou
 * @description
 * @time 12/01/2019 9:27 PM
 */
public class Util {

    public static class Node {
        public Node leftChild = null;
        public Node rightChild = null;
        public int value;
    }

    public static void print(int[] array) {
        String s = "";
        for(int i = 0 ; i < array.length ; i++){
            s += array[i] + "\t";
        }
        System.out.println(s);
    }

    public static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public static int[] mockArray(int length) {
        int[] array = new int[length];
        Random random = new Random();

        for(int i = 0; i < length; i ++){
            array[i] = random.nextInt(100);
        }
        return array;
    }

    //层次遍历 初始化一个 万千二叉树
    /*
    *                     1
    *                2             9
    *             3      4     10     13
    *          4    5   7  8  11 12 14  15
    *
    * */
    public static Node mockTreeWithLevel(int array[]){
        Node tree = new Node();
        tree.value = array[0];

        ArrayDeque<Node> queue = new ArrayDeque<>();
        queue.addLast(tree);

        for(int i = 1 ; i < array.length ; i++){
            Node poll = queue.poll();
            poll.leftChild = new Node();
            poll.leftChild.value = array[i];
            queue.addLast(poll.leftChild);
            if(++i < array.length){
                poll.rightChild = new Node();
                poll.rightChild.value = array[ i ];
                queue.addLast(poll.rightChild);
            }

        }

        return tree;
    }
}