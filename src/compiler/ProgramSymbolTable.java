package compiler;

import gen.CoolListener;
import gen.CoolParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Collections;

public class ProgramSymbolTable implements CoolListener {
    //  TODO CHECK DUPLICATES
    Scope currentScope;
    public static ArrayList<SymbolTable> symbolTables = new ArrayList<SymbolTable>();
    public static ArrayList<Property> propertyDefined = new ArrayList<Property>();
//    TODO there is no method for ELSE block!

    @Override
    public void enterProgram(CoolParser.ProgramContext ctx) {
        currentScope = new Scope("program", ctx.start.getLine());
        currentScope.depth = 0;
        currentScope.parentScope = null;

    }

    @Override
    public void exitProgram(CoolParser.ProgramContext ctx) {
//    TODO need to do something here!
        symbolTables.add(currentScope.SymbolTable);
        Collections.reverse(symbolTables);
        System.out.println();
        System.out.println(symbolTables);
        for (Property p:propertyDefined
             ) {
            if(!currentScope.SymbolTable.lookUp("class_" + p.type)) {
                System.out.println("105: in line [" + p.row + ":" + p.col + "], " + p.type + " is not defined");
            }
//            isDefined("property");
        }
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
        if (isExist(currentScope, classDefine.name, "class")) {
            currentScope.SymbolTable.insert("class_" + classDefine.name + "_" + ctx.start.getLine(), classDefine);
            Scope newScope = new Scope(ctx.TYPEID(0).getText(), ctx.start.getLine());
            newScope.parentScope = currentScope;
            currentScope = newScope;
            String errStr =  String.format("%s : in line[%d:%d], %s [%s] has been already defined",101, ctx.start.getLine(),
                    ctx.getStart().getCharPositionInLine(), classDefine.parent, classDefine.name);
            System.out.println(errStr);
        } else {
            currentScope.SymbolTable.insert("class_" + classDefine.name, classDefine);
            Scope newScope = new Scope(ctx.TYPEID(0).getText(), ctx.start.getLine());
            newScope.parentScope = currentScope;
            currentScope = newScope;
        }
    }

    @Override
    public void exitClassDefine(CoolParser.ClassDefineContext ctx) {
//        System.out.println("class scope" + currentScope.SymbolTable);
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
        if (isExist(currentScope, method.name, "method")) {
            method.returnType = ctx.TYPEID().getText();
            currentScope.SymbolTable.insert("method_" + method.name + "_" + ctx.start.getLine(), method);
            Scope newScope = new Scope(ctx.OBJECTID().getText(), ctx.start.getLine());
            newScope.parentScope = currentScope;
            currentScope = newScope;
            String errStr =  String.format("%s : in line[%d:%d], %s [%s] has been already defined",102, ctx.start.getLine(),
                    ctx.getStart().getCharPositionInLine(), method.returnType, method.name);
            System.out.println(errStr);
        } else {
            method.returnType = ctx.TYPEID().getText();
            currentScope.SymbolTable.insert("method_" + method.name, method);
            Scope newScope = new Scope(ctx.OBJECTID().getText(), ctx.start.getLine());
            newScope.parentScope = currentScope;
            newScope.depth = currentScope.parentScope.depth + 1;
            currentScope = newScope;
        }

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
        property.row = ctx.start.getLine();
        property.col = ctx.getStart().getCharPositionInLine();
        if (isExist(currentScope, property.name, "property")) {
            currentScope.SymbolTable.insert("property_" + property.name + "_" + ctx.start.getLine(), property);
            String errStr =  String.format("%s : in line[%d:%d], %s [%s] has been already defined",104, property.row,
                    property.col, property.type, property.name);
            System.out.println(errStr);
        } else {
//            TODO check if it's defining now or else if it's already defined
//            System.out.println(property);
//            System.out.println(isExist(currentScope, property.type, "property"));
            propertyDefined.add(property);
            currentScope.SymbolTable.insert("property_" + property.name, property);
        }

    }

    @Override
    public void exitProperty(CoolParser.PropertyContext ctx) {
//        System.out.println(currentScope.SymbolTable);

    }

    @Override
    public void enterFormal(CoolParser.FormalContext ctx) {
//        formal = parameters
    }

    @Override
    public void exitFormal(CoolParser.FormalContext ctx) {

    }

    @Override
    public void enterLetIn(CoolParser.LetInContext ctx) {
        Let let = new Let();
        int len = ctx.OBJECTID().toArray().length;
        if (len > 0) {
            for (int i = 0; i < len; i++) {
                let.parametersNames.add(ctx.OBJECTID(i).getText());
            }
            for (int i = 0; i < len; i++) {
                let.parametersTypes.add(ctx.TYPEID(i).getText());
            }
        }

        currentScope.SymbolTable.insert("let_" + ctx.start.getLine(), let);
//        Scope newScope = new Scope(ctx.LET().getText(), ctx.start.getLine());
//        newScope.parentScope = currentScope;
//        currentScope = newScope;

    }

    @Override
    public void exitLetIn(CoolParser.LetInContext ctx) {
        symbolTables.add(currentScope.SymbolTable);
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
    }

    @Override
    public void exitBlock(CoolParser.BlockContext ctx) {
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
    }

    @Override
    public void exitCase(CoolParser.CaseContext ctx) {
    }
// DONE check undefined method
    @Override
    public void enterOwnMethodCall(CoolParser.OwnMethodCallContext ctx) {
        isDefined(ctx.OBJECTID().getText(), ctx.start.getLine(), ctx.getStart().getCharPositionInLine());

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
//        System.out.println(ctx.getText());
//        String cur = ctx.OBJECTID().getText();
//        try {
//            if (!isDefined(cur)) {
//                System.out.println("error 105 - not defined");
//            } else {
////            TODO check if it's defining now or else if it's already defined
////            System.out.println(ctx.getText());
//            }
//        }
//        catch (Exception e)
//        {
//            System.out.println(e);
//        }

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

    public boolean isExist(Scope currentScope, String key, String type) {
        if (currentScope == null) {
            return false;
        }
        switch (type) {
            case "class":
                if (currentScope.lookUp("class_" + key)) {
                    return true;
                }
                break;
            case "method":
                if (currentScope.lookUp("method_" + key)) {
                    return true;
                }
                break;

//            case "field":
//            case "parameter":
//                if (currentScope.lookUp(key + "_field") || currentScope.lookUp(key + "_parameter")){
//                    return true;
//                }
//                break;
            case "property":

                if (currentScope.lookUp("property_" + key)) {
                    return true;
                }
                break;
        }
        return isExist(currentScope.parentScope, key, type);
    }
    public boolean isDefined(String key, int row, int col) {
        if(symbolTables.contains(key)) {
            System.out.println(key + " defined");
            return true;
        }
        else {
            System.out.println("105: in line [" +row+":"+col+"], " + key + " is not defined");
            return false;
        }

    }

}
