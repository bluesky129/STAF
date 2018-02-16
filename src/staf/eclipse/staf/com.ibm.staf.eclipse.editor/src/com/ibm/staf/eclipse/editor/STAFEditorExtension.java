/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.editor;

import org.eclipse.jface.util.PropertyChangeEvent;

import com.ibm.staf.eclipse.model.ISTAFRequest;

/**
 * STAFEditorExtension is an abstract class which provides implementations
 * for some of the ISTAFEditorExtension interface methods as well as some 
 * convenience methods. It is suggested that STAFEditor extensions extend 
 * this class.
 */
public abstract class STAFEditorExtension implements ISTAFEditorExtension
{
    //instance fields
    
    private STAFEditor editor;
    private String serviceName;
    
    
    /**
     * Constructor
     * 
     * STAFEditor will always use the empty argument constructor to
     * create STAFEditorExtensions.
     */
    public STAFEditorExtension()
    {
        super();
    }
    
    /* Abstract Methods */
    
    /**
     * This method is called when a property on an ISTAFRequest changes for which
     * this extension has registered as a listener. This is normally used to
     * get the results of a STAF request submission.
     * @param request The updated ISTAFRequest
     */
    public abstract void requestPropertyChanged(ISTAFRequest request);
    
    
    /* Implemented Methods */
    
    
    /**
     * Adds a request to the machine model of the STAFEditor. Calling
     * this method will add the command and its result after completion
     * to the "Command History" table of the main STAFEditor page.
     * @param request the ISTAFRequest to add
     */
    public void addRequest(ISTAFRequest request)
    {
        editor.addRequest(request);
    }
    
    
    /**
     * Convenience method for submitting STAF commands. This method will also
     * register the STAFEditorExtension as a propertyChangeListener for
     * the request.
     * 
     * To have the request and result appear on the "Command History" table of 
     * the main STAFEditor page call addRequest(ISTAFRequest) before calling
     * this method.
     * 
     * @param request - the ISTAFRequest to submit
     */
    public void submitCommand(ISTAFRequest request)
    {
        request.addPropertyChangeListener(this);
        editor.submitCommand(request);
    }
    
    
    /**
     * Retrieves the STAFEditor for this extension
     * @return STAFEditor
     */
    public STAFEditor getEditor()
    {
        return editor;
    }
    
    
    /**
     * @see com.ibm.staf.eclipse.editor.ISTAFEditorExtension#setEditor
     */
    public void setEditor(STAFEditor editor)
    {
        this.editor = editor;
    }
    
    
    /**
     * @see com.ibm.staf.eclipse.editor.ISTAFEditorExtension#setServiceName
     */
    public void setServiceName(String name)
    {
        this.serviceName = name;
    }
    
    
    /**
     * Called when a PropertyChangeEvent occurs
     * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange
     */
    public void propertyChange(PropertyChangeEvent event)
    {
        if (event.getProperty().equals(ISTAFRequest.REQUEST_UPDATE))
        {
            ISTAFRequest req = (ISTAFRequest) event.getNewValue();
            requestPropertyChanged(req);
            req.removePropertyChangeListener(this);
        }
    }

    
    /**
     * Returns the STAF service name associated with this instance of
     * this STAFEditorExtension
     * @return String serviceName
     */
    public String getServiceName()
    {
        return serviceName;
    }
    
    
    /**
     * Empty implementation of dispose(). Should be overridden
     * by extensions which need to dispose of resources.
     */
    public void dispose()
    {     
    }
}