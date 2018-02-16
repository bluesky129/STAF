/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.editor;

/**
 * Class to represent exceptions dealing with XML.
 */
public class XMLException extends Exception
{

    /**
     * Inherited constructor
     */
    public XMLException()
    {
        super();
    }

    /**
     * Inherited constructor
     */
    public XMLException(String message)
    {
        super(message);
    }

    /**
     * Inherited constructor
     */
    public XMLException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Inherited constructor
     */
    public XMLException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
