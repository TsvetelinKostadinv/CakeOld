/*
 * 10/12/2018 18:15:51
 * Result.java created by Tsvetelin
 */

package com.cake.syntax.baseElements;

import java.util.List;

import com.cake.syntax.variables.Variable;
import com.cake.syntax.variables.values.Value;

/**
 * 
 * A result object from the execution of a runnable syntax element
 * 
 * @author Tsvetelin
 *
 */
public class Result
{
    /**
     * This is what the method returned actually
     */
    private final Value returned;
    
    /**
     * If there were any errors they are here
     */
    private final Object error;

    /**
     * The caller of the runnable
     */
    private final RunnableSyntaxElement caller;
    
    private final List< Variable > exitVariables;

    /**
     * @param returned
     * @param error
     */
    public Result ( RunnableSyntaxElement caller , Value returned , Object error , List< Variable > exitVariables )
    {
        super();
        this.returned = returned;
        this.error = error;
        this.caller = caller;
        this.exitVariables = exitVariables;
    }


    /**
     * @return the returned
     */
    public Value getReturned ()
    {
        return returned;
    }


    /**
     * @return the caller
     */
    public RunnableSyntaxElement getCaller ()
    {
        return caller;
    }


    /**
     * @return the error
     */
    public Object getError ()
    {
        return error;
    }
    
    /**
     * @return the exitVariables
     */
    public List< Variable > getExitVariables ()
    {
        return exitVariables;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString ()
    {
        return caller.getName() + " called and returned: " + this.returned + " with errors: " + this.error;
    }

}
