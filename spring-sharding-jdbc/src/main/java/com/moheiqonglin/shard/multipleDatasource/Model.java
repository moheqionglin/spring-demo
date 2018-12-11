package com.moheiqonglin.shard.multipleDatasource;

import java.lang.reflect.Field;

public class Model {
	private String a;
	private String b;
	@Override
	public String toString() {
		return "model [a=" + a + ", b=" + b + "]";
	}


	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Model m = new Model();
		Class<?> classType = m.getClass();
		Field a = classType.getDeclaredField("a");
		Field b = classType.getDeclaredField("b");
		a.setAccessible(true); // 抑制Java对修饰符的检查
		b.setAccessible(true); // 抑制Java对修饰符的检查
		a.set(m, "测试aaa");
		b.set(m, "测试bbb");
		System.out.println(m);
	}
}
