/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.views;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.ibm.staf.eclipse.editor.Util;

/**
 * The LabelProvider for the Machine View
 */
public class STAFMachineViewLabelProvider extends LabelProvider
{    
    
    /**
     * Returns the name of the container (folder) or filename
     * without extension
     * @see LabelProvider#getText(Object)
     */
    public String getText(Object obj) 
    {
        if (obj instanceof IContainer)
        {
            return ((IContainer) obj).getName();
        }
        
        if (obj instanceof IFile)
        {
            String name = ((IFile) obj).getName();
            return name.substring(0, name.lastIndexOf("."));
        }
        
        //Should not go here
        String msg = "Unknown Resource Type: "+obj.toString();
        MultiStatus err = Util.getServiceInfo(
                               Activator.getDefault().getBundle(), 
                               IStatus.ERROR, msg, null);
        Activator.getDefault().getLog().log(err);
        return msg;
    }
    
    
    /**
     * Returns different icons based on whether the object
     * is a container (folder) or file
     * @see LabelProvider#getImage(Object)
     */
    public Image getImage(Object obj) 
    {        
        if (obj instanceof IProject)
        {
            return MachineView.stafIcon;
        }
        else if (obj instanceof IContainer)
        {
            return MachineView.groupIcon;
        }
        else
        {
            return MachineView.machIcon;
        }
    }
}