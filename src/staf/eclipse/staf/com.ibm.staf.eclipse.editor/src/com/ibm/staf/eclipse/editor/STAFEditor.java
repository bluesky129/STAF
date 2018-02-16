/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.editor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.ibm.staf.STAFException;
import com.ibm.staf.STAFHandle;
import com.ibm.staf.STAFMarshallingContext;
import com.ibm.staf.STAFResult;
import com.ibm.staf.eclipse.model.IExtensionMapping;
import com.ibm.staf.eclipse.model.ISTAFMachine;
import com.ibm.staf.eclipse.model.ISTAFRequest;
import com.ibm.staf.eclipse.model.STAFMachine;
import com.ibm.staf.eclipse.model.STAFRequest;

/**
 * The main STAFEditor extension. This class provides the STAF Editor primary
 * page tabs, provides the extension point for STAF Editor, and handles any
 * implemented extensions.
 */
public class STAFEditor extends MultiPageEditorPart 
                        implements IPropertyChangeListener         
{
    /* Constants */
    
    /** ID of this plugin */
    public static final String PLUGIN_ID = 
                               "com.ibm.staf.eclipse.editor.STAFEditor";
    /** ID for extension point provided by the STAF Editor */
    public static final String EXTENSION_POINT_ID = 
                               "com.ibm.staf.eclipse.editor.editorTab";
    /** Element name for the configuration element for extensions */
    public static final String EXTENSION_TAB_ELEM = "tab";
    /** Attribute name for the implementing class of the configuration 
     * element for extensions */
    public static final String CLASS_ATT = "class";
    /** Attribute name for the matching service names of the configuration
     * element for extensions */
    private static final String SERVICE_NAMES_ATT = "serviceNames";
    /** Attribute name for the optional tab title of the configuration
     * element for extensions */
    private static final String TITLE_ATT = "title";
    /** Attribute name for the alwaysShow attribute (boolean) of the 
     * configuration element for extensions */
    private static final String ALWAYS_SHOW_ATT = "alwaysShow";
    
    
    /* Instance fields */
    
    private ISTAFMachine machine;
    private STAFHandle sHandle;
    
    private boolean dirtyFlag = false;
    
    private MainEditorPage mainEditor;
    private PropertyPage propertyPage;
    
    
    /**
     * Empty Argument Constructor
     */
    public STAFEditor()
    {
        super();
    }

    
    /**
     * Called by eclipse to peform a save for the edited content
     * @see org.eclipse.ui.part.EditorPart#doSave(IProgressMonitor)
     */
    public void doSave(IProgressMonitor monitor)
    {        
        IFile file;
        if (getEditorInput() instanceof IFileEditorInput)
        {
           file = ((IFileEditorInput) getEditorInput()).getFile();
        }
        else
        {
            String msg = "Non IFileEditorInput returned by "+
                         "getEditorInput(). Returned: "+
                         getEditorInput().toString();
            MultiStatus err = Util.getServiceInfo(Activator.getDefault().getBundle(), 
                                                  IStatus.ERROR, msg, null);
            Activator.getDefault().getLog().log(err);
            return;
        }
                
        //convert machine info to XML using Java XML
        String out;
        try
        {
            Document doc = machine.toXML();

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult transformRes = 
                new StreamResult(new ByteArrayOutputStream());

            transformer.transform(source, transformRes);
            ByteArrayOutputStream byteSteam = 
                (ByteArrayOutputStream) transformRes.getOutputStream();
            out = byteSteam.toString();
        }
        catch (Exception e)
        {
            String msg = "Error saving to XML. "+e.toString();
            MultiStatus err = Util.getServiceInfo(Activator.getDefault().getBundle(), 
                              IStatus.ERROR, msg, e);
            Activator.getDefault().getLog().log(err);
            return;
        }
        
        //save contents using eclipse framework
        try
        {
            file.setContents(new ByteArrayInputStream(out.getBytes("UTF-8")), 
                             IResource.KEEP_HISTORY, null);
        }
        catch (CoreException ce)
        {
            MultiStatus err = Util.getServiceInfo(
                                   Activator.getDefault().getBundle(), ce); 
            Activator.getDefault().getLog().log(err);
            return;
        }
        catch (UnsupportedEncodingException e)
        {
            MultiStatus err = Util.getServiceInfo(
                              Activator.getDefault().getBundle(), IStatus.ERROR,
                              e.getLocalizedMessage(), e); 
            Activator.getDefault().getLog().log(err);
            return;
        }
        
        //clear the dirty flag & fire property change
        dirtyFlag = false;
        firePropertyChange(PROP_DIRTY);
    }

    
    /**
     * Called by eclipse to peform a saveAs for the edited content
     * @see org.eclipse.ui.part.EditorPart#doSaveAs()
     * 
     * SaveAs is not currently implemented for STAFEditor
     */
    public void doSaveAs()
    {
        // SaveAs not supported
    }
    
    
    /**
     * Called by eclipse to dispose of the editor
     * @see org.eclipse.ui.part.MultiPageEditorPart#dispose()
     */
    public void dispose()
    {
        mainEditor.dispose();
        propertyPage.dispose();
        
        try
        {
            sHandle.unRegister();
        }
        catch (STAFException se)
        {
            String msg = "Error occurred unregistering STAFHandle. Info: "+
                         se.toString();
            MultiStatus err = Util.getServiceInfo(
                              Activator.getDefault().getBundle(), 
                              IStatus.ERROR, msg, se); 
            Activator.getDefault().getLog().log(err);
        }
        
        super.dispose();
    }

    
    /**
     * Called by eclipse to initialize the editor
     * @see org.eclipse.ui.part.MultiPageEditorPart#init(IEditorSite, IEditorInput)
     */
    public void init(IEditorSite site, IEditorInput input)
                                       throws PartInitException
    {
        if (input instanceof IStorageEditorInput)
        {
            try
            {                
                //code to create machine object from xml and add this as 
                //propertyChangeListener
                
                IStorage storage = ((IStorageEditorInput) input).getStorage();
                machine = createMachineFromStream(storage.getContents());
                machine.addPropertyChangeListener(this);
                
                //perform some basic setup
                setSite(site);
                setInput(input);

                String filename = input.getName();
                String shortFilename = filename.substring(0, 
                                       filename.lastIndexOf(
                                                ISTAFMachine.FILENAME_EXT));
                setPartName(shortFilename);
                
                //register a STAFHandle
                sHandle = new STAFHandle("Eclipse/"+shortFilename);
            }
            catch (STAFException se)
            {
                String msg = "Could not register a STAF handle. Verify that " +
                             "STAF is running on the local system.";
                MultiStatus err = Util.getServiceInfo(
                                       Activator.getDefault().getBundle(), 
                                       IStatus.ERROR, msg, null);
                Activator.getDefault().getLog().log(err);
                throw new PartInitException(msg);
            }
            catch (Exception e)
            {
                String msg = "Error occurred initializing STAFEditor. Info: "+
                             e.toString();
                MultiStatus err = Util.getServiceInfo(
                                       Activator.getDefault().getBundle(), 
                                       IStatus.ERROR, msg, e); 
                Activator.getDefault().getLog().log(err);
                throw new PartInitException(msg);
            }
        }
        else
        {
            String msg = "Invalid input to Editor: "+input.getClass().getName();
            MultiStatus err = Util.getServiceInfo(
                                   Activator.getDefault().getBundle(), 
                                   IStatus.ERROR, msg, null); 
            Activator.getDefault().getLog().log(err);
            throw new PartInitException(msg);
        }
     
    }

    
    /**
     * Returns whether the dirtyFlag has been set for this editor. The dirty
     * flag indicates that a save is needed.
     * @see org.eclipse.ui.part.MultiPageEditorPart#isDirty()
     */
    public boolean isDirty()
    {
        return dirtyFlag;
    }

    
    /**
     * Indicates whether the editor allows saveAs.
     * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
     */
    public boolean isSaveAsAllowed()
    {
        //not allowed
        return false;
    }

    
    /**
     * Adds a request to the machine. This will update the model
     * and changes will be shown in the Main Editor's Command History.
     * @param req The ISTAFRequest to add
     */
    public void addRequest(ISTAFRequest req)
    {
        machine.addRequest(req);
    }
    
    
    /**
     * Submits a STAF command on a new thread. Callers should register
     * as PropertyChangeListeners with the request to receive notification
     * when the request completes.
     * @see ISTAFRequest#addPropertyChangeListener(org.eclipse.jface.util.IPropertyChangeListener)
     * @param req
     */
    public void submitCommand(final ISTAFRequest req)
    {
        try
        {
            new Thread(
                new Runnable() 
                    {
                        public void run()
                        {                            
                            STAFResult result = sHandle.submit2(
                                                machine.getEndpoint(),
                                                req.getService(),
                                                req.getCommand());
                            
                            req.setResult(result.rc, 
                                          result.result);
                        }       
                    }).start();
        }
        catch (Throwable t)
        {
            String msg = "Unexpected error during STAF command submission.";
            MultiStatus err = Util.getServiceInfo(
                                   Activator.getDefault().getBundle(), 
                                   IStatus.ERROR, msg, t);
            Activator.getDefault().getLog().log(err);
        }
    }
    
    
    /**
     * Creates an ISTAFMachine object from an InputStream of the XML
     * representation.
     * @param stream The InputStream to parse
     * @return ISTAFMachine represented by the XML
     * @throws XMLException If errors occur loading or parsing the XML
     */
    private ISTAFMachine createMachineFromStream(InputStream stream) 
                                                  throws XMLException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        
        try 
        {           
            DocumentBuilder builder = factory.newDocumentBuilder();                             
            doc = builder.parse(stream);         
        }
        catch (SAXParseException spe) 
        {         
            // Error generated by the parser
            String msg = "Parse Error, uri: "+spe.getSystemId()+" line: "+
                       spe.getLineNumber()+", column: "+spe.getColumnNumber();             
            // Use the contained exception, if any
            Exception  x = spe;     
            if (spe.getException() != null) 
            {               
                x = spe.getException();
            }           
            throw new XMLException(msg, x);
        } 
        catch (SAXException sxe) 
        {          
            // Error generated during parsing
            Exception  x = sxe;
            if (sxe.getException() != null) 
            {
                x = sxe.getException();
            }
            throw new XMLException("SAX Exception loading XML from stream", x);
        }
        catch (ParserConfigurationException pce) 
        {          
            // Parser with specified options can't be built
            throw new XMLException(
                  "ParserConfigurationException loading XML from stream", pce);
        }
        catch(FileNotFoundException fnfe) 
        {     
            throw new XMLException(
                      "FileNotFoundException loading XML from stream", fnfe);
        }
        catch(IOException ioe) 
        {        
            throw new XMLException("I/O Error loading XML from stream", ioe);
        }
        catch(Throwable t) 
        {            
            throw new XMLException(
                      "Unexpected Exception loading XML from stream", t);
        }
        
        ISTAFMachine machine = STAFMachine.parseXML(doc);
      
        return machine;
    }

    
    /**
     * Creates the Editor Pages (tabs)
     */
    protected void createPages()
    {
        createMainEditorPage();
        createPropertyPage();
        
        refreshServicesList();
    }

    
    /**
     * Creates the Properties page
     */
    private void createPropertyPage()
    {        
        propertyPage = new PropertyPage(this, getContainer());
        
        int index = addPage(propertyPage);
        setPageText(index, propertyPage.getTabName());
    }
    
    
    /**
     * Creates the Main STAF Editor page
     *
     */
    private void createMainEditorPage()
    {
        mainEditor = new MainEditorPage(this, getContainer());
        
        int index = addPage(mainEditor);
        setPageText(index, mainEditor.getTabName());    
    }

    
    /**
     * Handles loading any extensions to STAF Editor extension
     * point com.ibm.staf.eclipse.editor.editorTab
     */
    private void processExtensions() 
    {
        //remove all dynamic pages

        while (getPageCount() > 2)
        {
            removePage(2);
        }
        
        //processing causes slight delay in population of tabs
        //should be ok, because rest of UI is NOT blocked
        IExtensionPoint point = Platform.getExtensionRegistry().
                                getExtensionPoint(EXTENSION_POINT_ID);

        if (point != null) 
        {
            processRegisteredExtensions(point);
            
            processUserExtensionMappings(point);
        }
        
        
    }
    
    
    /**
     * Process all registered extension and adds any which
     * declared currently running services.
     * @param point IExtensionPoint declared by STAFEditor
     */
    private void processRegisteredExtensions(IExtensionPoint point)
    {
        processAlwaysShowExtensions(point);
        
        processStandardExtensions(point);
    }
    
    
    /**
     * Process all registered extension marked as "alwaysShow".
     * @param point IExtensionPoint declared by STAFEditor
     */
    private void processAlwaysShowExtensions(IExtensionPoint point)
    {
        IExtension[] extensions = point.getExtensions();

        //iterate over defined extensions
        for (int extensionIndex = 0; extensionIndex < extensions.length; 
             extensionIndex++) 
        {
            IConfigurationElement[] ces = extensions[extensionIndex].
                                              getConfigurationElements();

            //iterate over the ConfiguarationElements of each extension
            for (int cesIndex = 0; cesIndex < ces.length; cesIndex++) 
            {
                //parse the EXTENSION_TAB_ELEM
                if (ces[cesIndex].getName().equals(EXTENSION_TAB_ELEM))
                {
                    try
                    {
                        String alwaysShow =
                            ces[cesIndex].getAttribute(ALWAYS_SHOW_ATT); 
                        if (alwaysShow == null || !alwaysShow.equals("true"))
                        {
                            continue;
                        }
                        
                        //Create extension (tab)
                        STAFEditorExtension newExt = 
                            (STAFEditorExtension) ces[cesIndex].
                                createExecutableExtension(CLASS_ATT);
                        newExt.setEditor(this);
                        //the service name cannot be set for "alwaysShow"
                        //tabs because there is not necessarily a service
                        //associated. tabs designed as "alwaysShow" will have
                        //to determine the service name(s) they interact with
                        
                        //Use title for tab name
                        String title = 
                               ces[cesIndex].getAttribute(TITLE_ATT);
                        title = title == null ? "" : title;

                        Composite page = newExt.createPage(getContainer());

                        int index = addPage(page);
                        setPageText(index, title);   
                    }
                    catch (CoreException ce)
                    {
                        MultiStatus err = Util.getServiceInfo(
                                          Activator.getDefault().getBundle(), 
                                          ce); 
                        Activator.getDefault().getLog().log(err);
                    }
                }
            }
        }
    }
    
    
    /**
     * Process all registered extension and adds any which
     * declared currently running services. Skips any
     * marked as "alwaysShow".
     * @param point IExtensionPoint declared by STAFEditor
     */
    private void processStandardExtensions(IExtensionPoint point)
    {
        IExtension[] extensions = point.getExtensions();

        //iterate over defined extensions
        for (int extensionIndex = 0; extensionIndex < extensions.length; 
             extensionIndex++) 
        {
            IConfigurationElement[] ces = extensions[extensionIndex].
                                              getConfigurationElements();

            //iterate over the ConfiguarationElements of each extension
            for (int cesIndex = 0; cesIndex < ces.length; cesIndex++) 
            {
                //parse the EXTENSION_TAB_ELEM
                if (ces[cesIndex].getName().equals(EXTENSION_TAB_ELEM))
                {
                    try
                    {
                        String alwaysShow =
                            ces[cesIndex].getAttribute(ALWAYS_SHOW_ATT); 
                        if (alwaysShow != null && alwaysShow.equals("true"))
                        {
                            continue;
                        }
                        
                        String serviceRegEx = ces[cesIndex].getAttribute(
                                                            SERVICE_NAMES_ATT);
                        Pattern pattern = Pattern.compile(serviceRegEx, 
                                                          Pattern.CASE_INSENSITIVE);
                        
                        //Create extension (tab) if it matches a registered service
                        Iterator servicesIt = machine.getServices().iterator();
                        while (servicesIt.hasNext())
                        {
                            String service = (String) servicesIt.next();
                            Matcher m = pattern.matcher(service);
                            if(m.matches())
                            {
                                //Create extension (tab)
                                STAFEditorExtension newExt = 
                                    (STAFEditorExtension) ces[cesIndex].
                                        createExecutableExtension(CLASS_ATT);
                                newExt.setEditor(this);
                                newExt.setServiceName(service);
                                
                                //If title specified use for tab name, 
                                //otherwise use service name
                                String title = 
                                       ces[cesIndex].getAttribute(TITLE_ATT);
                                title = title != null ? title : service; 

                                Composite page = newExt.createPage(getContainer());

                                int index = addPage(page);
                                setPageText(index, title);
                            }
                        }
                    }
                    catch (CoreException ce)
                    {
                        MultiStatus err = Util.getServiceInfo(
                                          Activator.getDefault().getBundle(), 
                                          ce); 
                        Activator.getDefault().getLog().log(err);
                    }
                }
            }
        }
    }
    
    
    /**
     * Process all user defined registration mappings and adds any which
     * match currently running services and currently registered extensions
     * @param point IExtensionPoint declared by STAFEditor
     */
    private void processUserExtensionMappings(IExtensionPoint point)
    {
        //Iterate over user defined mappings
        IExtensionMapping[] mappings = machine.getExtensionMappings();
        for (int mappingIndex=0; mappingIndex < mappings.length; mappingIndex++)
        {
            String service = mappings[mappingIndex].getService();
            
            //skip mapping if service not found
            List services = machine.getServices();
            
            //forced to upper case earlier when storing in list. 
            //use uppercase for comparison now
            if (!(services.contains(service.toUpperCase())))
            {
                continue;
            }
            
            //Service found now try to find matching extension
            
            String extId = mappings[mappingIndex].getExtension();
            
            IExtension[] extension = point.getExtensions();
            for (int extIndex=0; extIndex < extension.length; extIndex++)
            {
                IExtension ext = extension[extIndex];
                IConfigurationElement[] ces = ext.getConfigurationElements();
                
                //Find proper ConfigurationElement
                for (int cesIndex=0; cesIndex < ces.length; cesIndex++)
                {
                    if (ces[cesIndex].getName().equals(EXTENSION_TAB_ELEM))
                    {
                        try
                        {
                            if (ces[cesIndex].getAttribute(CLASS_ATT).equals(extId))
                            {

                                STAFEditorExtension newExt = 
                                    (STAFEditorExtension) ces[cesIndex].
                                    createExecutableExtension(CLASS_ATT);

                                newExt.setEditor(this);
                                newExt.setServiceName(service);

                                Composite page = newExt.createPage(getContainer());

                                String title = mappings[mappingIndex].getTitle();
                                title = title == null ? service : title;
                                int index = addPage(page);
                                setPageText(index, title);
                            }
                        }
                        catch (CoreException ce)
                        {
                            MultiStatus err = Util.getServiceInfo(
                                    Activator.getDefault().getBundle(), 
                                    ce); 
                            Activator.getDefault().getLog().log(err);
                        }
                    }
                }//end configuration element iteration
            }//end extension iteration
        }//end mapping iteration
    }
    
    
    /**
     * Returns the hostname of the machine for this editor
     * @return hostname
     */
    public String getHostname()
    {
        return machine.getHostname();
    }
    
    
    /**
     * Returns the port of the machine for this editor
     * @return port
     */
    public String getPort()
    {
        return machine.getPort();
    }
    
    
    /**
     * Returns the interface of the machine for this editor
     * @return stafInterface
     */
    public String getInterface()
    {
        return machine.getInterface();
    }
    
    
    /**
     * Returns the endpoint (host@port) of the machine for this editor
     * @return hostname
     */
    public String getEndpoint()
    {
        return machine.getEndpoint();
    }


    /**
     * Returns a Map of user defined service to extension mappings
     * for the machine of this editor. key=service name, value=main 
     * extension class (implementing ISTAFEditorExtension)
     * This is a clone of the Map and modifications will not be reflected
     * in the model
     * @return Map of service/extension associations
     */
    public IExtensionMapping[] getMachineExtensionMappings()
    {
        return machine.getExtensionMappings();
    }
    
    
    /**
     * Returns the ISTAFMachine for this editor. 
     * @return ISTAFMachine
     */
    public ISTAFMachine getMachine()
    {
        return machine;
    }
    
    
    /**
     * Sets the dirty flag indicating that a save is needed and
     * fires a property change
     */
    public void setDirtyFlag()
    {
        dirtyFlag = true;
        firePropertyChange(PROP_DIRTY);
    }
    
        
    /**
     * Performs a SERVICE LIST SERVICES command on an async thread
     * and sets the returned list on the machine. This will in turn
     * notify any propertyChangeListeners on the machine.
     */
    public void refreshServicesList()
    {
        //Disable refresh button until refresh complete
        mainEditor.refreshButton.setEnabled(false);
        
        ISTAFRequest req = new STAFRequest("SERVICE", "LIST SERVICES");
        //add a property listener to the single request
        req.addPropertyChangeListener(new IPropertyChangeListener()
            {
                public void propertyChange(PropertyChangeEvent event)
                {
                    ISTAFRequest newReq = (ISTAFRequest) event.getNewValue();
                    
                    //handle errors
                    if (newReq.getRC() != STAFResult.Ok)
                    {
                        String msg = "Error refreshing services list. " +
                            "Endpoint: '"+getEndpoint()+"' Cmd: '"+
                            newReq.getService()+" "+newReq.getCommand()+
                            "' RC: '"+newReq.getRC()+"' Result: '"+
                            newReq.getResult();
                        MultiStatus err = Util.getServiceInfo(
                                               Activator.getDefault().getBundle(), 
                                               IStatus.ERROR, msg, null); 
                        Activator.getDefault().getLog().log(err);

                        //Re-enable refresh button after error
                        getContainer().getDisplay().asyncExec(new Runnable()
                        {
                            public void run()
                            {
                                mainEditor.refreshButton.setEnabled(true);
                            }
                        });
                        
                        return;
                    }
                    
                    //update list
                    
                    List servicesList = (List) STAFMarshallingContext.unmarshall(
                                        newReq.getResult()).getRootObject();
                    List serviceNameList = new ArrayList(servicesList.size());

                    Iterator servicesIt = servicesList.iterator();
                    while (servicesIt.hasNext())
                    {
                        Map service = (Map) servicesIt.next();
                        //force to upper case for later comparison
                        serviceNameList.add(service.get("name")
                                            .toString().toUpperCase());
                    }

                    machine.setServices(serviceNameList);
                }
            });
        
        submitCommand(req);
    }


    /**
     * Handles property changes generated by the model for updates
     * to the servicesList. This will cause all dynamic pages to be
     * removed and dynamic page processing will be performed to populate
     * dynamice pages.
     * @see IPropertyChangeListener#propertyChange(PropertyChangeEvent)
     */
    public void propertyChange(final PropertyChangeEvent event)
    {
        getContainer().getDisplay().asyncExec(new Runnable()
        {
            public void run()
            {
                //set dirty flag for all persistent model changes
                if (!(event.getProperty().equals(ISTAFMachine.SERVICES_UPDATE)))
                {
                    setDirtyFlag();
                }
                    
                if (event.getProperty().equals(ISTAFMachine.SERVICES_UPDATE) ||
                    event.getProperty().equals(ISTAFMachine.EXT_MAPPING_UPDATE))
                {
                    //process extensions and user mappings
                    processExtensions();
                    //Re-enable refresh button
                    mainEditor.refreshButton.setEnabled(true);
                }
            }
        });
    }
}