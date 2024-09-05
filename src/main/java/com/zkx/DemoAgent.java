package com.zkx;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

public class DemoAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("=================oshi agent======================");
        new AgentBuilder.Default()
                .type(ElementMatchers.named("com.common.component.Oshi")) // 匹配类名
                .transform((builder, typeDescription, classLoader, module) -> builder
                        .visit(Advice.to(OshiAdvice.class).on(ElementMatchers.named("init")))) // 匹配方法
                .installOn(inst);
    }

    public static class OshiAdvice {
        @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
        public static boolean before() {
            // 跳过 init 方法的逻辑，返回 true 表示跳过，false 表示继续
            return true;
        }
    }
}
