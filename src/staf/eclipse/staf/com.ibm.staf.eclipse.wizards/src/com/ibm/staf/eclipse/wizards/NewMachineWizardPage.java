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
import com.ibm.staf.eclipse.model.ISTAFMachine;

/**
 * The first page of the new wizard for creating Machines.
 * This page contains a containerText, a hostnameText, and a portText. 
 * The container text is initialized with any selection provided to the wizard.
 * The port text is initialized with the default port value. The page also
 * contains a disabled text (enabled by checkbox) for the filename. It is
 * initialized with the default filename.
 */
public class NewMachineWizardPage extends WizardPage
{
    /* Instance Fields */
    
    private Text containerText;
    private Text hostnameText;
    private Text portText;
    private Text interfaceText;
    private Text filenameText;

    private ISelection selection;

    
    /**
     * Constructor 
     * @param selection The ISelection passed to the wizard
     */
    public NewMachineWizardPage(ISelection selection)
    {
        super("wizardPage");
        setTitle("New Machine");
        setDescription("This wizard creates a new STAF Machine.");
        this.selection = selection;
    }

    /**
     * @see IDialogPage#createControl(Composite)
     */
    public void createControl(Composite parent)
    {
        //Create layout
        Composite container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        container.setLayout(layout);
        layout.numColumns = 3;
        layout.verticalSpacing = 9;
        
        //Create Container Label & Text
        Label label = new Label(container, SWT.NONE);
        label.setText("&Container:");
         
        containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
        GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
        containerText.setLayoutData(gd); 
        containerText.addModifyListener(new
            ModifyListener() 
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
        
        //Create Hostname Label & Text
        label = new Label(container, SWT.NONE);
        label.setText("&Hostname:");

        hostnameText = new Text(container, SWT.BORDER | SWT.SINGLE);
        gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gd.horizontalSpan = 2;
        hostnameText.setLayoutData(gd);
        hostnameText.addModifyListener(new ModifyListener()
        {
            public void modifyText(ModifyEvent e)
            {
                dialogChanged();
            }
        });
        
        //Create Port Label, Text & Default checkbox
        label = new Label(container, SWT.NONE);
        label.setText("&Port:");
        
        portText = new Text(container, SWT.BORDER | SWT.SINGLE);
        gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
        portText.setLayoutData(gd);
        portText.setEnabled(false);
        
        portText.addModifyListener(new ModifyListener()
        {
            public void modifyText(ModifyEvent e)
            {
                dialogChanged();
            }
        });
        
        Button defaultPortCheckBox = new Button(container, SWT.CHECK);
        defaultPortCheckBox.setText("Use local default");
        defaultPortCheckBox.setSelection(true);
        defaultPortCheckBox.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    Button b = (Button) e.getSource();
                    boolean enablePort = !b.getSelection();
                    
                    portText.setEnabled(enablePort);
                    
                    String defaultPortText = 
                        enablePort ? ISTAFMachine.DEFAULT_PORT : "";
                    portText.setText(defaultPortText);
                }
            });
        
        
        //Create Interface Label, Text & Default checkbox
        label = new Label(container, SWT.NONE);
        label.setText("&Interface:");
        
        interfaceText = new Text(container, SWT.BORDER | SWT.SINGLE);
        gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
        interfaceText.setLayoutData(gd);
        interfaceText.setEnabled(false);
        
        interfaceText.addModifyListener(new ModifyListener()
        {
            public void modifyText(ModifyEvent e)
            {
                dialogChanged();
            }
        });
        
        Button defaultInterfaceCheckBox = new Button(container, SWT.CHECK);
        defaultInterfaceCheckBox.setText("Use local default");
        defaultInterfaceCheckBox.setSelection(true);
        defaultInterfaceCheckBox.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e)
                {
                    Button b = (Button) e.getSource();
                    boolean enableInterface = !b.getSelection();
                    
                    interfaceText.setEnabled(enableInterface);
                    
                    String defaultInterfaceText = 
                        enableInterface ? ISTAFMachine.DEFAULT_INTERFACE : "";
                    interfaceText.setText(defaultInterfaceText);
                }
            });
        
                
        
        //Create default filename checkbox
        final Label filenameLabel, filenameExtLabel;
        
        Button defButton = new Button(container, SWT.CHECK);
        defButton.setText("Use default filename");
        gd = new GridData();
        gd.horizontalSpan = 3;
        defButton.setLayoutData(gd);
        defButton.setSelection(true);
        
        
        //Create filename Label, Text, and Extension Label
        filenameLabel = new Label(container, SWT.NONE);
        filenameLabel.setText("&Filename:");
        filenameLabel.setEnabled(false);
        
        filenameText = new Text(container, SWT.BORDER | SWT.SINGLE);
        filenameText.setEnabled(false);
        gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
        filenameText.setLayoutData(gd);
        
        filenameExtLabel = new Label(container, SWT.NONE);
        filenameExtLabel.setText(ISTAFMachine.FILENAME_EXT);
        filenameExtLabel.setEnabled(false);
        
        //Add default filename checkbox selectionListener
        
        defButton.addSelectionListener(new SelectionAdapter()
            {
                public void widgetSelected(SelectionEvent e) 
                {
                    Button b = (Button) e.getSource();
                    boolean enableFilename = !b.getSelection();
                    
                    filenameLabel.setEnabled(enableFilename);
                    filenameText.setEnabled(enableFilename);
                    filenameExtLabel.setEnabled(enableFilename);
                }
            });
        
        
        //init page
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
        
        //if containerText has a default, set focus to hostnameText
        //for easier entry by user
        if (!(containerText.getText().equals("")))
        {
            hostnameText.setFocus();
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
        IResource container = ResourcesPlugin.getWorkspace().getRoot()
                .findMember(new Path(getContainerName()));

        //must specify a container
        if (getContainerName().length() == 0)
        {
            updateStatus("Machine Group container must be specified");
            return;
        }
        //container must exist
        if (container == null
                || (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0)
        {
            updateStatus("Machine Group container must exist");
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
        
        String hostname = getHostname();
        String filename = getFilename();
        
        //Hostname must be specified
        if (hostname.length() == 0)
        {
            updateStatus("Hostname must be specified");
            return;
        }
        
        //update default filename
        if (!(filenameText.isEnabled()))
        {
            StringBuffer filenameBuf = new StringBuffer(getHostname());
            
            if (!getPort().equals(""))
            {
                filenameBuf.append("@"+getPort());
            }
            
            filenameText.setText(filenameBuf.toString());
        }
        
        //validate filename characters
        if (filename.replace('\\', '/').indexOf('/', 1) > 0 ||
            filename.indexOf('?') > 0 ||
            filename.indexOf('<') > 0 ||
            filename.indexOf('>') > 0 ||
            filename.indexOf(':') > 0 ||
            filename.indexOf('*') > 0 ||
            filename.indexOf('|') > 0 ||
            filename.indexOf('\"') > 0)
        {
            updateStatus("Filename must be a valid file name");
            return;
        }
        
        //make sure port is int if specified
        String port = getPort();
        if (!port.equals(""))
        {
            try
            {
                Integer.valueOf(port);
            }
            catch(NumberFormatException nfe)
            {
                updateStatus("Port must be an integer");
                return;
            }
        }
        
        //everything checked out
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
     * Returns text from hostnameText
     * @return hostname
     */
    public String getHostname()
    {
        return hostnameText.getText();
    }
    
    /**
     * Returns text from portText
     * @return port
     */
    public String getPort()
    {
        return portText.getText();
    }
    
    /**
     * Returns the text from interfaceText
     * @return stafInterface
     */
    public String getInterface()
    {
        return interfaceText.getText();
    }
    
    /**
     * Returns text from filenameText including the appropriate
     * file extension
     * @return filename
     */
    public String getFilename()
    {
        return filenameText.getText()+ISTAFMachine.FILENAME_EXT;
    }
}