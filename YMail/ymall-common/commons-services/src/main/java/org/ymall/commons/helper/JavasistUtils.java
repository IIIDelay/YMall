/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.ByteBuddyAgent;
import org.apache.ibatis.javassist.CannotCompileException;
import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtField;
import org.apache.ibatis.javassist.NotFoundException;
import org.apache.ibatis.javassist.bytecode.AnnotationsAttribute;
import org.apache.ibatis.javassist.bytecode.AttributeInfo;
import org.apache.ibatis.javassist.bytecode.ConstPool;
import org.apache.ibatis.javassist.bytecode.annotation.Annotation;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * 动态给类属性加注解工具类-Excel
 *
 */
@Slf4j
public class JavasistUtils {

    /**
     * 给clazz添加注解
     *
     * @param clazz
     * @param fieldName
     * @param annotationClass
     * @param initAnnotation
     */
    public static void addAnnotationToField(Class<?> clazz, String fieldName, Class<?> annotationClass,
                                            BiConsumer<Annotation, ConstPool> initAnnotation) {
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass;
            ctClass = pool.getCtClass(clazz.getName());
            if (ctClass.isFrozen()) {
                ctClass.defrost();
            }
            CtField ctField = ctClass.getDeclaredField(fieldName);
            ConstPool constPool = ctClass.getClassFile().getConstPool();

            Annotation annotation = new Annotation(annotationClass.getName(), constPool);
            if (initAnnotation != null) {
                initAnnotation.accept(annotation, constPool);
            }
            AnnotationsAttribute attr = getAnnotationsAttributeFromField(ctField);
            if (attr == null) {
                attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
                ctField.getFieldInfo().addAttribute(attr);
            }
            attr.addAnnotation(annotation);
            retransformClass(clazz, ctClass.toBytecode());
        } catch (NotFoundException | IOException | CannotCompileException e) {
            // 判断为在当前类中没有对应的属性 从父类获取
            if (e instanceof NotFoundException) {
                try {
                    ClassPool pool = ClassPool.getDefault();
                    CtClass ctClass;
                    ctClass = pool.getCtClass(clazz.getName());
                    CtClass superclazz = pool.getCtClass(clazz.getSuperclass().getName());
                    if (ctClass.isFrozen()) {
                        ctClass.defrost();
                    }
                    if (superclazz.isFrozen()) {
                        superclazz.defrost();
                    }
                    // 这里获取继承自父类的public属性
                    CtField extendField = ctClass.getField(fieldName);
                    ConstPool constPool = superclazz.getClassFile().getConstPool();

                    Annotation annotation = new Annotation(annotationClass.getName(), constPool);
                    if (initAnnotation != null) {
                        initAnnotation.accept(annotation, constPool);
                    }
                    AnnotationsAttribute attr = getAnnotationsAttributeFromField(extendField);
                    if (attr == null) {
                        attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
                        extendField.getFieldInfo().addAttribute(attr);
                    }
                    attr.addAnnotation(annotation);
                    retransformClass(clazz.getSuperclass(), superclazz.toBytecode());
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
            } else {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 给clazz 移除注解
     *
     * @param clazz
     * @param fieldName
     * @param annotationClass
     */
    public static void removeAnnotationFromField(Class<?> clazz, String fieldName, Class<?> annotationClass) {
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass;
            ctClass = pool.getCtClass(clazz.getName());
            if (ctClass.isFrozen()) {
                ctClass.defrost();
            }
            CtField ctField = ctClass.getDeclaredField(fieldName);
            AnnotationsAttribute attr = getAnnotationsAttributeFromField(ctField);
            if (attr != null) {
                attr.removeAnnotation(annotationClass.getName());
            }
            retransformClass(clazz, ctClass.toBytecode());
        } catch (NotFoundException | IOException | CannotCompileException e) {
            // 没有的话 去父类中查找
            if (e instanceof NotFoundException) {
                try {
                    ClassPool pool = ClassPool.getDefault();
                    CtClass ctClass;
                    ctClass = pool.getCtClass(clazz.getName());
                    CtClass superclazz = pool.getCtClass(clazz.getSuperclass().getName());
                    if (ctClass.isFrozen()) {
                        ctClass.defrost();
                    }
                    if (superclazz.isFrozen()) {
                        superclazz.defrost();
                    }
                    // 这里获取继承自父类的public属性
                    CtField extendField = ctClass.getField(fieldName);
                    ConstPool constPool = superclazz.getClassFile().getConstPool();
                    AnnotationsAttribute attr = getAnnotationsAttributeFromField(extendField);
                    if (attr != null) {
                        attr.removeAnnotation(annotationClass.getName());
                    }
                    retransformClass(clazz.getSuperclass(), superclazz.toBytecode());
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
            } else {
                log.error(e.getMessage(), e);
            }
        }
    }

    private static AnnotationsAttribute getAnnotationsAttributeFromField(CtField ctField) {
        List<AttributeInfo> attrs = ctField.getFieldInfo().getAttributes();
        AnnotationsAttribute attr = null;
        if (attrs != null) {
            Optional<AttributeInfo> optional = attrs.stream()
                .filter(AnnotationsAttribute.class::isInstance)
                .findFirst();
            if (optional.isPresent()) {
                attr = (AnnotationsAttribute) optional.get();
            }
        }
        return attr;
    }

    /**
     * 输出修改后的字节码
     *
     * @param clazz
     * @param byteCode
     */
    private static void retransformClass(Class<?> clazz, byte[] byteCode) {
        ClassFileTransformer cft = (loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> byteCode;

        Instrumentation instrumentation = ByteBuddyAgent.install();
        try {
            instrumentation.addTransformer(cft, true);
            instrumentation.retransformClasses(clazz);
        } catch (UnmodifiableClassException e) {
            e.printStackTrace();
        } finally {
            instrumentation.removeTransformer(cft);
        }
    }
}