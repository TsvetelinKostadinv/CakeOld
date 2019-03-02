/*
 * 06/12/2018 at 15:06:19 ÷.
 * Start.java created by Tsvetelin
 */

package com.cake.interpreter.running.runtime;

import java.util.List;

import com.cake.interpreter.STDLib.STDLibMethodContainer;
import com.cake.interpreter.STDLib.methods.STDLibMethod;

/**
 * 
 * A standard runner for future setup before the runtime kicks in
 * 
 * @author Tsvetelin
 *
 */
public class Runner
{
    /**
     * @return a new project runtime
     */
    public static CakeRuntime getNewProjectRuntime ()
    {
        CakeRuntime runtime = new CakeRuntime();
        
        runtime = addSTDLib(runtime);
        
        return runtime;
    }

    /**
     * @param runtime
     * @return
     */
    private static CakeRuntime addSTDLib ( CakeRuntime runtime )
    {
        List< STDLibMethod > stdMethods = STDLibMethodContainer.INSTANCE.getAllSTDMethods();
        
        for( STDLibMethod m : stdMethods )
        {
            runtime.addDecalredElement( m.getFullName() , m );
        }
        
        return runtime;
    }

}
