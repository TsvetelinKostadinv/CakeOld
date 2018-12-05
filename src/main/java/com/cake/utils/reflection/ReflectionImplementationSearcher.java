/*
 * 05/12/2018 at 16:18:47 ÷.
 * ReflectionImplementationSearcher.java created by Tsvetelin
 */

package com.cake.utils.reflection;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


/**
 * @author Tsvetelin
 *
 */
public class ReflectionImplementationSearcher
{

    public static List< Class< ? > > findClassesImpmenenting ( final Class< ? > interfaceClass ,
            final Package fromPackage )
    {

        if ( interfaceClass == null ) throw new NullPointerException( "The interface cannot be null!" );

        if ( fromPackage == null ) throw new NullPointerException( "The package cannot be null!" );

        final List< Class< ? > > rVal = new ArrayList< Class< ? > >();

        try
        {
            final Class< ? > [] targets = getAllClassesFromPackage( fromPackage.getName() );
            if ( targets != null )
            {
                for ( Class< ? > currentClass : targets )
                {
                    if ( interfaceClass.isAssignableFrom( currentClass ) )
                    {
                        rVal.add( currentClass );
                    }
                }
            }
        } catch ( ClassNotFoundException e )
        {
            throw new IllegalArgumentException( "Illegal package!" , e );
        } catch ( IOException e )
        {
            throw new IllegalArgumentException( "Error in classes!" , e );
        }

        return rVal;
    }


    /**
     * Load all classes from a package.
     * 
     * @param packageName
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static Class< ? > [] getAllClassesFromPackage ( final String packageName )
            throws ClassNotFoundException , IOException
    {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace( '.' , '/' );
        Enumeration< URL > resources = classLoader.getResources( path );
        List< File > dirs = new ArrayList< File >();
        while ( resources.hasMoreElements() )
        {
            URL resource = resources.nextElement();
            dirs.add( new File( resource.getFile() ) );
        }
        ArrayList< Class< ? > > classes = new ArrayList< Class< ? > >();
        for ( File directory : dirs )
        {
            classes.addAll( findClasses( directory , packageName ) );
        }
        return classes.toArray( new Class[classes.size()] );
    }


    /**
     * Find file in package.
     * 
     * @param directory
     * @param packageName
     * @return
     * @throws ClassNotFoundException
     */
    public static List< Class< ? > > findClasses ( File directory , String packageName ) throws ClassNotFoundException
    {

        List< Class< ? > > classes = new ArrayList< Class< ? > >();
        if ( !directory.exists() ) { return classes; }
        File [] files = directory.listFiles();
        for ( File file : files )
        {
            if ( file.isDirectory() )
            {
                assert !file.getName().contains( "." );
                classes.addAll( findClasses( file , packageName + "." + file.getName() ) );
            } else if ( file.getName().endsWith( ".class" ) )
            {
                classes.add( Class
                        .forName( packageName + '.' + file.getName().substring( 0 , file.getName().length() - 6 ) ) );
            }
        }
        return classes;
    }
}
