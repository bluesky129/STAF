/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.extension.trust;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
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
 * The main class for a STAFEditor extension for the Trust
 * service.
 * @see ISTAFEditorExtension 
 */
public class TrustExtension extends STAFEditorExtension
                            implements SelectionListener
{
    /* Class Constants */
    
    private static String MACHINE_TYPE = "Machine";
    private static String USER_TYPE = "User";
    private static String DEFAULT_TYPE = "Default";
    
    /* Class Fields */
    
    private static Image refreshIcon;
    private static Image clearIcon;
    
    /* Instance Fields */

    private Label typeLabel;
    private Text entryText;
    private Combo levelCombo;
    private Table trustTable;
    private Button machineRadioButton;
    private Button userRadioButton;
    private Button defaultRadioButton;
    
    private Action deleteTrustAction;
    
    
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
    public TrustExtension()
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
        
        createTrustGroup(composite);
        
        makeContextMenuActions();
        hookContextMenu();
        
        refreshTrustList();
        
        return composite;
    }

    
    /**
     * Creates the group to contain all widgets to display
     * the SET controls and the table listing TRUST settings.
     * @param parent
     * @return Group
     */
    private Group createTrustGroup(Composite parent)
    {
        Group trustGroup = new Group(parent, SWT.NONE);
        trustGroup.setText("STAF Trust Settings");
        
        GridLayout varLayout = new GridLayout(8, false);
        varLayout.marginHeight = 10;
        varLayout.marginWidth = 10;
        varLayout.verticalSpacing = 10;
        trustGroup.setLayout(varLayout);
        
        //Refresh Button
        Button refreshButton = new Button(trustGroup, SWT.PUSH);
        refreshButton.setToolTipText("Refresh Trust List");
        refreshButton.setImage(refreshIcon);
        refreshButton.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    refreshTrustList();
                }
            });
        
        //Clear Button
        Button clearButton = new Button(trustGroup, SWT.PUSH);
        clearButton.setToolTipText("Clear Fields");
        clearButton.setImage(clearIcon);
        clearButton.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    machineRadioButton.setSelection(true);
                    userRadioButton.setSelection(false);
                    defaultRadioButton.setSelection(false);
                    
                    typeLabel.setText(MACHINE_TYPE);
                    
                    entryText.setText("");
                    entryText.setEnabled(true);
                    
                    levelCombo.deselectAll();
                }
            });
        
        //Type Label & Entry Text
        typeLabel = new Label(trustGroup, SWT.NONE);
        typeLabel.setText(MACHINE_TYPE);
        
        entryText = new Text(trustGroup, SWT.SINGLE | SWT.BORDER);
        GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
        entryText.setLayoutData(data);
        
        //Level Label & Combo
        Label levelLabel = new Label(trustGroup, SWT.NONE);
        levelLabel.setText("Level");
        
        levelCombo = new Combo(trustGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
        String[] levels = {"0", "1", "2", "3", "4", "5"};
        levelCombo.setItems(levels);
        data = new GridData(SWT.CENTER, SWT.CENTER, false, false);
        levelCombo.setLayoutData(data);
        
        //Machine/User/Defult Radio Buttons
        Composite radioContainer = new Composite(trustGroup, SWT.NONE);
        RowLayout radioLayout = new RowLayout(SWT.VERTICAL);
        radioContainer.setLayout(radioLayout);

        machineRadioButton = new Button(radioContainer, SWT.RADIO);
        machineRadioButton.setText(MACHINE_TYPE);
        machineRadioButton.setSelection(true);
        machineRadioButton.addSelectionListener(new SelectionAdapter()
            {
               public void widgetSelected(SelectionEvent e)
               {
                   typeLabel.setText(MACHINE_TYPE);
                   entryText.setEnabled(true);
               }
            });
        
        userRadioButton = new Button(radioContainer, SWT.RADIO);
        userRadioButton.setText(USER_TYPE);
        userRadioButton.addSelectionListener(new SelectionAdapter()
        {
           public void widgetSelected(SelectionEvent e)
           {
               typeLabel.setText(USER_TYPE);
               entryText.setEnabled(true);
           }
        });
        
        defaultRadioButton = new Button(radioContainer, SWT.RADIO);
        defaultRadioButton.setText(DEFAULT_TYPE);
        defaultRadioButton.addSelectionListener(new SelectionAdapter()
        {
           public void widgetSelected(SelectionEvent e)
           {
               typeLabel.setText(DEFAULT_TYPE);
               entryText.setEnabled(false);
           }
        });
        
        //Set Button
        Button setButton = new Button(trustGroup, SWT.PUSH);
        setButton.setText("SET");
        setButton.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    performSet();
                }
            });
        
        //Table 
        trustTable = createVarTable(trustGroup);
        data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.horizontalSpan = 8;
        trustTable.setLayoutData(data);
        trustTable.addSelectionListener(this);
        
        return trustGroup;
    }


    /**
     * Creates the Table widget for displaying STAF TRUST levels.
     * @param parent
     * @return Table
     */
    private Table createVarTable(Composite parent)
    {
        //Create table widget
        Table trustTable = new Table(parent, SWT.H_SCROLL | SWT.V_SCROLL
                                     | SWT.SINGLE | SWT.FULL_SELECTION 
                                     | SWT.BORDER);
        
        trustTable.setToolTipText("Double-click an entry to populate above " +
                                  "fields.");

        TableLayout tableLayout = new TableLayout();
        trustTable.setLayout(tableLayout);

        trustTable.setLinesVisible(true);
        trustTable.setHeaderVisible(true);

        String[] STD_HEADINGS = { "Type", "Entry", "Level" };

        //add TableColumns
        tableLayout.addColumnData(new ColumnWeightData(15, 10, true));
        TableColumn tc0 = new TableColumn(trustTable, SWT.NONE);
        tc0.setText(STD_HEADINGS[0]);
        tc0.setAlignment(SWT.LEFT);
        tc0.setResizable(true);

        tableLayout.addColumnData(new ColumnWeightData(70, 30, true));
        TableColumn tc1 = new TableColumn(trustTable, SWT.NONE);
        tc1.setText(STD_HEADINGS[1]);
        tc1.setAlignment(SWT.LEFT);
        tc1.setResizable(true);

        tableLayout.addColumnData(new ColumnWeightData(15, 10, true));
        TableColumn tc2 = new TableColumn(trustTable, SWT.NONE);
        tc2.setText(STD_HEADINGS[2]);
        tc2.setAlignment(SWT.LEFT);
        tc2.setResizable(true);

        return trustTable;
    }
    
    
    /**
     * Refreshes the trustTable with trust list information.
     */
    private void refreshTrustList()
    {        
        String request = "LIST";
        
        ISTAFRequest req = new STAFRequest(getServiceName(), request);
        //add a property listener to the single request
        req.addPropertyChangeListener(new IPropertyChangeListener()
            {
                public void propertyChange(final PropertyChangeEvent event)
                {
                    //Do nothing if widget has been disposed
                    if (trustTable.isDisposed())
                    {
                        return;
                    }
                    
                    trustTable.getDisplay().asyncExec(new Runnable()
                        {
                            public void run()
                            {   
                                ISTAFRequest req = (ISTAFRequest) 
                                                      event.getNewValue();
                                
                                //handle errors
                                if (req.getRC() != STAFResult.Ok)
                                {
                                    String msg = "Error listing STAF trust " +
                                        "levels. Endpoint: '"+
                                        getEditor().getEndpoint()+"' Cmd: '"+
                                        req.getService()+" "+req.getCommand()+
                                        "' RC: '"+req.getRC()+"' Result: '"+
                                        req.getResult()+"'";
                                    MultiStatus err = Util.getServiceInfo(
                                        Activator.getDefault().getBundle(), 
                                        IStatus.ERROR, msg, null); 
                                    Activator.getDefault().getLog().log(err);
                                    
                                    return;
                                }
                                
                                //Populate with trust list from request
                                List trustList = 
                                    (List) STAFMarshallingContext.unmarshall(
                                          req.getResult()).getRootObject();
                                                    
                                populateTrustTable(trustList);
                            }
                        });
                }
            });
        
        submitCommand(req);
    }
    
    
    /**
     * Populates the trustTable with
     * all the trust listings contained in the unmarshalled List of
     * STAF/Service/Trust/Entry Map Classes. 
     * This method is synchronized to prevent 2 different
     * refresh threads from stepping on each other.
     * @param newTrustList List of STAF/Service/Trust/Entry 
     * Map Classes
     */
    private synchronized void populateTrustTable(List newTrustList)
    {
        //Clear trustTable
        trustTable.clearAll();
        trustTable.setItemCount(0);
        
        //Add new trusts to table
        
        Iterator trustIt = newTrustList.iterator();
        while (trustIt.hasNext())
        {
            Map trustMap = (Map) trustIt.next();
            addEntryToTrustTable(trustMap);
        }
    }
    
    
    /**
     * Adds a trust entry to the trustTable
     * @param trustMap The unmarshalled STAF/Service/Trust/Entry 
     * Map class to add
     */
    private void addEntryToTrustTable(Map trustMap)
    {        
        String type = (String) trustMap.get("type");
        String entry = (String) trustMap.get("entry");
        String level = (String) trustMap.get("trustLevel");
        if (entry == null)
        {
            entry = "<None>";
        }

        TableItem item = new TableItem(trustTable, SWT.NONE);
        item.setText(0, type);
        item.setText(1, entry);
        item.setText(2, level);
    }
    
    
    /**
     * Sets a Trust setting in the machine's Trust list
     */
    private void performSet()
    {
        StringBuffer cmd = new StringBuffer("SET ");
        
        if (machineRadioButton.getSelection())
        {
            cmd.append("MACHINE "+STAFUtil.wrapData(entryText.getText()));
        }
        else if (userRadioButton.getSelection())
        {
            cmd.append("USER "+STAFUtil.wrapData(entryText.getText()));
        }
        else
        {
            cmd.append("DEFAULT ");
        }
        
        cmd.append("LEVEL "+STAFUtil.wrapData(levelCombo.getText()));

        ISTAFRequest req = new STAFRequest(getServiceName(), cmd.toString());
        req.addPropertyChangeListener(new IPropertyChangeListener()
        {
            public void propertyChange(final PropertyChangeEvent event)
            {
                trustTable.getDisplay().asyncExec(new Runnable()
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
                            
                            refreshTrustList();
                        }
                    });
            }
        });
        
        submitCommand(req);
    }
    
    
    /**
     * Creates the context menu actions for the Trust table
     */
    private void makeContextMenuActions()
    {
        deleteTrustAction = new Action()
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
        deleteTrustAction.setText("Delete");
        deleteTrustAction.setToolTipText("Delete selected items");
        deleteTrustAction.setImageDescriptor(
                          PlatformUI.getWorkbench().getSharedImages()
                          .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
        
        //Set initialed enabled state
        if (trustTable.getSelectionCount() == 0)
        {
            deleteTrustAction.setEnabled(false);
        }
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
                TrustExtension.this.fillContextMenu(manager);
            }
        });
        Menu menu = menuMgr.createContextMenu(trustTable);
        trustTable.setMenu(menu);
    }
    
    
    /**
     * Adds actions to the context MenuManager
     * @param manager IMenuManager
     */
    private void fillContextMenu(IMenuManager manager)
    {
        manager.add(deleteTrustAction);
    }
    
    
    /**
     * Deletes the selected Trust entry and refreshes
     * the trust list.
     */
    private void performDeleteAction()
    {
        TableItem[] items = trustTable.getSelection();
        String type = items[0].getText(0);
        
        StringBuffer request = new StringBuffer("DELETE ");
        
        if (type.equalsIgnoreCase(MACHINE_TYPE))
        {
            request.append("MACHINE ");
        }
        else if (type.equalsIgnoreCase(USER_TYPE))
        {
            request.append("USER ");
        }
        else
        {
            //TODO other error handling??
            return;
        }
        
        request.append(STAFUtil.wrapData(items[0].getText(1)));
        
        ISTAFRequest req = new STAFRequest(getServiceName(), 
                                           request.toString());
        //add a property listener to the single request
        req.addPropertyChangeListener(new IPropertyChangeListener()
            {
                public void propertyChange(final PropertyChangeEvent event)
                {
                    trustTable.getDisplay().asyncExec(new Runnable()
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
                                
                                refreshTrustList();
                            }
                        });
                }
            });
        
        submitCommand(req);
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
     * Required by SelectionListener interface. Used
     * to detect double click event on the table.
     */
    public void widgetDefaultSelected(SelectionEvent e)
    {
        TableItem[] items = trustTable.getSelection();
        String type = items[0].getText(0);
        
        if (type.equalsIgnoreCase(MACHINE_TYPE))
        {
            typeLabel.setText(MACHINE_TYPE);
            
            entryText.setEnabled(true);
            entryText.setText(items[0].getText(1));
            
            machineRadioButton.setSelection(true);
            userRadioButton.setSelection(false);
            defaultRadioButton.setSelection(false);
        }
        else if (type.equalsIgnoreCase(USER_TYPE))
        {
            typeLabel.setText(USER_TYPE);
            
            entryText.setEnabled(true);
            entryText.setText(items[0].getText(1));
            
            userRadioButton.setSelection(true);
            machineRadioButton.setSelection(false);
            defaultRadioButton.setSelection(false);
        }
        else
        {
            typeLabel.setText(DEFAULT_TYPE);
            
            entryText.setEnabled(false);
            entryText.setText("");
            
            defaultRadioButton.setSelection(true);
            machineRadioButton.setSelection(false);
            userRadioButton.setSelection(false);
        }
        
        levelCombo.setText(items[0].getText(2));
    }

    
    /**
     * Required by SelectionListener interface. Used
     * to update enable/disable of delete action in
     * context menu based on selections in the table.
     */
    public void widgetSelected(SelectionEvent e)
    {
        if (trustTable.getSelectionCount() == 0)
        {
            deleteTrustAction.setEnabled(false);
        }
        else
        {
            TableItem[] items = trustTable.getSelection();
            String type = items[0].getText(0);
            if (type.equalsIgnoreCase(DEFAULT_TYPE))
            {
                deleteTrustAction.setEnabled(false);
            }
            else
            {
                deleteTrustAction.setEnabled(true);
            }
        }   
    }   
}