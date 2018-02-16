/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.extension.var;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

import com.ibm.staf.STAFMarshallingContext;
import com.ibm.staf.STAFResult;
import com.ibm.staf.STAFUtil;
import com.ibm.staf.eclipse.editor.STAFEditorExtension;
import com.ibm.staf.eclipse.editor.Util;
import com.ibm.staf.eclipse.model.ISTAFRequest;
import com.ibm.staf.eclipse.model.STAFRequest;

/**
 * The main class for a STAFEditor extension for the Variable
 * service.
 * @see ISTAFEditorExtension 
 */
public class VARExtension extends STAFEditorExtension 
                          implements SelectionListener
{
    /* Class Constants */
    
    private static String SYSTEM_TYPE = "System";
    private static String SHARED_TYPE = "Shared";
    
    /* Class Fields */
    
    private static Image refreshIcon;
    private static Image clearIcon;
    
    /* Instance Fields */
    
    private Button refreshButton;
    
    private Button systemRadioButton;
    private Button sharedRadioButton;
    
    private Text varText;
    private Text valueText;

    private Table varTable;
    
    private Text inputText;
    private Text outputText;
    
    private Action deleteVarAction;
    
     
    
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
        
        URL clearURL = bundle.getResource("icons/clear_co.gif");
        ImageDescriptor clearID = ImageDescriptor.createFromURL(clearURL);
        clearIcon = clearID.createImage();
    }
    

    /**
     * Constructor
     */
    public VARExtension()
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
        
        SashForm sashForm = new SashForm(composite, SWT.VERTICAL); 
        
        //Var List Group
        createListGroup(sashForm);
        
        //Create ResolveGroup
        createResolveGroup(sashForm);
        
        makeContextMenuActions();
        hookContextMenu();
        
        int[] weights = {75, 25};
        sashForm.setWeights(weights);
        sashForm.SASH_WIDTH = 5;
        
        refreshVarList();

        return composite;
    }

    
    /**
     * Creates the Group widget containing the widgets to
     * provide variable resolution
     * @param parent
     * @return Group
     */
    private Group createResolveGroup(Composite parent)
    {
        Group resolveGroup = new Group(parent, SWT.NONE);
        resolveGroup.setText("STAF Variable Resolution");
        GridLayout resolveLayout = new GridLayout(3, false);
        resolveLayout.marginHeight = 20;
        resolveLayout.marginWidth = 20;
        resolveLayout.horizontalSpacing = 20;
        resolveGroup.setLayout(resolveLayout);
        
        inputText = new Text(resolveGroup, SWT.MULTI | SWT.WRAP | SWT.BORDER);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        inputText.setLayoutData(data);
        
        Button resolveButton = new Button(resolveGroup, SWT.PUSH);
        resolveButton.setText("Resolves To ->");
        data = new GridData(SWT.CENTER, SWT.CENTER, false, false);
        resolveButton.setLayoutData(data);
        resolveButton.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    performResolve();
                }
            });
        
        outputText = new Text(resolveGroup, SWT.MULTI | SWT.WRAP | SWT.BORDER);
        data = new GridData(SWT.FILL, SWT.FILL, true, true);
        outputText.setLayoutData(data);
        
        return resolveGroup;
    }

    
    /**
     * Creates the Group widget to contain all widgets for
     * listing STAF variables
     * @param parent
     * @return Group
     */
    private Group createListGroup(Composite parent)
    {
        Group listGroup = new Group(parent, SWT.NONE);
        listGroup.setText("STAF Variables");

        GridLayout varLayout = new GridLayout(8, false);
        varLayout.marginHeight = 10;
        varLayout.marginWidth = 10;
        varLayout.verticalSpacing = 10;
        listGroup.setLayout(varLayout);
        
        
        //Refresh Button
        refreshButton = new Button(listGroup, SWT.PUSH);
        refreshButton.setToolTipText("Refresh Variable List");
        refreshButton.setImage(refreshIcon);
        refreshButton.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    refreshVarList();
                }
            });
        
        //Clear Button
        Button clearButton = new Button(listGroup, SWT.PUSH);
        clearButton.setToolTipText("Clear Fields");
        clearButton.setImage(clearIcon);
        clearButton.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    varText.setText("");
                    valueText.setText("");
                }
            });
        
        //Var Label & Text
        Label keyLabel = new Label(listGroup, SWT.NONE);
        keyLabel.setText("Key");
        
        varText = new Text(listGroup, SWT.SINGLE | SWT.BORDER);
        GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
        varText.setLayoutData(data);
        
        //Value Label & Text
        Label valueLabel = new Label(listGroup, SWT.NONE);
        valueLabel.setText("Value");
        
        valueText = new Text(listGroup, SWT.SINGLE | SWT.BORDER);
        data = new GridData(SWT.FILL, SWT.CENTER, true, false);
        valueText.setLayoutData(data);
        
        //System/Shared Radio Button
        Composite radioContainer = new Composite(listGroup, SWT.NONE);
        RowLayout radioLayout = new RowLayout(SWT.VERTICAL);
        radioContainer.setLayout(radioLayout);
        
        systemRadioButton = new Button(radioContainer, SWT.RADIO);
        systemRadioButton.setText(SYSTEM_TYPE);
        systemRadioButton.setSelection(true);
        
        sharedRadioButton = new Button(radioContainer, SWT.RADIO);
        sharedRadioButton.setText(SHARED_TYPE);

        
        //Set Button
        Button setButton = new Button(listGroup, SWT.PUSH);
        setButton.setText("SET");
        setButton.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    performSet();
                }
            });
        
        varTable = createVarTable(listGroup);
        data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.horizontalSpan = 8;
        varTable.setLayoutData(data);
        varTable.addSelectionListener(this);
        
        return listGroup;
    }

    
    /**
     * Creates the Table widget for display STAF variables.
     * @param parent
     * @return Table
     */
    private Table createVarTable(Composite parent)
    {
        //First create Table widget
        Table varTable = new Table(parent, SWT.H_SCROLL | SWT.V_SCROLL
                                   | SWT.SINGLE | SWT.FULL_SELECTION 
                                   | SWT.BORDER);
        
        varTable.setToolTipText("Double-click an entry to populate above " +
                                "fields.");

        TableLayout tableLayout = new TableLayout();
        varTable.setLayout(tableLayout);

        varTable.setLinesVisible(true);
        varTable.setHeaderVisible(true);
        
        //add TableColumns
        String[] STD_HEADINGS = { "Type", "Variable", "Value" };
        
        tableLayout.addColumnData(new ColumnWeightData(10, 10, true));
        TableColumn tc0 = new TableColumn(varTable, SWT.NONE);
        tc0.setText(STD_HEADINGS[0]);
        tc0.setAlignment(SWT.LEFT);
        tc0.setResizable(true);

        tableLayout.addColumnData(new ColumnWeightData(45, 10, true));
        TableColumn tc1 = new TableColumn(varTable, SWT.NONE);
        tc1.setText(STD_HEADINGS[1]);
        tc1.setAlignment(SWT.LEFT);
        tc1.setResizable(true);

        tableLayout.addColumnData(new ColumnWeightData(45, 10, true));
        TableColumn tc2 = new TableColumn(varTable, SWT.NONE);
        tc2.setText(STD_HEADINGS[2]);
        tc2.setAlignment(SWT.LEFT);
        tc2.setResizable(true);
        
        return varTable;
    }
    
    
    /**
     * Creates the context menu actions for the Var table
     */
    private void makeContextMenuActions()
    {
        deleteVarAction = new Action()
        {
            public void run()
            {
                String msg = "All selected items will be deleted.";
                if (MessageDialog.openConfirm(getEditor().getSite().getShell(), 
                                              "Confirm Delete", msg))
                {      
                    performDeleteAction();
                }
            }   
        };
        deleteVarAction.setText("Delete");
        deleteVarAction.setToolTipText("Delete selected items");
        deleteVarAction.setImageDescriptor(
                        PlatformUI.getWorkbench().getSharedImages()
                        .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
        
        //Set initialed enabled state
        if (varTable.getSelectionCount() == 0)
        {
            deleteVarAction.setEnabled(false);
        }
    }
    
    
    /**
     * Deletes the selected variable and refreshes
     * the variable list.
     */
    private void performDeleteAction()
    {
        TableItem[] items = varTable.getSelection();
        String type = items[0].getText(0);
        
        StringBuffer request = new StringBuffer("DELETE ");
        
        if (type.equalsIgnoreCase(SYSTEM_TYPE))
        {
            request.append("SYSTEM ");
        }
        else if (type.equalsIgnoreCase(SHARED_TYPE))
        {
            request.append("SHARED ");
        }
        else
        {
            //TODO other error handling?
            return;
        }
        
        request.append("VAR "+STAFUtil.wrapData(items[0].getText(1)));
        
        ISTAFRequest req = new STAFRequest(getServiceName(), 
                                           request.toString());
        //add a property listener to the single request
        req.addPropertyChangeListener(new IPropertyChangeListener()
            {
                public void propertyChange(final PropertyChangeEvent event)
                {
                    varTable.getDisplay().asyncExec(new Runnable()
                        {
                            public void run()
                            {   
                                ISTAFRequest req = 
                                    (ISTAFRequest) event.getNewValue();
                                
                                //handle errors
                                if (req.getRC() != STAFResult.Ok)
                                {
                                    String msg = "Error deleting STAF variable. "+
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
                                
                                refreshVarList();
                            }
                        });
                }
            });
        
        submitCommand(req);
    }
    
    
    /**
     * Sets a STAF variable and refreshes
     * the variable list.
     */
    private void performSet()
    {
        StringBuffer cmd = new StringBuffer("SET ");
        
        if (systemRadioButton.getSelection())
        {
            cmd.append("SYSTEM ");
        }
        else
        {
            cmd.append("SHARED ");
        }
        
        String keyValue = varText.getText()+"="+valueText.getText();
        cmd.append("VAR "+STAFUtil.wrapData(keyValue));

        ISTAFRequest req = new STAFRequest(getServiceName(), cmd.toString());
        req.addPropertyChangeListener(new IPropertyChangeListener()
        {
            public void propertyChange(final PropertyChangeEvent event)
            {
                varTable.getDisplay().asyncExec(new Runnable()
                    {
                        public void run()
                        {   
                            ISTAFRequest req = 
                                (ISTAFRequest) event.getNewValue();
                            
                            //handle errors
                            if (req.getRC() != STAFResult.Ok)
                            {
                                String msg = "Error setting STAF variable. "+
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
                            
                            refreshVarList();
                        }
                    });
            }
        });
        
        submitCommand(req);
    }
    
    
    /**
     * Performs variable resolution on the string in the inputText
     * and puts the resolved value in the outputText.
     * This request is added to the main STAFEditor page.
     */
    private void performResolve()
    {        
        String request = "RESOLVE STRING "+
                         STAFUtil.wrapData(inputText.getText());
        
        ISTAFRequest req = new STAFRequest(getServiceName(), request);
        //add a property listener to the single request
        req.addPropertyChangeListener(new IPropertyChangeListener()
            {
                public void propertyChange(final PropertyChangeEvent event)
                {
                    outputText.getDisplay().asyncExec(new Runnable()
                        {
                            public void run()
                            {   
                                ISTAFRequest req = 
                                    (ISTAFRequest) event.getNewValue();
                                
                                //handle errors
                                if (req.getRC() != STAFResult.Ok)
                                {
                                    String msg = "Error! RC: "+req.getRC()+
                                                 " Result: "+req.getResult();
                                    outputText.setText(msg);
                                    
                                    return;
                                }
                                
                                outputText.setText(req.getResult());
                            }
                        });
                }
            });
        
        addRequest(req);
        submitCommand(req);
    }
    
    
    /**
     * Connects the context menu with the table
     */
    private void hookContextMenu()
    {
        MenuManager menuMgr = new MenuManager("#PopupMenu");
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(new IMenuListener()
        {
            public void menuAboutToShow(IMenuManager manager)
            {
                VARExtension.this.fillContextMenu(manager);
            }
        });
        Menu menu = menuMgr.createContextMenu(varTable);
        varTable.setMenu(menu);
    }
    
    
    /**
     * Adds actions to the context MenuManager
     * @param manager IMenuManager
     */
    private void fillContextMenu(IMenuManager manager)
    {
        manager.add(deleteVarAction);
    }

    
    /**
     * Nothing to do currently, required by IExecutableExtension.
     */
    public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException
    {  
    }

    
    /**
     * Required by ISTAFEditorExtension, but all requests submitted by
     * this extension are handled with inline anonymous listeners.
     */
    public void requestPropertyChanged(final ISTAFRequest request)
    {
    }
    
    
    /**
     * Refreshes the varTable with variable list information.
     * Two calls are made to STAF to get SYSTEM and SHARED
     * variables.
     */
    public void refreshVarList()
    {
        /* Workaround for STAF bug of problems doing multi-threaded
         * VAR lists. STAF Bug #1744442. The workaround will disable
         * the refresh button while the vars are being queried/updated
         * and the LIST SYSTEM and LIST SHARED requests will occur 
         * serially. This bug has been fixed in STAF, but cannot guarantee
         * that clients will be running a patched version. */
        
        refreshButton.setEnabled(false);
        
        listSystemVars();
        
        /* original code for listing vars, before discovery of 
         * STAF Bug #1744442.
         */
        
//        String sysRequest = "LIST SYSTEM";
//        
//        ISTAFRequest sysReq = new STAFRequest(getServiceName(), sysRequest);
//        //add a property listener to the single request
//        sysReq.addPropertyChangeListener(new IPropertyChangeListener()
//            {
//                public void propertyChange(final PropertyChangeEvent event)
//                {
//                    //Do nothing if widget has been disposed
//                    if (varTable.isDisposed())
//                    {
//                        return;
//                    }
//                    
//                    varTable.getDisplay().asyncExec(new Runnable()
//                        {
//                            public void run()
//                            {   
//                                ISTAFRequest req = (ISTAFRequest) 
//                                                      event.getNewValue();
//                                
//                                //handle errors
//                                if (req.getRC() != STAFResult.Ok)
//                                {
//                                    String msg = "Error listing STAF variables. Cmd: '"+
//                                        req.getService()+" "+req.getCommand()+
//                                        "' RC: '"+req.getRC()+"' Result: '"+
//                                        req.getResult()+"'";
//                                    MultiStatus err = Util.getServiceInfo(
//                                        Activator.getDefault().getBundle(), 
//                                        IStatus.ERROR, msg, null); 
//                                    Activator.getDefault().getLog().log(err);
////                                    ErrorDialog.openError(
////                                            getEditor().getSite().getShell(), 
////                                            "STAF Error", null, err);
//                                    
//                                    return;
//                                }
//                                
//                                //Populate with vars from request
//                                Map varMap = (Map) STAFMarshallingContext.unmarshall(
//                                             req.getResult()).getRootObject();
//                    
//                                SortedMap sortedMap = new TreeMap(varMap);
//                                
//                                populateVarMap(sortedMap, SYSTEM_TYPE);
//                            }
//                        });
//                }
//            });
//        
//        submitCommand(sysReq);
//        
//        
//        String sharedRequest = "LIST SHARED";
//        
//        ISTAFRequest sharedReq = new STAFRequest(getServiceName(), 
//                                                 sharedRequest);
//        //add a property listener to the single request
//        sharedReq.addPropertyChangeListener(new IPropertyChangeListener()
//            {
//                public void propertyChange(final PropertyChangeEvent event)
//                {
//                    //Do nothing if widget has been disposed
//                    if (varTable.isDisposed())
//                    {
//                        return;
//                    }
//                    
//                    varTable.getDisplay().asyncExec(new Runnable()
//                        {
//                            public void run()
//                            {                       
//                                ISTAFRequest req = 
//                                    (ISTAFRequest) event.getNewValue();
//                                
//                                //handle errors
//                                if (req.getRC() != STAFResult.Ok)
//                                {
//                                    String msg = "Error listing STAF variables. Cmd: '"+
//                                        req.getService()+" "+req.getCommand()+
//                                        "' RC: '"+req.getRC()+"' Result: '"+
//                                        req.getResult()+"'";
//                                    MultiStatus err = Util.getServiceInfo(
//                                        Activator.getDefault().getBundle(), 
//                                        IStatus.ERROR, msg, null); 
//                                    Activator.getDefault().getLog().log(err);
////                                    ErrorDialog.openError(
////                                            getEditor().getSite().getShell(), 
////                                            "STAF Error", null, err);
//                                    
//                                    return;
//                                }
//                                
//                                //Populate with vars from request
//                                Map varMap = (Map) STAFMarshallingContext.unmarshall(
//                                             req.getResult()).getRootObject();
//                    
//                                SortedMap sortedMap = new TreeMap(varMap);
//                                
//                                populateVarMap(sortedMap, SHARED_TYPE);
//                            }
//                        });
//                }
//            });
//        
//        submitCommand(sharedReq);
        /* end original code for listing vars */
    }
    
    
    /**
     * List SYSTEM VARS and adds them to table. Calls listSharedVars()
     * after system vars have been returned and updated. This prevents
     * us from triggering STAF Bug #1744442.
     */
    private void listSystemVars()
    {
        String sysRequest = "LIST SYSTEM";
      
        ISTAFRequest sysReq = new STAFRequest(getServiceName(), sysRequest);
        //add a property listener to the single request
        sysReq.addPropertyChangeListener(new IPropertyChangeListener()
          {
              public void propertyChange(final PropertyChangeEvent event)
              {
                  //Do nothing if widget has been disposed
                  if (varTable.isDisposed())
                  {
                      return;
                  }
                  
                  varTable.getDisplay().asyncExec(new Runnable()
                      {
                          public void run()
                          {   
                              ISTAFRequest req = (ISTAFRequest) 
                                                    event.getNewValue();
                              
                              //handle errors
                              if (req.getRC() != STAFResult.Ok)
                              {
                                  String msg = "Error listing STAF variables. "+
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
                              
                              //Populate with vars from request
                              Map varMap = (Map) STAFMarshallingContext.unmarshall(
                                           req.getResult()).getRootObject();
                  
                              SortedMap sortedMap = new TreeMap(varMap);
                              
                              populateVarMap(sortedMap, SYSTEM_TYPE);
                              
                              listSharedVars();
                          }
                      });
              }
          });
      
        submitCommand(sysReq);
    }
    
    
    /**
     * List SHARED VARS and adds them to table.
     */
    private void listSharedVars()
    {
        String sharedRequest = "LIST SHARED";
      
        ISTAFRequest sharedReq = new STAFRequest(getServiceName(), 
                                               sharedRequest);
        //add a property listener to the single request
        sharedReq.addPropertyChangeListener(new IPropertyChangeListener()
          {
              public void propertyChange(final PropertyChangeEvent event)
              {
                  //Do nothing if widget has been disposed
                  if (varTable.isDisposed())
                  {
                      return;
                  }
                  
                  varTable.getDisplay().asyncExec(new Runnable()
                      {
                          public void run()
                          {                       
                              ISTAFRequest req = 
                                  (ISTAFRequest) event.getNewValue();
                              
                              //handle errors
                              if (req.getRC() != STAFResult.Ok)
                              {
                                  String msg = "Error listing STAF variables. "+
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
                              
                              //Populate with vars from request
                              Map varMap = (Map) STAFMarshallingContext.unmarshall(
                                           req.getResult()).getRootObject();
                  
                              SortedMap sortedMap = new TreeMap(varMap);
                              
                              populateVarMap(sortedMap, SHARED_TYPE);
                              
                              refreshButton.setEnabled(true);
                          }
                      });
              }
          });
      
        submitCommand(sharedReq);
        
    }


    /**
     * Removes all entries from the varTable that are of "type" and
     * then adds all entries in sortedMap to the varTable as type
     * "type".
     * This method is synchronized to prevent two different threads
     * from updating the varTable simultaneously.
     * @param newVars Map of new set of variables of type "type"
     * to use to populate the map (key=var, value=var value)
     * @param type The type of variables being populated 
     * (SYSTEM, SHARED, etc.)
     */
    private synchronized void populateVarMap(Map newVars, String type)
    {
        //Remove any items from Table that are of the appropriate type
        //First find indices in Map of type
        //Can't remove them inside loop because it mucks up the indices 
        //of the table
        
        TableItem[] items = varTable.getItems();
        List indicesToRemove = new ArrayList();
        
        for (int i=0; i < items.length; i++)
        {
            String itemType = items[i].getText(0);
            
            if (itemType.equalsIgnoreCase(type))
            {
                indicesToRemove.add(new Integer(i));
            }
        }
        
        //Convert List of Integers to Array of ints
        
        int[] ints = new int[indicesToRemove.size()];
        Iterator indicesIt = indicesToRemove.iterator();
        for (int i=0; i < ints.length; i++)
        {
            ints[i] = ((Integer) indicesIt.next()).intValue();
        }
        
        //Delete entries from table
        
        varTable.remove(ints);
        
        //Add new entries to the table
        addEntriesToTable(newVars, type);
    }
    
    
    /**
     * Adds all variable entries in the Map to the varTable in
     * the order returned by the Map's iterator.
     * @param data The Map of variables (key=var, value=var value)
     * @param type The type of variable (SYSTEM, SHARED, etc.)
     */
    private void addEntriesToTable(Map data, String type)
    {
        Iterator varIt = data.keySet().iterator();
        while (varIt.hasNext())
        {
            String var = (String) varIt.next();
            
            if (var.equals("staf-map-class-name"))
            {
                //skip map-class-name entry
                continue;
            }
            
            String value = (String) data.get(var);
            value = value == null ? "<None>" : value;

            TableItem item = new TableItem(varTable, 
                                           SWT.NONE);
            
            item.setText(0, type);
            item.setText(1, var);
            item.setText(2, value);
        }
    }
    

    /**
     * Required by SelectionListener interface. Used
     * to detect double click event on the table.
     */
    public void widgetDefaultSelected(SelectionEvent e)
    {
        TableItem[] items = varTable.getSelection();
        
        varText.setText(items[0].getText(1));
        valueText.setText(items[0].getText(2));
    }

    
    /**
     * Required by SelectionListener interface. Used
     * to update enable/disable of delete action in
     * context menu based on selections in the table.
     */
    public void widgetSelected(SelectionEvent e)
    {
        if (varTable.getSelectionCount() == 0)
        {
            deleteVarAction.setEnabled(false);
        }
        else
        {
            deleteVarAction.setEnabled(true);
        }   
    }   
}