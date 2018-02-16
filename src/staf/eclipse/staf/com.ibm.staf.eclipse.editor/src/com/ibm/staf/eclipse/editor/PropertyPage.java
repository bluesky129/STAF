/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.editor;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.PlatformUI;

import com.ibm.staf.eclipse.model.ExtensionMapping;
import com.ibm.staf.eclipse.model.IExtensionMapping;
import com.ibm.staf.eclipse.model.ISTAFMachine;
import com.ibm.staf.eclipse.model.STAFMachine;


/**
 * The "Properties" page of the multi-page editor.
 */
public class PropertyPage extends Composite implements IPropertyChangeListener,
                                                       ISelectionChangedListener
{
    /* Constants */
    
    /** Display name for the tab for this editor page */
    public final static String PROPERTY_PAGE_TAB_NAME = "Properties";
    
    /* Instance Fields */
    
    private STAFEditor editor;
    
    private Text hostText;
    private Text portText;
    private Text interfaceText;
    private TableViewer extensionTableViewer;
    
    private Combo propertyServicesCombo;
    private Combo propertyExtensionsCombo;
    private Text propertyExtensionsText;
    
    private Action deleteExtensionMappingAction;


    
    /**
     * Constructor
     * Creates the GUI components of the editor page
     * @param editor A reference to the STAFEditor class
     * @param parent The Composite parent of all GUI components on
     * the page
     */
    public PropertyPage(STAFEditor editor, Composite parent)
    {
        super(parent, SWT.NONE);
        
        this.editor = editor;
        editor.getMachine().addPropertyChangeListener(this);
        
        GridLayout layout = new GridLayout(1, true);
        layout.marginHeight = 10;
        layout.marginWidth = 10;
        setLayout(layout);
        
        Group topGroup = createTopGroup(this);
        GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
        topGroup.setLayoutData(data);
        
        Group tableGroup = createTableGroup(this);
        data = new GridData(SWT.FILL, SWT.FILL, true, true);
        tableGroup.setLayoutData(data);
    }
    
    
    /**
     * Creates the top area of the properties page
     */
    private Group createTopGroup(Composite parent)
    {
        //Create Group
        Group topGroup = new Group(parent, SWT.NONE);
        topGroup.setText("System Properties");
        
        //Create layout
        GridLayout topGroupLayout = new GridLayout();
        topGroupLayout.numColumns = 3;
        topGroupLayout.marginHeight = 10;
        topGroupLayout.marginWidth = 10;
        topGroup.setLayout(topGroupLayout);
        
        
        //Create Title Label
        Label titleLabel = new Label(topGroup, SWT.NONE);
        titleLabel.setText("Properties for "+editor.getPartName());
        GridData data = new GridData();
        data.horizontalSpan = 3;
        titleLabel.setLayoutData(data);
        
        //Create Filename Label
        try
        {
            Label filenameLabel = new Label(topGroup, SWT.NONE);
            IPath path = ((IStorageEditorInput) editor.getEditorInput()).
                             getStorage().getFullPath();
            IPath root = ResourcesPlugin.getWorkspace().getRoot().getLocation();
            filenameLabel.setText("Filename: " + root.toOSString() + 
                                                 path.toOSString());
            data = new GridData();
            data.horizontalSpan = 3;
            filenameLabel.setLayoutData(data);
        }
        catch (CoreException ce)
        {
            MultiStatus err = Util.getServiceInfo(
                                   Activator.getDefault().getBundle(), ce);
            Activator.getDefault().getLog().log(err);
        }
        
        //Create Host Label & Text
        Label hostLabel = new Label(topGroup, SWT.NONE);
        hostLabel.setText("&Hostname");
        
        hostText = new Text(topGroup, SWT.BORDER);
        hostText.setText(editor.getHostname());
        data = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
        hostText.setLayoutData(data);
        
        //Create Port Label & Text & default checkbox
        Label portLabel = new Label(topGroup, SWT.NONE);
        portLabel.setText("&Port");
        
        portText = new Text(topGroup, SWT.BORDER);
        data = new GridData(SWT.FILL, SWT.CENTER, true, false);
        portText.setLayoutData(data);
        if (editor.getPort() == null)
        {
            portText.setText("");
            portText.setEnabled(false);
        }
        else
        {
            portText.setText(editor.getPort());
            portText.setEnabled(true);
        }
        
        Button portCheckbox = new Button(topGroup, SWT.CHECK);
        portCheckbox.setText("Use local default");
        portCheckbox.setSelection(editor.getPort() == null);
        portCheckbox.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    Button b = (Button) e.getSource();
                    boolean enablePort = !b.getSelection();
                    
                    portText.setEnabled(enablePort);
                    
                    if (enablePort)
                    {
                        String portString = editor.getPort();
                        portString = (portString == null) ? "" : portString;
                        portText.setText(portString);
                    }
                    else
                    {
                        portText.setText("");
                    }
                }
            });
        
        //Create Interface Label & Text & default checkbox
        Label interfaceLabel = new Label(topGroup, SWT.NONE);
        interfaceLabel.setText("&Interface");
        
        interfaceText = new Text(topGroup, SWT.BORDER);
        interfaceText.setText((editor.getInterface() == null) ? "" : 
                               editor.getInterface());
        data = new GridData(SWT.FILL, SWT.CENTER, true, false);
        interfaceText.setLayoutData(data);
        if (editor.getInterface() == null)
        {
            interfaceText.setText("");
            interfaceText.setEnabled(false);
        }
        else
        {
            interfaceText.setText(editor.getInterface());
            interfaceText.setEnabled(true);
        }
        
        Button interfaceCheckbox = new Button(topGroup, SWT.CHECK);
        interfaceCheckbox.setText("Use local default");
        interfaceCheckbox.setSelection(editor.getInterface() == null);
        interfaceCheckbox.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                Button b = (Button) e.getSource();
                boolean enablePort = !b.getSelection();
                
                interfaceText.setEnabled(enablePort);
                
                if (enablePort)
                {
                    String interfaceString = editor.getInterface();
                    interfaceString = (interfaceString == null) ? 
                                      "" : interfaceString;
                    interfaceText.setText(interfaceString);
                }
                else
                {
                    interfaceText.setText("");
                }
            }
        });
                
        //Create Apply Button
        Button applyButton = new Button(topGroup, SWT.PUSH);
        applyButton.setText("Apply");
        applyButton.setToolTipText("Apply Hostname / Port Updates");
        data = new GridData();
        data.horizontalSpan = 3;
        applyButton.setLayoutData(data);
        applyButton.addSelectionListener(new SelectionAdapter()
            {
               public void widgetSelected(SelectionEvent e)
               {
                   applyPropertyChanges();
               }
            });
        
        return topGroup;
    }
    
    
    /**
     * Creates the Group for the service extension mapping table.
     */
    private Group createTableGroup(Composite parent)
    {
        //Create Group
        Group tableGroup = new Group(parent, SWT.NONE);
        
        //Set Layout
        tableGroup.setText("Extension Configuration");
        GridLayout tableGroupLayout = new GridLayout();
        tableGroupLayout.numColumns = 7;
        tableGroupLayout.marginHeight = 10;
        tableGroupLayout.marginWidth = 10;
        tableGroupLayout.verticalSpacing = 10;
        tableGroup.setLayout(tableGroupLayout);
        
        //Create Extension Mapping Table & Viewer
        Table table = createExtensionTable(tableGroup);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.horizontalSpan = 7;
        table.setLayoutData(data);
        
        extensionTableViewer = createExtensionTableViewer(table);
        
        //Add actions to table
        makeContextMenuActions();
        hookContextMenu();
            
        //Create services Label & Combo
        Label propertyServicesLabel = new Label(tableGroup, SWT.NONE);
        propertyServicesLabel.setText("Service Name:");
        
        propertyServicesCombo = new Combo(tableGroup, SWT.BORDER);
        propertyServicesCombo.setToolTipText("Select Service To Map To Extension");
        
        //Create title Lable & Text
        Label propertyExtensionTitle = new Label(tableGroup, SWT.NONE);
        propertyExtensionTitle.setText("Title");
        
        propertyExtensionsText = new Text(tableGroup, SWT.SINGLE | SWT.BORDER);
        propertyExtensionsText.setToolTipText("Enter Optional Tab Title. \n\n " +
            "If not specified, the registered service name will be used.");
        
        //Create extensions Label & Combo
        Label propertyExtensionsLabel = new Label(tableGroup, SWT.NONE);
        propertyExtensionsLabel.setText("Extension:");
        
        propertyExtensionsCombo = new Combo(tableGroup, SWT.READ_ONLY);
        propertyExtensionsCombo.setToolTipText("Select Extension To Map To Service Name");
        
        IExtensionPoint point = Platform.getExtensionRegistry().
                                getExtensionPoint(STAFEditor.EXTENSION_POINT_ID);

        //Add any <extension> tags for our extension-point to combo
        if (point != null) 
        {
            IExtension[] extensions = point.getExtensions();

            for (int i = 0; i < extensions.length; i++) 
            {
                IConfigurationElement[] ces = extensions[i].
                                              getConfigurationElements();
                for (int j=0; j < ces.length; j++)
                {
                    String name = ces[j].getAttribute(STAFEditor.CLASS_ATT);
                    propertyExtensionsCombo.add(name);
                }
            }
        }
        
        //Create Add Button
        Button addButton = new Button(tableGroup, SWT.PUSH);
        addButton.setText("Add");
        addButton.setToolTipText("Add Service/Extension Mapping");
        addButton.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    String title = propertyExtensionsText.getText();
                    title = title.equals("") ? null : title;
                    IExtensionMapping mapping = new ExtensionMapping(
                                                propertyServicesCombo.getText(), 
                                                propertyExtensionsCombo.getText(),
                                                title);
                    editor.getMachine().addExtensionMapping(mapping);
                }
            });
        
        return tableGroup;
    }
    
    
    /**
     * Creates the TableViewer using the given Table
     * @param table Table
     */
    private TableViewer createExtensionTableViewer(Table table)
    {
        //create content and label providers
        TableViewer viewer = new TableViewer(table);
        viewer.setContentProvider(new ExtensionContentProvider());
        viewer.setLabelProvider(new ExtensionLabelProvider());
        
        //add this as a selection change listener
        viewer.addSelectionChangedListener(this);
        
        viewer.setInput(editor.getMachine());
        
        return viewer;
    }
    
    
    /**
     * Create the service/extension mapping Table
     * @param parent Composite parent for the GUI components
     * @return Table empty, formatted table
     */
    private Table createExtensionTable(Composite parent)
    {
        Table table = new Table(parent, SWT.H_SCROLL | SWT.V_SCROLL | 
                                SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER);

        TableLayout layout = new TableLayout();
        table.setLayout(layout);

        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        String[] STD_HEADINGS = { "Service Name", "Title", "Extension" };

        layout.addColumnData(new ColumnWeightData(25, 25, true));
        TableColumn tc0 = new TableColumn(table, SWT.NONE);
        tc0.setText(STD_HEADINGS[0]);
        tc0.setAlignment(SWT.LEFT);
        tc0.setResizable(true);
        
        layout.addColumnData(new ColumnWeightData(25, 25, true));
        TableColumn tc1 = new TableColumn(table, SWT.NONE);
        tc1.setText(STD_HEADINGS[1]);
        tc1.setAlignment(SWT.LEFT);
        tc1.setResizable(true);
        
        layout.addColumnData(new ColumnWeightData(50, 25, true));
        TableColumn tc2 = new TableColumn(table, SWT.NONE);
        tc2.setText(STD_HEADINGS[2]);
        tc2.setAlignment(SWT.LEFT);
        tc2.setResizable(true);
        
        return table;
    }
    
    
    /**
     * Creates the context menu actions for the Extension Mapping table
     */
    private void makeContextMenuActions()
    {
        deleteExtensionMappingAction = new Action()
        {
            public void run()
            {
                String msg = "All selected items will be deleted.";
                if (MessageDialog.openConfirm(
                        extensionTableViewer.getControl().getShell(), 
                        "Confirm Delete", msg))
                {      
                    IStructuredSelection selection = (IStructuredSelection) 
                                         extensionTableViewer.getSelection();
                    Iterator selectionIt = selection.iterator();
                    while (selectionIt.hasNext())
                    {
                        IExtensionMapping currSelect = (IExtensionMapping) selectionIt.next();
                        editor.getMachine().removeExtensionMapping(currSelect);
                    }
                }
            }    
        };
        deleteExtensionMappingAction.setText("Delete");
        deleteExtensionMappingAction.setToolTipText("Delete selected items");
        deleteExtensionMappingAction.setImageDescriptor(
                PlatformUI.getWorkbench().getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
        
        //Set initialed enabled state
        if (extensionTableViewer.getSelection().isEmpty())
        {
            deleteExtensionMappingAction.setEnabled(false);
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
                PropertyPage.this.fillContextMenu(manager);
            }
        });
        Menu menu = menuMgr.createContextMenu(extensionTableViewer.getControl());
        extensionTableViewer.getControl().setMenu(menu);
    }
    
    
    /**
     * Adds actions to the context MenuManager
     * @param manager IMenuManager
     */
    private void fillContextMenu(IMenuManager manager)
    {
        manager.add(deleteExtensionMappingAction);
    }
    
    
    /**
     * Returns the display name for the tab for this page
     * @return tab name
     */
    public String getTabName()
    {
        return PROPERTY_PAGE_TAB_NAME;
    }


    /**
     * Handles property changes generated by the model for updates
     * to the servicesList and extensionMapping.
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
                    propertyServicesCombo.setItems((String[]) services.toArray(
                                                   new String[services.size()]));
                }
            });
        }
    }
    
    
    /**
     * Used to enable/disable delete action
     * @see ISelectionChangedListener#selectionChanged(SelectionChangedEvent)
     */
    public void selectionChanged(SelectionChangedEvent event)
    {
        IStructuredSelection sel = (IStructuredSelection) event.getSelection();
        
        if (sel.isEmpty())
        {
            deleteExtensionMappingAction.setEnabled(false);
        }
        else
        {
            deleteExtensionMappingAction.setEnabled(true);
        }        
    }
    
    
    /**
     * Applies property changes (hostname/port) to the machine model
     */
    private void applyPropertyChanges()
    {
        //verify values
        
        //hostName
        if (hostText.getText().length() == 0)
        {
            MessageDialog.openError(getShell(), 
                                    "Hostname Error",
                                    "Hostname must be specified");
            return;
        }

        //port
        String port = portText.getText();
        
        if (port.equals(""))
        {
            port = null;
        }
        else
        {
            try
            {
                Integer.valueOf(port);
            }
            catch (NumberFormatException nfe)
            {
                MessageDialog.openError(getShell(), 
                                        "Port Error",
                                        "Port must be an integer");
                return;
            }
        }
        
        //interface
        String stafInterface = interfaceText.getText();
        if (stafInterface.equals(""))
        {
            stafInterface = null;
        }
        
        
        //update model
        
        editor.getMachine().setHostname(hostText.getText());   
        editor.getMachine().setPort(port);
        editor.getMachine().setInterface(stafInterface);
    }
    
    
    /**
     * The ContentProvider for the Service/Extension Mapping Table JFace viewer.
     * Provides content based on the ExtensionMapping of the ISTAFMachine model.
     */
    class ExtensionContentProvider implements IStructuredContentProvider,
                                              IPropertyChangeListener
    {
        private TableViewer viewer;

        /**
         * Returns the elements (ISTAFRequests) for the table
         * @see IStructuredContentProvider#getElements(Object)
         */
        public Object[] getElements(Object inputElement)
        {
            return ((STAFMachine) inputElement).getExtensionMappings();
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

            if (ctrl != null && !ctrl.isDisposed())
            {
                ctrl.getDisplay().asyncExec(new Runnable()
                {
                    public void run()
                    {   
                        viewer.refresh();
                    }
                });
            }
        }
    }//end ExtensionContentProvider

    
    /**
     * The LabelProvider for the Service/Extension Mapping Table JFace viewer.
     */
    class ExtensionLabelProvider extends LabelProvider implements
                                                         ITableLabelProvider
    {
        /**
         * @see ITableLabelProvider#getColumnText(Object, int)
         */
        public String getColumnText(Object obj, int index)
        {
            IExtensionMapping ext = (IExtensionMapping) obj;
            switch (index)
            {
                case 0:
                    return ext.getService();
                    
                case 1:
                    return ext.getTitle();

                case 2:
                    return ext.getExtension();

                default:
                    //should not go here
                    String msg = "Unexpected column index passed to " +
                                 "ExtensionLabelProvider: "+index;
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
}