/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.model;

import java.util.List;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.w3c.dom.Document;


/**
 * Interface to represent a STAF Machine
 */
public interface ISTAFMachine
{
    /* Constants */
    
    /** Property Change Event key */
    public static final String REQUEST_ADD = "REQUEST_ADD";
    /** Property Change Event key */
    public static final String REQUEST_REMOVE = "REQUEST_REMOVE";
    /** Property Change Event key */
    public static final String MACH_UPDATE = "MACH_UPDATE";
    /** Property Change Event key */
    public static final String EXT_MAPPING_UPDATE = "EXT_MAPPING_UPDATE";
    /** Property Change Event key */
    public static final String SERVICES_UPDATE = "SERVICES_UPDATE";
    /** Filename extension associated with the STAF Editor */
    public final static String FILENAME_EXT = ".stafmach";
    /** Default port value */
    public final static String DEFAULT_PORT = "6500";
    /** Default interface value */
    public final static String DEFAULT_INTERFACE = "tcp";
    
    
    /**
     * Returns the hostname for the machine
     * @return String hostname
     */
    public String getHostname();
    
    /**
     * Sets the hostname for the machine
     * @param name hostname of the machine
     */
    public void setHostname(String name);

    /**
     * Returns the port for the machine
     * @return String port or null if using default (not specified) port
     */
    public String getPort();
    
    /**
     * Sets the port for the machine. Specify null to indicate
     * to use the default (not specified) port
     * @param port of the machine
     */
    public void setPort(String port);
    
    /**
     * Sets the interface for the machine. Specify null to indicate
     * to use the default (not specified) interface
     * @param stafInterface of the machine
     */
    public void setInterface(String stafInterface);
    
    /**
     * Returns the interface for the machine
     * @return String stafInterface or null if using default (not specified) 
     * interface
     */
    public String getInterface();
    
    /**
     * Returns the endpoint for the machine
     * @return String [interface://]host[@port]
     */
    public String getEndpoint();
    
    /**
     * Returns the ISTAFRequests for the machine
     * @return ISTAFRequest[] requests stored in the machine model
     */
    public ISTAFRequest[] getRequests();

    /**
     * Adds a request to the machine model
     * @param request ISTAFRequest to add
     */
    public void addRequest(ISTAFRequest request);
    
    /**
     * Removes a request from the machine model
     * @param request ISTAFRequest to remove
     */
    public void removeRequest(ISTAFRequest request);
    
    /**
     * Adds an IPropertyChangeListener for machine model changes.
     * Listeners will receive notifications of changes to the machine
     * or any contained requests.
     * @param listener IPropertyChangeListener to add
     */
    public void addPropertyChangeListener(IPropertyChangeListener listener);

    /**
     * Removes an IPropertyChangeListener from the list of listeners receiving
     * machine model updates.
     * @param listener IPropertyChangeListener to remove
     */
    public void removePropertyChangeListener(IPropertyChangeListener listener);
    
    /**
     * Adds an entry to the machine's service/extension map.
     * @param service Name of service to associate with the extension
     * @param extension The full name of the ISTAFEditorExtension class.
     * (Ex: com.ibm.staf.eclipse.editorextension.var.VARExtension)
     */
    public void addExtensionMapping(IExtensionMapping mapping);
    
    /**
     * Removes an entry from the machine's service/extension map.
     * @param service Name of the service to remove from the association
     * table
     */
    public void removeExtensionMapping(IExtensionMapping service);
    
    /**
     * Returns a Map of user defined service to extension mappings
     * for the machine key=service name, value=main extension class 
     * (implementing ISTAFEditorExtension)
     * @return Map of service/extension associations
     */
    public IExtensionMapping[] getExtensionMappings();
    
    /**
     * Sets the list of STAF service names available to this machine
     * @param services List of service names
     */
    public void setServices(List services);
    
    /**
     * Retrieves the list of STAF service names available to this machine
     * @return List of service names
     */
    public List getServices();
    
    /**
     * Returns a Document representation of the machine
     * @return Document
     */
    public Document toXML() throws Exception;
}
