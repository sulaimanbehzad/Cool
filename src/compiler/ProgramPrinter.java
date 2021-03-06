package compiler;

import gen.CoolListener;
import gen.CoolParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class ProgramPrinter implements CoolListener {

    int first_visit = 0;
    String indentor = "";

    private String checkIndentation(String ind) {
        if (ind.length() >= 1) {
            ind = ind.substring(0, ind.length() - 1);
        } else {
        }
        return ind;
    }

    @Override
    public void enterProgram(CoolParser.ProgramContext ctx) {
        System.out.println("program start{");
        indentor += "\t";
    }

    @Override
    public void exitProgram(CoolParser.ProgramContext ctx) {
        System.out.println("}");
    }

    @Override
    public void enterClasses(CoolParser.ClassesContext ctx) {
    }

    @Override
    public void exitClasses(CoolParser.ClassesContext ctx) {
    }

    @Override
    public void enterEof(CoolParser.EofContext ctx) {

    }

    @Override
    public void exitEof(CoolParser.EofContext ctx) {

    }

    @Override
    public void enterClassDefine(CoolParser.ClassDefineContext ctx) {
//        System.out.println("class " + ctx.TYPEID().get(0)+ "/");
        try {
            System.out.println(indentor + "class " + ctx.TYPEID().get(0) + "/ " + "class parents: " + ctx.TYPEID().get(1) + ", {");
        } catch (Exception e) {
//            could print object as parent for class A
            System.out.println(indentor + "class " + ctx.TYPEID().get(0) + "/ " + "class parents: Object" + ", {");
        }
        indentor += "\t";
    }

    @Override
    public void exitClassDefine(CoolParser.ClassDefineContext ctx) {
        indentor = checkIndentation(indentor);
        System.out.println(indentor + "}");
    }

    @Override
    public void enterMethod(CoolParser.MethodContext ctx) {
        try {
            first_visit = 0;
            System.out.print(indentor + "class method: " + ctx.OBJECTID() + "/ return type=" + ctx.TYPEID() + " {\n");
            indentor += "\t";
            System.out.print(indentor + "parameters list = [");
        } catch (Exception e) {

        }
    }


    @Override
    public void exitMethod(CoolParser.MethodContext ctx) {
        indentor = checkIndentation(indentor);
        System.out.println(indentor + "}");
    }

    @Override
    public void enterProperty(CoolParser.PropertyContext ctx) {
        try {
            System.out.println(indentor + "field: " + ctx.OBJECTID() + "/ type= " + ctx.TYPEID());
        } catch (Exception e) {
        }
    }

    @Override
    public void exitProperty(CoolParser.PropertyContext ctx) {

    }

    @Override
    public void enterFormal(CoolParser.FormalContext ctx) {
        System.out.print(ctx.getText() + ", ");
    }

    @Override
    public void exitFormal(CoolParser.FormalContext ctx) {

    }

    @Override
    public void enterLetIn(CoolParser.LetInContext ctx) {

    }

    @Override
    public void exitLetIn(CoolParser.LetInContext ctx) {
        try {
            System.out.println(indentor + "field: " + ctx.OBJECTID().get(0) + "/ type= " + ctx.TYPEID().get(0));
        } catch (Exception e) {
        }
    }

    @Override
    public void enterMinus(CoolParser.MinusContext ctx) {

    }

    @Override
    public void exitMinus(CoolParser.MinusContext ctx) {

    }

    @Override
    public void enterString(CoolParser.StringContext ctx) {

    }

    @Override
    public void exitString(CoolParser.StringContext ctx) {

    }

    @Override
    public void enterIsvoid(CoolParser.IsvoidContext ctx) {

    }

    @Override
    public void exitIsvoid(CoolParser.IsvoidContext ctx) {

    }

    @Override
    public void enterWhile(CoolParser.WhileContext ctx) {
        String s = ctx.getText();
//        System.out.println(s);
//        TODO: check if it has another WHILE inside
        if (!s.contains("if")) {
            System.out.println(indentor + "nested statement {");
            indentor += "\t";
        }
    }

    @Override
    public void exitWhile(CoolParser.WhileContext ctx) {
        String s = ctx.getText();
        if (!s.contains("if")) {
            indentor = checkIndentation(indentor);
            System.out.println("}");
        }
    }

    @Override
    public void enterDivision(CoolParser.DivisionContext ctx) {

    }

    @Override
    public void exitDivision(CoolParser.DivisionContext ctx) {

    }

    @Override
    public void enterNegative(CoolParser.NegativeContext ctx) {

    }

    @Override
    public void exitNegative(CoolParser.NegativeContext ctx) {

    }

    @Override
    public void enterBoolNot(CoolParser.BoolNotContext ctx) {

    }

    @Override
    public void exitBoolNot(CoolParser.BoolNotContext ctx) {

    }

    @Override
    public void enterLessThan(CoolParser.LessThanContext ctx) {

    }

    @Override
    public void exitLessThan(CoolParser.LessThanContext ctx) {

    }

    @Override
    public void enterBlock(CoolParser.BlockContext ctx) {

    }

    @Override
    public void exitBlock(CoolParser.BlockContext ctx) {

    }

    @Override
    public void enterId(CoolParser.IdContext ctx) {
//        System.out.println("ID: " + ctx.children);
    }

    @Override
    public void exitId(CoolParser.IdContext ctx) {

    }

    @Override
    public void enterMultiply(CoolParser.MultiplyContext ctx) {

    }

    @Override
    public void exitMultiply(CoolParser.MultiplyContext ctx) {

    }

    @Override
    public void enterIf(CoolParser.IfContext ctx) {
        String s = ctx.getText();
//        System.out.println(s);
//        TODO: Check if it has another if inside
        if (!s.contains("while")) {
            System.out.println(indentor + "nested statement {");
            indentor += "\t";
        }
    }

    @Override
    public void exitIf(CoolParser.IfContext ctx) {
        String s = ctx.getText();
        if (!s.contains("while")) {
            indentor = checkIndentation(indentor);
            System.out.println(indentor + "}");
        }
    }

    @Override
    public void enterCase(CoolParser.CaseContext ctx) {

    }

    @Override
    public void exitCase(CoolParser.CaseContext ctx) {

    }

    @Override
    public void enterOwnMethodCall(CoolParser.OwnMethodCallContext ctx) {
        System.out.println(ctx.getText());
    }

    @Override
    public void exitOwnMethodCall(CoolParser.OwnMethodCallContext ctx) {

    }

    @Override
    public void enterAdd(CoolParser.AddContext ctx) {

    }

    @Override
    public void exitAdd(CoolParser.AddContext ctx) {

    }

    @Override
    public void enterNew(CoolParser.NewContext ctx) {

    }

    @Override
    public void exitNew(CoolParser.NewContext ctx) {

    }

    @Override
    public void enterParentheses(CoolParser.ParenthesesContext ctx) {

    }

    @Override
    public void exitParentheses(CoolParser.ParenthesesContext ctx) {

    }

    @Override
    public void enterAssignment(CoolParser.AssignmentContext ctx) {
        //probably wrong to use assignment
        try {
//            String type =
            if (first_visit == 0) {
                System.out.println("]");
                first_visit++;
            }
//            System.out.println("field: " + ctx.OBJECTID()+ "/ type= " + ctx.OBJECTID());
        } catch (Exception e) {

        }
    }

    @Override
    public void exitAssignment(CoolParser.AssignmentContext ctx) {
        try {
//            System.out.println(indentor + "field: " + ctx.OBJECTID());
        } catch (Exception e) {
        }
    }

    @Override
    public void enterFalse(CoolParser.FalseContext ctx) {

    }

    @Override
    public void exitFalse(CoolParser.FalseContext ctx) {

    }

    @Override
    public void enterInt(CoolParser.IntContext ctx) {

    }

    @Override
    public void exitInt(CoolParser.IntContext ctx) {

    }

    @Override
    public void enterEqual(CoolParser.EqualContext ctx) {

    }

    @Override
    public void exitEqual(CoolParser.EqualContext ctx) {

    }

    @Override
    public void enterTrue(CoolParser.TrueContext ctx) {

    }

    @Override
    public void exitTrue(CoolParser.TrueContext ctx) {

    }

    @Override
    public void enterLessEqual(CoolParser.LessEqualContext ctx) {

    }

    @Override
    public void exitLessEqual(CoolParser.LessEqualContext ctx) {

    }

    @Override
    public void enterMethodCall(CoolParser.MethodCallContext ctx) {

    }

    @Override
    public void exitMethodCall(CoolParser.MethodCallContext ctx) {

    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {
    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}
