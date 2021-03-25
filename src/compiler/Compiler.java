package compiler;

import gen.CoolBaseListener;
import gen.CoolLexer;
import gen.CoolListener;
import gen.CoolParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
public class Compiler {
    public static void main(String[] args) throws IOException {
        CharStream stream = (CharStream) CharStreams.fromFileName("./sample/test.mla");
        CoolLexer lexer = new CoolLexer(stream);
        TokenStream tokens = new CommonTokenStream(lexer);
        CoolParser parser = new CoolParser(tokens);
        parser.setBuildParseTree(true);
        ParseTree tree = parser.program();
        ParseTreeWalker walker = new ParseTreeWalker();
        CoolListener listener = new CoolBaseListener();
        walker.walk(listener, tree);



    }

}
