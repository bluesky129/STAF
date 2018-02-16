/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Interface to represent user defined service
 * to extension mapping.
 */
public interface IExtensionMapping
{
    /**
     * Retrieves the service name for this mapping
     * @return service name
     */
    public String getService();
    
    /**
     * Retrieves the service name for this mapping
     * @return service name
     */
    public String getExtension();
    
    /**
     * Retrieves the optional tab title for this mapping
     * @return service name or null if no title is defined
     */
    public String getTitle();
    
    /**
     * Returns an Element representation of the mapping
     * @return Element
     */
    public Element toXML(Document doc);
}
