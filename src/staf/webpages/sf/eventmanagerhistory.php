<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>

<head>
  <title>Software Testing Automation Framework (STAF)</title>
</head>

<body>

<?php
require "top.php";
require "navigate.php";
?>

<!-- Insert text for page here -->

<tr>
<td>

<center><h1>EventManager Service History</h1></center>
<PRE>
-------------------------------------------------------------------------------
History Log for EventManager Service

  Legend:
   - Fixes
   + Features           
   = Internal changes
   * Changes required to migrate from one release to another

  Notes:
  1) To get more information on each bug, use the following url, replacing
     Ticket# with the Bug#: https://sourceforge.net/p/staf/bugs/Ticket#/
     For example, to get more information on Bug #2982317, go to:
       https://sourceforge.net/p/staf/bugs/2982317/
  2) To get more information on each feature, use the following url, replacing
     Ticket# with the Feature#: https://sourceforge.net/p/staf/feature-requests/Ticket#/
     For example, to get more information on Feature #1867258, go to:
       https://sourceforge.net/p/staf/feature-requests/1867258/
  3) If you specify an old ticket number (i.e. created before June 2013 when
     the STAF project was upgraded to the new SourceForge developer platform),
     you'll be automatically redirected to the link with the new ticket number.

-------------------------------------------------------------------------------

Version 3.4.0: 06/30/2011

  + Changed to use Jython 2.5.2 instead of Jython 2.1 (Feature #1867258)
    Note: EventManager now requires Java 1.5 or later
  = Changed to take advantage of some new features in Java 1.5 such as Generics
    (Feature #2961492)

-------------------------------------------------------------------------------

Version 3.3.8: 11/01/2010

  + Added a STAFEventManagerID Python variable containing the ID of the
    triggered registration and added more documentation about using Python in
    the service's user guide (Feature #3095056)
  
-------------------------------------------------------------------------------

Version 3.3.7: 09/30/2010

  - Changed the MonitorThread to exit if the QUEUE GET WAIT request it submits
    in a loop fails so it doesn't run continuously consuming lots of CPU
    (Bug #2982317)
  - Improved error handling when an error occurs writing registration data to
    the eventman.ser file by logging and returning an error to let you know
    that registration data in memory is out of sync (Bug #3031791)

-------------------------------------------------------------------------------

Version 3.3.6: 12/15/2009

  = Changed so that no longer use deprecated Java methods (Bug #1505690)
  - Improved error messages for invalid command requests and added exception
    catching when handling a service request (Bug #2895347)

-------------------------------------------------------------------------------

Version 3.3.5: 04/23/2009

  - Fixed the STAF unmarshall method for Jython so that it no longer causes an
    error when invalid marshalled data is input.  This required setting a new
    Jython version, 2.1-staf-v3.3, in the manifest (Bug #2515811)
  - Fixed a problem sharing the same PythonInterpreter across threads, and
    restructured the code (Bug #2594306)
  - Fixed a memory leak when processing a registration that submits a STAF
    service request to a remote machine (Bug #2771642)
  - Fixed a problem registering/unregistering event types and subtypes that
    contain one or more spaces (Bug #2778837)

-------------------------------------------------------------------------------

Version 3.3.4: 12/10/2008

  - Fixed a process completion notification problem that can occur if a
    PROCESS START request is registered without the WAIT option and the
    process completes very quickly (Bug #2265304)
  - Fixed a RC 48 (Does Not Exist) problem viewing the service log via the
    EventManagerUI when the service name entered on the EventManagerUI does
    not have the same case as used when registering the service (Bug #2393318)

-------------------------------------------------------------------------------

Version 3.3.3: 09/24/2008

  - Changed the LIST LONG request to mask private data in the Prepare Script
    value (Bug #1938715)

-------------------------------------------------------------------------------

Version 3.3.2: 02/26/2008

  + Changed STAF license from the Common Public License (CPL) 1.0 to the
    Eclipse Public License (EPL) 1.0 (Feature #1893042)  

-------------------------------------------------------------------------------

Version 3.3.1: 01/08/2008

  - Changed to not resolve STAF variables in the REGISTER request's MACHINE,
    SERVICE, and REQUEST options and added the OLDVARRESOLUTION service
    parameter for backward compatibility (Bug #1844449)
  - Added a check when triggering/submitting a registered STAF service request
    to see if the submit request failed (Bug #1862787)
  - Fixed "The java class is not found: com.ibm.staf.STAFException" error
    when starting EventManagerUI (Bug #1866950)

-------------------------------------------------------------------------------

Version 3.3.0: 11/20/2007

  - Fixed problem where editing an EventManagerUI registration with Python
    errors could cause the original registration to be lost (Bug #1802869)
  + Added ability to enable/disable EventManager registrations
    (Feature #1821696)
  = Updated EventManagerUI to automatically wrap the text specified for tool
    tips (Bug #1822409)
  + Added ability to start the EventManagerUI by executing
    "java -jar STAFEventManager.jar" (Feature #1827098)
    
-------------------------------------------------------------------------------

Version 3.2.2: 04/23/2007

  - Added a missing space separator for some options in a REGISTER request
    (Bug #1675955)
  - Added examples for how to use the PYTHONREQUEST and PREPARE options on a
    REGISTER request and another EventManagerUI registration example
    (Bug #1675198)
    
-------------------------------------------------------------------------------

Version 3.2.1: 11/09/2006

  = Updated the manifest to specify Jython version 2.1-staf-v3.  This version
    changed the marshall and formatObject methods in Lib/STAFMarshalling.py to
    significantly improve their performance (Bug #1559277)
    
-------------------------------------------------------------------------------

Version 3.2.0: 08/31/2006

  - Redesigned the content of the EventManager service's log records
    (Bug #1513006)
  + Added a TRIGGER request that allows a registered STAF command to be
    submitted at any time (without the registered event type/subtype being
    generated (Feature #1501230)
  - Sort the results of a LIST request by ID (Bug #1536239)
  + Added a SHORT option for the LIST request (Feature #1536307)
  - Changed to use Pass/Fail log levels based on the RC the STAF
    commands/processes return (Bug #1538796)
  + Added a new User Interface, EventManagerUI (which replaces
    EventManagerRegister) to simplify interaction with the EventManager
    service, including the ability to edit registrations, copy
    registrations, etc. (Feature #1493217)

-------------------------------------------------------------------------------

Version 3.1.3: 07/17/2006

  = Updated the manifest to specify Jython version 2.1-staf-v2.  This version
    changed the marshall method in Lib/STAFMarshalling.py to check for objects
    with a callable stafMarshall method (Bug #1408579)
  - Updated to not use enum as an identifier so that the source code can be
    compiled on Java 1.5 (Bug #1499440)
  + Added a DESCRIPTION option for the REGISTER request and for the
    registration GUI (Feature #1505561)
  - Added Python compilation of the request during registration (Bug #1505089)
  - Fixed a Python UnboundedLocalError on 'STAFPropKey' error that could occur
    when processing an event with no properties (Bug #1524135)
        
-------------------------------------------------------------------------------

Version 3.1.2: 03/10/2006

   - Fixed EventManager STAFException during shutdown or when the service was
     dynamically removed (Bug #1447346)

-------------------------------------------------------------------------------

Version 3.1.1: 10/12/2005

  - Fixed problem where the service would get in a bad state if an unexpected
    exception occurred while getting messages from its queue (Bug #1315382)
  - Fixed problem handling property values that contain marshalled data in a
    queued message sent by the Event service (Bug #1324093)
  - Changed to only create a Python Interpreter and assign Python variables if
    the registration uses Python (Bug #1324959)
  - Changed to create a new Python Interpreter for each matching registration
    that uses Python (Bug #1324117)
   
-------------------------------------------------------------------------------

Version 3.1.0: 09/29/2005

  + Provided the ability to mask passwords and other sensitive data
    (Feature #622392)
  + Updated to require STAF V3.1.0 or later using the new compareSTAFVersion()
    method since utilizing new privacy methods (Feature #1292268)  

-------------------------------------------------------------------------------

Version 3.0.1: 06/27/2005

  + Added a LIST SETTINGS request to list the operational settings for the
    service (Feature #989754)
  - Changed to use "local" instead of the machine host name for the default
    Event service machine for performance reasons (Bug #1208721)
    
-------------------------------------------------------------------------------

Version 3.0.0: 04/21/2005

  - Improved error message provided for RC 25 (Access Denied) for all requests
    (Bug #1054858)
  = Removed the zxJDBC code from our distribution of Jython (Bug #1118221)
  - Changed to use STAFUtil's common resolve variable methods (Bug #1151440)
  - Changed to require trust level 4 for a UNREGISTER request (Bug #1156340)
  - Changed license from GPL to CPL for all source code (Bug #1149491)  
   
-------------------------------------------------------------------------------

Version 3.0.0 Beta 7: 12/13/2004

  = Recompiled
  
-------------------------------------------------------------------------------

Version 3.0.0 Beta 6: 11/19/2004

  - Updated EventManager to free process handles when complete (Bug #988247)

-------------------------------------------------------------------------------

Version 3.0.0 Beta 5a: 11/09/2004

  + Changed to load STAFMarshalling Python module needed by STAX
    (Feature #740150)

-------------------------------------------------------------------------------

Version 3.0.0 Beta 5: 10/30/2004

  + Changed to return STAFResult from init/term methods (Feature #584049)
  + Updated to handle new queue type for queued messages (Feature #1044711)
  + Updated to handle new marshalled messages in queued messages and to create
    a marshalled map class for its LIST result and added a LONG option to the
    LIST request (Feature #740150)
  + Changed to use new STAFServiceInterfaceLevel30 (Feature #550251)
  
-------------------------------------------------------------------------------

Version 3.0.0 Beta 4: 09/29/2004

  - Updated User's Guide to reference the EM instead of the STAX service
    machine and made the install/configuration section clearer (Bug #989784)
  + Updated to handle new marshalled result format for a QUEUE GET request
    (Feature #740150)
  
-------------------------------------------------------------------------------

Version 3.0.0 Beta 3: 06/28/2004

  + Added un-register of the service handle during term() (Feature #966079)
  
-------------------------------------------------------------------------------

Version 3.0.0 Beta 2: 04/29/2004

  + Updated to use STAFServiceInterfaceLevel5, only supported in STAF V3.0.0,
    and to use new syntax for the VAR service (Feature #464843) 
  + Updated to use the new info.writeLocation field so that it now writes the
    eventman.ser file to directory &lt;info.writeLocation>/service/&lt;serviceName>
    instead of writing the file to {STAF/Config/STAFRoot}/data/eventmanagerdata
    (Feature #592875)
    
-------------------------------------------------------------------------------

Version 1.2.2: 03/13/2004

  - Fixed bug where serialized registration data from EventManager versions
    prior to 1.2.0 was not being read in during service initialization
    (Bug #915200)

-------------------------------------------------------------------------------

Version 1.2.1: 02/27/2004

  + Instrumented the EventManager service's handling of LIST requests to
    record diagnostics data to help prepare for the migration to STAF V3.0.
    The EventManager service requires STAF V2.6.0 or later as a result of this
    change. (Feature #853620)  

-------------------------------------------------------------------------------

Version 1.2.0: 02/02/2004

  + Added logging to the EventManager service (Feature #838763)

-------------------------------------------------------------------------------

Version 1.1.8: 11/19/2003

  - Changed to require a trust level of 5 for the REGISTER command 
    (Bug #837274)

-------------------------------------------------------------------------------

Version 1.1.7: 09/29/2003

  - Fixed problem where EventManager was not passing back the correct message
    when encountering a parsing error (Bug #814808)

-------------------------------------------------------------------------------

Previous versions included:

  + Added an eventinfo dictionary to the EventManager service (Feature #608058)
  - Display better information if the EventManager service python options 
    contain invalid python code (Bug #608078) 
  - Fixed EventManager service bug where python errors cause queue thread to
    die (Bug #607513) 

-------------------------------------------------------------------------------

Version 1.1.1: 08/12/2002

  - Made the Type and Subtype fields case-insensitive (Bug #594244) 
  
-------------------------------------------------------------------------------
</PRE>

</td>
</tr>

<!-- end of text for page -->

<?php
require "bottom.php";
?>

</body>
</html>
