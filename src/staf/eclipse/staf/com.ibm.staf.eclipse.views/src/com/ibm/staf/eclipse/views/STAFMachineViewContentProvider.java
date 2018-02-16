/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Control;

import com.ibm.staf.eclipse.editor.Activator;
import com.ibm.staf.eclipse.editor.Util;
import com.ibm.staf.eclipse.model.ISTAFMachine;
import com.ibm.staf.eclipse.wizards.NewProjectWizard;

/**
 * The IStructuredContentProvider for MachineView
 */
public class STAFMachineViewContentProvider implements
                              IStructuredContentProvider, ITreeContentProvider, 
                              IResourceChangeListener
{
    private TreeViewer viewer;


    /**
     * Returns all projects of type NewProjectWizard.STAF_PROJECT
     * @see IStructuredContentProvider#getElements(Object)
     */
    public Object[] getElements(Object inputElement)
    {       
        IWorkspaceRoot root = ((IWorkspace) inputElement).getRoot();
        IProject[] projects = root.getProjects();
        
        List stafProjects = new ArrayList();
        for (int i=0; i < projects.length; i++)
        {
            try
            {
                IProject currProject = projects[i];
                String comment = currProject.getDescription().getComment();
                if (comment.equals(NewProjectWizard.STAF_PROJECT))
                {
                    stafProjects.add(currProject);
                }
            }
            catch (CoreException ce)
            {
                MultiStatus err = Util.getServiceInfo(
                                       Activator.getDefault().getBundle(), ce);
                Activator.getDefault().getLog().log(err);
            }
        }
        
        return stafProjects.toArray(new Object[stafProjects.size()]);
    }
    
    /**
     * Dispose
     */
    public void dispose()
    {
        ((IWorkspace) viewer.getInput()).removeResourceChangeListener(this);
        viewer = null;
    }

    /**
     * Updates the viewer and property change listeners when input changes
     * @see IContentProvider#inputChanged(Viewer, Object, Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
    {        
        this.viewer = (TreeViewer) viewer;
        
        if (oldInput != newInput)
        {
            if (oldInput != null)
            {
                ((IWorkspace) oldInput).removeResourceChangeListener(this);
            }
            
            if (newInput != null)
            {
                ((IWorkspace) newInput).addResourceChangeListener(this);
            }
        }
    }

    
    /**
     * Returns the child elements of the given parent element.
     * The parent must be of type IContainer
     * @see ITreeContentProvider#getChildren(Object) 
     */
    public Object[] getChildren(Object parentElement)
    {
        if (parentElement instanceof IContainer)
        {
            try
            {
                IResource[] children = ((IContainer) parentElement).members();
                
                return filterChildren(children);
            }
            catch (CoreException e)
            {
                MultiStatus err = Util.getServiceInfo(
                                       Activator.getDefault().getBundle(), e);
                Activator.getDefault().getLog().log(err);
            }
            
        }
        
        return new Object[0]; 
    }
    
    
    /**
     * Filters the list of children to only return files with extensions
     * of ISTAFMachine.FILENAME_EXT
     */
    private IResource[] filterChildren(IResource[] children)
    {
        List filteredChildren = new ArrayList();
        
        for (int i=0; i < children.length; i++)
        {
            if ((children[i] instanceof IFile) &&
                (!children[i].getName().endsWith(ISTAFMachine.FILENAME_EXT)))     
            {
                continue;
            }

            filteredChildren.add(children[i]);
        }
        
        return (IResource[]) filteredChildren.toArray(
                                new IResource[filteredChildren.size()]);
    }

    
    /**
     * Returns the parent for the given element or null if the parent
     * cannot be computed.
     * Object should be of type IResource
     * @see ITreeContentProvider#getParent(Object)
     */
    public Object getParent(Object element)
    {
        if (element instanceof IResource)
        {
            return ((IResource) element).getParent();
        }
        
        return null;
    }

    
    /**
     * Returns whether the given element has children.
     * @see ITreeContentProvider#hasChildren(Object)
     */
    public boolean hasChildren(Object element)
    {
        Object[] children = getChildren(element);
        
        if (children.length == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    
    /**
     * @see IResourceChangeListener#resourceChanged(IResourceChangeEvent)
     */
    public void resourceChanged(IResourceChangeEvent event)
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
}