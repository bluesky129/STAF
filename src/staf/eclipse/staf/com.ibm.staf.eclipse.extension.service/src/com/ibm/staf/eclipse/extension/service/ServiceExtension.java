/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.extension.service;

import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.Bundle;

import com.ibm.staf.eclipse.editor.STAFEditorExtension;
import com.ibm.staf.eclipse.model.ISTAFRequest;

/**
 * The main class for a STAFEditor extension for the Service
 * service. This extension provides a multiple tab page
 * via CTabFolder.
 * @see ISTAFEditorExtension 
 */
public class ServiceExtension extends STAFEditorExtension
{
    /* Class Fields */
    
    public static Image refreshIcon;
    
    /*
     * Use static block to initialize Images.
     */
    static
    {
        //TODO figure out if/when these should be disposed
        Bundle bundle = Activator.getDefault().getBundle();
        
        URL refreshURL = bundle.getResource("icons/cmd_Refresh.gif");
        ImageDescriptor refreshID = ImageDescriptor.createFromURL(refreshURL);
        refreshIcon = refreshID.createImage();
    }

    
    /**
     * Constructor
     */
    public ServiceExtension()
    {
        super();
    }

    
    /**
     * Each TabItem for the TabFolder is handled by a class.
     * Each class implementing a TabItem must be of type Composite
     * and will be set as the Control for the tab.
     * @see ISTAFEditorExtension#createPage
     */
    public Composite createPage(Composite parent)
    {
        Composite composite = new Composite(parent, SWT.NONE);
        FillLayout layout = new FillLayout();
        layout.marginHeight = 10;
        layout.marginWidth = 10;
        composite.setLayout(layout);
        
        CTabFolder folder = new CTabFolder(composite, SWT.BORDER | SWT.FLAT);
        
        CTabItem requestsTab = new CTabItem(folder, SWT.NONE);
        requestsTab.setText("Requests");
        Composite requestPage = new RequestPage(this, folder, SWT.NONE);
        requestsTab.setControl(requestPage);
        
        CTabItem servicesTab = new CTabItem(folder, SWT.NONE);
        servicesTab.setText("Services");
        Composite servicePage = new ServicePage(this, folder, SWT.NONE);
        servicesTab.setControl(servicePage);
        
        return composite;
    }

    
    /**
     * Nothing to do currently, required by IExecutableExtension.
     */
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException
    {
    }
    
    
    /**
     * Required by ISTAFEditorExtension, but all requests submitted by
     * this extension are handled with inline anonymous listeners.
     */
    public void requestPropertyChanged(ISTAFRequest request)
    {
    }
}