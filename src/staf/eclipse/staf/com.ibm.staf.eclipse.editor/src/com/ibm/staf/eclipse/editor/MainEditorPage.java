/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.editor;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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

import com.ibm.staf.STAFMapClassDefinition;
import com.ibm.staf.STAFMarshallingContext;
import com.ibm.staf.STAFResult;
import com.ibm.staf.eclipse.model.ISTAFMachine;
import com.ibm.staf.eclipse.model.ISTAFRequest;
import com.ibm.staf.eclipse.model.STAFMachine;
import com.ibm.staf.eclipse.model.STAFRequest;

/**
 * The "STAF Editor" page of the multi-page editor.
 */
public class MainEditorPage extends Composite 
                                      implements ISelectionChangedListener,
                                                 IPropertyChangeListener
{
    /* Constants */
    
    /** STAF Map Class key identifier */
    public final static String MAP_CLASS_NAME_KEY = "staf-map-class-name";
    /** STAF display name key identifier */
    public final static String MC_DISPLAY_NAME = "display-name";
    /** STAF key identifier */
    public final static String MC_KEY = "key";
    /** Display name for the tab for this editor page */
    public final static String STAF_EDITOR_TAB_NAME = "STAF Editor";
    
    /* Class Fields */
    
    private static Image refreshIcon;
    private static Image clearIcon;
    
    /* Instance fields */
    
    private STAFEditor editor;
    
    private Label machInfo;
    private Label versionInfo;
    private Combo servicesCombo;
    private Text cmdInput;
    
    protected Button refreshButton;
    
    private TableViewer viewer;
    private Table cmdHistoryTable;
    
    private Group resultGroup;
    private StackLayout resultStacklayout;
    private Text resultText;
    private Table simpleResultTable;
    private Table dynamicResultTable;
    private org.eclipse.swt.widgets.List resultList;
    
    private Action deleteResultAction;
    

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
     * Creates the GUI components of the editor page
     * @param editor A reference to the STAFEditor class
     * @param parent The Composite parent of all GUI components on
     * the page
     */
    public MainEditorPage(STAFEditor editor, Composite parent)
    {
        super(parent, SWT.NONE);
        
        this.editor = editor;
        editor.getMachine().addPropertyChangeListener(this);
        
        FillLayout layout = new FillLayout();
        layout.marginHeight = 10;
        layout.marginWidth = 10;
        setLayout(layout);
        
        SashForm sashForm = new SashForm(this, SWT.VERTICAL);
        
        // Create top panel
        createTopPanelGroup(sashForm);
        
        // Create table in middle panel
        createCmdHistoryGroup(sashForm);
        
        // Create display text in bottom panel
        createResultArea(sashForm);
        
        int[] weights = {15, 52, 33};
        sashForm.setWeights(weights);
        sashForm.SASH_WIDTH = 5;
    }
    
    
    /**
     * Returns the display name for the tab for this page
     * @return tab name
     */
    public String getTabName()
    {
        return STAF_EDITOR_TAB_NAME;
    }
    
    
    /**
     * Creates the Top Panel Group for the editor page
     * @param parent The Composite parent of GUI components in the group
     */
    private void createTopPanelGroup(Composite parent)
    {
        Group group = new Group(parent, SWT.NONE);
        group.setText("Command Submission");

        // Use GridLayout
        GridLayout topPaneLayout = new GridLayout(5, false);
        topPaneLayout.marginHeight = 10;
        topPaneLayout.marginWidth = 10;
        
        group.setLayout(topPaneLayout);
        
        //Create composite to hold machInfo/versionInfo
        Composite infoComp = new Composite(group, SWT.NONE);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.horizontalSpan = 5;
        infoComp.setLayoutData(data);
        GridLayout infoCompLayout = new GridLayout(2, false);
        infoComp.setLayout(infoCompLayout);
        
        // Create machine info text
        machInfo = new Label(infoComp, SWT.LEFT);
        data = new GridData(SWT.FILL, SWT.FILL, true, true);
        machInfo.setLayoutData(data);
        updateMachInfoText();
        
        //Create version text
        versionInfo = new Label(infoComp, SWT.RIGHT);
        data = new GridData(SWT.RIGHT, SWT.FILL, false, true);
        versionInfo.setLayoutData(data);
        updateSTAFVersion();
        
        //Create service refresh button
        refreshButton = new Button(group, SWT.PUSH);
        refreshButton.setImage(refreshIcon);
        refreshButton.setToolTipText("Refresh Service List");
        refreshButton.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    editor.refreshServicesList();
                }
            });//end new SelectionAdapter
        
        //Create clear button
        Button clearButton = new Button(group, SWT.PUSH);
        clearButton.setToolTipText("Clear Fields");
        clearButton.setImage(clearIcon);
        clearButton.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    servicesCombo.setText("");
                    cmdInput.setText("");
                }
            });
        
        // Create service combo        
        servicesCombo = new Combo(group, SWT.NONE);
        servicesCombo.setToolTipText("Select STAF Service");
        
        // Create cmd input text
        cmdInput = new Text(group, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        cmdInput.setEditable(true);
        cmdInput.setToolTipText("Enter Service Command");
        data = new GridData(SWT.FILL, SWT.CENTER, true, false);
        cmdInput.setLayoutData(data);
        
        // add keylistener to allow return to do a submit
        cmdInput.addKeyListener(new KeyAdapter()
            {
                public void keyPressed(KeyEvent e)
                {
                    if (e.character == '\r')
                    {
                        doSubmit();
                    }
                }
            });//end new KeyAdapater
        
        // Create submit button
        Button submitButton = new Button(group, SWT.PUSH);
        submitButton.setText("Submit");
        submitButton.setToolTipText("Submit STAF Command");
        submitButton.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {                    
                    doSubmit();
                }
            });//end new SelectionAdapter
    }
    
    
    private void updateSTAFVersion()
    {
        ISTAFRequest req = new STAFRequest("MISC", "VERSION");
        //add a property listener to the single request
        req.addPropertyChangeListener(new IPropertyChangeListener()
            {
                public void propertyChange(PropertyChangeEvent event)
                {
                    final ISTAFRequest newReq = 
                                       (ISTAFRequest) event.getNewValue();
                    
                    final String version;
                    
                    //handle errors
                    if (newReq.getRC() != STAFResult.Ok)
                    {
                        String msg = "Error retrieving STAF version. "+
                            "Endpoint: '"+editor.getEndpoint()+"' Cmd: '"+
                            newReq.getService()+" "+newReq.getCommand()+
                            "' RC: '"+newReq.getRC()+"' Result: '"+
                            newReq.getResult();
                        MultiStatus err = Util.getServiceInfo(
                                               Activator.getDefault().getBundle(), 
                                               IStatus.ERROR, msg, null); 
                        Activator.getDefault().getLog().log(err);
                        
                        version = "Unknown";
                    }
                    else
                    {
                        version = newReq.getResult();
                    }
                    
                    //update versionInfo
                    getDisplay().asyncExec(new Runnable()
                        {
                            public void run()
                            {
                                versionInfo.setText("STAF Version: "+
                                                    version);
                                versionInfo.pack(true);
                                versionInfo.getParent().layout();
                            }
                        });   
                }
            });
        
        editor.submitCommand(req);
    }


    /**
     * Updates the machInfo Label with the current machine information
     */
    private void updateMachInfoText()
    {
        String port = editor.getPort();
        port = (port == null) ? "default" : port;
        
        String stafInterface = editor.getInterface();
        stafInterface = (stafInterface == null) ? "default" : stafInterface;
        
        machInfo.setText("Hostname: "+editor.getHostname()+
                         "  Port: "+port+
                         "  Interface: "+stafInterface);
        machInfo.pack(true);
    }
    
    
    /**
     * Submits STAF command in a separate thread. 
     */
    private void doSubmit()
    {
        String service = servicesCombo.getText();
        String cmd = cmdInput.getText();
        
        ISTAFRequest req = new STAFRequest(service, cmd);
        
        //Because the Command History table uses ContentProvider
        //it will handle adding and removing listeners
        editor.addRequest(req);
        editor.submitCommand(req);
    }
    
    
    /**
     * Create the JFace viewer for the Command History Table
     * @param parent Composite parent for the GUI components
     */
    private void createCmdHistoryGroup(Composite parent)
    {
        //create Group
        Group group = new Group(parent, SWT.NONE);
        group.setText("Command History");
        FillLayout layout = new FillLayout();
        layout.marginHeight = 10;
        layout.marginWidth = 10;
        group.setLayout(layout);
        
        //create Table
        cmdHistoryTable = createCmdHistoryTable(group);
        cmdHistoryTable.addSelectionListener(new SelectionAdapter()
           {
               //handles double-click
               public void widgetDefaultSelected(SelectionEvent e)
               {
                   TableItem[] items = cmdHistoryTable.getSelection();
                   
                   servicesCombo.setText(items[0].getText(0));
                   cmdInput.setText(items[0].getText(1));
               }
           });
        
        //create content and label providers
        viewer = new TableViewer(cmdHistoryTable);
        viewer.setContentProvider(new STAFMachineContentProvider());
        viewer.setLabelProvider(new STAFRequestLabelProvider());
        
        //add this as a selection change listener
        viewer.addSelectionChangedListener(this);
        
        //add actions
        makeContextMenuActions();
        hookContextMenu();
        
        viewer.setInput(editor.getMachine());
    }
    
    
    /**
     * Creates the Command History SWT Table
     * @param parent Composite parent for the Table GUI component
     * @return Table
     */
    private Table createCmdHistoryTable(Composite parent)
    {
        Table table = new Table(parent, SWT.H_SCROLL | SWT.V_SCROLL
                                | SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER);

        table.setToolTipText("Double-click an entry to populate above " +
                             "fields.");
        
        TableLayout layout = new TableLayout();
        table.setLayout(layout);

        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        String[] STD_HEADINGS = { "Service", "Command", "RC" };

        layout.addColumnData(new ColumnWeightData(15, true));
        TableColumn tc0 = new TableColumn(table, SWT.LEFT);
        tc0.setText(STD_HEADINGS[0]);

        layout.addColumnData(new ColumnWeightData(75, true));
        TableColumn tc1 = new TableColumn(table, SWT.LEFT);
        tc1.setText(STD_HEADINGS[1]);

        layout.addColumnData(new ColumnWeightData(10, true));
        TableColumn tc2 = new TableColumn(table, SWT.LEFT);
        tc2.setText(STD_HEADINGS[2]);
        
        return table;
    }
    
    
    /**
     * Enables or disables the deleteResultAction and updates
     * the results table when the selection changes
     * @see ISelectionChangedListener#selectionChanged(SelectionChangedEvent)
     */
    public void selectionChanged(SelectionChangedEvent event)
    {
        IStructuredSelection sel = (IStructuredSelection) event.getSelection();
        
        if (sel.isEmpty())
        {
            updateResults("");
            deleteResultAction.setEnabled(false);
            return;
        }
        else
        {
            deleteResultAction.setEnabled(true);
        }
        
        STAFRequest req = (STAFRequest) sel.getFirstElement();
        
        if (req.getResult() == null)
        {
            updateResults("Command not complete.");
            return;
        }
        
        updateResults(req.getResult());
    }
    
    
    /**
     * Updates the result table with the given STAF result.
     * Different result display components are created based
     * on the format of the STAF result.
     * @param result STAFResult - should not be unmarshalled or modified
     */
    private void updateResults(String result)
    {
        STAFMarshallingContext mc = STAFMarshallingContext.unmarshall(result);
        Object resultObj = mc.getRootObject();
        
        if (resultObj instanceof Map)
        {   
            Map resultMap = (Map) resultObj;
            
            if (areMapValuesStrings(resultMap))
            {
                //Map with all String values, display in Table
                displayResultsAsSimpleTable(resultMap, mc);
            }
            else
            {
                //Map with 1 or more non-String values, display as Text
                displayResultsAsText(resultObj);
            }
        }
        else if (resultObj instanceof List)
        {
            List resultCList = (List) resultObj;
            
            //test if each item in list is String
            
            if (areListItemsStrings(resultCList))
            {
                //all strings display as a List
                displayResultsAsList(resultCList);
                return;
            }
            
            //not all Strings, test if all Maps
            
            if (areListItemsMaps(resultCList))
            {
                Iterator listIt = resultCList.iterator();
                while (listIt.hasNext())
                {
                    Map mapItem = (Map) listIt.next();
                    //TODO needs to also check if all maps implement same map class ???
                    if (!(areMapValuesStrings(mapItem)))
                    {
                        //"other" type of map, for now display as formatted text
                        displayResultsAsText(resultObj);
                        return;
                    }
                }
                
                // all items in List are Maps & all Map values are Strings
                // display in a table with dynamically created columns
                displayResultsAsDynamicTable(resultCList, mc);
                return;
            }
            
            //List with mixed or non-String/Map items
            //"other" type of map, for now display as formatted text
            
            displayResultsAsText(resultObj);
        }
        else
        {
            //String or other root object
            displayResultsAsText(resultObj);
        }
    }
    
    
    /**
     * Utility method to determine if all Map values are strings
     * @param map The Map to test
     * @return true if all values are of type String, false otherwise
     */
    protected boolean areMapValuesStrings(Map map)
    {
        Iterator valueIt = map.values().iterator();
        while (valueIt.hasNext())
        {
            if (!(valueIt.next() instanceof String))
            {
                return false;
            }
        }
        
        return true;
    }
    
    
    /**
     * Utility method to determine if all List items are strings
     * @param map The List to test
     * @return true if all items are of type String, false otherwise
     */
    protected boolean areListItemsStrings(List list)
    {
        Iterator listIt = list.iterator();

        while (listIt.hasNext())
        {
            if (!(listIt.next() instanceof String))
            {
                return false;
            }
        }
        
        return true;
    }
    
    
    /**
     * Utility method to determine if all List items are Maps
     * @param map The List to test
     * @return true if all items are of type Map, false otherwise
     */
    protected boolean areListItemsMaps(List list)
    {
        Iterator listIt = list.iterator();
        while (listIt.hasNext())
        {
            if (!(listIt.next() instanceof Map))
            {
                return false;
            }
        }
        
        return true;
    }
    
    
    /**
     * Displays results in a table with dynamically created columns
     * based on the map class. The List should contain Maps which are
     * all of the same Map Class. Each Object placed in the map will have
     * toString() called on it.
     * @param resultCList Unmarshalled List Root Object containing Maps
     * of the same map class
     * @param mc The STAFMarshallingContext of the List
     */
    protected void displayResultsAsDynamicTable(List resultCList,
                                                STAFMarshallingContext mc)
    {
        if (dynamicResultTable != null)
        {
            dynamicResultTable.dispose();
        }

        // First create Table widget
        dynamicResultTable = new Table(resultGroup, SWT.H_SCROLL | SWT.V_SCROLL
                                       | SWT.MULTI | SWT.FULL_SELECTION 
                                       | SWT.BORDER);

        TableLayout tableLayout = new TableLayout();
        dynamicResultTable.setLayout(tableLayout);

        dynamicResultTable.setLinesVisible(true);
        dynamicResultTable.setHeaderVisible(true);

        // Get MapClass

        Map firstMap = (Map) resultCList.get(0);
        STAFMapClassDefinition mapClass = null;
        if (firstMap.containsKey(MAP_CLASS_NAME_KEY))
        {
            mapClass = mc.getMapClassDefinition((String) firstMap
                         .get(MAP_CLASS_NAME_KEY));
        }

        if (mapClass == null)
        {
            String msg = "Could not get map class '"+
                         firstMap.get(MAP_CLASS_NAME_KEY)+"' from "+
                         "STAFMarshallingContext to build dynamic table.";
            MultiStatus err = Util.getServiceInfo(
                                   Activator.getDefault().getBundle(), 
                                   IStatus.ERROR, msg, null);
            Activator.getDefault().getLog().log(err);
            return;
        }

        // Create Table Columns

        Iterator mcKeyIt = mapClass.keyIterator();
        while (mcKeyIt.hasNext())
        {
            Map keyInfo = (Map) mcKeyIt.next();

            String key = (String) keyInfo.get(MC_KEY);

            String displayKey = key;
            if (keyInfo.containsKey(MC_DISPLAY_NAME))
            {
                displayKey = (String) keyInfo.get(MC_DISPLAY_NAME);
            }

            tableLayout.addColumnData(new ColumnWeightData(10, 10, true));
            TableColumn tc0 = new TableColumn(dynamicResultTable, SWT.NONE);
            tc0.setText(displayKey);
            tc0.setAlignment(SWT.LEFT);
            tc0.setResizable(true);
            tc0.pack();
        }

        // Populate table

        Iterator listIt = resultCList.iterator();
        while (listIt.hasNext())
        {
            Map rowMap = (Map) listIt.next();

            TableItem item = new TableItem(dynamicResultTable, SWT.NONE);

            mcKeyIt = mapClass.keyIterator();
            int columnIndex = 0;
            while (mcKeyIt.hasNext())
            {
                Map currentKeyMap = (Map) mcKeyIt.next();
                Object currentKey = currentKeyMap.get(MC_KEY);

                item.setText(columnIndex, rowMap.get(currentKey).toString());

                columnIndex++;
            }
        }

        //Set table as the visible object in layout
        resultStacklayout.topControl = dynamicResultTable;
        resultGroup.layout();
    }

    
    /**
     * Displays results in an SWT List. Each item in the List will
     * have toString() called on it
     * @param list Unmarshalled List Root Object containing Objects
     * to display
     */
    protected void displayResultsAsList(List list)
    {
        if (resultList == null)
        {
            resultList = new org.eclipse.swt.widgets.List(resultGroup,
                    SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.BORDER);
        }
        else
        {
            resultList.removeAll();
        }

        Iterator listIt = list.iterator();
        while (listIt.hasNext())
        {
            resultList.add(listIt.next().toString());
        }

        //Set List as the visible object in layout
        resultStacklayout.topControl = resultList;
        resultGroup.layout();
    }

    
    /**
     * Displays results as a STAF formatted object String
     * @param result The Root Object to display
     */
    protected void displayResultsAsText(Object result)
    {
        if (resultText == null)
        {
            resultText = new Text(resultGroup, SWT.MULTI | SWT.LEFT
                    | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL 
                    | SWT.BORDER);
        }

        resultText.setText(STAFMarshallingContext.formatObject(result));

        //Set Text as the visible object in layout
        resultStacklayout.topControl = resultText;
        resultGroup.layout();
    }

    
    /**
     * Displays results as a simple map with 2 columns, 'key' and 'value'.
     * If the map contains a map class, the display-name for the key will be
     * used.
     * @param map Unmarshalled Map Root Object
     * @param mc The STAFMarshallingContext for the root object
     */
    protected void displayResultsAsSimpleTable(Map map,
                                               STAFMarshallingContext mc)
    {
        if (simpleResultTable == null)
        {
            // First create Table widget
            simpleResultTable = new Table(resultGroup, SWT.H_SCROLL
                    | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION 
                    | SWT.BORDER);

            TableLayout tableLayout = new TableLayout();
            simpleResultTable.setLayout(tableLayout);

            simpleResultTable.setLinesVisible(true);
            simpleResultTable.setHeaderVisible(true);
            String[] STD_HEADINGS = { "Key", "Value" };

            tableLayout.addColumnData(new ColumnWeightData(50, 10, true));
            TableColumn tc0 = new TableColumn(simpleResultTable, SWT.NONE);
            tc0.setText(STD_HEADINGS[0]);
            tc0.setAlignment(SWT.LEFT);
            tc0.setResizable(true);

            tableLayout.addColumnData(new ColumnWeightData(50, 10, true));
            TableColumn tc1 = new TableColumn(simpleResultTable, SWT.NONE);
            tc1.setText(STD_HEADINGS[1]);
            tc1.setAlignment(SWT.LEFT);
            tc1.setResizable(true);
        }
        else
        {
            // table is already created so clear to be used again
            simpleResultTable.clearAll();
            simpleResultTable.setItemCount(0);
        }

        STAFMapClassDefinition mapClass = null;
        if (map.containsKey(MAP_CLASS_NAME_KEY))
        {
            mapClass = mc.getMapClassDefinition((String) map
                    .get(MAP_CLASS_NAME_KEY));
        }

        // Populate Table

        if (mapClass == null)
        {
            populateSimpleTableFromMap(map);
        }
        else
        {
            populateSimpleTableFromMapClass(mapClass, map);
        }

        //Set table as the visible object in layout
        resultStacklayout.topControl = simpleResultTable;
        resultGroup.layout();
    }
    
    
    /**
     * Populates the simpleResultTable with keys and values from
     * the map. Each Object will have toString() called on it.
     * @param map The map of data to display
     */
    private void populateSimpleTableFromMap(Map map)
    {
        Iterator keyIt = map.keySet().iterator();
        while (keyIt.hasNext())
        {
            Object key = keyIt.next();

            Object value = map.get(key);

            TableItem item = new TableItem(simpleResultTable, SWT.NONE);
            item.setText(0, key.toString());
            item.setText(1, value.toString());
        }
    }
    
    
    /**
     * Populates the simpleResultTable with the display-keys and values from
     * the map. Each Object will have toString() called on it.
     * @param mapClass The STAFMapClassDefintion for the map
     * @param map The map of data to dispay
     */
    private void populateSimpleTableFromMapClass(
                         STAFMapClassDefinition mapClass, Map map)
    {
        Iterator keyIt = mapClass.keyIterator();
        while (keyIt.hasNext())
        {
            Map keyInfo = (Map) keyIt.next();

            Object key = keyInfo.get(MC_KEY);

            Object displayKey = key;
            if (keyInfo.containsKey(MC_DISPLAY_NAME))
            {
                displayKey = keyInfo.get(MC_DISPLAY_NAME);
            }

            Object value = map.get(key);

            TableItem item = new TableItem(simpleResultTable, SWT.NONE);
            item.setText(0, displayKey.toString());
            item.setText(1, value.toString());
        }
    }
    
    
    /**
     * Creates the context menu actions for the Command History table
     */
    private void makeContextMenuActions()
    {
        deleteResultAction = new Action()
        {
            public void run()
            {
                String msg = "All selected items will be deleted.";
                if (MessageDialog.openConfirm(viewer.getControl().getShell(), 
                                              "Confirm Delete", msg))
                {
                    IStructuredSelection selection = (IStructuredSelection) 
                                                      viewer.getSelection();
                    Iterator selectionIt = selection.iterator();
                    while (selectionIt.hasNext())
                    {
                        STAFRequest currSelect = (STAFRequest) selectionIt.next();
                        editor.getMachine().removeRequest(currSelect);
                    }
                }
            }    
        };
        deleteResultAction.setText("Delete");
        deleteResultAction.setToolTipText("Delete selected items");
        deleteResultAction.setImageDescriptor(
                           PlatformUI.getWorkbench().getSharedImages()
                           .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
        
        //Set initialed enabled state
        if (viewer.getSelection().isEmpty())
        {
            deleteResultAction.setEnabled(false);
        }        
    }
    
    
    /**
     * Connects the context menu with the viewer
     */
    private void hookContextMenu()
    {
        MenuManager menuMgr = new MenuManager("#PopupMenu");
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(new IMenuListener()
        {
            public void menuAboutToShow(IMenuManager manager)
            {
                MainEditorPage.this.fillContextMenu(manager);
            }
        });
        Menu menu = menuMgr.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);
    }
    
    
    /**
     * Adds actions to the context MenuManager
     * @param manager IMenuManager
     */
    private void fillContextMenu(IMenuManager manager)
    {
        manager.add(deleteResultAction);
    }
    
    
    /**
     * Creates the Result (bottom) area of the editor
     * @param parent Composite parent for the GUI components
     */
    private void createResultArea(Composite parent)
    {
        resultGroup = new Group(parent, SWT.NONE);
        resultGroup.setText("Command Results");
        
        resultStacklayout = new StackLayout();
        resultStacklayout.marginHeight = 10;
        resultStacklayout.marginWidth = 10;
        resultGroup.setLayout(resultStacklayout);
    }
    
    
    /**
     * The ContentProvider for the Command History Table JFace viewer.
     * Provides content based on the ISTAFMachine model.
     */
    class STAFMachineContentProvider implements IStructuredContentProvider,
                                                IPropertyChangeListener
    {
        private TableViewer viewer;

        /**
         * Returns the elements (ISTAFRequests) for the table
         * @see IStructuredContentProvider#getElements(Object)
         */
        public Object[] getElements(Object inputElement)
        {
            return ((STAFMachine) inputElement).getRequests();
        }

        
        /**
         * @see IContentProvider#dispose()
         */
        public void dispose()
        {
            viewer = null;
        }

        
        /**
         * Updates the viewer and property change listeners when input changes
         * @see IContentProvider#inputChanged(Viewer, Object, Object)
         */
        public void inputChanged(Viewer viewer, Object oldInput, 
                                 Object newInput)
        {
            this.viewer = (TableViewer) viewer;

            if (oldInput != newInput)
            {
                if (oldInput != null)
                {
                    ((ISTAFMachine) oldInput)
                            .removePropertyChangeListener(this);
                }

                if (newInput != null)
                {
                    ((ISTAFMachine) newInput).addPropertyChangeListener(this);
                }
            }
        }

        
        /**
         * Handles property changes generated by the model for
         * request updates.
         * @see IPropertyChangeListener#propertyChange(PropertyChangeEvent)
         */
        public void propertyChange(PropertyChangeEvent event)
        {
            Control ctrl = viewer.getControl();
            final PropertyChangeEvent finalEvent = event;

            if (ctrl != null && !ctrl.isDisposed())
            {
                ctrl.getDisplay().asyncExec(new Runnable()
                {
                    public void run()
                    {   
                        viewer.refresh();

                        if (finalEvent.getProperty().equals(
                                ISTAFMachine.REQUEST_ADD))
                        {
                            //select newly added request and set dirty flag
                            StructuredSelection sel = new StructuredSelection(
                                    finalEvent.getNewValue());
                            viewer.setSelection(sel);
                        }

                        if (finalEvent.getProperty().equals(
                                ISTAFRequest.REQUEST_UPDATE))
                        {
                            //if current selection is same as updated request
                            //set selection (won't appear in results otherwise)
                            //and set dirty flag always
                            IStructuredSelection currSel = 
                                (IStructuredSelection) viewer.getSelection();
                            if (currSel.getFirstElement().equals(
                                           finalEvent.getNewValue()))
                            {
                                viewer.setSelection(currSel);
                            }
                        }
                    }
                }); //end new Runnable
            }
        }
    }//end STAFMachineContentProvider

    
    /**
     * The LabelProvider for the Command History Table JFace viewer.
     */
    class STAFRequestLabelProvider extends LabelProvider implements
                                                         ITableLabelProvider
    {
        /**
         * @see ITableLabelProvider#getColumnText(Object, int)
         */
        public String getColumnText(Object obj, int index)
        {
            STAFRequest req = (STAFRequest) obj;
            switch (index)
            {
                case 0:
                    return req.getService();

                case 1:
                    return req.getCommand();

                case 2:
                    if (req.getRC() == -1)
                    {
                        return "In Progress";
                    }
                    else
                    {
                        return String.valueOf(req.getRC());
                    }

                default:
                    //should not go here
                    String msg = "Unexpected column index passed to " +
                                 "STAFRequestLabelProvider: "+index;
                    MultiStatus err = Util.getServiceInfo(
                                           Activator.getDefault().getBundle(), 
                                           IStatus.ERROR, msg, null);
                    Activator.getDefault().getLog().log(err);
                    return null;
            }

        }

        
        /**
         * @see ITableLabelProvider#getColumnImage(Object, int)
         */
        public Image getColumnImage(Object obj, int index)
        {
            return null;
        }
    }//end STAFRequestLabelProvider


    /**
     * Handles property changes generated by the model for updates
     * to the servicesList and machine information.
     * @see IPropertyChangeListener#propertyChange(PropertyChangeEvent)
     */
    public void propertyChange(final PropertyChangeEvent event)
    {
        if (event.getProperty().equals(ISTAFMachine.SERVICES_UPDATE))
        {
            getDisplay().asyncExec(new Runnable()
                {
                    public void run()
                    {
                        List services = (List) event.getNewValue();
                        servicesCombo.setItems((String[]) services.toArray(
                                               new String[services.size()]));
                        
                        updateSTAFVersion();
                    }
                });
        }
        
        if (event.getProperty().equals(ISTAFMachine.MACH_UPDATE))
        {
            getDisplay().asyncExec(new Runnable()
            {
                public void run()
                {
                    updateMachInfoText();
                }
            });
        }
        
    }
}