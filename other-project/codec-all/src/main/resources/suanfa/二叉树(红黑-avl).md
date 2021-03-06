概念：
平衡二叉树： 
1. 每个节点度为<=2
2. 左子树值 < 根 < 右字树
3. 任何节点两个字树高度<=1

红黑树： 
宏观上： 不追求完全平衡
https://www.cnblogs.com/yyxt/p/4983967.html

1. 所有节点是红色或者黑色组成
2. 跟和叶子节点是黑色 且所有叶子节点是 null（画图的时候经常会忽略这个null节点，望注意）
3. 红色节点的子节点必须是黑色的（任何一条路径上红色节点不能连续，黑色节点可以连续 ）
4. 任何一条路径上黑色节点个数要相等。


对比： 
1. 查询/插入/删除性能： 红黑树最高 2log(n+1), 二叉树最高 log(n)， 因此红黑树牺牲了一点点查询性能。
2. 当破坏平衡的时候， 红黑树最多3次旋转，但是 平衡二叉树最多 log（n）次

java中的 treeMap和 treeset以及 hashmap的key重叠的时候用的都是红黑树。
1. treemap 的插入和查询性能都要比hashmap低很多，因为每次操作优势 log(n)复杂度。
2. 好处treemap 有序的。

综上：在大量插入和删除的场景中（会涉及到频繁的左旋和右旋） treemap treeset 为了性能折中，选择了红黑树，因为红黑树 的旋转次数可控。且查询/插入/删除性能跟AVL基本相同。



作业： 

红黑树的左旋和右旋方法？ 
https://www.toutiao.com/i6393155479756866049/ 