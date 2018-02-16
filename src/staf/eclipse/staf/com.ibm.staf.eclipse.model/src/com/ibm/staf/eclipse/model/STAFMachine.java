/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * Implementation of the ISTAFMachine interface. Also implements
 * IPropertyChangeListener to listen for property changes in contained
 * model Objects.
 * @see ISTAFMachine
 */
public class STAFMachine implements ISTAFMachine, IPropertyChangeListener
{
    /* Constants */
    
    /** XML Element name */
    public final static String STAFMACHINE_ELEM = "STAFMachine";
    /** XML Attribute name */
    public final static String HOSTNAME_ATT = "hostname";
    /** XML Attribute name */
    public final static String PORT_ATT = "port";
    /** XML Attribute name */
    public final static String INTERFACE_ATT = "interface";
    
    /* Instance Fields */
    
    private String hostname;
    private String port;
    private String stafInterface;
    private List requests = Collections.synchronizedList(new ArrayList());
    private List extensionMappings = Collections.synchronizedList(new ArrayList());
    
    //list of services is not persisted in the XML
    private List services;
    
    private ListenerList propertyChangeListeners;
    
    
    /**
     * Constructor
     * @param hostName Hostname of machine
     * @param port Port of machine
     */
    public STAFMachine(String hostName, String port, String stafInterface)
    {
        super();
        
        this.hostname = hostName;
        this.port = port;
        this.stafInterface = stafInterface;
    }
    

    /**
     * @see ISTAFMachine#getHostname()
     */
    public String getHostname()
    {
        return hostname;
    }
    

    /**
     * @see ISTAFMachine#getPort()
     */
    public String getPort()
    {
        return port;
    }
    
    
    /**
     * @see ISTAFMachine#getInterface()
     */
    public String getInterface()
    {
        return stafInterface;
    }

    
    /**
     * @see ISTAFMachine#getRequests()
     */
    public ISTAFRequest[] getRequests()
    {
        return (ISTAFRequest[]) requests.toArray(new ISTAFRequest[requests.size()]);
    }

    
    /**
     * Adds the request to the machine model and registers the
     * machine as a listener for request property changes.
     * @see ISTAFMachine#addRequest(ISTAFRequest)
     */
    public void addRequest(ISTAFRequest request)
    {
        requests.add(request);
        request.addPropertyChangeListener(this);
        firePropertyChange(REQUEST_ADD, null, request);
    }
    
    
    /**
     * Removes the request from the machine model and unregisters the
     * machine as a listener for request property changes.
     * @see ISTAFMachine#addRequest(ISTAFRequest)
     */
    public void removeRequest(ISTAFRequest request)
    {
        requests.remove(request);
        request.removePropertyChangeListener(this);
        firePropertyChange(REQUEST_REMOVE, request, null);
    }
    
    
    /**
     * Adds the extension mapping to the service/extension map
     * and fires a propertyChange.
     * @see ISTAFMachine#addExtensionMapping(String, String)
     */
    public void addExtensionMapping(IExtensionMapping mapping)
    {
        extensionMappings.add(mapping);   
        firePropertyChange(EXT_MAPPING_UPDATE, null, mapping);
    }
    
    
    /**
     * Removes the extension mapping from the service/extension map
     * and fires a propertyChange.
     * @see ISTAFMachine#removeExtensionMapping(String)
     */
    public void removeExtensionMapping(IExtensionMapping mapping)
    {
        extensionMappings.remove(mapping);
        firePropertyChange(EXT_MAPPING_UPDATE, mapping, null);
    }
    
    
    /**
     * Returns a clone of the service/extension map.
     * Changes to the returned Map will not be reflected
     * in the model.
     * @see ISTAFMachine#getExtensionMappings()
     */
    public IExtensionMapping[] getExtensionMappings()
    {
        return (IExtensionMapping[]) extensionMappings.toArray(
               new IExtensionMapping[extensionMappings.size()]);
    }
    
    
    /**
     * Retrieves the list of registered service names for this machine. 
     */
    public List getServices()
    {
        return services;
    }


    /**
     * Sets the list of registered service names for this machine and fires
     * a propertyChange.
     */
    public void setServices(List services)
    {
        List oldServices = services;
        this.services = services;
        firePropertyChange(SERVICES_UPDATE, oldServices, services);
    }
    
    
    /**
     * Static method to create an ISTAFMachine from a Document
     * Object representing the machine.
     * @param doc Document Object to parse
     * @return ISTAFMachine
     */
    public static ISTAFMachine parseXML(Document doc)
    {
        Element machElem = doc.getDocumentElement();
        
        String hostName = machElem.getAttribute(HOSTNAME_ATT);
        
        String port = null;
        if (machElem.hasAttribute(PORT_ATT))
        {
            port = machElem.getAttribute(PORT_ATT);
        }
        
        String stafInterface = null;
        if (machElem.hasAttribute(INTERFACE_ATT))
        {
            stafInterface = machElem.getAttribute(INTERFACE_ATT);
        }
        
        STAFMachine machine = new STAFMachine(hostName, port, stafInterface); 
        
        //Iterate over extension mappings
        
        NodeList extensionMappings = machElem.getElementsByTagName(
                                  ExtensionMapping.EXTENSION_MAPPING_ELEM);
        
        for (int i=0; i < extensionMappings.getLength(); i++)
        {
            IExtensionMapping mapping = ExtensionMapping.parseXML(
                                        (Element) extensionMappings.item(i));
            machine.addExtensionMapping(mapping);
        }
        
        //Iterate over STAF requests
        
        NodeList requests = machElem.getElementsByTagName(
                                     STAFRequest.REQUEST_ELEM);
        
        for (int i=0; i < requests.getLength(); i++)
        {
            ISTAFRequest req = STAFRequest.parseXML(
                                           (Element) requests.item(i));
            machine.addRequest(req);
        }
        
        return machine;
    }
    
    
    /**
     * @see ISTAFMachine#toXML()
     */
    public Document toXML() throws ParserConfigurationException
    {
        Document doc = DocumentBuilderFactory.newInstance().
                               newDocumentBuilder().newDocument();
        
        Element root = doc.createElement(STAFMACHINE_ELEM);
        
        root.setAttribute(HOSTNAME_ATT, getHostname());
        
        if (getPort() != null)
        {
            root.setAttribute(PORT_ATT, getPort());
        }
        
        if (getInterface() != null)
        {
            root.setAttribute(INTERFACE_ATT, getInterface());
        }
        
        
        IExtensionMapping[] mappings = getExtensionMappings();
        for (int i=0; i < mappings.length; i++)
        {
            root.appendChild(mappings[i].toXML(doc));
        }
        
        ISTAFRequest[] reqs = getRequests();
        for (int i=0; i < reqs.length; i++)
        {
            root.appendChild(reqs[i].toXML(doc));
        }
        
        doc.appendChild(root);
        return doc;
    }
    
    
    /**
     * @see ISTAFMachine#getEndpoint()
     */
    public String getEndpoint()
    {
        if (getHostname().equalsIgnoreCase("LOCAL"))
        {
            return "LOCAL";
        }
        else
        {
            StringBuffer buf = new StringBuffer();
            
            if (getInterface() != null)
            {
                buf.append(getInterface()+"://");
            }
            
            buf.append(getHostname());
            
            if (getPort() != null)
            {
                buf.append("@"+getPort());
            }
            
            return buf.toString();
        }
    }
    
    
    /**
     * @see ISTAFMachine#addPropertyChangeListener(IPropertyChangeListener)
     */
    public void addPropertyChangeListener(IPropertyChangeListener listener)
    {
        getPropertyChangeListeners().add(listener);
    }

    
    /**
     * @see ISTAFMachine#removePropertyChangeListener(IPropertyChangeListener)
     */
    public void removePropertyChangeListener(IPropertyChangeListener listener)
    {
        getPropertyChangeListeners().remove(listener);
    }
    
    
    /**
     * Returns the ListenerList of IPropertyChangeListeners or creates
     * an empty list if it does not exist.
     * @return ListenerList
     */
    private ListenerList getPropertyChangeListeners() 
    {
        if (propertyChangeListeners == null)
        {
            propertyChangeListeners = new ListenerList();
        }
          
        return propertyChangeListeners;
    }
    
    
    /**
     * Fires a property change to all IPropertyChangeListeners
     * @param changeId Identifier for type of change
     * @param oldValue Object before change
     * @param newValue Object after change
     */
    protected void firePropertyChange(String changeId, Object oldValue,
                                      Object newValue) 
    {
        final PropertyChangeEvent event = new PropertyChangeEvent(this,
                                          changeId, oldValue, newValue);

        Object[] listeners = getPropertyChangeListeners().getListeners();
        for (int i = 0; i < listeners.length; i++) 
        {
            ((IPropertyChangeListener) listeners[i]).propertyChange(event);
        }
    }

    
    /**
     * Fires a property change for all IPropertyChangeListeners. This
     * allows listener for machine property events to receive events
     * for contained objects.
     * @see IPropertyChangeListener#propertyChange(PropertyChangeEvent)
     */
    public void propertyChange(PropertyChangeEvent event)
    {
        firePropertyChange(event.getProperty(), event.getOldValue(), 
                           event.getNewValue());
        
    }

    
    /**
     * @see ISTAFMachine#setHostname(String)
     */
    public void setHostname(String hostName)
    {
        STAFMachine clonedMach = (STAFMachine) this.clone();
        this.hostname = hostName;
        firePropertyChange(MACH_UPDATE, clonedMach, this);
    }

    
    /**
     * @see ISTAFMachine#setPort(String)
     */
    public void setPort(String port)
    {
        STAFMachine clonedMach = (STAFMachine) this.clone();
        this.port = port;
        firePropertyChange(MACH_UPDATE, clonedMach, this);
    }
    
    
    /**
     * @see ISTAFMachine#setInterface(String)
     */
    public void setInterface(String stafInterface)
    {
        STAFMachine clonedMach = (STAFMachine) this.clone();
        this.stafInterface = stafInterface;
        firePropertyChange(MACH_UPDATE, clonedMach, this);
    }
    
    
    /**
     * Provides a clone of the machine object
     */
    public Object clone()
    {
        STAFMachine cloneMach = new STAFMachine(getHostname(), getPort(), 
                                                getInterface());
        cloneMach.requests = this.requests;
        cloneMach.propertyChangeListeners = this.propertyChangeListeners;
        cloneMach.extensionMappings = this.extensionMappings;
        return cloneMach;
    }
}