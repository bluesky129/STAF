/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.model;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Interface to represent a STAF Request
 */
public interface ISTAFRequest
{
    /**
     * Property Change Event key
     */
    static final String REQUEST_UPDATE = "REQUEST_UPDATE";
    
    /**
     * Returns the command
     * @return command
     */
    public String getCommand();

    /**
     * Returns the RC as an int. If this request
     * has not yet completed returns -1.
     * @return rc
     */
    public int getRC();

    /**
     * Returns the result
     * @return result
     */
    public String getResult();

    /**
     * Sets the RC and Result
     * @param rc
     * @param result
     */
    public void setResult(int rc, String result);

    /**
     * Returns the service name
     * @return service name
     */
    public String getService();
    
    /**
     * Adds an IPropertyChangeListener for request model changes.
     * @param listener IPropertyChangeListener to add
     */
    public void addPropertyChangeListener(IPropertyChangeListener listener);

    /**
     * Removes an IPropertyChangeListener from the list of listeners receiving
     * request model updates.
     * @param listener IPropertyChangeListener to remove
     */
    public void removePropertyChangeListener(IPropertyChangeListener listener);
    
    /**
     * Returns an Element representation of the request
     * @param doc Document object that may be used to create other Node
     * Objects (Elements, etc.)
     * @return Element
     */
    public Element toXML(Document doc);
    
    /**
     * Retrieves the set for this request. The key is any object, passed
     * to the setKey method, used for identification purposes. 
     * @return key
     */
    public Object getKey();
    
    /**
     * Sets the key for this request. The key is any object, passed
     * to the setKey method, used for identification purposes.
     * @param key 
     */
    public void setKey(Object key);
}
