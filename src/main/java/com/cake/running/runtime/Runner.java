/*
 * 06/12/2018 at 15:06:19 �.
 * Start.java created by Tsvetelin
 */

package com.cake.running.runtime;


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
        return new CakeRuntime();
    }

}
