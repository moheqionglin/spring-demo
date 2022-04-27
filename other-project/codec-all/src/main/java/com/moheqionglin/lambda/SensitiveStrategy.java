package com.moheqionglin.lambda;

public enum SensitiveStrategy {
    //公孙中山 -> 公*山
    USERNAME(s->s.replaceAll("(\\S)\\S*(\\S)","$1*$2")),

    //前四位，后四位明文，中间模糊
    ID_CARD(s->s.replaceAll("(\\d{4})\\d*(\\d{4})", "$1****$2")),

    //前3 后4明文， 其他模糊
    PHONE(s->s.replaceAll("(\\d{3})\\d*(\\d{4})", "$1***$2")),

    //地址
    ADDRESS(s -> s.replaceAll("(\\S{8})\\S{4}(\\S*)\\S{4}", "$1****$2****"))
    ;
    Desensitizer desensitizer;

    SensitiveStrategy(Desensitizer desensitizer) {
        this.desensitizer = desensitizer;
    }

    public Desensitizer getDesensitizer() {
        return desensitizer;
    }
}
