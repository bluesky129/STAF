/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.views;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.ibm.staf.eclipse.wizards.NewMachineGroupWizard;
import com.ibm.staf.eclipse.wizards.NewMachineWizard;
import com.ibm.staf.eclipse.wizards.NewProjectWizard;

/**
 * Implementation of IPerspectiveFactory to provide a custom
 * STAF perspective.
 */
public class STAFPerspectiveFactory implements IPerspectiveFactory
{

    private static final String ERROR_LOG_VIEW_ID = 
                                "org.eclipse.pde.runtime.LogView";
    private static final String RESOURCE_PERSPECTIVE_ID = 
                                "org.eclipse.ui.resourcePerspective";
    
    /**
     * @see IPerspectiveFactory#createInitialLayout(IPageLayout)
     */
    public void createInitialLayout(IPageLayout layout)
    {
        IFolderLayout topLeft = layout.createFolder("topLeft", 
                IPageLayout.LEFT, 0.20F, 
                IPageLayout.ID_EDITOR_AREA);
        topLeft.addView(MachineView.PLUGIN_ID);
        topLeft.addView(IPageLayout.ID_RES_NAV);
  
        layout.addView(ERROR_LOG_VIEW_ID, 
                       IPageLayout.BOTTOM, 0.80F, 
                       IPageLayout.ID_EDITOR_AREA);
        
        //Add new wizard shortcuts
        layout.addNewWizardShortcut(NewProjectWizard.PLUGIN_ID);
        layout.addNewWizardShortcut(NewMachineGroupWizard.PLUGIN_ID);
        layout.addNewWizardShortcut(NewMachineWizard.PLUGIN_ID);
        
        //Add views to shortcut
        layout.addShowViewShortcut(MachineView.PLUGIN_ID);
        layout.addShowViewShortcut(IPageLayout.ID_RES_NAV);
        layout.addShowViewShortcut(ERROR_LOG_VIEW_ID);
        
        //Add persepective to shortcut
        layout.addPerspectiveShortcut(RESOURCE_PERSPECTIVE_ID);
    }
}