/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.model;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;


/**
 * Implementation of the ISTAFRequest interface.
 * @see ISTAFRequest
 */
public class STAFRequest implements ISTAFRequest
{
    /* Constants */
    
    /** XML Element name */
    public final static String REQUEST_ELEM = "request";
    /** XML Element name */
    public final static String SERVICE_ELEM = "service";
    /** XML Element name */
    public final static String COMMAND_ELEM = "command";
    /** XML Element name */
    public final static String RC_ELEM = "rc";
    /** XML Element name */
    public final static String RESULT_ELEM = "result";
    
    /* Instance Fields */
    
    private String service;
    private String command;
    private int rc = -1;
    private String result = null;
    private Object key;
    
    private ListenerList propertyChangeListeners;
    
    
    /**
     * Constructor
     * @param service name of service
     * @param command command string
     */
    public STAFRequest(String service, String command)
    {
        super();
        
        this.service = service;
        this.command = command;
    }

    
    /**
     * @see ISTAFRequest#getCommand()
     */
    public String getCommand()
    {
        return command;
    }

    /**
     * Returns -1 if the request has not completed.
     * @see ISTAFRequest#getRC()
     */
    public int getRC()
    {
        return rc;
    }
    
    /**
     * Returns null if the request has not completed.
     * @see ISTAFRequest#getResult()
     */
    public String getResult()
    {
        return result;
    }

    /**
     * Sets the result and fires a property change
     * @see ISTAFRequest#setResult(String, String)
     */
    public void setResult(int rc, String result)
    {
        STAFRequest oldRequest = (STAFRequest) this.clone();
        this.rc = rc;
        this.result = result;
        firePropertyChange(REQUEST_UPDATE, oldRequest, this);
    }

    /**
     * @see ISTAFRequest#getService()
     */
    public String getService()
    {
        return service;
    }

    /**
     * Static method to create an ISTAFRequest from an Element
     * Object representing the request.
     * @param elem Element Object to parse
     * @return ISTAFRequest
     */
    public static ISTAFRequest parseXML(Element elem)
    {
        Element serviceElem = 
            (Element) elem.getElementsByTagName(SERVICE_ELEM).item(0);
        Node serviceText = serviceElem.getFirstChild();
        String service = serviceText.getNodeValue();
        
        Element cmdElem = 
            (Element) elem.getElementsByTagName(COMMAND_ELEM).item(0);
        Node cmdText = cmdElem.getFirstChild();
        String command = cmdText.getNodeValue();
        
        Element rcElem = (Element) elem.getElementsByTagName(RC_ELEM).item(0);
        Node rcText = rcElem.getFirstChild();
        String rc = rcText.getNodeValue();
        
        Element resultElem = 
            (Element) elem.getElementsByTagName(RESULT_ELEM).item(0);
        Node resultText = resultElem.getFirstChild();
        String result = (resultText == null) ? null : resultText.getNodeValue();
        
        STAFRequest req = new STAFRequest(service, command);
        req.setResult(Integer.parseInt(rc), result);
        
        return req;
    }
    
    /**
     * @see ISTAFRequest#toXML(Document)
     */
    public Element toXML(Document doc)
    {
        Element elem = doc.createElement(REQUEST_ELEM);
        
        Element service = doc.createElement(SERVICE_ELEM);
        Text serviceText = doc.createTextNode(getService());
        service.appendChild(serviceText);
        
        Element command = doc.createElement(COMMAND_ELEM);
        Text cmdText = doc.createTextNode(getCommand());
        command.appendChild(cmdText);
        
        Element rc = doc.createElement(RC_ELEM);
        Text rcText = doc.createTextNode(String.valueOf(getRC()));
        rc.appendChild(rcText);
        
        Element result = doc.createElement(RESULT_ELEM);
        if (getResult() != null)
        {
            Text resultText = doc.createTextNode(getResult());
            result.appendChild(resultText);
        }
        
        elem.appendChild(service);
        elem.appendChild(command);
        elem.appendChild(rc);
        elem.appendChild(result);
        
        return elem;
    }
    
    
    /**
     * @see ISTAFRequest#addPropertyChangeListener(IPropertyChangeListener)
     */
    public void addPropertyChangeListener(IPropertyChangeListener listener)
    {
        getPropertyChangeListeners().add(listener);
    }

    /**
     * @see ISTAFRequest#removePropertyChangeListener(IPropertyChangeListener)
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
     * Provides a clone of the request object
     */
    public Object clone()
    {
        STAFRequest clone = new STAFRequest(getService(), getCommand());
        clone.rc = this.rc;
        clone.result = this.result;
        return clone;
    }


    /**
     * @see ISTAFRequest#getKey()
     */
    public Object getKey()
    {
        return key;
    }


    /**
     * @see ISTAFRequest#setKey(Object)
     */
    public void setKey(Object key)
    {
        this.key = key;
    }
}