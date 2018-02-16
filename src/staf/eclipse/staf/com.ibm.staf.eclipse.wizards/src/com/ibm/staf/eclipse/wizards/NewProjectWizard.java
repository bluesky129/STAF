/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

import com.ibm.staf.eclipse.editor.Activator;
import com.ibm.staf.eclipse.editor.Util;

/**
 * The new wizard for creating STAF Projects
 */
public class NewProjectWizard extends Wizard implements INewWizard
{
    /* Constants */
    
    /** ID of this plugin */
    public static final String PLUGIN_ID = 
        "com.ibm.staf.eclipse.wizards.NewProjectWizard";
    /** Comment String to identifier STAF Projects */
    public final static String STAF_PROJECT = "STAF Project";
    
    /* Instance Fields */
    
    // Reuse of the new project page provided by the platform UI dialogs
    private WizardNewProjectCreationPage mainPage;
    // cache of newly-created project
    private IProject newProject;

    /**
     * Add the one page to the wizard, the reused page
     * <code>WizardNewProjectCreationPage</code>. This page provides basic
     * project name validation.
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
    public void addPages()
    {
        mainPage = new WizardNewProjectCreationPage("New STAF Project");
        mainPage.setDescription("Create a STAF new project.");
        mainPage.setTitle("New STAF Project");
        addPage(mainPage);
    }
    

    /**
     * Returns the newly created project.
     * @return the created project, or <code>null</code> if project not
     *         created
     */
    public IProject getNewProject()
    {
        return newProject;
    }

    /**
     * Initializes this creation wizard using the passed workbench and object
     * selection.
     * <p>
     * This method is called after the no argument constructor and before other
     * methods are called.
     * </p>
     * 
     * @param workbench
     *            the current workbench
     * @param selection
     *            the current object selection
     * 
     * @see org.eclipse.ui.IWorkbenchWizard#init(IWorkbench,
     *      IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection)
    {

    }

    /**
     * Creates a project
     * 
     * @return <code>true</code> to indicate the finish request was accepted,
     *         and <code>false</code> to indicate that the finish request was
     *         refused
     * 
     * @see org.eclipse.jface.wizard.IWizard#performFinish()
     */
    public boolean performFinish()
    {
        createNewProject();
        return true;
    }
    

    /**
     * Creates a new project resource with the name selected in the wizard page.
     * Project creation is wrapped in a <code>WorkspaceModifyOperation</code>.
     * <p>
     * @see org.eclipse.ui.actions.WorkspaceModifyOperation
     * @return the created project resource, or <code>null</code> if the
     *         project was not created
     */
    public IProject createNewProject()
    {
        if (newProject != null)
        {
            return newProject;
        }

        // get a project handle
        final IProject newProjectHandle = mainPage.getProjectHandle();

        // get a project description
        IPath defaultPath = Platform.getLocation();
        IPath newPath = mainPage.getLocationPath();
        if (defaultPath.equals(newPath))
        {
            newPath = null;
        }
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IProjectDescription description = workspace
                .newProjectDescription(newProjectHandle.getName());
        description.setLocation(newPath);
        description.setComment(STAF_PROJECT);

        // create the new project operation
        WorkspaceModifyOperation op = new WorkspaceModifyOperation()
        {
            protected void execute(IProgressMonitor monitor)
                    throws CoreException
            {
                createProject(description, newProjectHandle, monitor);
            }
        };

        // run the new project creation operation
        try
        {
            getContainer().run(false, true, op);
        }
        catch (InterruptedException e)
        {
            MultiStatus err = Util.getServiceInfo(
                                   Activator.getDefault().getBundle(), 
                                   IStatus.ERROR, 
                                   e.getLocalizedMessage(), e);
            Activator.getDefault().getLog().log(err);
            return null;
        }
        catch (InvocationTargetException e)
        {
            Throwable cause = e.getCause();
            MultiStatus err = Util.getServiceInfo(
                                   Activator.getDefault().getBundle(), 
                                   IStatus.ERROR, 
                                   cause.getLocalizedMessage(), cause);
            Activator.getDefault().getLog().log(err);
            return null;
        }

        newProject = newProjectHandle;

        return newProject;
    }

    
    /**
     * Creates a project resource given the project handle and description.
     * 
     * @param description
     *            the project description to create a project resource for
     * @param projectHandle
     *            the project handle to create a project resource for
     * @param monitor
     *            the progress monitor to show visual progress with
     * 
     * @exception CoreException
     *                if the operation fails
     * @exception OperationCanceledException
     *                if the operation is canceled
     */
    public void createProject(IProjectDescription description,
                              IProject projectHandle, IProgressMonitor monitor)
                              throws CoreException, OperationCanceledException
    {
        try
        {
            monitor.beginTask("", 2000);

            projectHandle.create(description, new SubProgressMonitor(monitor,
                    1000));

            if (monitor.isCanceled())
                throw new OperationCanceledException();

            projectHandle.open(new SubProgressMonitor(monitor, 1000));

        }
        finally
        {
            monitor.done();
        }
    }
}