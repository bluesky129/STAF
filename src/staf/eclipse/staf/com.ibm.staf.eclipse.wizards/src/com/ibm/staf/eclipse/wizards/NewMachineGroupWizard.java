/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
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

import com.ibm.staf.eclipse.editor.Activator;
import com.ibm.staf.eclipse.editor.Util;


/**
 * The new wizard for creating Machine Groups
 */
public class NewMachineGroupWizard extends Wizard implements INewWizard
{
    /* Constants */
    
    /** ID of this plugin */
    public static final String PLUGIN_ID = 
        "com.ibm.staf.eclipse.wizards.NewMachineGroupWizard";
    
    /* Instance Fields */
    
    private NewMachineGroupWizardPage page;
    private ISelection selection;
    

    /**
     * Constructor
     */
    public NewMachineGroupWizard()
    {
        super();
        setNeedsProgressMonitor(true);
    }

    /**
     * Adds the pages to the wizard.
     */
    public void addPages()
    {
        page = new NewMachineGroupWizardPage(selection);
        addPage(page);
    }

    /**
     * This method is called when 'Finish' button is pressed in the wizard. We
     * will create an operation and run it using wizard as execution context.
     */
    public boolean performFinish()
    {
        final String groupName = page.getGroupName();
              
        final String containerName = page.getContainerName();
        
        IRunnableWithProgress op = new IRunnableWithProgress()
        {
            public void run(IProgressMonitor monitor)
                                             throws InvocationTargetException
            {
                try
                {
                    doFinish(containerName, groupName, monitor);
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
     * The worker method. It will find the container and create the machine
     * group (folder) in it.
     */
    private void doFinish(String containerName, String groupName, 
                          IProgressMonitor monitor) throws CoreException
    {        
        // create a sample file

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
        IFolder newContainer = parentContainer.getFolder(new Path(groupName));
        
        newContainer.create(true, true, monitor);
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