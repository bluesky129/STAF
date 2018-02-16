/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.extension.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.ibm.staf.STAFMarshallingContext;
import com.ibm.staf.STAFResult;
import com.ibm.staf.STAFUtil;
import com.ibm.staf.eclipse.editor.STAFEditorExtension;
import com.ibm.staf.eclipse.editor.Util;
import com.ibm.staf.eclipse.model.ISTAFRequest;
import com.ibm.staf.eclipse.model.STAFRequest;

/**
 * Implements the Composite to contain the widgets and functionality
 * for the Services page of the ServiceExtension.
 */
public class ServicePage extends Composite
{
    /* Instance Fields */
    
    private STAFEditorExtension extension;
    
    private Table serviceTable;
    private Table detailsTable;

    
    /**
     * Constructor
     * All widgets intialized here
     * @param extension A reference to the STAFEditorExtension for service
     * @param parent The CTabFolder which will contain the requests page. Used
     * as the parent of this widget.
     * @param style The SWT style bit(s). Passed to super constructor.
     */
    public ServicePage(STAFEditorExtension extension, CTabFolder parent, int style)
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
        
        refreshServiceList();
    }
    
    
    /**
     * Creates the Group widget which contains all wigets for the
     * services table
     * @param parent
     * @return Group
     */
    private Group createTableGroup(Composite parent)
    {
        Group group = new Group(parent, SWT.NONE);
        group.setText("STAF Services");
        
        GridLayout gridLayout = new GridLayout(1, false);
        group.setLayout(gridLayout);
        
        //Create refresh button
        Button refreshButton = new Button(group, SWT.PUSH);
        refreshButton.setImage(ServiceExtension.refreshIcon);
        refreshButton.setToolTipText("Refresh Service List");
        refreshButton.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    refreshServiceList();
                }
            });//end new SelectionAdapter
        
        //Create requests table
        serviceTable = createServiceTable(group);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        serviceTable.setLayoutData(data);
        serviceTable.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    TableItem[] items = serviceTable.getSelection();
                    String serviceName = items[0].getText(0);
                    displayServiceDetails(serviceName);
                }
            });
        
        return group;
    }
    
    
    /**
     * Creates the Group widget to contain all widgets to display
     * service details.
     * @param parent
     * @return Group
     */ 
    private Group createDetailsGroup(Composite parent)
    {
        Group group = new Group(parent, SWT.NONE);
        group.setText("Service Details");
        
        FillLayout layout = new FillLayout();
        layout.marginHeight = 10;
        layout.marginWidth = 10;
        group.setLayout(layout);
        
        detailsTable = createDetailsTable(group);
        
        return group;
    }
    
    
    /**
     * Creates the Table widget to display service details.
     * @param parent
     * @return Table
     */
    private Table createDetailsTable(Composite parent)
    {
        //Create Table Widget
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
     * Creates the Table widget to display the list of services.
     * @param parent
     * @return Table
     */
    private Table createServiceTable(Composite parent)
    {
        //Create Table Widget
        Table table = new Table(parent, SWT.H_SCROLL | SWT.V_SCROLL
                                | SWT.SINGLE | SWT.FULL_SELECTION 
                                | SWT.BORDER);

        TableLayout tableLayout = new TableLayout();
        table.setLayout(tableLayout);

        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        
        //Add Columns
        String[] STD_HEADINGS = { "Name", "Library", 
                                  "Executable" };
        
        tableLayout.addColumnData(new ColumnWeightData(15, 10, true));
        TableColumn tc0 = new TableColumn(table, SWT.NONE);
        tc0.setText(STD_HEADINGS[0]);
        tc0.setAlignment(SWT.LEFT);
        tc0.setResizable(true);
        
        tableLayout.addColumnData(new ColumnWeightData(15, 10, true));
        TableColumn tc1 = new TableColumn(table, SWT.NONE);
        tc1.setText(STD_HEADINGS[1]);
        tc1.setAlignment(SWT.LEFT);
        tc1.setResizable(true);
        
        tableLayout.addColumnData(new ColumnWeightData(70, 10, true));
        TableColumn tc2 = new TableColumn(table, SWT.NONE);
        tc2.setText(STD_HEADINGS[2]);
        tc2.setAlignment(SWT.LEFT);
        tc2.setResizable(true);
        
        return table;
    }
    
    
    /**
     * Refreshes the list of services and updates the service table
     * with the new list. Also clears details table.
     */
    public void refreshServiceList()
    {           
        String request = "LIST SERVICES";
        
        ISTAFRequest req = new STAFRequest(extension.getServiceName(), 
                                           request);
        //add a property listener to the single request
        req.addPropertyChangeListener(new IPropertyChangeListener()
            {
                public void propertyChange(final PropertyChangeEvent event)
                {
                    //Do nothing if widget has been disposed
                    if (serviceTable.isDisposed())
                    {
                        return;
                    }
                    
                    serviceTable.getDisplay().asyncExec(new Runnable()
                        {
                            public void run()
                            {     
                                ISTAFRequest req = (ISTAFRequest) 
                                                      event.getNewValue();
                                
                                //handle errors
                                if (req.getRC() != STAFResult.Ok)
                                {
                                    String msg = "Error listing STAF services. "+
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
                                
                                //Populate table
                                List servicesList = 
                                    (List) (STAFMarshallingContext.unmarshall(
                                           req.getResult()).getRootObject());
                                
                                populateServiceList(servicesList);
                            }
                        });
                }
            });
        
        extension.submitCommand(req);
    }
    
    
    /**
     * Populates the serviceTable with
     * all the services contained in the unmarshalled List of
     * STAF/Service/Service/ServiceInfo Map Classes. 
     * This method is synchronized to prevent 2 different
     * refresh threads from stepping on each other.
     * @param newServices List of STAF/Service/Service/ServiceInfo 
     * Map Classes
     */
    private synchronized void populateServiceList(List newServices)
    {
        //Clear service and details table
        serviceTable.clearAll();
        serviceTable.setItemCount(0);
        detailsTable.clearAll();
        detailsTable.setItemCount(0);
        
        //Add all services to table
        Iterator servicesIt = newServices.iterator();
        while (servicesIt.hasNext())
        {
            Map serviceMap = (Map) servicesIt.next();

            addServiceToTable(serviceMap);
        }
    }
    
    
    /**
     * Adds a service to the services table widget (List).
     * @param requestMap The unmarshalled STAF/Service/Service/ServiceInfo
     * Map 
     */
    private void addServiceToTable(Map serviceMap)
    {
        String name = (String) serviceMap.get("name");
        String lib = (String) serviceMap.get("library");
        String exec = (String) serviceMap.get("executable");
        if (exec == null)
        {
            exec = "<None>";
        }
 
        TableItem item = new TableItem(serviceTable, SWT.NONE);
 
        item.setText(0, name);
        item.setText(1, lib);
        item.setText(2, exec);
    }
    
    
    /**
     * Retrieves and displays service details in the detailsTable 
     * of the given service name
     * @param service name of service
     */
    private void displayServiceDetails(String service)
    {
        String request = "QUERY SERVICE "+STAFUtil.wrapData(service);
        
        ISTAFRequest req = new STAFRequest(extension.getServiceName(), 
                                           request);
        //add a property listener to the single request
        req.addPropertyChangeListener(new IPropertyChangeListener()
            {
                public void propertyChange(final PropertyChangeEvent event)
                {
                    detailsTable.getDisplay().asyncExec(new Runnable()
                        {
                            public void run()
                            {   
                                ISTAFRequest req = 
                                    (ISTAFRequest) event.getNewValue();
                                
                                if (req.getRC() != STAFResult.Ok)
                                {
                                    String msg = "Error querying STAF service. "+
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
                                
                                //update details text with request details
                                STAFMarshallingContext mc = 
                                    STAFMarshallingContext.unmarshall(req.getResult());
                                Map resultMap = (Map) mc.getRootObject();

                                updateDetailsTable(resultMap);
                            }
                        });
                }
            });
        
        extension.submitCommand(req);
    }
    
    
    /**
     * Updates the detailsTable with the service details
     * @param details The unmarshalled STAF/Service/Service/QueryService
     * Map
     */
    private void updateDetailsTable(Map details)
    {        
        detailsTable.clearAll();
        detailsTable.setItemCount(0);
        
        String name = (String) details.get("name");
        TableItem item = new TableItem(detailsTable, SWT.NONE);
        item.setText(0, "Name");
        item.setText(1, name);

        String lib = (String) details.get("library");
        item = new TableItem(detailsTable, SWT.NONE);
        item.setText(0, "Library");
        item.setText(1, lib);

        String exec = (String) details.get("executable");
        exec = exec == null ? "<None>" : exec;
        item = new TableItem(detailsTable, SWT.NONE);
        item.setText(0, "Executable");
        item.setText(1, exec);

        String parms = (String) details.get("parameters");
        parms = parms == null ? "<None>" : parms;
        item = new TableItem(detailsTable, SWT.NONE);
        item.setText(0, "Parameters");
        item.setText(1, parms);
        
        List options = (List) details.get("options");
        Iterator optionsIt = options.iterator();
        int i=1;
        while (optionsIt.hasNext())
        {
            item = new TableItem(detailsTable, SWT.NONE);
            item.setText(0, "Option #"+i++);
            item.setText(1, (String) optionsIt.next());
        }
    }
}