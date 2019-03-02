/*
 * 09/01/2019 21:37:33
 * PrintMethod.java created by Tsvetelin
 */

package com.cake.interpreter.STDLib.methods.printing;


import com.cake.interpreter.STDLib.block.STDLibBlock;
import com.cake.interpreter.STDLib.methods.STDLibMethod;
import com.cake.interpreter.running.runtime.CakeRuntime;
import com.cake.interpreter.syntax.baseElements.Result;
import com.cake.interpreter.syntax.methods.promise.MethodPromise;
import com.cake.interpreter.syntax.parsers.ParsersContainer;
import com.cake.interpreter.syntax.variables.values.Value;
import com.cake.tokenization.tokenizer.tokenizers.stringTokenizer.StringTokenizer;


/**
 * @author Tsvetelin
 *
 */
public class PrintLNMethod extends STDLibMethod
{

    public static final String PROMISE = "global int println = ( ANYTHING toPrint )";

    private static final MethodPromise promise = ParsersContainer.METHOD_PROMISE_PARSER
            .parse( STDLibBlock.STD_LIB_BLOCK , new StringTokenizer().tokenize( PROMISE ) ).getValue();


    /**
     * @param promise
     * @param superBlock
     */
    public PrintLNMethod ()
    {
        super( promise );
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.methods.Method#run(com.cake.running.runtime.CakeRuntime,
     * com.cake.syntax.variables.values.Value[])
     */
    @Override
    public Result run ( CakeRuntime runtime , Value... values )
    {
        System.out.println( values[0].getValue() );
        return null;
    }

}
