/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.extension.handle;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.osgi.framework.Bundle;

import com.ibm.staf.STAFMarshallingContext;
import com.ibm.staf.STAFResult;
import com.ibm.staf.eclipse.editor.STAFEditorExtension;
import com.ibm.staf.eclipse.editor.Util;
import com.ibm.staf.eclipse.model.ISTAFRequest;
import com.ibm.staf.eclipse.model.STAFRequest;

/**
 * The main class for a STAFEditor extension for the Handle
 * service.
 * @see ISTAFEditorExtension 
 */
public class HandleExtension extends STAFEditorExtension
{
    /* Class Fields */
    
    private static Image refreshIcon;
    
    /* Instance Fields */
    
    private Table handleTable;

    private Button registeredCBox;
    private Button inProcessCBox;
    private Button staticCBox;
    private Button pendingCBox;
    
    
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
    public HandleExtension()
    {
        super();
    }

    
    /**
     * @see ISTAFEditorExtension#createPage
     */
    public Composite createPage(Composite parent)
    {
        Composite composite = new Composite(parent, SWT.NONE);

        FillLayout layout = new FillLayout();
        layout.marginHeight = 10;
        layout.marginWidth = 10;
        composite.setLayout(layout);
        
        //Button Row
        createHandleGroup(composite);
        
        //Set Initial List
        refreshHandleList();

        return composite;
    }
    

    /**
     * Creates the group to contain all widgets the various
     * buttons and the handle table
     * @param parent
     * @return Group
     */
    private Group createHandleGroup(Composite parent)
    {
        Group handleGroup = new Group(parent, SWT.NONE);
        handleGroup.setText("STAF Handles");
        handleGroup.setToolTipText("Use checkboxes to filter handles.");
        
        GridLayout layout = new GridLayout(5, false);
        layout.marginHeight = 10;
        layout.marginWidth = 10;
        layout.verticalSpacing = 10;
        handleGroup.setLayout(layout);
        
        createButtonRow(handleGroup);
        
        handleTable = createHandleTable(handleGroup);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.horizontalSpan = 5;
        handleTable.setLayoutData(data);
        
        return handleGroup;
    }


    /**
     * Creates a row of buttons for display in handle group
     * @param parent
     */
    private void createButtonRow(Composite parent)
    {
        //Refresh Button
        Button refreshButton = new Button(parent, SWT.PUSH);
        refreshButton.setToolTipText("Refresh Handle List");
        refreshButton.setImage(refreshIcon);
        refreshButton.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    refreshHandleList();
                }
            });
        
        //Registered Check Box
        registeredCBox = new Button(parent, SWT.CHECK);
        registeredCBox.setText("Registered");
        registeredCBox.setSelection(true);
        registeredCBox.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    refreshHandleList();
                }
            });
        
        //InProcess Check Box
        inProcessCBox = new Button(parent, SWT.CHECK);
        inProcessCBox.setText("InProcess");
        inProcessCBox.setSelection(true);
        inProcessCBox.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    refreshHandleList();
                }
            });
        
        //Static Check Box
        staticCBox = new Button(parent, SWT.CHECK);
        staticCBox.setText("Static");
        staticCBox.setSelection(true);
        staticCBox.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    refreshHandleList();
                }
            });
        
        //Pending Check Box
        pendingCBox = new Button(parent, SWT.CHECK);
        pendingCBox.setText("Pending");
        pendingCBox.setSelection(false);
        pendingCBox.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    refreshHandleList();
                }
            });
    }
    
    
    /**
     * Creates the Table widget for displaying STAF Handles.
     * @param parent
     * @return Table
     */
    private Table createHandleTable(Composite parent)
    {
        //First create Table widget
        Table handleTable = new Table(parent, SWT.H_SCROLL | SWT.V_SCROLL
                                      | SWT.SINGLE | SWT.FULL_SELECTION 
                                      | SWT.BORDER);

        TableLayout tableLayout = new TableLayout();
        handleTable.setLayout(tableLayout);

        handleTable.setLinesVisible(true);
        handleTable.setHeaderVisible(true);
        
        //add TableColumns
        String[] STD_HEADINGS = { "Handle", "Handle Name", "State", 
                                  "Last Used", "PID" };
        
        tableLayout.addColumnData(new ColumnWeightData(10, 10, true));
        TableColumn tc0 = new TableColumn(handleTable, SWT.NONE);
        tc0.setText(STD_HEADINGS[0]);
        tc0.setAlignment(SWT.LEFT);
        tc0.setResizable(true);
        
        tableLayout.addColumnData(new ColumnWeightData(30, 10, true));
        TableColumn tc1 = new TableColumn(handleTable, SWT.NONE);
        tc1.setText(STD_HEADINGS[1]);
        tc1.setAlignment(SWT.LEFT);
        tc1.setResizable(true);
        
        tableLayout.addColumnData(new ColumnWeightData(10, 10, true));
        TableColumn tc2 = new TableColumn(handleTable, SWT.NONE);
        tc2.setText(STD_HEADINGS[2]);
        tc2.setAlignment(SWT.LEFT);
        tc2.setResizable(true);
        
        tableLayout.addColumnData(new ColumnWeightData(30, 10, true));
        TableColumn tc3 = new TableColumn(handleTable, SWT.NONE);
        tc3.setText(STD_HEADINGS[3]);
        tc3.setAlignment(SWT.LEFT);
        tc3.setResizable(true);
        
        tableLayout.addColumnData(new ColumnWeightData(10, 10, true));
        TableColumn tc4 = new TableColumn(handleTable, SWT.NONE);
        tc4.setText(STD_HEADINGS[4]);
        tc4.setAlignment(SWT.LEFT);
        tc4.setResizable(true);
        
        return handleTable;
    }
    
    
    /**
     * Refreshes the handleTable with trust list information.
     */
    private void refreshHandleList()
    {        
        StringBuffer request = new StringBuffer("LIST HANDLES LONG");
        
        boolean noBoxesSelected = true;
        if (registeredCBox.getSelection())
        {
            request.append(" REGISTERED");
            noBoxesSelected = false;
        }
        
        if (inProcessCBox.getSelection())
        {
            request.append(" INPROCESS");
            noBoxesSelected = false;
        }
        
        if (staticCBox.getSelection())
        {
            request.append(" STATIC");
            noBoxesSelected = false;
        }
        
        if (pendingCBox.getSelection())
        {
            request.append(" PENDING");
            noBoxesSelected = false;
        }
        
        //If none selected display nothing in table
        if (noBoxesSelected)
        {
            populateHandleTable(new ArrayList());
            return;
        }
        
        ISTAFRequest req = new STAFRequest(getServiceName(), 
                                           request.toString());
        
        //add a property listener to the single request
        req.addPropertyChangeListener(new IPropertyChangeListener()
            {
                public void propertyChange(final PropertyChangeEvent event)
                {
                    //Do nothing if widget has been disposed
                    if (handleTable.isDisposed())
                    {
                        return;
                    }
                    
                    handleTable.getDisplay().asyncExec(new Runnable()
                        {
                            public void run()
                            {   
                                ISTAFRequest req = (ISTAFRequest) 
                                                      event.getNewValue();
                                
                                //handle errors
                                if (req.getRC() != STAFResult.Ok)
                                {
                                    String msg = "Error listing STAF handles." +
                                        "Endpoint: '"+getEditor().getEndpoint()+
                                        "' Cmd: '"+
                                        req.getService()+" "+req.getCommand()+
                                        "' RC: '"+req.getRC()+"' Result: '"+
                                        req.getResult()+"'";
                                    MultiStatus err = Util.getServiceInfo(
                                        Activator.getDefault().getBundle(), 
                                        IStatus.ERROR, msg, null); 
                                    Activator.getDefault().getLog().log(err);
                                    
                                    return;
                                }
                                
                                //Populate with handle list from request
                                List handleList = 
                                    (List) STAFMarshallingContext.unmarshall(
                                          req.getResult()).getRootObject();
                                                    
                                populateHandleTable(handleList);
                            }
                        });
                }
            });
        
        submitCommand(req);
    }
    
    
    /**
     * Populates the handleTable with
     * all the handle listings contained in the unmarshalled List of
     * STAF/Service/Handle/HandleInfoLong Map Classes. 
     * This method is synchronized to prevent 2 different
     * refresh threads from stepping on each other.
     * @param newHandleList List of STAF/Service/Handle/HandleInfoLong
     * Map Classes
     */
    private synchronized void populateHandleTable(List newHandleList)
    {
        //Clear trustTable
        handleTable.clearAll();
        handleTable.setItemCount(0);
        
        //Add shandles to table
        
        Iterator handleIt = newHandleList.iterator();
        while (handleIt.hasNext())
        {
            Map handleMap = (Map) handleIt.next();
            addEntryToHandleTable(handleMap);
        }
    }


    /**
     * Adds a handle entry to the handleTable
     * @param handleMap The unmarshalled STAF/Service/Handle/HandleInfoLong 
     * Map class to add
     */
    private void addEntryToHandleTable(Map handleMap)
    {
        String handle = (String) handleMap.get("handle");
        String name = (String) handleMap.get("name");
        String state = (String) handleMap.get("state");
        String lastUsed = (String) handleMap.get("lastUsedTimestamp");
        String pid = (String) handleMap.get("pid");
        
        if (name == null)
        {
            name = "<None>";
        }

        TableItem item = new TableItem(handleTable, SWT.NONE);
        item.setText(0, handle);
        item.setText(1, name);
        item.setText(2, state);
        item.setText(3, lastUsed); 
        item.setText(4, pid); 
    }


    /**
     * Required by ISTAFEditorExtension, but all requests submitted by
     * this extension are handled with inline anonymous listeners.
     */
    public void requestPropertyChanged(ISTAFRequest request)
    {
    }

    
    /**
     * Nothing to do currently, required by IExecutableExtension.
     */
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException
    {
    }
}