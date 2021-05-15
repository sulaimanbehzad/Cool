package compiler;

import gen.CoolListener;
import gen.CoolParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;

public class ProgramSymbolTable implements CoolListener {

    Scope currentScope;
    public static ArrayList<SymbolTable> symbolTables = new ArrayList<SymbolTable>();
//    TODO there is no method for ELSE block!

    @Override
    public void enterProgram(CoolParser.ProgramContext ctx) {
        currentScope = new Scope("program", ctx.start.getLine());
        currentScope.depth = 0;

    }

    @Override
    public void exitProgram(CoolParser.ProgramContext ctx) {
//    TODO need to do something here!
        symbolTables.add(currentScope.SymbolTable);
        System.out.println(symbolTables);
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
        ClassDefine classDefine;
        if (ctx.TYPEID().toArray().length > 1) {
            classDefine = new ClassDefine(ctx.TYPEID(0).toString(), ctx.TYPEID(1).toString());
        } else {
            classDefine = new ClassDefine(ctx.TYPEID(0).toString());
        }
        currentScope.SymbolTable.insert("class_" + classDefine.name, classDefine);
        Scope newScope = new Scope(ctx.TYPEID(0).getText(), ctx.start.getLine());
        newScope.parentScope = currentScope;
        currentScope = newScope;
//        System.out.println(classDefine);

    }

    @Override
    public void exitClassDefine(CoolParser.ClassDefineContext ctx) {
        symbolTables.add(currentScope.SymbolTable);
        Scope newScope;
        newScope = currentScope.parentScope;
        currentScope = newScope;
    }

    @Override
    public void enterMethod(CoolParser.MethodContext ctx) {
        Method method = new Method(ctx.OBJECTID().getText());
        int len = ctx.formal().toArray().length;
        if (len > 0) {
            for (int i = 0; i < len; i++) {
                method.parametersTypes.add(ctx.formal(i).TYPEID().toString());
            }
        }
        method.returnType = ctx.TYPEID().getText();
        currentScope.SymbolTable.insert("method_" + method.name, method);
        Scope newScope = new Scope(ctx.OBJECTID().getText(), ctx.start.getLine());
        newScope.parentScope = currentScope;
        currentScope = newScope;
    }

    @Override
    public void exitMethod(CoolParser.MethodContext ctx) {
        symbolTables.add(currentScope.SymbolTable);
        Scope newScope;
        newScope = currentScope.parentScope;
        currentScope = newScope;
    }

    @Override
    public void enterProperty(CoolParser.PropertyContext ctx) {
        Property property = new Property();
        property.name = ctx.OBJECTID().getText();
        property.type = ctx.TYPEID().getText();
        currentScope.SymbolTable.insert("property_" + property.name, property);
    }

    @Override
    public void exitProperty(CoolParser.PropertyContext ctx) {

    }

    @Override
    public void enterFormal(CoolParser.FormalContext ctx) {

    }

    @Override
    public void exitFormal(CoolParser.FormalContext ctx) {

    }

    @Override
    public void enterLetIn(CoolParser.LetInContext ctx) {
//        TODO must be compelete
    }

    @Override
    public void exitLetIn(CoolParser.LetInContext ctx) {
//        TODO must be compelete
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
        Scope newScope = new Scope(ctx.WHILE().getText(), ctx.start.getLine());
        newScope.parentScope = currentScope;
        currentScope = newScope;

    }

    @Override
    public void exitWhile(CoolParser.WhileContext ctx) {
        symbolTables.add(currentScope.SymbolTable);
        Scope newScope;
        newScope = currentScope.parentScope;
        currentScope = newScope;
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
//        Scope newScope = new Scope("block", ctx.start.getLine());
//        newScope.parentScope = currentScope;
//        currentScope = newScope;
    }

    @Override
    public void exitBlock(CoolParser.BlockContext ctx) {
//        symbolTables.add(currentScope.SymbolTable);
//        Scope newScope;
//        newScope = currentScope.parentScope;
//        currentScope = newScope;
    }

    @Override
    public void enterId(CoolParser.IdContext ctx) {

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
        Scope newScope = new Scope(ctx.IF().getText(), ctx.start.getLine());
        newScope.parentScope = currentScope;
        currentScope = newScope;
    }

    @Override
    public void exitIf(CoolParser.IfContext ctx) {
        symbolTables.add(currentScope.SymbolTable);
        Scope newScope;
        newScope = currentScope.parentScope;
        currentScope = newScope;
    }

    @Override
    public void enterCase(CoolParser.CaseContext ctx) {
        Scope newScope = new Scope(ctx.CASE().getText(), ctx.start.getLine());
        newScope.parentScope = currentScope;
        currentScope = newScope;
    }

    @Override
    public void exitCase(CoolParser.CaseContext ctx) {
        symbolTables.add(currentScope.SymbolTable);
        Scope newScope;
        newScope = currentScope.parentScope;
        currentScope = newScope;
    }

    @Override
    public void enterOwnMethodCall(CoolParser.OwnMethodCallContext ctx) {

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

    }

    @Override
    public void exitAssignment(CoolParser.AssignmentContext ctx) {

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
