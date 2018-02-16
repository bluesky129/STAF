/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import com.ibm.staf.eclipse.editor.Activator;
import com.ibm.staf.eclipse.editor.Util;

/**
 * The first page of the new wizard for creating Machine Groups.
 * This page contains a containerText and groupNameText. The container
 * text is initialized with any selection provided to the wizard.
 */
public class NewMachineGroupWizardPage extends WizardPage
{
    /* Instance Fields */
    
    private Text containerText;
    private Text groupNameText;
    private ISelection selection;

    
    /**
     * Constructor 
     * @param selection The ISelection passed to the wizard
     */
    public NewMachineGroupWizardPage(ISelection selection)
    {
        super("wizardPage");
        setTitle("New Machine Group");
        setDescription("This wizard creates a new STAF Machine Group.");
        this.selection = selection;
    }

    /**
     * @see IDialogPage#createControl(Composite)
     */
    public void createControl(Composite parent)
    {
        //create layout
        Composite container = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        container.setLayout(layout);
        layout.numColumns = 3;
        layout.verticalSpacing = 9;
        
        //create container Label & Text
        Label label = new Label(container, SWT.NULL);
        label.setText("&Container:");
         
        containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
        GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
        containerText.setLayoutData(gd); 
        containerText.addModifyListener(new ModifyListener() 
        { 
            public void modifyText(ModifyEvent e) 
            {
                dialogChanged(); 
            } 
        });
        
        //Create Browse Button
        Button button = new Button(container, SWT.PUSH);
        button.setText("Browse..."); 
        button.addSelectionListener(new SelectionAdapter() 
        { 
            public void widgetSelected(SelectionEvent e) 
            {
                handleBrowse(); 
            } 
        });
        
        //Create Group Label & Text
        label = new Label(container, SWT.NULL);
        label.setText("&Group Name:");

        groupNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
        gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
        groupNameText.setLayoutData(gd);
        groupNameText.addModifyListener(new ModifyListener()
        {
            public void modifyText(ModifyEvent e)
            {
                dialogChanged();
            }
        });
        
        //Init page
        initialize();
        dialogChanged();
        setControl(container);
    }

    /**
     * Tests if the current workbench selection is a suitable container to use
     * and initializes the containerText if so.
     */
    private void initialize()
    {
        if (selection != null && selection.isEmpty() == false
            && selection instanceof IStructuredSelection)
        {
            IStructuredSelection ssel = (IStructuredSelection) selection;
            if (ssel.size() > 1)
                return;
            Object obj = ssel.getFirstElement();
            if (obj instanceof IResource)
            {
                IContainer container;
                if (obj instanceof IContainer)
                    container = (IContainer) obj;
                else
                    container = ((IResource) obj).getParent();
                
                String initContainer = container.getFullPath().toString();
                if (initContainer.charAt(0) == '/')
                {
                    initContainer = initContainer.substring(1);
                }
                containerText.setText(initContainer);
            }
        }
        
        //if containerText has a default, set focus to groupNameText
        //for easier entry by user
        if (!(containerText.getText().equals("")))
        {
            groupNameText.setFocus();
        }
    }

    /**
     * Uses the standard container selection dialog to choose the new value for
     * the container field.
     */
    private void handleBrowse()
    {
        ContainerSelectionDialog dialog = new ContainerSelectionDialog(
                getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
                "Select new file container");
        if (dialog.open() == ContainerSelectionDialog.OK)
        {
            Object[] result = dialog.getResult();
            if (result.length == 1)
            {
                containerText.setText(((Path) result[0]).toString());
            }
        }
    }
    
    
    /**
     * Checks for a variety of errors and displays messages. Also
     * control enabling/disabling the Finish button.
     */
    private void dialogChanged()
    {
        String groupName = getGroupName();
        
        //must specify groupName
        if (groupName.length() == 0)
        {
            updateStatus("Machine Group name must be specified");
            return;
        }
        
        IResource container = ResourcesPlugin.getWorkspace().getRoot()
                .findMember(new Path(getContainerName()));
        //must specify container
        if (getContainerName().length() == 0)
        {
            updateStatus("File container must be specified");
            return;
        }
        //container must exist
        if (container == null
                || (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0)
        {
            updateStatus("File container must exist");
            return;
        }
        
        //must be in a STAF project
        try
        {
            String comment = container.getProject().getDescription().
                                                    getComment();
            if (!(comment.equals(NewProjectWizard.STAF_PROJECT)))
            {
                updateStatus("Containing project must be a STAF Project.");
                return;
            }
        }
        catch (CoreException ce)
        {
            MultiStatus err = Util.getServiceInfo(
                                   Activator.getDefault().getBundle(), ce);      
            Activator.getDefault().getLog().log(err);
        }
        
        //Project must be writable
        if (!container.isAccessible())
        {
            updateStatus("Project must be writable");
            return;
        }
            
        //all checked out
        updateStatus(null);
    }

    /**
     * Updates the status message on the wizard page.
     * If message is null page will be marked as complete
     * @param message
     */
    private void updateStatus(String message)
    {
        setErrorMessage(message);
        setPageComplete(message == null);
    }

    /**
     * Returns text from containerText
     * @return container name
     */
    public String getContainerName()
    {
        return containerText.getText();
    }
   
    /**
     * Returns text from groupNameText
     * @return group name
     */
    public String getGroupName()
    {
        return groupNameText.getText();
    }
}