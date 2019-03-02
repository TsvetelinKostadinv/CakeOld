/*
 * 14/02/2019 18:07:01
 * Either.java created by Tsvetelin
 */

package com.cake.interpreter.utils.dynamic;


import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;


/**
 * 
 * Either type. Used when the type can be either one of <code>T</code> or
 * <code>U</code> <br>
 * <code>Strict</code> implies that the type can only contain one value and the
 * other MUST be <code>null</code>
 * 
 * @param L
 *            - the "left" type of the variable
 * @param R
 *            - the "right" type of the variable
 * 
 * @author Tsvetelin
 *
 */
public class StrictEither < L , R > implements Serializable 
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final L left;

    private final R right;


    StrictEither ( L left , R right )
    {
        if ( left == null && right != null )
        {
            this.left = left;
            this.right = right;
        } else if ( left != null && right == null )
        {
            this.left = left;
            this.right = right;
        } else if ( left == null && right == null )
        {
            throw new IllegalArgumentException( "Cannot set both to null!" );
        } else
        {
            throw new IllegalArgumentException( "Cannot assign both types simutaniously!" );
        }
    }


    public static < L , R > StrictEither< L , R > left ( L value )
    {
        return new StrictEither<>( value , null );
    }


    public static < L , R > StrictEither< L , R >  right ( R value )
    {
        return new StrictEither<>( null , value );
    }


    public boolean containsLeft ()
    {
        return left != null;
    }


    public boolean containsRight ()
    {
        return right != null;
    }


    public Optional< L > getLeft ()
    {
        return Optional.of( left );
    }


    public Optional< R > getRight ()
    {
        return Optional.of( right );
    }


    public < newT , newU > StrictEither< newT , newU > map (
            Function< StrictEither< L , R > , StrictEither< newT , newU > > function )
    {
        Objects.requireNonNull( function , "Function cannot be null" );
        return function.apply( this );
    }


    public < NewL , NewR > StrictEither< NewL , NewR > map ( Function< L , NewL > leftFunc ,
            Function< R , NewR > rightFunc )
    {
        Objects.requireNonNull( leftFunc , "Function cannot be null(left)" );
        Objects.requireNonNull( rightFunc , "Function cannot be null(right)" );
        
        NewL newLeft;
        NewR newRight;
        
        try {
            newLeft = leftFunc.apply( left );
            newRight = null;
        } catch (NullPointerException e) {
            newLeft = null;
            newRight = rightFunc.apply( right );
        }
        
        
        return new StrictEither< NewL , NewR >( newLeft , newRight );
    }


    public < NewL > StrictEither< NewL , R > mapLeft ( Function< L , NewL > leftFunc )
    {
        Objects.requireNonNull( leftFunc , "Function cannot be null(left)" );
        
        NewL newLeft = null;
        
        try {
            newLeft = leftFunc.apply( left );
        } catch (NullPointerException e) {
            
        }
        
        return new StrictEither< NewL , R >( newLeft , right );
    }


    public < NewR > StrictEither< L , NewR > mapRight ( Function< R , NewR > rightFunc )
    {
        Objects.requireNonNull( rightFunc , "Function cannot be null(right)" );
        
        NewR newRight = null;
        
        try
        {
            newRight = rightFunc.apply( right );
        } catch ( Exception e )
        {
        }
        return new StrictEither< L , NewR >( left , newRight );
    }


    public < T > T fold ( Function< L , T > leftFunc , Function< R , T > rightFunc , BinaryOperator< T > operation )
    {
        Objects.requireNonNull( leftFunc , "Function cannot be null(left)" );
        Objects.requireNonNull( rightFunc , "Function cannot be null(right)" );
        Objects.requireNonNull( operation , "Function cannot be null(operator)" );
        
        
        T left = leftFunc.apply( this.left );
        
        T right = rightFunc.apply( this.right );

        T result = operation.apply( left , right );

        return result;
    }


    public StrictEither< R , L > swap ()
    {
        return new StrictEither<>( right , left );
    }


    public < NewL extends L , NewR extends R > StrictEither< NewL , NewR > narrow ( 
            Function< L , NewL > leftFunc ,
            Function< R , NewR > rightFunc )
    {
        Objects.requireNonNull( leftFunc , "Function cannot be null(left)" );
        Objects.requireNonNull( rightFunc , "Function cannot be null(right)" );
        
        NewL newLeft = leftFunc.apply( this.left );
        NewR newRight = rightFunc.apply( this.right );
        
        return new StrictEither< NewL , NewR >( newLeft , newRight );
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString ()
    {
        return String.format( "Either type with left(type %s and value %s) and right(type %s and value %s)" ,
                left == null ? "NULL" : left.getClass().getName() , left == null ? "NULL" : left ,
                right == null ? "NULL" : right.getClass().getName() , right == null ? "NULL" : right );
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals ( Object obj )
    {
        return obj instanceof StrictEither< ? , ? > && ( (StrictEither< ? , ? >) obj ).left.equals( this.left )
                && ( (StrictEither< ? , ? >) obj ).right.equals( this.right );
    }


//    public static void main ( String [] args )
//    {
//        StrictEither< Integer , Double > e = new StrictEither< Integer , Double >( 3 , null );
//
//        System.out.println( e );
//    }

}
