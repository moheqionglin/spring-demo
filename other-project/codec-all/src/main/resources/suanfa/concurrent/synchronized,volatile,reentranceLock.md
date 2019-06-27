volatile : 
    1. 禁止编译器指令重排序（编译器有时候为了执行效率可能对指令进行重排序）
    2. volatile修饰的变量，会及时刷新到主存中。

AtomicIntegerFieldUpdater    
    1. 更新器更新的字段类型必须是 int
    2. 更新器更新的字段必须被 volatile修饰，确保线程间可见性。
    3. 变量不能static。 原因是Unsafe.objectFieldOffset()方法不支持静态变量。 CAS本质是通过对象实例的字段偏移量来进行复制的。
    