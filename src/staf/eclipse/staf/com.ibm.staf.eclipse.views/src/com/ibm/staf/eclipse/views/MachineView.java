/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.views;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Bundle;

import com.ibm.staf.eclipse.editor.STAFEditor;
import com.ibm.staf.eclipse.editor.Util;
import com.ibm.staf.eclipse.wizards.NewMachineGroupWizard;
import com.ibm.staf.eclipse.wizards.NewMachineWizard;
import com.ibm.staf.eclipse.wizards.NewProjectWizard;


/**
 * Provides a the STAF Machine view
 */
public class MachineView extends ViewPart implements ISelectionChangedListener
{
    /* Constants */
    
    public static final String PLUGIN_ID = 
        "com.ibm.staf.eclipse.views.MachineView";
    private static final String EXPANDED_RESOURCE_MEM_KEY = "expandedRes";
    
    /* Instance Fields */
    
    private TreeViewer viewer;
    private Action newProjectAction;
    private Action newGroupAction;
    private Action newMachineAction;
    private Action deleteAction;
    private Action doubleClickAction;
    private IMemento memento;
    
    /* Class Fields */
    
    protected static Image groupIcon;
    protected static Image machIcon;
    protected static Image stafIcon;
    
    
    //TODO find out if/when these should be disposed
    static
    {
        Bundle bundle = Activator.getDefault().getBundle();
        
        URL groupURL = bundle.getResource("icons/groups_obj.gif");
        ImageDescriptor groupID = ImageDescriptor.createFromURL(groupURL);
        groupIcon = groupID.createImage();
        
        URL machURL = bundle.getResource("icons/machine_obj.gif");
        ImageDescriptor machID = ImageDescriptor.createFromURL(machURL);
        machIcon = machID.createImage();
        
        URL stafURL = bundle.getResource("icons/STAF.GIF");
        ImageDescriptor stafID = ImageDescriptor.createFromURL(stafURL);
        stafIcon = stafID.createImage();
    }
    

    /**
     * Constructor.
     */
    public MachineView()
    {
        super();
    }

    /**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     */
    public void createPartControl(Composite parent)
    {
        //create viewer
        viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);

        viewer.setContentProvider(new STAFMachineViewContentProvider());
        viewer.setLabelProvider(new STAFMachineViewLabelProvider());

        //add this as selectoin change listener for viewer
        getSite().setSelectionProvider(viewer);
        getSite().getSelectionProvider().addSelectionChangedListener(this);
        
        //set input
        viewer.setInput(ResourcesPlugin.getWorkspace());
        
        //restore viewer state
        restoreViewerState();
        
        //add actions
        makeActions();
        hookContextMenu();
        hookDoubleClickAction();
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
                MachineView.this.fillContextMenu(manager);
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
        manager.add(newProjectAction);
        manager.add(newGroupAction);
        manager.add(newMachineAction);
        manager.add(new Separator());
        manager.add(deleteAction);
    }

    
    /**
     * Creates the context menu actions for the view
     */
    private void makeActions()
    {
        newProjectAction = new Action()
        {
            public void run()
            {
                try
                {
                    String newProjWizID = NewProjectWizard.PLUGIN_ID;
                    
                    IWorkbench workBench = getViewSite().getWorkbenchWindow().
                                                         getWorkbench();
                    IWorkbenchWizard wizard = workBench.getNewWizardRegistry().
                                              findWizard(newProjWizID).createWizard();
                    IStructuredSelection selection = (IStructuredSelection) getSite().getSelectionProvider().getSelection();
                    wizard.init(workBench, selection);
                
                    WizardDialog dialog = new WizardDialog(
                            workBench.getActiveWorkbenchWindow().getShell(), 
                            wizard);
                    dialog.open();
                }
                catch (CoreException e)
                {
                    MultiStatus err = Util.getServiceInfo(
                                      Activator.getDefault().getBundle(), e); 
                    Activator.getDefault().getLog().log(err);

                    return;
                }
            }
        };
        newProjectAction.setText("New STAF Project...");
        newProjectAction.setToolTipText("Create a new STAF project");
        newProjectAction.setImageDescriptor(
                          ImageDescriptor.createFromImage(stafIcon));
        
        
        newGroupAction = new Action()
        {
            public void run()
            {
                try
                {
                    String newMachGrpWizID = NewMachineGroupWizard.PLUGIN_ID;
                    
                    IWorkbench workBench = getViewSite().getWorkbenchWindow().
                                                         getWorkbench();
                    IWorkbenchWizard wizard = workBench.getNewWizardRegistry().
                                              findWizard(newMachGrpWizID).createWizard();
                    IStructuredSelection selection = 
                        (IStructuredSelection) getSite().getSelectionProvider().getSelection();
                    wizard.init(workBench, selection);
                
                    WizardDialog dialog = new WizardDialog(
                            workBench.getActiveWorkbenchWindow().getShell(), 
                            wizard);
                    dialog.open();
                }
                catch (CoreException e)
                {
                    MultiStatus err = Util.getServiceInfo(
                                      Activator.getDefault().getBundle(), e); 
                    Activator.getDefault().getLog().log(err);

                    return;
                }
            }
        };
        newGroupAction.setText("New Machine Group...");
        newGroupAction.setToolTipText("Create a new machine group");
        newGroupAction.setImageDescriptor(
                          ImageDescriptor.createFromImage(groupIcon));


        newMachineAction = new Action()
        {
            public void run()
            {
                try
                {
                    String newMachWizID = NewMachineWizard.PLUGIN_ID;
                    
                    IWorkbench workBench = getViewSite().getWorkbenchWindow().
                                                         getWorkbench();
                    IWorkbenchWizard wizard = workBench.getNewWizardRegistry().
                                              findWizard(newMachWizID).createWizard();
                    IStructuredSelection selection = 
                        (IStructuredSelection) getSite().getSelectionProvider().getSelection();
                    wizard.init(workBench, selection);
                
                    WizardDialog dialog = new WizardDialog(
                            workBench.getActiveWorkbenchWindow().getShell(), 
                            wizard);
                    dialog.open();
                }
                catch (CoreException e)
                {
                    MultiStatus err = Util.getServiceInfo(
                                      Activator.getDefault().getBundle(), e);
                    Activator.getDefault().getLog().log(err);

                    return;
                }
            }
        };
        newMachineAction.setText("New Machine...");
        newMachineAction.setToolTipText("Create a new machine");
        newMachineAction.setImageDescriptor(
                            ImageDescriptor.createFromImage(machIcon));
        
        
        deleteAction = new Action()
        {
            public void run()
            {
                String msg = "Are you sure you want to delete the selected items?";
                if (MessageDialog.openConfirm(viewer.getControl().getShell(), 
                                              "Confirm Delete", msg))
                {
                    IStructuredSelection selection = 
                        (IStructuredSelection) getSite().getSelectionProvider().getSelection();
                    Iterator selectionIt = selection.iterator();
                    while (selectionIt.hasNext())
                    {
                        IResource currSelect = (IResource) selectionIt.next();
                        try
                        {
                            currSelect.delete(false, null);
                        }
                        catch (CoreException e)
                        {
                            MultiStatus err = Util.getServiceInfo(
                                        Activator.getDefault().getBundle(), e); 
                            Activator.getDefault().getLog().log(err);

                            return;
                        }
                    }
                }
            }
        };
        deleteAction.setText("Delete");
        deleteAction.setToolTipText("Delete selected items");
        deleteAction.setImageDescriptor(
                     PlatformUI.getWorkbench().getSharedImages()
                     .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));

        if (getSite().getSelectionProvider().getSelection().isEmpty())
        {
            deleteAction.setEnabled(false);
        }
                     
        
        doubleClickAction = new Action()
        {
            public void run()
            {
                ISelection selection = viewer.getSelection();
                Object obj = ((IStructuredSelection) selection)
                             .getFirstElement();
                
                if (!(obj instanceof IFile))
                {
                    //do nothing if not an IFile
                    return;
                }
                
                IFile file = (IFile) obj;
                try
                {
                    IDE.openEditor(getViewSite().getPage(), file, 
                                   STAFEditor.PLUGIN_ID);
                }
                catch (PartInitException pie)
                {
                    //Doesn't seem like we can actually get here
                    //log error
                    String msg = "Unexpected Error";
                    MultiStatus err = Util.getServiceInfo(
                                           Activator.getDefault().getBundle(), 
                                           IStatus.ERROR, msg, pie);
                    Activator.getDefault().getLog().log(err);
                }
            }
        };
    }

    
    /**
     * Connects the doubleClickAction to the IDoubleClickListener
     */
    private void hookDoubleClickAction()
    {
        viewer.addDoubleClickListener(new IDoubleClickListener()
        {
            public void doubleClick(DoubleClickEvent event)
            {
                doubleClickAction.run();
            }
        });
    }


    /**
     * Passing the focus request to the viewer's control.
     */
    public void setFocus()
    {
        viewer.getControl().setFocus();
    }


    /**
     * Called by eclipse to dispose the view
     */
    public void dispose()
    {    
        getSite().getSelectionProvider().removeSelectionChangedListener(this);
        viewer = null;
        
        super.dispose();
    }
    
    
    /**
     * Overrides {@link ViewPart#init(IViewSite)} and saves the IMemento
     */
    public void init(IViewSite site, IMemento memento) throws PartInitException 
    {
        super.init(site, memento);
        
        this.memento = memento;
    }
    
    
    /**
     * Restores the viewers expanded resource state using the stored
     * IMemento
     */
    private void restoreViewerState()
    {
        //skip processing if no memento set
        if (memento == null)
        {
            return;
        }
        
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        
        List expandedResources = new ArrayList();
        
        int i = 0;
        while (true)
        {
            String savedPath = 
                memento.getString(EXPANDED_RESOURCE_MEM_KEY+i++);
            if (savedPath == null)
            {
                break;
            }
            
            IContainer container = 
                root.getContainerForLocation(new Path(savedPath));
            expandedResources.add(container);
        }

        viewer.setExpandedElements(expandedResources.toArray());
    }
    

    /**
     * Saves the viewers expanded resource state to the IMemento
     */
    public void saveState(IMemento memento)
    {
        super.saveState(memento);
        
        Object[] objs = viewer.getExpandedElements();
        for (int i=0; i < objs.length; i++)
        {
            if (!(objs[i] instanceof IContainer))
            {
                //skip if not IContainer
                //not sure why non-IContainers are sometimes returned as 
                //expanded
                continue;
            }
            IPath path = ((IContainer) objs[i]).getLocation();
            String key = EXPANDED_RESOURCE_MEM_KEY+i;
            
            memento.putString(key, path.toString());
        }
    }


    /**
     * Enables and disables the deleteAction based on the selection
     * @see ISelectionChangedListener#selectionChanged(SelectionChangedEvent)
     */
    public void selectionChanged(SelectionChangedEvent event)
    {
        IStructuredSelection selection = (IStructuredSelection) event.getSelection();
        if (selection == null || selection.isEmpty())
        {
            deleteAction.setEnabled(false);
        }
        else
        {
            deleteAction.setEnabled(true);
        }
    }
}