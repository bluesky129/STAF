/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.wizards;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.w3c.dom.Document;

import com.ibm.staf.eclipse.editor.Activator;
import com.ibm.staf.eclipse.editor.Util;
import com.ibm.staf.eclipse.model.ISTAFMachine;
import com.ibm.staf.eclipse.model.STAFMachine;


/**
 * The new wizard for creating Machine Groups
 */
public class NewMachineWizard extends Wizard implements INewWizard
{
    /* Constants */
    
    /** ID of this plugin */
    public final static String PLUGIN_ID = 
        "com.ibm.staf.eclipse.wizards.NewMachineWizard";
    
    /* Instance Fields */
    
    private NewMachineWizardPage page;
    private ISelection selection;

    
    /**
     * Constructor
     */
    public NewMachineWizard()
    {
        super();
        setNeedsProgressMonitor(true);
    }

    
    /**
     * Adds the pages to the wizard.
     */
    public void addPages()
    {
        page = new NewMachineWizardPage(selection);
        addPage(page);
    }

    /**
     * This method is called when 'Finish' button is pressed in the wizard. We
     * will create an operation and run it using wizard as execution context.
     */
    public boolean performFinish()
    {
        final String machineName = page.getHostname();
              
        final String containerName = page.getContainerName();
        
        final String port = page.getPort();
        
        final String stafInterface = page.getInterface();
        
        final String filename = page.getFilename();
        
        IRunnableWithProgress op = new IRunnableWithProgress()
        {
            public void run(IProgressMonitor monitor)
                    throws InvocationTargetException
            {
                try
                {
                    doFinish(containerName, machineName, port, stafInterface,
                             filename, monitor);
                }
                catch (CoreException e)
                {
                    throw new InvocationTargetException(e);
                }
                finally
                {
                    monitor.done();
                }
            }
        };
        
        try
        {
            getContainer().run(true, false, op);
        }
        catch (InterruptedException e)
        {
            MultiStatus err = Util.getServiceInfo(
                                   Activator.getDefault().getBundle(), 
                                   IStatus.ERROR, 
                                   e.getLocalizedMessage(), e);
            Activator.getDefault().getLog().log(err);
            return false;
        }
        catch (InvocationTargetException e)
        {
            Throwable cause = e.getCause();
            MultiStatus err = Util.getServiceInfo(
                                   Activator.getDefault().getBundle(), 
                                   IStatus.ERROR, 
                                   cause.getLocalizedMessage(), cause);
            Activator.getDefault().getLog().log(err);
            return false;
        }
        
        return true;
    }

    
    /**
     * The worker method. It will find the container and create the file.
     */
    private void doFinish(String containerName, String machineName, 
                          String port, String stafInterface, String filename, 
                          IProgressMonitor monitor) 
                                               throws CoreException
    {        
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IResource parentResource = root.findMember(new Path(containerName));
        if (!parentResource.exists() || !(parentResource instanceof IContainer))
        {
            String msg = "Container \""+containerName+"\" does not exist.";
            MultiStatus err = Util.getServiceInfo(
                                   Activator.getDefault().getBundle(), 
                                   IStatus.ERROR, msg, null);
            Activator.getDefault().getLog().log(err);
            return;
        }
        
        IContainer parentContainer = (IContainer) parentResource;
        final IFile file = parentContainer.getFile(new Path(filename));
        
        if (file.exists())
        {
            String msg = "File \""+file+"\" already exists.";
            MultiStatus err = Util.getServiceInfo(
                                   Activator.getDefault().getBundle(), 
                                   IStatus.ERROR, msg, null);
            Activator.getDefault().getLog().log(err);
            return;
        }
        
        //Set port/interface to null if empty string
        port = port.equals("") ? null : port;
        stafInterface = stafInterface.equals("") ? null : stafInterface;
        
        try 
        {
            InputStream stream = openContentStream(machineName, port, 
                                                   stafInterface);

            file.create(stream, true, monitor);
            
            stream.close();
        } 
        catch (IOException e) {
            MultiStatus err = Util.getServiceInfo(
                                   Activator.getDefault().getBundle(), 
                                   IStatus.ERROR, e.getLocalizedMessage(), e);
            Activator.getDefault().getLog().log(err);
            return;
        }
    }

    
    /**
     * Initiliaze the file with appropriate XML information.
     */
    private InputStream openContentStream(String machine, String port, 
                                          String stafInterface) 
                                                          throws IOException
    {
        String out;
        try
        {
            ISTAFMachine mach = new STAFMachine(machine, port, stafInterface);
            Document doc = mach.toXML();

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
            throw new IOException(msg);
        }
        
        return new ByteArrayInputStream(out.getBytes());
    }
    

    /**
     * Saves the selection for use in the wizard pages
     * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection)
    {
        this.selection = selection;
    }
}