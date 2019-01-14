package com.moheqionglin.alth.tree;

import com.moheqionglin.alth.Util;

import java.util.Stack;

/**
 * @author wanli.zhou
 * @description
 * @time 13/01/2019 9:40 AM
 */
public class Tree {

    /*
     *                      1
     *                2             9
     *             3      4     10     13
     *          4    5   7  8  11 12 14  15
     *
     * */
    public static void main(String[] args) {
        Util.Node tree = Util.mockTreeWithLevel(new int[]{1, 2, 9, 3, 6, 10, 13, 4, 5, 7, 8, 11, 12, 14, 15});

        System.out.println("=====先根序===");
        //地柜 先序
        preOrder(tree);
        System.out.println();
        //非递归， 使用栈模拟 递归
        preOrderWithStack(tree);
        System.out.println();
        preOrderWithStack优化版本(tree);


        System.out.println();
        System.out.println("=====中根序===");
        System.out.println();
        inOrder(tree);
        System.out.println();
        inOrderWithStack(tree);
        System.out.println();
        inOrderWithStack优化(tree);

        System.out.println();
        System.out.println("=====后根序===");
        System.out.println();

        postOrder(tree);
        System.out.println();
        postOrderWithStack(tree);

        //树高度
        System.out.println();
        System.out.println();
        System.out.println(treeHigh(tree));


    }

    private static void inOrderWithStack(Util.Node tree) {
        Util.Node node = tree;
        Stack<Util.Node> stack = new Stack();
        while (node != null){
            stack.push(node);
            node = node.leftChild;

            if(node == null){
                Util.Node tmp = null;
                while (true){

                    if(stack.isEmpty()){
                        node = null;
                        break;
                    }
                    tmp = stack.pop();
                    System.out.print(tmp.value + "\t");
                    if(tmp != null && tmp.rightChild != null){
                        node = tmp.rightChild;
                        break;
                    }
                }
            }
        }
    }
    private static void inOrderWithStack优化(Util.Node tree) {
        Util.Node node = tree;
        Stack<Util.Node> stack = new Stack();
        while (node != null || !stack.isEmpty()){
            while (node != null){
                stack.push(node);
                node = node.leftChild;
            }
            if(!stack.isEmpty()){
                node = stack.pop();
                System.out.print(node.value + "\t");
                node = node.rightChild;
            }
        }
    }

    /**
     *
     * @param tree
     *
     * 先序 TLR， 中序 LTR， 后 LRT
     *两个stack。
     *
     *  一个stack遍历用；
     *  另一个stack最后输出：  栈里面顺序应该TRL
     *
     *
     */
    private static void postOrderWithStack(Util.Node tree) {
        Util.Node node = tree;
        //辅助遍历树
        Stack<Util.Node> stack1 = new Stack<>();
        //最终结果打印
        Stack<Util.Node> stack2 = new Stack<>();

        while (node != null || !stack1.isEmpty()){
            while (node != null){
                stack1.push(node);
                stack2.push(node);
                node = node.rightChild;
            }

            if(!stack1.isEmpty()){
                node = stack1.pop();
                node = node.leftChild;
            }
        }

        while (!stack2.isEmpty()){
            System.out.print(stack2.pop().value + "\t");
        }

    }
    private static void preOrderWithStack(Util.Node tree) {
        Stack<Util.Node> stack = new Stack<>();
        Util.Node node = tree;
        while (node != null){

            if(node.leftChild != null){
                System.out.print(node.value + "\t");
                stack.push(node);
                node = node.leftChild;
            }else if(node.rightChild != null){
                System.out.print(node.value + "\t");
                stack.push(node);
                node = node.rightChild;
            }else {
                System.out.print(node.value + "\t");
               while (true){
                   Util.Node tmp = null;
                   if(stack.isEmpty()){
                       node = null;
                      break;
                   }
                   tmp = stack.pop();
                   if(tmp != null && tmp.rightChild != null){
                       node = tmp.rightChild;
                       break;
                   }
               }
            }

        }

    }

    private static void preOrderWithStack优化版本(Util.Node tree) {
        Stack<Util.Node> stack = new Stack<>();
        Util.Node node = tree;
        while (node != null || !stack.isEmpty()){

            while(node != null){
                System.out.print(node.value + "\t");
                stack.push(node);
                node = node.leftChild;
            }

            if(!stack.isEmpty()){
                Util.Node pop = stack.pop();
                node = pop.rightChild;
            }

        }

    }

    private static int treeHigh(Util.Node tree) {
        if(tree == null){
            return 0;
        }

        int leftTree = treeHigh(tree.leftChild) ;
        int rightTree = treeHigh(tree.rightChild)  ;
        return leftTree > rightTree ? leftTree + 1 : rightTree + 1;
    }


    private static void preOrder(Util.Node tree) {
        if(tree == null){
            return;
        }
        System.out.print(tree.value + "\t");
        preOrder(tree.leftChild);
        preOrder(tree.rightChild);

    }

    private static void inOrder(Util.Node tree) {
        if(tree == null){
            return;
        }
        inOrder(tree.leftChild);
        System.out.print(tree.value + "\t");
        inOrder(tree.rightChild);

    }
    private static void postOrder(Util.Node tree) {
        if(tree == null){
            return;
        }
        postOrder(tree.leftChild);
        postOrder(tree.rightChild);
        System.out.print(tree.value + "\t");

    }


}