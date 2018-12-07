/*
 * 24/10/2018 at 18:00:30
 * TokenTypeHolder.java created by Tsvetelin
 */

package com.cake.tokens.types;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import com.cake.utils.container.Container;


/**
 * 
 * Container for the Types.
 * 
 * @author Tsvetelin
 *
 */
public class TokenTypesContainer extends Container< TokenTypesContainer.TokenTypeHolder >
{

    /**
     * The singleton pattern ensures that there is one and only one instance of this
     * object at any time
     */
    public static final TokenTypesContainer INSTANCE = new TokenTypesContainer();

    /**
     * @author Tsvetelin
     *
     */
    public class TokenTypeHolder
    {

        private String identifier;


        /**
         * Private so it can't be instantiated outside
         * 
         * @param identifier
         *            - the name of the type
         */
        private TokenTypeHolder ( String identifier )
        {

            this.identifier = identifier;
        }


        /**
         * 
         * @return the name of the type
         */
        public String getIdentifier ()
        {

            return identifier;
        }


        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals ( Object arg0 )
        {

            return arg0 instanceof TokenTypeHolder && ( (TokenTypeHolder) arg0 ).identifier.equals( this.identifier );
        }


        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString ()
        {

            return "Token type of: " + identifier;
        }

    }


    /**
     * Making it private in order to implement the singleton pattern
     */
    private TokenTypesContainer ()
    {
        elements = new ArrayList<>();
        Iterator< String > iter = Arrays.asList( BaseTypesIdentificators.values() )
                                        .stream()
                                        .map( x -> x.getValue() )
                                        .iterator();
        while ( iter.hasNext() )
        {
            elements.add( new TokenTypeHolder( iter.next() ) );

        }
    }


    /**
     * 
     * @param identifier
     *            - the name of the type to be inserted. The full path of the bundle
     *            is to be used here.
     */
    public void addType ( String identifier )
    {
        super.addElement( new TokenTypeHolder( identifier ) );
    }


    /**
     * 
     * @param identifier
     *            - the searched type
     * @return the <code>TokenTypeHolder</code> object representing this type if it
     *         exists otherwise returns <code>null</code>
     */
    public TokenTypeHolder getTypeForIdentifier ( String identifier )
    {

        for ( TokenTypeHolder type : elements )
        {
            if ( type.getIdentifier().equals( identifier ) ) { return type; }
        }
        return null;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator< TokenTypeHolder > iterator ()
    {

        return elements.iterator();

    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals ( Object arg0 )
    {

        return arg0 instanceof TokenTypesContainer && ( (TokenTypesContainer) arg0 ).elements.equals( this.elements );
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString ()
    {

        StringBuilder sb = new StringBuilder();
        sb.append( "This is a list of all Token types." + System.lineSeparator() );
        sb.append( "It contains: " + elements.size() + " entries" + System.lineSeparator() );
        for ( int i = 0 ; i < elements.size() ; i++ )
        {
            sb.append( i + ": " + elements.get( i ).toString() + System.lineSeparator() );
        }
        return sb.toString();
    }


}
