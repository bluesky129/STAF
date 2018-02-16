/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.extension.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.MultiStatus;
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
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.ibm.staf.STAFMarshallingContext;
import com.ibm.staf.STAFResult;
import com.ibm.staf.eclipse.editor.STAFEditorExtension;
import com.ibm.staf.eclipse.editor.Util;
import com.ibm.staf.eclipse.model.ISTAFRequest;
import com.ibm.staf.eclipse.model.STAFRequest;


/**
 * Implements the Composite to contain the widgets and functionality
 * for the Requests page of the ServiceExtension.
 */
public class RequestPage extends Composite
{
    /* Constants */
    
    /** Property Change Event key */
    public static final String REQUEST_LIST_UPDATE = "REQUEST_LIST_UPDATE";
    
    /* Instance Fields */
    
    private STAFEditorExtension extension;
    
    private List requests = new ArrayList();
    
    private Table requestTable;
    private ListenerList propertyChangeListeners;

    private Table detailsTable;

    
    /**
     * Constructor
     * All widgets intialized here
     * @param extension A reference to the STAFEditorExtension for service
     * @param parent The CTabFolder which will contain the requests page. Used
     * as the parent of this widget.
     * @param style The SWT style bit(s). Passed to super constructor.
     */
    public RequestPage(STAFEditorExtension extension, CTabFolder parent, int style)
    {
        super(parent, style);
        
        this.extension = extension;
        
        FillLayout layout = new FillLayout();
        layout.marginHeight = 10;
        layout.marginWidth = 10;
        setLayout(layout);
        
        SashForm form = new SashForm(this, SWT.VERTICAL);
        
        createTableGroup(form);
        
        createDetailsGroup(form);
        
        int[] weights = {67, 33};
        form.setWeights(weights);
        form.SASH_WIDTH = 5;
        
        refreshRequestsList();
    }
    
    
    /**
     * Creates the Group widget which contains all wigets for the
     * request table
     * @param parent
     * @return Group
     */
    private Group createTableGroup(Composite parent)
    {
        Group group = new Group(parent, SWT.NONE);
        group.setText("STAF Requests");
        
        GridLayout gridLayout = new GridLayout(1, false);
        group.setLayout(gridLayout);
        
        //Create refresh button
        Button refreshButton = new Button(group, SWT.PUSH);
        refreshButton.setImage(ServiceExtension.refreshIcon);
        refreshButton.setToolTipText("Refresh Requests List");
        refreshButton.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    refreshRequestsList();
                }
            });//end new SelectionAdapter
        
        //Create requests table
        requestTable = createRequestTable(group);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        requestTable.setLayoutData(data);
        
        //create content and label providers
        TableViewer viewer = new TableViewer(requestTable);
        viewer.setContentProvider(new RequestsContentProvider());
        viewer.setLabelProvider(new RequestsLabelProvider());
        
        //add this as a selection change listener
        viewer.addSelectionChangedListener(new ISelectionChangedListener()
            {
                public void selectionChanged(SelectionChangedEvent event)
                {
                    IStructuredSelection sel = 
                        (IStructuredSelection) event.getSelection();
                
                    ServiceRequest req = (ServiceRequest) sel.getFirstElement();
                    displayRequestDetails(req);
                }
            });
        
        viewer.setInput(requests);
        
        return group;
    }
    
    
    /**
     * Creates the request Table widget
     * @param parent
     * @return Table
     */
    private Table createRequestTable(Composite parent)
    {
        //First create Table widget
        Table table = new Table(parent, SWT.H_SCROLL | SWT.V_SCROLL
                                | SWT.SINGLE | SWT.FULL_SELECTION 
                                | SWT.BORDER);

        TableLayout tableLayout = new TableLayout();
        table.setLayout(tableLayout);

        table.setLinesVisible(true);
        table.setHeaderVisible(true);        
        
        //Add Columns
        String[] STD_HEADINGS = { "Request #", "Start Timestamp", 
                                  "Service", "Request" };
        
        tableLayout.addColumnData(new ColumnWeightData(10, 10, true));
        TableColumn tc0 = new TableColumn(table, SWT.NONE);
        tc0.setText(STD_HEADINGS[0]);
        tc0.setAlignment(SWT.LEFT);
        tc0.setResizable(true);
        
        tableLayout.addColumnData(new ColumnWeightData(15, 10, true));
        TableColumn tc1 = new TableColumn(table, SWT.NONE);
        tc1.setText(STD_HEADINGS[1]);
        tc1.setAlignment(SWT.LEFT);
        tc1.setResizable(true);
        
        tableLayout.addColumnData(new ColumnWeightData(10, 10, true));
        TableColumn tc2 = new TableColumn(table, SWT.NONE);
        tc2.setText(STD_HEADINGS[2]);
        tc2.setAlignment(SWT.LEFT);
        tc2.setResizable(true);
        
        tableLayout.addColumnData(new ColumnWeightData(65, 10, true));
        TableColumn tc3 = new TableColumn(table, SWT.NONE);
        tc3.setText(STD_HEADINGS[3]);
        tc3.setAlignment(SWT.LEFT);
        tc3.setResizable(true);
        
        return table;
    }
    
    
    /**
     * Creates the Group widget to contain all widgets to display
     * request details.
     * @param parent
     * @return Group
     */
    private Group createDetailsGroup(Composite parent)
    {
        Group group = new Group(parent, SWT.NONE);
        group.setText("Request Details");
        
        FillLayout layout = new FillLayout();
        layout.marginHeight = 10;
        layout.marginWidth = 10;
        group.setLayout(layout);
        
        detailsTable = createDetailsTable(group);
        
        return group;
    }
    
    
    /**
     * Creates the Table widget to display request details.
     * @param parent
     * @return Table
     */
    private Table createDetailsTable(Composite parent)
    {
        //First create Table widget
        Table table = new Table(parent, SWT.H_SCROLL | SWT.V_SCROLL
                                        | SWT.MULTI | SWT.BORDER);

        TableLayout tableLayout = new TableLayout();
        table.setLayout(tableLayout);

        table.setLinesVisible(true);
        table.setHeaderVisible(false);
        
        //Add Columns
        tableLayout.addColumnData(new ColumnWeightData(25, 10, true));
        TableColumn tc0 = new TableColumn(table, SWT.NONE);
        tc0.setAlignment(SWT.LEFT);
        tc0.setResizable(true);
        
        tableLayout.addColumnData(new ColumnWeightData(75, 10, true));
        TableColumn tc1 = new TableColumn(table, SWT.NONE);
        tc1.setAlignment(SWT.LEFT);
        tc1.setResizable(true);
        
        return table;
    }
    
    
    /**
     * Refreshes the list of requests, modifies the internal model of
     * requests and fires property change events on all model listeners.
     */
    public void refreshRequestsList()
    {                   
        String request = "LIST REQUESTS LONG";
        
        ISTAFRequest req = new STAFRequest(extension.getServiceName(), 
                                           request);
        //add a property listener to the single request
        req.addPropertyChangeListener(new IPropertyChangeListener()
            {
                public void propertyChange(final PropertyChangeEvent event)
                {
                    //Do nothing if widget has been disposed
                    if (requestTable.isDisposed())
                    {
                        return;
                    }
                    
                    requestTable.getDisplay().asyncExec(new Runnable()
                        {
                            public void run()
                            {     
                                ISTAFRequest req = (ISTAFRequest) 
                                                      event.getNewValue();
                                
                                //handle errors
                                if (req.getRC() != STAFResult.Ok)
                                {
                                    String msg = "Error listing STAF requests. "+
                                        "Endpoint: '"+
                                        extension.getEditor().getEndpoint()+
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
                                
                                //Populate request list
                                List requestsList = 
                                    (List) (STAFMarshallingContext.unmarshall(
                                           req.getResult()).getRootObject());
                                
                                populateRequestList(requestsList);
                            }
                        });
                }
            });
        
        extension.submitCommand(req);
    }
    
    
    /**
     * Populates the internal List of ServiceRequest Objects with
     * all the requests contained in the unmarshalled List of
     * STAF/Service/Service/RequestInfo Map Classes. 
     * This method is synchronized to prevent 2 different
     * refresh threads from stepping on each other.
     * @param newRequests The List of unmarshalled 
     * STAF/Service/Service/RequestInfo Map Classes
     */
    private synchronized void populateRequestList(List newRequests)
    {
        //Save old request list and clear requests
        List oldRequests = new ArrayList(requests);
        requests.clear();
        
        //populate requests with new data
        Iterator requestsIt = newRequests.iterator();
        while (requestsIt.hasNext())
        {
            Map requestMap = (Map) requestsIt.next();
            
            addRequest(requestMap);
        }
        
        //fire property change
        firePropertyChange(REQUEST_LIST_UPDATE, 
                           oldRequests, requests);
    }
    
    
    /**
     * Adds a request to the model (List). No notification
     * occurs from calling this method.
     * @param requestMap The unmarshalled STAF/Service/Service/RequestInfo
     * Map 
     */
    private void addRequest(Map requestMap)
    {
        ServiceRequest req = new ServiceRequest();
        
        req.setRequestNumber((String) requestMap.get("requestNumber"));
        req.setStartTimeStamp((String) requestMap.get("startTimestamp"));
        req.setHandleName((String) requestMap.get("sourceHandleName"));
        req.setHandleNum((String) requestMap.get("sourceHandle"));
        req.setSource((String) requestMap.get("sourceMachine"));
        req.setTarget((String) requestMap.get("targetMachine"));
        req.setService((String) requestMap.get("service"));
        req.setRequest((String) requestMap.get("request"));
        
        requests.add(req);
    }
    
    
    /**
     * Adds a listener that will be notified when the list of requests
     * is updated.
     */
    public void addPropertyChangeListener(IPropertyChangeListener listener)
    {
        getPropertyChangeListeners().add(listener);
    }
    
    
    /**
     * Removes a listener from request list update notifications.
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
     * Displays request details in the detailsTable of the given 
     * ServiceRequest
     * @param req ServiceRequest
     */
    private void displayRequestDetails(ServiceRequest req)
    {        
        detailsTable.clearAll();
        detailsTable.setItemCount(0);
        
        if (req != null)
        {
            TableItem item = new TableItem(detailsTable, SWT.NONE);
            item.setText(0, "Request #");
            item.setText(1, req.getRequestNumber());
            
            item = new TableItem(detailsTable, SWT.NONE);
            item.setText(0, "Start Timestamp");
            item.setText(1, req.getStartTimeStamp());
            
            item = new TableItem(detailsTable, SWT.NONE);
            item.setText(0, "Handle Name");
            item.setText(1, req.getHandleName());
            
            item = new TableItem(detailsTable, SWT.NONE);
            item.setText(0, "Handle #");
            item.setText(1, req.getHandleNum());
            
            item = new TableItem(detailsTable, SWT.NONE);
            item.setText(0, "Source");
            item.setText(1, req.getSource());
            
            item = new TableItem(detailsTable, SWT.NONE);
            item.setText(0, "Target");
            item.setText(1, req.getTarget());
            
            item = new TableItem(detailsTable, SWT.NONE);
            item.setText(0, "Service");
            item.setText(1, req.getService());
            
            item = new TableItem(detailsTable, SWT.NONE);
            item.setText(0, "Request");
            item.setText(1, req.getRequest());
        }

    }
    
    
    /**
     * The ContentProvider for the Requests List Table JFace viewer.
     * Provides content based on the ServiceRequests in the requests List
     */
    class RequestsContentProvider implements IStructuredContentProvider,
                                             IPropertyChangeListener
    {
        private TableViewer viewer;
        
        
        /**
         * Constructor
         * Registers the ContentProvider as a property change listener
         * for request updates
         */
        public RequestsContentProvider()
        {
            RequestPage.this.addPropertyChangeListener(this);
        }
        

        /**
         * Returns the elements (ServiceRequests) for the table
         * @see IStructuredContentProvider#getElements(Object)
         */
        public Object[] getElements(Object inputElement)
        {
            return ((List) inputElement).toArray(); 
        }

        
        /**
         * Disposes the ContentProvider. Removes it as a property
         * change listener for request updates.
         * @see IContentProvider#dispose()
         */
        public void dispose()
        {
            viewer = null;
            
            RequestPage.this.removePropertyChangeListener(this);
        }

        
        /**
         * Updates the viewer when input changes
         * @see IContentProvider#inputChanged(Viewer, Object, Object)
         */
        public void inputChanged(Viewer viewer, Object oldInput, 
                                 Object newInput)
        {
            this.viewer = (TableViewer) viewer;
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
     * The LabelProvider for the Requests List Table JFace viewer.
     */
    class RequestsLabelProvider extends LabelProvider implements
                                                      ITableLabelProvider
    {
        /**
         * @see ITableLabelProvider#getColumnText(Object, int)
         */
        public String getColumnText(Object obj, int index)
        {
            ServiceRequest req = (ServiceRequest) obj;
            switch (index)
            {                
                case 0:
                    return req.getRequestNumber();

                case 1:
                    return req.getStartTimeStamp();
                    
                case 2:
                    return req.getService();
                
                case 3:
                    return req.getRequest();

                default:
                    //should not go here
                    String msg = "Unexpected column index passed to " +
                                 "RequestsLabelProvider: "+index;
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
