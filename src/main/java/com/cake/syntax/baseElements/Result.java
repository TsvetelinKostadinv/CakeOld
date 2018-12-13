/*
 * 10/12/2018 18:15:51
 * Result.java created by Tsvetelin
 */

package com.cake.syntax.baseElements;

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
    private final Object returned;
    
    /**
     * If there were any errors they are here
     */
    private final Object error;

    /**
     * The caller of the runnable
     */
    private final RunnableSyntaxElement caller;


    /**
     * @param returned
     * @param error
     */
    public Result ( RunnableSyntaxElement caller , Object returned , Object error )
    {
        super();
        this.returned = returned;
        this.error = error;
        this.caller = caller;
    }


    /**
     * @return the returned
     */
    public Object getReturned ()
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
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString ()
    {
        return caller.getName() + " called and returned: " + this.returned + " with errors: " + this.error;
    }

}
