/*
 * 01/01/2019 19:28:56
 * MethodsTest.java created by Tsvetelin
 */

package test.cake.syntax.methods;


import java.util.List;

import com.cake.compilation.tokenizer.tokenizers.StringTokenizer;
import com.cake.compilation.tokens.Token;
import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.baseElements.Result;
import com.cake.syntax.blocks.Block;
import com.cake.syntax.methods.Method;
import com.cake.syntax.methods.parser.MethodParser;
import com.cake.syntax.operations.returnOp.ReturnOperator;
import com.cake.syntax.operations.returnOp.parser.ReturnOperatorParser;
import com.cake.syntax.parsers.Parser;
import com.cake.syntax.parsers.checkers.Checker;
import com.cake.syntax.variables.Type;
import com.cake.syntax.variables.Variable;
import com.cake.syntax.variables.parser.VariableDeclarationParser;
import com.cake.syntax.variables.values.Value;


/**
 * @author Tsvetelin
 *
 */
public class MethodsTest implements Checker
{

    public static void main ( String [] args )
    {
        CakeRuntime runtime = new CakeRuntime();

        String code = "local int test = ( REAL x , REAL y ) -> {}";
        String varDecl = "local real res = 5";
        String returnOpDecl = "return res * 2";


        StringTokenizer t = new StringTokenizer();

        List< Token > tokenizedCode = t.tokenize( code );

        // ParsersContainer cont = ParsersContainer.INSTANCE;
        //
        // Parser< ? > p = cont.getParserFor( tokenizedCode ).get( 0 );

        Parser< Method > methP = new MethodParser();
        Parser< Variable > varP = new VariableDeclarationParser();
        Parser< ReturnOperator > retOpP = new ReturnOperatorParser();

        Method m = methP.parseWithRuntime( runtime , null , tokenizedCode ).getValue();
        
        String mAddress = methP.parseWithRuntime( runtime , null , tokenizedCode ).getKey();
        Variable v = varP.parseWithRuntime( runtime , m , t.tokenize( varDecl ) ).getValue();
        String varAddr = varP.parseWithRuntime( runtime , m , t.tokenize( varDecl ) ).getKey();
        
        varAddr = varAddr.replaceFirst( "null" , "test" );
        
        runtime.addDecalredElement( varAddr , v );
        
        List< Token > retTokens = t.tokenize( returnOpDecl );
        
        retTokens.set( 1 , new Token( mAddress + "#" + v.getName() , IDENTIFIER_TYPE ) );
        
        ReturnOperator retOp = retOpP.parseWithRuntime( runtime , m , retTokens ).getValue();
        
        Block body = new Block( m.getName() , m.getAccessModifier() , null );

        body.addSubCommand( v );
        body.addSubCommand( retOp );

        System.out.println( m.getPromise().toString() );
        
        Method full = new Method( m.getPromise() , body , null );

        Result res = full.run( runtime , new Value( Type.REAL.name() , 2 ) , new Value( Type.REAL.name() , 3 ) );
        
        System.out.println( "Returned: " );
        System.out.println( res.getReturned() );
        System.out.println( "Vars: " );
        res.getExitVariables().forEach( System.out::println );
        
        runtime.forEach( ( x , y ) -> System.out.println( "Address: " + x + " with value: " + y.toString() ) );
        
    }

}
