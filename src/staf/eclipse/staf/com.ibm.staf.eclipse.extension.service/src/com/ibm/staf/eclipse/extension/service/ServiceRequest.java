/*****************************************************************************/
/* Software Testing Automation Framework (STAF)                              */
/* (C) Copyright IBM Corp. 2007                                              */
/*                                                                           */
/* This software is licensed under the Eclipse Public License (EPL) V1.0.    */
/*****************************************************************************/

package com.ibm.staf.eclipse.extension.service;

/**
 * Class to represent the details of a service request.
 * This class is needed because request details cannot be
 * retrieved once the request completes and completed requests
 * will often appear in the user's list of active requests.
 */
public class ServiceRequest
{
    /* Instance Fields */
    
    private String requestNumber;
    private String startTimeStamp;
    private String handleName;
    private String handleNum;
    private String source;
    private String target;
    private String service;
    private String request;

    
    /**
     * Constructor
     */
    public ServiceRequest()
    {
        super();
    }

    /**
     * @return the handleName
     */
    public String getHandleName()
    {
        return handleName;
    }

    /**
     * @param handleName the handleName to set
     */
    public void setHandleName(String handleName)
    {
        this.handleName = handleName;
    }

    /**
     * @return the handleNum
     */
    public String getHandleNum()
    {
        return handleNum;
    }

    /**
     * @param handleNum the handleNum to set
     */
    public void setHandleNum(String handleNum)
    {
        this.handleNum = handleNum;
    }

    /**
     * @return the request
     */
    public String getRequest()
    {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(String request)
    {
        this.request = request;
    }

    /**
     * @return the requestNumber
     */
    public String getRequestNumber()
    {
        return requestNumber;
    }

    /**
     * @param requestNumber the requestNumber to set
     */
    public void setRequestNumber(String requestNumber)
    {
        this.requestNumber = requestNumber;
    }

    /**
     * @return the service
     */
    public String getService()
    {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(String service)
    {
        this.service = service;
    }

    /**
     * @return the source
     */
    public String getSource()
    {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source)
    {
        this.source = source;
    }

    /**
     * @return the startTimeStamp
     */
    public String getStartTimeStamp()
    {
        return startTimeStamp;
    }

    /**
     * @param startTimeStamp the startTimeStamp to set
     */
    public void setStartTimeStamp(String startTimeStamp)
    {
        this.startTimeStamp = startTimeStamp;
    }

    /**
     * @return the target
     */
    public String getTarget()
    {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(String target)
    {
        this.target = target;
    }
}