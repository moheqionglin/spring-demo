package com.moheqionglin.lambda;


import java.util.function.BiPredicate;

public enum SelfSelfBiPredicateStrategy {
    //公孙中山 -> 公*山
    USERNAME((s1, s2) -> s1.equals(s2)),
    ;
    BiPredicate<String, String> desensitizer;

    SelfSelfBiPredicateStrategy(BiPredicate<String, String> desensitizer) {
        this.desensitizer = desensitizer;
    }

    public BiPredicate<String, String> getDesensitizer() {
        return desensitizer;
    }
}
