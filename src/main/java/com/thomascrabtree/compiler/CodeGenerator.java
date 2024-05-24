package com.thomascrabtree.compiler;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CodeGenerator {

    private final List<Statement> statements;
    private final ClassWriter classWriter = new ClassWriter(0);
    private final VariableHolder variables;
    private MethodVisitor mv;

    public CodeGenerator(List<Statement> statements, VariableHolder variables) {
        this.statements = statements;
        this.variables = variables;
    }

    public void generateCode() {

        writeDefaultOpenings();

        try {
            writeStatements();
        } catch (CompilerException e) {
            throw new RuntimeException(e.getMessage());
        }

        writeDefaultEndings();

        writeFile();

    }

    private void writeDefaultOpenings() {
        //Class definition
        classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "CompilerTest", null, "java/lang/Object", null);

        // Main
        mv = classWriter.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();
    }

    private void writeStatements() throws CompilerException {

        for (int i = 0; i < statements.size(); i++) {

            if (statements.get(i).getTokenType().equals(TokenType.VARIABLE_NAME)) {

                String[] sValue = statements.get(i).getStatementValues().split(" ", 2);
                String variableName = sValue[0];
                String variableValue = sValue[1];

                if (sValue[1].matches("-?\\d+(\\.\\d+)?")){
                    int value = Integer.parseInt(variableValue);
                    if (value > 32767) throw new CompilerException("Value of number variable too large");
                    mv.visitIntInsn(Opcodes.SIPUSH, value);
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                } else {
                    mv.visitLdcInsn(variableValue);
                }
                int position = variables.getVariablePosition(variableName);
                mv.visitVarInsn(Opcodes.ASTORE, position);
            } else if (statements.get(i).getTokenType().equals(TokenType.PRINT)) {

                String[] sValue = statements.get(i).getStatementValues().split(" ");
                String variableName = sValue[0];

                //TODO if the variable doesn't exist print the value
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitVarInsn(Opcodes.ALOAD, variables.getVariablePosition(variableName));
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/Object;)V", false);
            } else if (statements.get(i).getTokenType().equals(TokenType.DROP)){
                //TODO do we want this to just drop from the variable list or from the actual class code?
            }
        }
    }

    private void writeDefaultEndings() {
        // Return statement
        mv.visitInsn(Opcodes.RETURN);

        // Needed otherwise fails
        mv.visitMaxs(20, 20);
        mv.visitEnd();
        classWriter.visitEnd();
    }

    private void writeFile() {

        byte[] bytecode = classWriter.toByteArray();
        try (FileOutputStream fos = new FileOutputStream("CompilerTest.class")) {
            fos.write(bytecode);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
