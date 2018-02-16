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
 * Implementation of IExtensionMapping. Overrides Object.equals(Object)
 * method to allow comparisons with the Java Collecion APIs.
 * @see IExtensionMapping
 */
public class ExtensionMapping implements IExtensionMapping
{
    /* Constants */
    
    /** XML Element name */
    public final static String EXTENSION_MAPPING_ELEM = "extensionMapping";
    /** XML Attribute name */
    public final static String SERVICE_ATT = "service";
    /** XML Attribute name */
    public final static String EXTENSION_ATT = "extension";
    /** XML Attribute name */
    public final static String TITLE_ATT = "title";
    
    /* Instance Fields */
    
    private String service;
    private String extension;
    private String title;
    
    /**
     * Constructor
     * @param service
     * @param extension
     */
    public ExtensionMapping(String service, String extension)
    {
        this(service, extension, null);
    }
    
    /**
     * Constructor
     * @param service
     * @param extension
     * @param title
     */
    public ExtensionMapping(String service, String extension, String title)
    {
        this.service = service;
        this.extension = extension;
        this.title = title;
    }
    

    /**
     * @see IExtensionMapping#getExtension()
     */
    public String getExtension()
    {
        return extension;
    }

    /**
     * @see IExtensionMapping#getService()
     */
    public String getService()
    {
        return service;
    }
    
    /**
     * @see IExtensionMapping#getTitle()
     */
    public String getTitle()
    {
        return title;
    } 
    
    /**
     * Provided for use in comparisons in Java Collection
     * APIs
     * @see Object#equals(Object)
     */
    public boolean equals(Object obj)
    {
        if (obj instanceof IExtensionMapping)
        {
            IExtensionMapping mapping = (IExtensionMapping) obj;
            
            if (service.equalsIgnoreCase(mapping.getService()) &&
                extension.equalsIgnoreCase(mapping.getExtension()))
            {
                return true;
            }
        }
        
        return false;
    }


    /**
     * Provided because we provided equals() method and to maintain 
     * contract defined in Object.hashCode().
     * @see Object#hashCode()
     */
    public int hashCode()
    {
        return getService().hashCode()+getExtension().hashCode();
    }

    
    /**
     * @see IExtensionMapping#toXML(Document)
     */
    public Element toXML(Document doc)
    {
        Element extensionMapping = doc.createElement(EXTENSION_MAPPING_ELEM);
        extensionMapping.setAttribute(SERVICE_ATT, getService());
        extensionMapping.setAttribute(EXTENSION_ATT, getExtension());
        
        if (getTitle() != null)
        {
            extensionMapping.setAttribute(TITLE_ATT, getTitle());
        }
        
        return extensionMapping;
    }

    
    /**
     * Static method to create an IExtensionMapping from an Element
     * Object representing the mapping.
     * @param elem Element Object to parse
     * @return ISTAFRequest
     */
    public static IExtensionMapping parseXML(Element elem)
    {
        String title = elem.getAttribute(TITLE_ATT);
        title = (title.equals("")) ? null : title;
        
        IExtensionMapping mapping = new ExtensionMapping(
                                    elem.getAttribute(SERVICE_ATT),
                                    elem.getAttribute(EXTENSION_ATT),
                                    title);
        
        return mapping;
    }  
}