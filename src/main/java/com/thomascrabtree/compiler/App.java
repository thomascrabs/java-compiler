package com.thomascrabtree.compiler;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;

public class App {
    public static void main(String[] args) throws Exception {
        ClassWriter classWriter = new ClassWriter(0);
        classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "CgSample", null, "java/lang/Object", null);

        // Main
        MethodVisitor mv = classWriter.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();

        // Store, load and print number
        mv.visitIntInsn(Opcodes.BIPUSH, 10);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
        mv.visitVarInsn(Opcodes.ASTORE, 1);
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/Object;)V", false);

        // Store, load and print string
        mv.visitLdcInsn("Hello, World");
        mv.visitVarInsn(Opcodes.ASTORE, 2);
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(Opcodes.ALOAD, 2);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/Object;)V", false);

        // Return statement
        mv.visitInsn(Opcodes.RETURN);

        // Needed otherwise fails
        mv.visitMaxs(20, 20);
        mv.visitEnd();

        classWriter.visitEnd();

        // Write file
        byte[] bytecode = classWriter.toByteArray();
        try (FileOutputStream fos = new FileOutputStream("CgSample.class")) {
            fos.write(bytecode);
        }
    }
}
