/*
 * 05/11/2018 at 18:15:03 ÷.
 * ParsersContainer.java created by Tsvetelin
 */

package com.cake.syntax.parsers;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.cake.utils.container.Container;
import com.cake.utils.reflection.ReflectionImplementationSearcher;


/**
 * 
 * Container for all the parsers. They should be included here in order to be
 * used. <br>
 * It searches for parsers that implement
 * <code>com.cake.syntax.parsers.Parser</code> <br>
 * NOTE: the parsers should be included in a package containing "parser"!! <br>
 * NOTE: NOT WORKING AT THE MOMENT
 * 
 * @see com.cake.syntax.parsers.Parser
 * 
 * @author Tsvetelin
 *
 */
public class ParsersContainer extends Container< Parser< ? > >
{

    public static ParsersContainer INSTANCE = new ParsersContainer();


    private List< Parser< ? > > parsers = new LinkedList<>();


    private ParsersContainer ()
    {

        //loadParsers();
    }


    /**
     * 
     */
    private void loadParsers ()
    {

        List< Package > packages = Arrays.stream( this.getClass().getClassLoader().getDefinedPackages() )
                .filter( x -> x.getName().toLowerCase().contains( "parser" ) ).collect( Collectors.toList() );
        System.out.println( packages );
        for ( Package pack : packages )
        {
            System.out.println( "Searching in: " + pack.getName() );
            List< Class< ? > > currentParsers = ReflectionImplementationSearcher.findClassesImpmenenting( Parser.class ,
                    pack );
            System.out.println( "Found: " + currentParsers.toString() );
            currentParsers.stream().filter( x -> !x.isAnnotationPresent( IgnoreParser.class ) )
                    .peek( x -> System.out.println( "Without annotation: " + x.getSimpleName() ) )
                    .filter( x -> !x.isInterface() )
                    .peek( x -> System.out.println( "Are not interfaces: " + x.getSimpleName() ) )
                    .filter( x -> !Modifier.isAbstract( x.getModifiers() ) )
                    .peek( x -> System.out.println( "Non abstract: " + x.getSimpleName() ) )
                    .forEach( x -> parsers.add( invokeConstructor( x ) ) );
        }

    }


    /**
     * @param classForConstruction
     * @return
     */
    private Parser< ? > invokeConstructor ( Class< ? > classForConstruction )
    {

        try
        {
            Constructor< ? > constr = classForConstruction.getConstructor();

            constr.setAccessible( true );

            return (Parser< ? >) constr.newInstance();
        } catch ( InstantiationException e )
        {
            // should never get here
        } catch ( IllegalAccessException e )
        {
            // should never get here
        } catch ( IllegalArgumentException e )
        {
            throw new RuntimeException( "Class with name: " + classForConstruction.getName()
                    + " does not declare a constructor with no parameters!" );
        } catch ( InvocationTargetException e )
        {
            throw new RuntimeException(
                    "Class with name: " + classForConstruction.getName() + " threw exception while initializing!" );
        } catch ( NoSuchMethodException e )
        {
            throw new RuntimeException( "Class with name: " + classForConstruction.getName()
                    + " does not declare a constructor with no parameters!" );
        } catch ( SecurityException e )
        {
        }
        return null;
    }


    public void addParser ( Parser< ? > parser )
    {
        if(!elements.contains( parser )) super.addElement( parser );
    }


    public void removeParser ( Parser< ? > parser )
    {

        super.removeElement( parser );
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator< Parser< ? > > iterator ()
    {

        return parsers.iterator();
    }


    public static void main ( String [] args )
    {

        ParsersContainer.INSTANCE.hashCode();
    }

}
