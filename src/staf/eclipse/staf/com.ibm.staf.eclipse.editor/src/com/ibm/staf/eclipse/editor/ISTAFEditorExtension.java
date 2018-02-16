/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.editor;

import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.widgets.Composite;

/**
 * This interface must be implemented by any plug-ins extending the
 * STAFEditor. Each extension to the point com.ibm.staf.eclipse.editor.editorTab
 * will be given a tab for each matching STAF service.
 * 
 * STAFEditorExtension is an abstract class which provides implementations
 * for some of the interface methods as well as some convenience methods.
 * It is suggested that STAFEditor extensions extend the STAFEditorExtension
 * class.
 */
public interface ISTAFEditorExtension extends IExecutableExtension,
                                              IPropertyChangeListener
{   
    /**
     * This method is called by STAFEditor to instruct the extension to
     * create its page construction for the editor tab. 
     * @param parent The Composite parent of the extension's page. 
     * @return Composite representing the desired gui components
     * of the extension's page.
     */
    public Composite createPage(Composite parent);
    
    
    /**
     * This method is called by STAFEditor after construction to set required
     * fields. This method must store the STAFEditor for later use. It should not
     * be called by other classes or methods.
     * @param editor the parent STAFEditor of this extension 
     */
    public void setEditor(STAFEditor editor);
    
    
    /**
     * This method is called by STAFEditor after construction to set required
     * fields. This method must store the STAF service name for later use. It should
     * not be called by other classes or methods.
     * @param name the STAF service name 
     */
    public void setServiceName(String name);
    
    
    /**
     * Allows the STAFEditorExtension to dispose of any extra
     * resources that were created. There is no need to dispose of
     * the Composite returned from createPage(Composite parent)
     * or its children as Eclipse will handle it in its disposal
     * hierachy. 
     * @see #createPage(Composite)
     */
    public void dispose();
}
