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

<center><h1>STAX History</h1></center>
<PRE>
-------------------------------------------------------------------------------
History Log for STAX  
  
  Legend:
   - Fixes
   + Features           
   = Internal changes
   * Changes required to migrate from one release to another

  Notes:
  1) To get more information on each bug, use one of the following urls,
     replacing Ticket# with the Bug#:
       https://sourceforge.net/p/staf/bugs/Ticket#/
     or use the old format for bugs created before June 2013:
       https://sourceforge.net/support/tracker.php?aid=Ticket#  
     Examples:
       https://sourceforge.net/p/staf/bugs/1447/
       https://sourceforge.net/support/tracker.php?aid=3506350
  2) To get more information on each feature, use one of the following urls,
     replacing Ticket# with the Feature#:
       https://sourceforge.net/p/staf/feature-requests/Ticket#/
     or use the old format for features created before June 2013:
       https://sourceforge.net/support/tracker.php?aid=Ticket#
     Examples:
       https://sourceforge.net/p/staf/feature-requests/710/
       https://sourceforge.net/support/tracker.php?aid=3509474

-------------------------------------------------------------------------------

Version 3.5.17: 06/28/2016
  
 - Fixed a typo in the STAX DTD for the process element's disabledauth
   sub-element's action attribute's description (Bug #1577)
 - Fixed multiple typos in the STAX User's Guide (Bug #1578) 
          
-------------------------------------------------------------------------------

Version 3.5.16: 12/15/2015
  
- Changed the STAX HOLD request to not be able to hold a child block that has
   a parent block which is already held as this is documented as not allowed
   (Bug #1429)
 - Added descriptions of error codes 4004 and 4005 to "Appendix B: STAX Service
   Error Code Reference" in the STAX User's Guide (Bug #1562)
 = Removed redefinition of static variables for service error codes in
   STAXBlockActionFactory.java since already defined in STAX.java (Bug #1563)
   
-------------------------------------------------------------------------------

Version 3.5.15: 09/29/2015
  
 + Updated STAX User Guide in regards to Windows 8.1 and Windows Server 2012 R2
   now being supported by STAF (Feature #768)
 + Updated STAX User Guide in regards to Windows 10 now being supported by STAF
   (Feature #769)  
 - Fixed typos in "Sample STAX Job 2" in "Appendix A" of the STAX User Guide
   (Bug #1558)
   
-------------------------------------------------------------------------------

Version 3.5.14: 06/26/2015

  - Fixed a problem where STAX jobs could hang (especially when many STAX jobs
    are running concurrently) due to not calling the CloneGlobals Python
    function in a completely thread-safe manner (Bug #1549)           
             
-------------------------------------------------------------------------------

Version 3.5.13: 03/31/2015

  + Added support for upgrading STAF on Linux PPC64 LE (Little Endian) machines
    to libraries/STAFUpgradeUtil.xml used by the STAF Upgrade job (Feature #743)

-------------------------------------------------------------------------------

Version 3.5.12: 12/10/2014

  + Added the ability to enable/disable debugging of processes running in STAX
    jobs via a new STAX service parameter, DEBUGPROCESS, and by adding a
    SET DEBUGPROCESS request (Feature #753)
  - Fixed a problem where a STAX job could hang (especially when many STAX jobs
    are running concurrently) when calling a function with a "local" scope or
    when starting a new thread due to STAX not calling the CloneGlobals function
    in a thread-safe manner (Bug #1521)
  + Added the ability to enable/disable debugging of xml parsing of STAX jobs
    via a new STAX service parameter, DEBUGXMLPARSE, and by adding a
    SET DEBUGXMLPARSE request (Feature #757)
    
-------------------------------------------------------------------------------

Version 3.5.11: 09/24/2014

  + Added the ability to enable/disable STAX thread debugging via a new STAX
    service parameter, DEBUGTHREAD, and by adding a SET DEBUGTHREAD request
    (Feature #749)
  - Updated the STAX User's Guide to highlight the breakpoint element in the
    example of how to set a breakpoint within a job (Bug #1529)
  + Improved formatting of messages logged in the STAX JVM Log by adding
    timestamps and job information (Feature #732)
  + Added the ability to enable/disable debugging of the STAX CloneGlobals
    Python function via a new STAX service parameter, DEBUGCLONEFUNCTION, and
    by adding a SET DEBUGCLONEFUNCTION request (Feature #752)
    
-------------------------------------------------------------------------------

Version 3.5.10: 06/30/2014

  - Updated STAX User Guide and the Getting Started with STAX V3 document to
    reference Oracle Java instead of Sun Java (Bug #1524)
    
-------------------------------------------------------------------------------

Version 3.5.9: 03/30/2014

  + Added a STAX Python variable, STAXServicePath, to provide the location
    where the STAX jar file is installed (Feature #738)
  - Improved how the STAX Monitor handles a corrupt properties file by adding
    info to the error message about how to fix the problem and by exiting the
    STAX Monitor instead of hanging when displaying the logo panel (Bug #1514)
  - Increased the default size of the STAX Monitor Properties dialog so that
    the Save/Cancel buttons show up completely without scrolling (Bug #1515)
  - Fixed an problem where a timer did not expire at the specified duration (in
    situations where many jobs with timer elements were running simultaneously)
    by adding the ability to configure STAX to use a TimedEventQueue for each
    job instead of a common TimedEventQueue (Bug #1511)
  + Provided the ability to list the contents of the Timed Event Queue
    (Feature #740)
  - Fixed a problem in the STAX Monitor so that deleting a Script entry via the
    "Scripts" tab when submitting a new job no longer causes an
    ArrayIndexOutOfBoundsException to be thrown (Bug #1516)
  + Added the ability to configure what action to take (e.g. raise a
    STAXLogError signal or log the message using the "Info" logging level) when
    an invalid log level is specified by a log or message element (Feature #741)
  - Fixed a problem after upgrading the STAX Monitor where opening a Job
    Parameters File may not set new job parameters to their default values
    (Bug #1517)
  + Enhanced the STAX Monitor to set the color of FAIL column entries in the
    "Testcase Info" panel to green if 0 or to red if non-zero (Feature #742)       

-------------------------------------------------------------------------------

Version 3.5.8: 12/16/2013

  - Improved error handling if STAFProc dies while the STAX service is being
    terminated (Bug #1505)
  - Added flushing stdout/stderr when a STAX job completes to ensure all
    output, including from sys.stderr.write(), is logged (Bug #1510)
  + Provided a command to get the total number of running jobs by adding a
    TOTAL option to the STAX LIST JOBS request (Feature #736)     

-------------------------------------------------------------------------------

Version 3.5.7: 09/30/2013

  - Fixed a problem where the STAX service could cause STAFProc to crash with
    a STAFException in JSTAF.STAFServiceAcceptRequest when generating a job
    status event (Bug #1494)
  + Added the ability to disable sending STAX job status events to decrease
    memory usage if not using the STAX Monitor (Feature #731)
  - Fixed a problem where the STAX service could not increase the maximum log
    record size to 1M if the LOG service was registered after the STAX service
    in the STAF configuration file (Bug #1495)
  - Documented (in the STAX User's Guide and in the STAFUpgrade function
    description) that the STAFUpgradeUtil.xml library file does not support
    using InstallAnywhere NoJVM installer files when upgrading STAF (Bug #1498)
  - Documented (in the STAX User's Guide and in the STAFUpgrade function
    description) that the STAFUpgradeUtil.xml library file does not provide
    the ability to specify some install options such as overriding the version
    of STAF Python, Perl, or Tcl support installed (Bug #1497)      

-------------------------------------------------------------------------------

Version 3.5.6: 06/28/2013

  - Fixed a typo in section "STAX Logging" in the STAX User's Guide to make it
    clearer that the job start message logged in the STAX service log contains
    the arguments passed to the starting function (Bug #3613064)

-------------------------------------------------------------------------------

Version 3.5.5: 03/29/2013

  - Added a new sub-section to the "Starting the STAX Monitor" section in the
    STAX User's Guide about runnning the STAX Monitor as an Administrator on
    Windows systems where STAFProc is running as an Administrator because UAC
    is enabled (Bug #3606977)
  
-------------------------------------------------------------------------------

Version 3.5.4: 09/28/2012

  + Updated to package Event service V3.1.5 (Feature #3557001)

-------------------------------------------------------------------------------

Version 3.5.3: 03/29/2012

  - Fixed a problem accessing a PyFile object within a parallel element so that
    it no longer results in a STAXPythonEvaluationError signal being raised due
    to a NullPointerException (Bug #3506350)
  + Changed the STAX service to import the com.ibm.staf.STAFUtil Java class so
    that each STAX job no longer needs to import it before using any of its
    methods such as STAFUtil.wrapData() (Feature #3509474)
  + Added examples in the STAX User's Guide on how to get the current date/time
    using Python modules or Java classes within a script element
    (Feature #637329)

-------------------------------------------------------------------------------

Version 3.5.2: 12/08/2011

  - Fixed the STAXPythonEvaluationError message for an error that occurs within
    a command sub-element in a process element (Bug #3432896)
  - Fixed a problem where the STAX Monitor's "Monitored" column could sometimes
    be incorrect by not indicating a job was being monitored (Bug #3436586)
  - Fixed a problem monitoring a job that has multiple active subjobs but the
    "Active Job Elements" panel only showed one subjob running (Bug #3404871)
  + Added the ability to specify a timeout when holding a job/block such that
    if the timeout is exceeded the job/block will be automatically released
    (Feature #3432939)
  + Added the ability for the STAX Monitor to monitor a sub-job from the start
    of execution (Feature #3404871)
  - Fixed a problem where the import element didn't handle UNC file names that
    used forward slashes instead of backward slashes (Bug #3439830)  

-------------------------------------------------------------------------------

Version 3.5.1: 09/28/2011

  - Fixed an exception in the STAX Monitor's Job Wizard when saving an argument
    for a function defined with function-single-arg (Bug #3357755)
  - Improved how STAX prints multi-byte characters to the JVM Log or STAX Job
    User Log when the Python 'print' statement is used within a script element
    (Bug #3365259)
  - Improved unicode support by using the PyCF_SOURCE_IS_UTF8 compiler flag so
    that Python strings with code points > 255 are recognized instead of being
    replaced by '?' (Bug #3368207)
  + Updated the "STAX Logging" section in the STAX User's Guide to talk about
    the STAF Log Formatter which formats a STAX Job Log as html or text
    (Feature #3368230)
  - Improved how the STAX Monitor submits REGISTER requests to the Event
    service (Bug #3392143)  
  + Added the ability to stop a process via the STAX Monitor's Active Job
    Elements tab by right mouse clicking on the process and selecting "Stop"
    (Feature #3375664)
  - Fixed an NPE when that could occur submitting a STAX LIST JOBS request if
    a job in the list had not yet been assigned a start time (Bug #3398090)
  + Provided the ability to have persistent STAX Job IDs by adding a new
    RESETJOBID parameter so that when STAFProc is restarted, if it is disabled,
    the Job ID is not reset to 1 (Feature #605785)
  - Fixed a job hang problem that was caused by improper handling of a process
    race condition which is more likely to occur for processes that complete
    very quickly in a heavily stressed environment (Bug #3409618)

-------------------------------------------------------------------------------

Version 3.5.0: 06/30/2011

  - Fixed a typo in the STAX User's Guide for a STAXGlobal Class example
    (Bug #3190673)
  + Changed to use Jython 2.5.2 instead of Jython 2.1 which provides new
    Python functionality that can be used within STAX jobs (Feature #1867258)
    Notes:
    1) The STAX service and monitor require Java 1.5 or later.
    2) Packages Jython 2.5.2 within the STAX.jar file
  - Fixed a problem in STAXThread's pyListEval() method where it could return
    a PyList instead of a Java List object because in Jython 2.5.2 the PyList
    class implements the Java List class (Bug #3310702)
  - Fixed a memory leak in the STAX Monitor (Bug #3313322)
  - Provided a better error message if the STAX Monitor is started using an
    old JSTAF.jar file (from STAF V3.3.2 or earlier) instead of just throwing
    a "NoSuchFieldError: resultObj" exception (Bug #3316983)
  = Changed to take advantage of some new features in Java 1.5 such as Generics
    (Feature #2961492)
  + Provided the ability to stop a process in a STAX job by adding a STOP
    PROCESS request to the STAX service (Feature #3310775)  
  - Fixed a problem using non-ASCII characters in STAX xml files (Bug #3348124)  

-------------------------------------------------------------------------------

Version 3.5.0 Beta 1: 12/02/2010

  + Changed to use Jython 2.5.2 instead of Jython 2.1 which provides new
    Python functionality that you can use within STAX jobs (Feature #18678258)
    Notes:
    1) Jython 2.5.2 requires Java 1.5 or later so STAX now requires Java 1.5
       or later.
    2) Since the final version of Jython 2.5.2 has not been released yet, you
       must download and install Jython 2.5.2 RC 2 and use the provided
       STAXJarUpdateForJython252 Java application to embed Jython 2.5.2 into
       the STAX.jar file (only need to do this for the Beta)

-------------------------------------------------------------------------------

Version 3.4.5: 12/14/2010

  + Changed the default signal handler for the STAXEmptyList signal to do
    nothing so it no longer logs an error in the job log (Feature #3083827)
  + Changed the result from EXECUTE WAIT RETURNRESULT and GET RESULT requests
    to include a list of errors, if any, in the job log (Feature #3082388)
  + Added the ability to import all .xml files from a directory via the import
    element (Feature #3076665)
  + Added the ability to list jobs that are in the process of being submitted
    for execution (that are in a "Pending" state) by assigning a job ID before
    the xml file is parsed (Feature #3032420)
  + Changed the STAX Monitor to display a "Please wait" message while a job is
    being submitted for execution and to display and manipulate jobs in the
    "Active Jobs" list that are in a "Pending" state (Feature #3105346)
  - Fixed an import error if a relative file name is specified and its parent
    file name ends in a file separator (Bug #3111992)
  + Added a new STAF MoveError return code to STAFJython.py which required the
    STAX Jython version to be updated to 2.1-staf-v3.4 (Feature #968429)

-------------------------------------------------------------------------------

Version 3.4.4: 09/30/2010

  - Fixed a problem in STAXProcessAction::requestComplete() where it wasn't
    lower-casing the processMap key in a corner case situation (Bug #3042618)
  - Updated the examples in the Getting Started with STAX document to work on
    all platforms (Bug #3047355)
  + Improved performance during process termination by submitting PROCESS STOP
    and FREE requests via a single request asynchronously (Feature #3048933)  
  + Added support for upgrading STAF on Mac OS X 64-bit machines to
    libraries/STAFUpgradeUtil.xml (Feature #1961092)
  + Added a maxthreads attribute to the paralleliterate element to run a task
    on a subset of the list in parallel to help prevent running out of memory
    (Feature #3034797)
  - Fixed a typo in the STAXFunctionArgValidate signal's error message for
    validating function arguments in a map form (Bug #3065572)
  + Added function-import as a new sub-element for the function element to
    provide the ability to require functions from other xml files
    (Feature #2952811)
  + Added the ability to list/query functions in a STAX job that's running
    by adding a FUNCTIONS option to the STAX LIST JOB request and by adding a
    FUNCTION option to the STAX QUERY JOB request (Feature #3077278)
  + Changed to resolve any STAF variables in an imported file's name or machine
    before storing in the cache to improve the hit ratio (Feature #3077455)
  + Added the ability to import all .xml files from a directory via the
    function-import element (Feature #3076665)      

-------------------------------------------------------------------------------

Version 3.4.3: 06/29/2010

  - Fixed how cached documents are updated in the file cache (Bug #3007659)
  + Added support for file caching algorithm LFU (Least Frequently Used) with
    a maximum age for cached documents, and provided summary information for
    the file cache, including the hit ratio, to help you determine the caching
    algorithm with the best performance (Feature #2965419)
  - Fixed a problem where the STAX Monitor could show a thread as running,
    when it was actually at a breakpoint (Bug #3011168)
  + Added support for upgrading STAF on IBM i 64-bit machines to
    libraries/STAFUpgradeUtil.xml (Feature #2948129)
  - Improved performance when listing thread variables (Bug #3016120)
  + Improved performance managing STAX-Threads in a job (Feature #3016070)
  + Added a LONG option to the STAX LIST THREADS request and updated the STAX
    Monitor to use this request to improve performance when debugging a job
    (Feature #3016198)
  + Updated the STAX Monitor to use the STAX service machine's date/time
    when calculating elapsed times (Feature #817425)
  - Changed the STAX Monitor so if it cannot unregister for Job Events due to
    RC=5 (HandleDoesNotExist), an error pop-up is not displayed (Bug #3017720)
  + Added a MaxSTAXThreads parameter/setting to help prevent paralleliterate
    and parallel elements from running too many STAX-Threads simultaneously
    which can cause the STAX JVM to get an OutOfMemoryError (Feature #2946775)

-------------------------------------------------------------------------------

Version 3.4.2: 05/10/2010

  - Changed the STAX service to automatically increase the maximum record size
    for the local LOG service to 1M to decrease the possibility that a message
    logged to the STAX job and job user logs will be truncated (Bug #2980829)
  - Changed the STAX Monitor's run thread to exit if the QUEUE GET WAIT request
    it submits in a loop fails so it doesn't run continuously consuming lots of
    CPU (Bug #2982317)
  - Fixed problem where the testcase totals and testcase list could be
    incorrectly empty when the RETURNRESULTS option is specified on a STAX
    EXECUTE request (Bug #2999262)

-------------------------------------------------------------------------------

Version 3.4.1: 03/30/2010

  - Fixed a NullPointerException in the STAX Monitor at line 680 in
    STAXMonitorDebugExtension handling a start thread event (Bug #2935089)
  - Fixed a NullPointerException in the STAX Monitor at line 733 in
    STAXMonitorDebugExtension handling a stop thread event (Bug #2948638)
  - Fixed a problem in the STAXUtilQueryAllTests and STAXUtilQueryTest
    functions provided in STAXUtil.xml if the STAX service name is not STAX
    (Bug #2959845)
  + Changed to get multiple messages off a STAX job handle's queue at a time,
    up to the maximum specified by the new MaxGetQueueMessages setting
    (Feature #2962083)
  - Fixed a problem where the STAX Monitor was not checking if the STAX service
    version is V3.4.0 or later (Bug #2973899)
  - Fixed a problem where a STAX job's STAFQueueMonitor thread could sometimes
    continue running in an infinite loop consuming lots of CPU after the job
    has been terminated (Bug #2976151)

-------------------------------------------------------------------------------

Version 3.4.0: 12/18/2009

  - Improved error messages for invalid command requests and added exception
    catching when handling a service request (Bug #2895347)
  - Fixed ArrayIndexOutOfBoundsExceptions in the STAX Monitor that could occur
    when selecting an action on a job in the Active Jobs panel (Bug #2901468)
  + Added breakpoint/debugging capabilities to the STAX service and the STAX
    Monitor (Feature #998243)

-------------------------------------------------------------------------------

Version 3.3.8: 09/30/2009

  - Fixed a problem in the STAX Monitor's "Active Job Elements" panel so that
    blocks are not removed until they have actually ended (Bug #2812996)
  + Added the ability to replace functions that have already been imported by
    adding a "replace" attribute to the import element (Feature #1379596)
  - Improved file caching to perform case-insensitive file name matching for
    files residing on a Windows machine (Bug #2822103) 
  + Added the STAX service's version and any extension elements to the STAX
    DTD's comment header (Feature #2826155)
  = Changed so that no longer use deprecated Java methods (Bug #1505690)
  - Fixed a race problem where a STAX job could hang if a finally element's
    task completes very quickly (Bug #2832883)
  - Fixed a NullPointerException in STAXTestcaseAction.java that could occur
    when terminating a STAX job and cause a job to "hang" (Bug #2835013)
  - Removed logging process warning message "STAXProcessActionFactory.
    handleQueueMessage: No listener found for message: ..." in STAX Job Log
    (Bug #2839829)
  - Fixed a NullPointerException in STAXSTAFCommandAction that could occur
    when terminating a STAX job and cause a job to "hang" (Bug #2839767)
  - Improved documention on which STAX elements and attributes text values
    are literals and which are evaluated via Python (Bug #2842184)
  - Added a STAF/STAX FAQ entry titled "Why is the STAX JVM crashing with an
    OutOfMemoryError logged in the STAX JVM log?" and added a link to it in
    the "Debugging" section in the STAX User's Guide (Bug #2844530)  

-------------------------------------------------------------------------------

Version 3.3.7: 06/30/2009

  + Added support for upgrading STAF on HP-UX PA-RISC 64-bit machines to
    libraries/STAFUpgradeUtil.xml (Feature #2540001)
  - Modified the STAX User's Guide to add a section on using STAF Java Classes
    (e.g. STAFUtil, STAFRC) and added a statement to the STAX Python Interfaces
    section that you cannot import the STAF Python module PySTAF (Bug #2786695)
  - Fixed NullPointerException when saving the STAX Monitor properties
    (Bug #2790933)
  - Fixed a problem where processes that were terminated (e.g. because a timer
    popped or a job/block was terminated) were not being freed (Bug #2793537)
  - Fixed a problem where a STAX GET RESULT JOB &lt;JobID> request could return
    data that couldn't be unmarshalled (Bug #2798665)
  + Added a MAXRETURNFILESIZE setting for the STAX service to specify a maximum
    size for files returned by a &lt;process> element, or by a &lt;stafcmd> that
    submits a FS GET FILE request, to help prevent OutOfMemory errors. The STAX
    service now requires STAF V3.3.4 or later. (Feature #2804367 & 2638614)

-------------------------------------------------------------------------------

Version 3.3.6: 03/30/2009

  - Modified the STAX User's Guide to show the updated STAX Monitor LogViewer's
    "File" menu order and added a separator line (Bug #2412279)
  - Fixed how the STAX Monitor handles cancelling changes to properties on the
    Testcases, Sub-jobs, and Extension Jars tabs (Bug #2407789)
  + Added variables STAXCurrentXMLFile and STAXCurrentXMLMachine, to provide
    the xml file and machine containing the function currently running
    (Feature #2452043)
  - Fixed a problem so that if local is specified for the EXECUTE request's
    MACHINE option, the STAXJobXMLMachine variable is also set to local, not
    to the local machine's host name (Bug #2417898)
  - Fixed the STAF unmarshall method for Jython so that it no longer causes an
    error when invalid marshalled data is input.  This required setting a new
    Jython version, 2.1-staf-v3.3, in the manifest (Bug #2515811)
  + Added variable STAFResultString to provide the marshalled result string
    after a stafcmd (Feature #2540734)
  - Fixed a STAXPythonEvaluationError due to a ClassCastException that occurs
    if a process returns a file containing marshalled data (Bug #2541762)
  - Fixed a typo in the STAX User's Guide &lt;process-action> example and added
    information and examples about the differences in using the process handle
    depending on the &lt;command> mode attribute (Bug #2549437) 
  + Added exception stacktraces (Feature #2505714)
  - Improved STAX Monitor performance by using System.currentTimeMillis()
    instead of Calendar.getInstance().getTime().getTime() (Bug #988268)
  - Added the job completion status to the events generated when a STAX job
    completes (Bug #2593748)
  - Improved the readability of the STAX LIST/QUERY help text (Bug #2613837)
  - Updated the STAX Monitor to require STAX service V3.3.4 or later, to
    resolve a NullPointerException (Bug #2633953)
  - Added debugging for a ClassCastException in STAXMonitor.mouseReleased()
    (Bug #2692644)
  - Fixed a problem where the STAX file cache wasn't being cleaned up if the
    maxFileCacheSize was dynamically set to 0 (Bug #2703685)
  + Changed the import element so that the machine attribute is no longer
    required (defaults to the machine where the current xml file resides) and
    added the ability to specify a relative file path for the file attribute,
    relative to the current xml file's parent directory (Feature #2417461)
  + Changed the STAX service and monitor to automatically increase the maximum
    queue size for handles to 1000 for the service and 10000 for the monitor,
    so no longer have to SET MAXQUEUESIZE in STAF.cfg (Feature #2703813)
  + Changed the STAX Monitor to use the ALL option when submitting a GET WAIT
    request to the QUEUE service to improve performance.  The STAX Monitor now
    requires STAF V3.3.3 or later (Feature #2616498)
  - Improved performance of the STAX Monitor by using the auto-unmarshalled
    result object instead of calling the unmarshall API (Bug #2712374)
  + Added the ability to get the results for a completed STAX job, including
    testcase information, without having to parse the STAX Job Log by adding a
    GET RESULT [DETAILS] request and by adding testcase info to the result
    from an EXECUTE WAIT RETURNRESULT [DETAILS] request and to the STAX job
    end notification message (Feature #2505719)
  - Fixed a problem where a STAX EXECUTE WAIT request may never complete or
    timeout when a job completes very quickly (Bug #2722136)
        
-------------------------------------------------------------------------------

Version 3.3.5: 12/08/2008

  - Fixed a problem with how the timer element calculates the duration if it
    is specified in years using the 'y' suffix (Bug #2183070)
  - Fixed a job hang problem if specify a blank timer duration (Bug #2207697)
  + Added support for more time representations (e.g. seconds, minutes, hours,
    days, weeks) for the WAIT option on an EXECUTE request.  This change means
    that the STAX service now requires STAF V3.3.2 or later (Feature #2182713)
  - Fixed a NullPointerException in the STAX Monitor in STAXMonitorFrame.
    handleCommand() that could occur monitoring an existing job (Bug #2226865)
  - Fixed a problem where a "terminated" stafcmd was not being removed from
    the list of active stafcmds (Bug #2230305)
  - Fixed "STAFException thrown during STAXMonitorSTAFCmdTablePlugin
    initialization. RC: 7" error when starting to monitor an already-executing
    job that has one or more testcase names containing spaces (Bug #2229833)
  - Fixed a problem where the STAX Monitor did not exit if the required STAF
    version is not running (Bug #2383550)
  + Added the ability to save STAX logs as text or html files via the STAX
    Monitor Log Viewer.  This change means that the STAX Monitor now requires
    STAF V3.3.2 or later as it is dependent on changes made to the
    STAFLogViewer which is part of STAF Java support (Feature #2278018)
  - Fixed a problem where STAXMonitorSubjobExtension wasn't calling the
    STAFLogViewer passing it the selected font name (Bug #2406885)
  - Fixed a NullPointerException in the STAX Monitor in STAXMonitorUtil.
    updateRowHeights() at line 392 (Bug #2407025)
  - Fixed how the STAX Monitor handles cancelling changes to some properties
    on the Property panel's Option tab like Font (Bug #2406891)

-------------------------------------------------------------------------------

Version 3.3.4: 09/24/2008

  - Updated libraries/STAFUpgradeUtil.xml to support upgrading to STAF V3.3.0
    or later (Bug #2014202)
  - Improved the error message you get when executing a STAX XML document that
    references an external entity (Bug #2040679)  
  + Made some usability improvements for STAX testcases (Feature #1757703):
    - Added a "Start Date-Time" to the STAX QUERY TESTCASE output
    - Added "Start Date-Time" and "Status Date-Time" columns to the "Testcase
      Info" panel in the STAX Monitor, and provided the ability to sort
      testcases by these columns
    - Added an icon to the STAX Monitor column headers to indicate whether
      the column is being sorted in ascending or descending order
    - Added the ability to configure which columns will be displayed in the
      STAX Monitor's "Testcase Info" panel and to specify a default sort
      column and order
  - Updated libraries/STAFUpgradeUtil.xml to prevent the UpgradeSTAF STAX job
    from hanging if a process completion message cannot be sent (Bug #2050252)
  - Fixed problem where the STAX Monitor would sometimes show processes and
    STAF commands as still running even though they had completed
    (Bug #2104536)
  - Fixed a ClassCastException viewing a STAX job log via the "Sub-jobs" tab
    of the "Active Job Elements" panel for the STAX Monitor (Bug #2116911)  
  - Fixed a NullPointerException when using the STAX Monitor to monitor a job
    that contains subjobs (Bug #2117036)
  - Fixed a problem where STAF static handles were created each time the STAX
    Monitor was used to view a STAX job log or JVM log, but were never deleted
    (Bug #2116623)
  + Provided the ability to get the hierarchy information for a testcase name
    (Feature #1789697)
  - Fixed a problem where using the STAX Monitor remotely would slow down STAX
    job execution (Bug #1922591)

-------------------------------------------------------------------------------

Version 3.3.3: 06/27/2008

  - Changed to have the STAX registration fail if using an unsupported JVM,
    instead of later getting a STAXXMLParseException when executing a STAX job
    (Bug #1904889)
  - Fixed a problem where the logtcstartstop attribute on a &lt;job> element in
    a STAX job no longer worked due to a regression in V3.3.2 (Bug #1939391)
  - Fixed a missing log records problem caused by RC 13 errors logged to the
    STAX JVM log that occurred if the LOG service is configured to resolve
    variables in messages (Bug #1959750)
  - Changed how libraries/STAFUpgradeUtil.xml checks if AIX machine supports
    64-bit so it works if STAFProc is running as non-root (Bug #1964776)

-------------------------------------------------------------------------------

Version 3.3.2: 02/26/2008

  + Added the ability to redirect Python output in a STAX Job (e.g. from
    "print" statements within a script element) to the STAX Job User Log by
    default, and/or to the STAX Monitor's Messages panel (Feature #1867201)
  - Fixed Python exception "TypeError: unhashable type" seen in the JVM Log if
    a STAX job is run whose main function returns a STAXGlobal variable that
    contains a list or dictionary (Bug #1883108)
  - Changed libraries/STAFUpgradeUtil.xml to check if AIX is running a 64-bit
    kernel before choosing the 64-bit STAF AIX installer file if a 64-bit
    processor type is preferred (Bug #1889735)
  - Changed libraries/STAFUpgradeUtil.xml to verify the 'installShieldTempDir'
    argument during the verification phase and to create the directory if it
    doesn't exist (Bug #1898197)
  + Changed STAF license from the Common Public License (CPL) 1.0 to the
    Eclipse Public License (EPL) 1.0 (Feature #1893042)

-------------------------------------------------------------------------------

Version 3.3.1: 12/11/2007

  - Fixed a problem in libraries/STAFUpgradeUtil.xml where if using a tar file
    to install STAF on Solaris, STAFInst could fail with error:  Source and
    target directories are the same (Bug #1810319)
  - Fixed a problem in STAXDoc so that all links in the generated HTML files
    use a "/" and not "\" so that the links work on any platform (Bug #1818230)
  - Fixed a RC 4001 executing a STAX job due to a ClassCastException if the JVM
    is configured to use a XSLT transformer other than Xalan (Bug #1820166)
  - Added information on how to get the stax.dtd file when using an XML Editor
    to the Getting Started With STAX guide (Bug #1824737)
  = For performance reasons, cached the large string containing Python code
    run when initializing the main thread for each STAX job (Bug #1841242)
  - Improved the error message logged in the STAX Job log for any unhandled
    conditions (Bug #1848065)

-------------------------------------------------------------------------------

Version 3.3.0.1: 10/05/2007

  - Fixed NullPointerException in the STAX Monitor when saving changes to the
    STAX Monitor properties, such as the STAX service name (Bug #1808263)

-------------------------------------------------------------------------------

Version 3.3.0: 10/02/2007

  + Added support for upgrading STAF on Mac OS X Intel and PPC machines to
    libraries/STAFUpgradeUtil.xml (Feature #651053)
  + Updated the STAX Monitor to save the File Browse directory when the
    STAX Monitor is restarted (Feature #605639)
  - Fixed problem where the SCRIPTFILEMACHINE option was included on the STAX
    EXECUTE request when no script files were specified (Bug #1761498)
  + Updated the STAX Monitor to save the last Job Parms directory when the
    STAX Monitor is restarted (Feature #909255)
  - Added documentation for the job status in the result from a STAX EXECUTE
    WAIT RETURNRESULT request (Bug #1771222)
  - Fixed syntax error in the &lt;function> scope examples in the User's Guide
    (Bug #1777117)
  - Fixed syntax error in a message example in the User's Guide (Bug #1778210)
  - Changed the default STAXLogError signal handler to log to the JVM Log
    instead of to a Job log to avoid a duplicate signal error (Bug #1778948)
  - Fixed a problem to set variables like RC, STAFResult, and STAXResult only
    when elements like stafcmd, process, job, and timer end (Bug #1780591)
  - Fixed a problem where a thread wasn't being scheduled in an obsure error
    condition when starting a process (Bug #1793549)
  - Fixed a typo in an example for stafcmd in the User's Guide (Bug #1796763)
  - Fixed a problem "slicing" a STAXGlobal variable (Bug #1798346)
  - Updated the STAX Monitor to adjust the size of some dialogs based on the
    number of lines in the message (Bug #1797182)   
  - Improved the error handling when the "other" &lt;function-arg-def> argument
    is not specified as the last argument in a &lt;function-list-args> or
    &lt;function-map-args> element (Bug #1797495)
  - Corrected the STAX User's Guide to indicate that the "type" attribute for
    &lt;function-arg-def> is optional and defaults to "required" (Bug #1799300)
  + Added file caching for STAX xml files (specified via an EXECUTE request or
    via an import element) to improve performance (Feature #1759808)
  + Added the ability to view, control, and clear the file cache
    (Feature #1759810)
  - Fixed a minor documentation error in STAXUtil.xml (Bug #1802063)
  - Fixed a problem where a Python error in a function-arg-def's default
    attribute value was not being detected before run-time (Bug #1803034)  
  + Added information to the STAX User's Guide to document the events
    generated by the STAX service that provide job status (Feature #1799659)
  + Updated the STAX Monitor to allow recently displayed STAX job/user logs
    to be redisplayed (Feature #1803936)
  + Improved debugging by providing line number, file, and machine information
    when an error occurs during compile time or run time (Feature #1737411)
  - Fixed a problem where if you specified the FUNCTION option but not thet
    ARGS option, the function arguments were not always None (Bug #1805358)  

-------------------------------------------------------------------------------

Version 3.2.1: 05/21/2007

  - Fixed a Python "NameError: false" problem comparing a STAXGlobal variable
    without using it's get method if it was defined as a numeric or a string
    (Bug #1679340)
  - Fixed a problem handling some "dangling" conditions for function, loop,
    iterate, try, and catch elements that could result in unexpected behavior
    (Bug #1680219)
  - Fixed a NullPointerException in STAXSTAFCommandAction.handleCondition()
    (Bug #1682234)
  - Fixed a problem where the process and job elements don't terminate their
    action thread properly in certain conditions (Bug #1683989)
  + Added a finally element to the try/catch block such that the finally task
    is executed no matter whether the try task completes normally or abruptly,
    and no matter whether a catch task is first given control (Feature #605790)
  - Created a separate "STAXGlobal Class" section in the STAX User's Guide and
    added examples of how to prevent a STAXGlobal from being updated
    simultaneously by two threads (Bug #1679376)
  - Added examples showing how to override a STAX signal if Python error occurs
    to the STAX User's Guide and how to perform clean-up before a job is
    terminated (Bug #1679402)
  - Improved the call stack information provided by the iterate and other
    elements when querying a thread in a STAX job (Bug #1707655)
  - Fixed a NullPointerException in STAXBlockAction.terminateBlock() that could
    occur when terminating a block that just started (Bug #1708884)
  - Fixed a problem in the loop element to evaluate the until expression (if
    specified) at the bottom of the loop (Bug #1711464)
  + Added a new STAX variable named STAXBlockRC which is set whenever a block
    element exits to indicate whether the block was terminated, not terminated,
    or could not be started (Feature #801222)
  + Added an indication of whether a STAX job completed normally, abnormally,
    or was terminated.  Added a status field to the job completion message and
    to the result from a STAX EXECUTE WAIT RETURNRESULT request, and added a
    new STAX variable named STAXSubJobStatus that is set when a job element
    completes (Feature #1679929)
  + Added a new "info" status to allow updating the last status message for a
    testcase without recording a pass or fail and added the last status message
    as part of the result from LIST TESTCASES and QUERY TESTCASE requests
    (Feature #1715211)
  - Fixed a problem where the number of starts recorded for a testcase could be
    wrong when using both the testcase element and the UPDATE TESTCASE request
    (Bug #1721042)
  - Fixed a NumberFormatException in STAXJob::submitAsync and STAXSTAFMessage
    which could occur if STAF request number > 2,147,483,647 (Bug #1722260)
  
-------------------------------------------------------------------------------

Version 3.2.0: 02/28/2007

  - Changed libraries/STAFUpgradeUtil.xml to always use the tar.gz file when
    upgrading STAF on Solaris 2.6 as InstallShield 11.5 (used by STAF V3.2.0+)
    does not support Solaris 2.6 (Bug #1595973)
  - Documented the RETURNDETAILS option for a STAX EXECUTE TEST request in the
    help and in the STAX User's Guide (Bug #1604748)
  - Fixed negative timeout value exception in STAXTimedEventQueue.run()
    (Bug #1611751)
  - Added clarification on what holding a block means (Bug #1611330)
  + Changed so that jobs no longer share the module cache sys.modules and the
    module search path sys.path as this was never intended (Feature #811828)
  + Added an if attribute to the terminate, hold, and release elements
    (Feature #1160891)
  - Fixed a typo in STAX User's Guide on signalhandler example (Bug #1622652)  
  - Fixed ArrayIndexOutOfBoundsException when deleting Script Files in the
    STAX Monitor (Bug #1614926)
  - Changed the STAX Monitor to use the STAFLogViewer class to display STAX job
    logs (requires STAF V3.1.5 or later) (Bug #1638029)
  + Added support for upgrading STAF on Solaris x86 32-bit machines to
    libraries/STAFUpgradeUtil.xml (Feature #1075496)
  + Provided the ability to view the STAX JVM log (or any other JVM log) via
    the STAX Monitor if running STAF V3.2.1 or later which contains the
    STAFJVMLogViewer class in JSTAF.jar (Feature #1633551)
  - Fixed NullPointerException in STAXJob$STAFQueueMonitor methods
    (Bug #1656352) 
  - Fixed ConcurrentModificationException in STAXJob.getCompiledPyCode
    (Bug #1656340)
  - Changed some STAX classes to use StringBuffer when concatenating lots of
    data for better performance (Bug #1659236)
  - Fixed "AttributeError: 'string' object has no attribute 'copy'" problem
    where a job would hang if assigned a variable named "copy" in a job because
    the STAX service uses the Python "copy" module (Bug #1660870)
  - Fixed AWTError in the STAX Monitor when using the Job Wizard (Bug #1656951)
  + Added support for upgrading STAF on FreeBSD i386 machines to
    libraries/STAFUpgradeUtil.xml (Feature #578893)
  - Fixed problem in libraries/STAFUpgradeUtil.xml where if using a tar file
    to install STAF, STAFInst could fail with error:  Source and target
    directories are the same (Bug #1671182)

-------------------------------------------------------------------------------

Version 3.1.5: 11/09/2006

  - Fixed a problem in libraries/STAFUpgradeUtil.xml where a STAF Upgrade failed
    if the STAF install directory contained a space (Bug #1524889)
  - Fixed a problem in libraries/STAFUpgradeUtil.xml where a STAF Upgrade could
    fail doing an InstallShield install on AIX if the options length > 399
    (Bug #1526137)
  - Added more information to the STAX User's Guide about process stdout/stderr
    files and on using a timer element to stop a process (Bug #1546778)
  = Updated the manifest to specify Jython version 2.1-staf-v3.  This version
    changed the marshall and formatObject methods in Lib/STAFMarshalling.py to
    significantly improve their performance (Bug #1559277)
  - Updated the STAX User's Guide to be more specific that the process
    sub-elements "must" be in the order listed (Bug #1566585)
  - Fixed how the STAXCurrentFunction variable is set (Bug #1567497)
  - Added documentation for the "if" attribute for optional process elements in
    the Concepts section of the STAX User's Guide (Bug #1567043)
  - Changed to set the STAFResult variable to a process timeout msg (instead of
    None) if a process fails to start within the timeout value (Bug #1570848)
  - Added documentation on how to create a new STAF handle and to get messages
    from this handle's queue from within a STAX job (Bug #1573897)
  - Fixed a problem where the job result was not always set to None when a STAX
    job was terminated, such as by a STAXPythonEvaluationError (Bug #1565708)  
  - Fixed StreamCorruptedException and UTFDataFormatException errors when
    starting the STAX Monitor (Bug #1550137) 
 
-------------------------------------------------------------------------------

Version 3.1.4: 07/12/2006

  - Increased the size of the error dialog displayed by the STAX Monitor for a
    long error message to make it easier to view the message (Bug #1520850)
  + Added LOG and SEND MESSAGE requests to the STAX service to provide the
    ability to log a message in the STAX Job User log and/or to send a message
    to the STAX Monitor (Feature #1517769)

-------------------------------------------------------------------------------

Version 3.1.3: 06/27/2006

  + Added a STAX variable for the current function name, STAXCurrentFunction
    (Feature #1495672)
  + Added call/condition stack information to the signal message logged when a
    STAX signal is raised to improve debugging and provided a common format for
    signal messages (Feature #1495669)
  - Updated the STAX Monitor Log Viewer so that it will correctly remove all of
    the log records if the refresh or level mask change returns no log records
    (Bug #1505613)
  + Added ability to specify the focus (e.g. minimized, foreground) for windows
    opened by a &lt;process> element run on a Windows system (Feature #1495665)
  - Fixed a problem in the STAX Monitor where info provided for processes
    currently running in a STAX job was inconsistent/incomplete (Bug #1507532)
  - Fixed the result for a STAX QUERY JOB &lt;JobID> PROCESS &lt;Location:Handle>
    where the stopUsing/stderrFile values could be incorrect (Bug #1511950)
  - Updated STAXDoc to display text for the function-other-args element
    correctly (Bug #1509342)
  - Updated STAXDoc to display only the first sentence for a function prolog/
    description on each Function Summary page by default and added a new
    -functionsummary All option if you prefer the old format (Bug #1509328)

-------------------------------------------------------------------------------

Version 3.1.2: 05/18/2006

  - Fixed a problem calling a function like has_key() directly on a STAXGlobal
    instance without having to call it's get() method first (Bug #1408579)
  = Updated the manifest to specify Jython version 2.1-staf-v2.  This version
    changed the marshall method in Lib/STAFMarshalling.py to check for objects
    with a callable stafMarshall method (Bug #1408579)
  - Fixed a problem marshalling a STAXGlobal object by defining a stafMarshall
    method for the STAXGlobal class (Bug #1457971)
  - Fixed slow STAX Monitor performance when running processes in parallel,
    and changed the default process monitor update interval to 60 seconds
    (Bug #1384638)
  + Provided a STAX library (libraries/STAFUpgradeUtil.xml) which contains a
    function named STAFUpgrade that upgrades the version of STAF running on a
    remote machine.  Also, provided a sample STAX job (samples/UpgradeSTAF.xml)
    that shows how to call this function to upgrade STAF on one or more remote
    machines simultaneously (Feature #1482655)
  - Fixed problem in the STAXDoc and FunctionList XSL stylesheets for
    handling properties for function-arg-def elements (Bug #1485081).    
  - Fixed delay in STAF shutdown if STAX jobs have been submitted
    (Bug #1480729)
  - Fixed problem where the STAX Monitor would reformat the data on the
    Submit Job tabs (Bug #1485091)

-------------------------------------------------------------------------------

Version 3.1.1: 12/07/2005

  - Fixed NullPointerException when listing threads or querying threads for a
    job that doesn't have any threads running (Bug #1329497)
  - Fixed NullPointerException that can occur in an error situation when
    running the STAX Monitor (Bug #1330198)
  - Made various updates for building STAX documentation (Bug #1336795)  
  - Fixed problem where the &lt;function-arg-property-data> information was being
    returned in random order (Bug #1314273)
  - Added automatic import of STAFMarshalling.py's STAFMarshallingContext and
    STAFMapClassDefinition classes (Bug #1369632)
  
-------------------------------------------------------------------------------

Version 3.1.0: 09/29/2005

  - Fixed problem where the Monitored green ball icon was sometimes not
    displaying correctly in the STAX Monitor (Bug #1262534)
  + Added a KEY option when specifying a EXECUTE NOTIFY ONEND request and added
    the key to the STAX Job End notification message (Feature #1281018)
  + Provided the ability to mask passwords and other sensitive data
    (Feature #622392)
  + Updated to require STAF V3.1.0 or later (so can use new privacy methods)
    and changed to use new STAF version compare methods and deprecated the
    STAXVersion class (Feature #1292268)
  + Added the ability to specify properties for function arguments
    (Feature #1279520)

-------------------------------------------------------------------------------

Version 3.0.2: 08/15/2005
   
  - Fixed some typos in STAX User's Guide for &lt;process> example (Bug #1238388)
  = Changed to not use enum as a Java variable name so can compile using 
    Java 5.0 since enum is now a Java keyword (Bug #1241613)
  - Fixed STAXPythonEvaluationException in the STAXUtilImportSTAFVars function
    (in STAXUtil.xml) if only one variable is being imported (Bug #1241493)
  - Fixed problem in STAXUtil.xml's STAFProcessUsing function to handle
    redirecting stderr to stdout corrrectly (Bug #1247056)
  - Fixed problem where multi-line Job results were not displayed correctly in
    the STAX Monitor (Bug #1256760)
  - Fixed exceptions in STAX Monitor when returning marshalled data in the job
    result and added logging a Status message that contains the job result in
    the STAX Job log (Bug #1255951)
  - Fixed problem where the job result may not be None if an error occurs 
    evaluating the value for a &lt;return> element (Bug #1257026)

-------------------------------------------------------------------------------

Version 3.0.1: 06/27/2005
   
  - Fixed STAX Monitor Job Wizard problems with saved function arguments
    (Bug #1190449)
  - Document that the STAX Service requires Java 1.4 or later (Bug #1192762)
  - Fixed typo in the STAX User's Guide for a &lt;job> example (Bug #1195335)
  - Fixed problem in &lt;loop> where the until expression was being evaluated at
    the top of each loop instead of at the bottom of each loop (Bug #1196936)
  - Fixed incorrect CLASSPATH &lt;env> in the Getting Started with STAX document
    (Bug #1200975)
  - Fixed a typo for an example in the STAX User's Guide (Bug #1202268)
  + Updated LIST SETTINGS request to display Extension file and Extension
    parameters (Feature #989754)  
  - Fixed an error in the STAXDoc User's Guide showing how to reference a
    miscellaneous unprocessed file via the function-prolog tag (Bug #1216712)
  - Added STAX Extensions File DTD to the STAX zip/jar file (Bug #1216745)
  - Fixed RunDelayRequest.xml typo in the Getting Started with STAX 
    document (Bug #1219939)
  + Added ability to be notified when a STAX job ends by adding a NOTIFY ONEND
    option to the EXECUTE request.  Also, added the ability to list job end
    notifications via a new NOTIFY LIST request and to register/unregister to
    be notified via new NOTIFY REGISTER/UNREGISTER requests (Feature #1217858)
   
-------------------------------------------------------------------------------

Version 3.0.0: 04/21/2005

  - Improved error message when get RC 25 (Access Denied) when submitting a
    STAX EXECUTE request (Bug #1101852)
  - Fixed NullPointerException if STAX VERSION request fails due to problem
    accessing the STAX service machine (Bug #1105669)
  - Fixed STAX Monitor Job Wizard errors in generating function argument values
    (Bug #1042460)
  + Provided two new Python variables, STAXJobStartFunctionName and
    STAXJobStartFunctionArgs (Feature #1110050)
  - Fixed problem in STAXUtilExportsSTAFVars function handling variables 
    containing double quotes (Bug #1114317)
  - Improved error message provided for RC 25 (Access Denied) for all requests
    (Bug #1054858)  
  - Use default STAX Monitor properties if the monprp.ser file is corrupted
    (Bug #1118227)
  = Changed to use Xerces2-J 2.6.2 as the XML parser (Feature #1045560)
  = Removed the zxJDBC code from our distribution of Jython (Bug #1118221)
  + Provided new Python variables (STAXJobLogName, STAXJobUserLogName, and
    STAXServiceMachineNickname) for easier querying of STAX job logs within a
    STAX job (Feature #1121048)
  - Fixed the problem where the CLEARLOGS option wasn't working if you use
    the SET MACHINENICKNAME config setting in your STAF.cfg file (Bug #1121129)
  - Changed the STAX EXECUTE request so that the values for xml source machine,
    MACHINE, and SCRIPTFILEMACHINE default to the requesting machine's endpoint
    (Bug #1145688)
  - Fixed problem in sample1.xml where TestProcess could not be executed
    (Bug #1151253)
  - Changed to use STAFUtil's common resolve variable methods (Bug #1151440)
  + Added ability to log and send a message to the STAX Monitor via a &lt;log> or
    &lt;message> element (Feature #1101006)
  - Changed license from GPL to CPL for all source code (Bug #1149491)
  - Fixed so that STAXPythonEvaluationErrors for 'STAXPyEvalResult =' no longer
    occur (Bug #1158649)
  - Changed to handle STAXGlobal list objects in the iterate/paralleliterate
    elements without specifying the get() method (Bug #1156045)
  + Added support for setting Monitor properties for the font name to use when
    displaying messages in the Messages and Log Viewer panels (Feature #845900)
  - Changed to use Monospaced font for text areas in the STAX Monitor where
    Python code is specified (Bug #1163364)
  - Fixed problem using STAX Monitor's Job Wizard where if you enter more than
    one line of text for an argument, lines 1-n are not saved (Bug #1164120)
  - Fixed problem resubmitting a job via STAX Monitor if select "Local machine"
    for the Script File Machine (Bug #1164868) 
  + Added the formatObject function to STAFMarshalling.py to allow "pretty
    printing" of structured objects and marshalling contexts and documented
    the marshalling/unmarshalling classes and functions (Feature #740150)
  + Added a new tool called STAXDoc that allows you to easily generate HTML
    documentation for STAX xml files (Feature #1156926)   

-------------------------------------------------------------------------------

Version 3.0.0 Beta 7: 12/13/2004

  - Fixed job hang problem that could occur if a Python evaluation error occurs
    evaluating a list such as iterate's "in" attribute (Bug #1075469)
  - Fixed race condition in call element (Bug #1073774)
  - Fixed bug where PyDictionaries (aka maps) were not being cloned using a
    deep-copy when creating a new STAX Thread, unlike other Python objects such
    as PyLists (Bug #1075652)
  + Changed to "pretty print" the marshalled queued message when logging a
    "No listener found" warning for better readability (Feature #740150)
  - Fixed bug where viewing STAX Job logs by right-clicking from "Sub-jobs" tab
    fails if the machine nickname has been overridden (Bug #1079543)
  - Fixed problem getting Monitor service messages for processes (Bug #1079583)
  - Fixed problems running STAX Monitor in a STAF 2.x/STAX.3x environment
    (Bug #1079611)
  - Changed STAX Monitor to use "local" if specified for the STAX/Event machine
    in the properties and via process and stafcmd elements (Bug #1083287)
    
-------------------------------------------------------------------------------

Version 3.0.0 Beta 6: 11/19/2004

  + Changed the STAX Monitor Log Viewer to specify the ALL option when querying
    a STAX log in case the LOG service limits the default maximum records
    returned on a QUERY request (Feature #1040232)
    
-------------------------------------------------------------------------------

Version 3.0.0 Beta 5a: 11/09/2004

  + Changed &lt;stafcmd> to use Python unmarshalling APIs instead of Java
    unmarshalling APIs so that the STAFResult for requests with multiple values
    such as LIST/QUERY contain Python Lists and Maps instead of Java Lists and
    Maps (Feature #740150)
  
-------------------------------------------------------------------------------

Version 3.0.0 Beta 5: 10/30/2004

  - Fixed problem iterating Java list objects (Bug #1043148)
  + Changed to return STAFResult from init/term methods (Feature #584049)
  + Updated to handle new queue type for queued messages (Feature #1044711)
  + Updated STAX service to handle new marshalled messages in queued messages,
    as well as handling the old message format from STAF 2.x machines, and to
    generate events with a message that is now a marshalled map.  Also, updated
    STAX Monitor to handle the new messages format (Feature #740150)
  + Updated to not use the effective machine name as it has been removed
    (Feature #550251)
  + Changed to use new STAFServiceInterfaceLevel30 (Feature #550251)
  + Added condition stack to output when querying a STAX thread for help in
    debugging problems (Feature #1054755)  
  - Fixed problem re-selecting rows in the STAX Monitor Active Processes and
    Active STAFCmds tables (Bug #1061183)
  
-------------------------------------------------------------------------------

Version 3.0.0 Beta 4: 09/29/2004

  - Fixed memory leak running a sub-job (Bug #981548)  
  - Changed to use upper-case STAX service name when setting the STAXJobUserLog
    variable and in the STAX Monitor (Bug #982163)
  + Added new Python variables for the STAX service name/machine and the
    Event service name/machine (Feature #982109)
  - Fixed some variable resolution problems (Bug #986196)
  + Made some usability enhancements when setting STAX Monitor properties:
    - Automatically update Event machine/service based on the specified STAX
      service's Event settings
      Note:  Can no longer edit Event machine/service property fields
    - Allow local to be specified for the STAX Service machine
    (Feature #987502)
  - Fixed problem where &lt;defaultcall> passed None to the function if no
    arguments were specified (Bug #991804)
  - Fixed typo to specify function-no-args, not function-no-arg, in STAX User's
    Guide (Bug #1003544)
  - Fixed typo for loop example in STAX User's Guide (Bug #1004127)
  + Added support for new format for multi-valued results (marshalled results):
    - Updated STAX service to marshall multi-valued results
    - Updated &lt;stafcmd> to unmarshall the result before assigning to STAFResult
      and to set a new variable named STAFResultContext to contain the
      marshalling context object.
    - Updated STAX Monitor to handle marshalled results
    (Feature #740150)
  - Fixed STAXThread::pyListEval() to handle single item better (Bug #1017855)
  - Fixed so that a command parsing error on an EXECUTE request returns RC 7,
    "Invalid Request String", instead of RC 1, "Invalid API" (Bug #1020590).
  - Fixed location substitution for 'local' for a stafcmd when it's details
    are displayed in the STAX Monitor (Bug #1033015)
      
-------------------------------------------------------------------------------

Version 3.0.0 Beta 3: 06/28/2004

  - Fixed problem where job would hang if called a function passing too many
    map/list arguments and if an extra argument is a STAXGlobal (Bug #945541)
  - Clarified STAXGlobal variable scope in STAX User's Guide (Bug #950525)
  - Fixed memory leak in the STAX service (Bug #958312)
  + Added new arguments when starting the STAX Monitor to configure its
    properties (Feature #909254)
  - Fixed problem where STAX Monitor was using wrong SCRIPTFILEMACHINE for
    local if XML Job File machine was not the local machine (Bug #803485)
  - Fixed problem where "global" variables are not visible to functions
    defined in one STAXPythonInterpreter when the function is called from a
    cloned STAXPythonInterpreter (Bug #960415)
  + Added un-register of the service handle during term() (Feature #966079)
  - Fixed NumberFormatException in STAXTestcase.addElapsedTime (Bug #974925)
  + Added a STAXJobScriptFiles variable (Feature #977071)
  - Fixed "TypeError: call of non-function" problem where a job would log this
    error and hang if a variable named "type" was assigned in a job since the
    STAX service uses the Python built-in function named "type" (Bug #981435)
  
-------------------------------------------------------------------------------

Version 3.0.0 Beta 2: 04/29/2004

  + Updated to use XML Parser for Java (Xerces) version 1.3.0 (Feature #931256)
  + Updated to record more info from exceptions when parsing (Feature #931415)
  + Updated to use STAFServiceInterfaceLevel5, only supported in STAF V3.0.0,
    and to use new syntax for the VAR service (Feature #464843) 
  + Updated to use the new info.writeLocation field and to create a directory
    for each STAX job, &lt;info.writeLocation>/service/&lt;serviceName>/job/
    Job&lt;JobNumber> and set Python variable, STAXJobWriteLocation, to this
    directory so that it can be used in STAX jobs as a working area where files
    can be created/copied, etc. (Feature #592875)

-------------------------------------------------------------------------------

Version 1.5.2.1: 04/07/2004

  - Types for function list and map arguments not retained (Bug #931201)

-------------------------------------------------------------------------------

Version 1.5.2: 04/01/2004

  - Synchronize the Process/STAFCmd number and Process key increment methods
    (Bug #913141)
  - Fix NullPointerException closing the STAX Monitor (Bug #914357)
  + Add import of STAFResult class as STAFRC to make it's constant definitions
    for all the STAF return codes available to STAX jobs (Feature #915820)
  - Don't return a STAXInvalidStartFunction error when using the TEST option
    (but not a starting FUNCTION option) for a xml job file that does not
    contain a &lt;defaultcall> element (Bug #922757)
  - Fixed STAX Monitor Job Wizard problem when the XML file path contains
    spaces (Bug #922989)
  - Fixed problem where RC 7 was not being returned when using the PROCESS KEY
    option for &lt;process> elements on pre-V2.6.0 STAF machines (Bug #923350)

-------------------------------------------------------------------------------

Version 1.5.1: 03/03/2004

  - Fixed problem displaying job logs from main STAX Monitor window if STAX
    service name is not STAX (Bug #891895).
  - Fixed StringIndexOutOfBoundsExceptions in STAX Monitor which could occur
    monitoring a job if the STAX service name is not STAX (Bug #891970)
  - Fixed errors deleting temporary files/directory for extension jar files
    when starting the STAX Monitor (Bug #895302)
  + Made the STAX Monitor STAX and Event hostname text entry fields larger
    (Feature #837327)
  - Fixed STAX Monitor extension "leakage" problem where extension information
    for other jobs was showing up in the last job monitored (Bug #900523)
  + Instrumented the STAX service's handling of EXECUTE WAIT RETURNRESULT,
    LIST, and QUERY, SET requests to record diagnostics data to help prepare
    for the migration to STAF V3.0.  The STAX service requires STAF V2.6.0 or
    later as a result of this change. (Feature #853620)  
  + Updated the STAX &lt;process> element to use the notify key when submitting
    Process Start commands (Feature #626917)

-------------------------------------------------------------------------------

Version 1.5.0: 01/26/2004

  - Fixed problem in the STAXUtilImportSTAFVars function in STAXUtil.xml where
    it would fail if only one STAF variable was specified (Bug #816775)
  - Fixed error where STAX Monitor would not display log entries for levels
    Debug2/3, Trace 2/3, and User 1/8 (Bug #816882)
  - Fixed problem where the STAXMonitor's Current Selection tab displayed an
    extraneous vertical scrollbar (Bug #807198)
  + Added pyMapEval function (Feature #821394)
  - Fixed problem where STAX Monitor's Testcase Info table was not displaying
    testcases with a strict mode and 0 passes/fails (Bug #821471)
  - Added CLEARLOGS option to STAX QUERY JOB output (Bug #821926)
  - Fixed RC 7 trying to re-submit STAX job to run via STAX Monitor
    (Bug #817339)
  - Fixed problem where the entire STAX Monitor Testcase Information was not
    being displayed (Bug #825284)
  - Display stack trace when STAX Monitor extensions throw an 
    InvocationTargetException (Bug #825649)
  + Added support for logging the elapsed time and/or the number of starts for
    testcases and provided this info in the STAX Monitor.  Also, added support
    for logging testcase starts and stops in the STAX Job log.
    Note that the default settings for the STAX service log options are:
      Clear logs         : Disabled
      Log TC Elapsed Time: Enabled
      Log TC Num Starts  : Enabled
      Log TC Start/Stop  : Disabled
    These default settings can be changed when registering the STAX service or
    via the new SET option, or overridden by job when submitting a STAX job 
    for execution. (Feature #795402)
  - Fixed problem where STAX logs could not be displayed in the STAX Monitor
    if the service name was not "STAX" (Bug #836579)
  + Made the STAX Monitor STAX and Event hostname text entry fields larger
    (Feature #837327)
  + Added versioning support for STAX extensions including:  
    - Listing/querying STAX extensions, their versions, and other information
    - Support for a new EXTENSIONXMLFILE parameter to specify STAX extensions
      via XML when configuring the STAX service, including the ability to
      specify parameters for STAX extensions.
    - Ability to specify the version and description for a STAX extension
      via the manifest for the extension jar file  
    - Ability to specify that a STAX extension requires a particular STAX
      service and/or STAX Monitor version. (Feature #818693)
  + Enhanced STAX extension samples.  For example, modified the delay
    extension to demonstrate how to process parameters passed to extensions.
    (Feature #846091)
  + Provide a STAX Extensions Developer's Guide and document how to register
    STAX service/monitor extensions in the STAX User's Guide (Feature #846103)
  - Fixed Extensions tab colors on the Monitor Properties panel (Bug #852724)
  - Added a new section to the STAX User's Guide, Appendix G: Jython and
    CPython Differences (Bug #853490)
  - Fixed resource leak by closing BufferedReaders (Bug #853596)
  + Enhanced support for STAX Monitor Extensions including:
    - Automatically obtains any monitor extensions registered with the STAX
      service machine
    - Verifies that the required version of the STAX Monitor specified for
      each monitor extension is installed
    - Provides the ability to display information about registered monitor
      extensions via a new Properties->Extensions tab or via a new -extensions
      parameter when starting the STAX Monitor.
    - Renamed the old Properties->Extensions tab to Extension Jars to allow
      you to override or add local monitor extensions (Feature #853596)
  + Changed the EXECUTE request so that it now compiles all Python code in a
    STAX Job before running the job and if using the TEST option, so that all
    Python code compile errors are reported immediately (Feature #874173) 
  + Allow STAX Monitor Job and Job User log tables to be selectable, so they
    can be copied to the clipboard (Feature #845931)
  + Improved performance of the STAX service by caching compiled Python code
    (Feature #872627)
  + Register error messages for the STAX service with the HELP service
    (Feature #605788)
  + Added a STAX Monitor Job Wizard which guides the selection of functions 
    and specification of function arguments (Feature #826094)
  + Improved STAX Monitor CPU performance (Feature #879299)
  + Added STAX Monitor options to configure the Elapsed Time and Process 
    Monitor intervals (Feature #854416)
  - Fixed ArrayIndexOutOfBoundsException which can occur when items are 
    removed from the Active Processes, STAFCmds, or SubJobs tables
    (Bug #880229)
  - Fixed NullPointerException in the STAX Monitor when a service request 
    option ends with a semi-colon (Bug #880876)
  - Fixed repaint problem with STAX Monitor Info extensions (Bug #883873)
  - Fixed problem where STAX Monitor extensions to the Active Job Elements tree
    would not have the tree selection background color when selected
    (Bug #885150)
    
-------------------------------------------------------------------------------

Version 1.4.1: 09/29/2003

  - Fixed problem where if a process returned a negative RC, STAX was setting
    it to 0 instead.  Now if a negative RC is returned, the RC will be set to a
    large number, e.g. 4294967295 instead of -1 (Bug #752936)
  - Added more information about using &lt; instead of &lt; in STAX xml files to
    the STAX User's Guide (Bug #777205)  
  - Added more error checking to the STAX Monitor's STAX Job Parameters 
    dialog (Bug #784441)
  + Added an option to automatically monitor sub-jobs in the STAX Monitor
    and added a new attribute, monitor, to the &lt;job> element (Feature #784463)
  + Added a new STAX Utilities function called STAXUtilImportSTAFConfigVars to
    STAXUtil.xml which extracts system information into a map (Feature #785025)
  - Fixed a race condition with the &lt;process> element where a 'quick' process
    could hang the STAX job (Bug #790966)
  - Fixed problem where STAX Monitor extensions could not update the Active
    Job Elements tree when monitoring an existing job (Bug #807527)
  - Increased flexibility in how the STAX Monitor finds the JSTAF.jar file
    when started via 'java -jar STAXMon.jar' (Bug #808980)
  - Fixed a function list/map argument type conversion problem (Bug #814783)  
  - Allow STAX Monitor extensions to have both ID and Name (Bug #815346)
        
-------------------------------------------------------------------------------

Version 1.4.0: 06/04/2003

  - Fixed problem where the STAX Monitor was only displaying Log files from
    the local machine (Bug #715990)
  - Fixed problem where raising a STAXImportError signal for a parser exception
    on a Chinese system caused a Java exception (Bug #723409)
  - Fixed problem where the STAX Monitor was not unregistering handles for
    Monitor extensions (Bug #731079)
  - Added more debugging info when a STAXPythonEvaluationError signal is
    raised (Bug #731650)  
  + Added an example of aliasing a STAX extension (Feature #733606)
  - Fixed STAX User Guide to refer to the STAXFunctionArgValidate signal
    rather than the STAXFunctionValidateArg signal (Bug #735830)
  + Added support for a &lt;job> element so that sub-jobs can be executed within
    a STAX job (Feature #606780)
  - Made sure all variables set by the process element (e.g. STAXResult,
    STAFResult, STAXHandleProcess) are initialized (Bug #745586)
  - Fixed hang problem that could occur when a process with a process-action
    element was terminated via a condition like a timer popping (Bug #745534)
  - Fixed race condition causing erroneous STAXProcessStartTimeout (Bug #745749)
  - Fixed problem with STAX Monitor not resetting the SCRIPTFILEMACHINE 
    option (Bug #744460)
  + Added support to return the result of a job's starting function via
    a new RETURNRESULT option for a EXECUTE WAIT request (Feature #745813)

-------------------------------------------------------------------------------

Version 1.3.3: 03/18/2003

  - Made EXECUTE options TEST, HOLD, and WAIT mutually exclusive (Bug #652822)
  - Fixed bug where the STAX Monitor was using case-sensitive checks of the 
    STAX machine name for incoming events (Bug #656113) 
  + Added support to limit the number of displayed Messages displayed in the
    STAX Monitor (Feature #657077)
  + Added EXECUTE option CLEARLOGS which will delete the existing STAX Job 
    and Job User logs before the current STAX job is executed (Feature #656121)
  - Fixed memory leak in STAXMonitor's STAFCmd nodes (Bug #660208)
  - Fixed memory leak in STAXMonitor's job start timestamp table (Bug #660534)
  - Fixed NullPointerException in STAXMonitorFrame when specifying a pre-1.3.2
    saved job parms file (Bug #661311)
  + Added a PROCESSTIMEOUT option to ensure that process start requests do
    not hang (Feature #667512) 
  + Added STAX Monitor support for the CLEARLOGS option (Feature #682127)
  - Updated STAX User Guide for typo in DTD and added references to the
    STAXGlobal class and another example (Bug #702113)
 
-------------------------------------------------------------------------------

Version 1.3.2: 12/10/2002

  - Fixed NullPointerException in STAXMonitorUtil.getElapsedTime (Bug #623768)
  + Added support for extensions in the STAX Monitor (Feature #605637)
  + Use tabbed pane in the STAXMonitor Start New Job panel (Feature #629113)
  + Use tabbed pane in the STAXMonitor Properties panel (Feature #629226)
  + Added SCRIPTFILE support to the STAX Job Monitor (Feature #629119)
  + Added verification in the STAX Job Monitor that Extension Jar files
    exist (Feature #631222) 
  - Fixed Monitor extensions incorrectly displaying other Active Job Elements 
    node text (Bug #639846)
  - Fixed synchronization problem on STAX job end (Bug #639150)
  - Fixed empty call problem where a STAXPythonEvaluationException signal was
    incorrectly being raised (Bug #638535)
  - Fixed NumberFormatException in ProcessAction (Bug #631300)
  + Provided some common STAX Utility functions in libraries/STAXUtil.xml and
    added new elements function-prolog and function-epilog to replace the
    now deprecated function-description element (Feature #641425)
  + Added support to more easily restart STAX jobs via the STAX Job Monitor
    (Feature #639010)
  + Added support for WAIT option on EXECUTE request (Feature #643626)
  - Fixed problem specifying more than one notification type for STAX Monitor 
    extensions (Bug #649300)
  + Added support to be able to log to the STAX Job Log from a &lt;script) element
    or other Python code using a new STAXJobUserLog variable (Feature #651190)
  - Fixed ArrayIndexOutOfBoundsException when viewing a STAX Job User Log via
    the STAX Monitor (Bug #651213)  
  - Fixed problem where STAX Monitor was not unregistering with the Event
    service if File->Exit was selected (Bug #652223)
  - Fixed NullPointerException in the STAX Monitor when terminating the job 
    via the Active Job Elements tree (Bug #652362)

-------------------------------------------------------------------------------

Version 1.3.1: 10/08/2002

  + Added a shell attribute to the process command element to override the
    default shell by process (Feature #619831)
  - Fixed incorrect STAFResult after starting a process (Bug #621073)

-------------------------------------------------------------------------------

Version 1.3.0.1: 09/25/2002

  - Fixed STAXXMLParseException: Can't find bundle for base name
    org.apache.xerces.impl.msg.XMLMessages, locale en_US (Bug #614659)
    
-------------------------------------------------------------------------------

Version 1.3.0: 09/24/2002

  - Fixed incorrect default value for STAXJarFile argument in sample1.xml 
    (Bug #604616)
  - Fixed problem where you couldn't cast STAX Extension types because they
    had been loaded by different class loaders (Bug #604759)
  + Added submit methods to STAXJob for extensions (Feature #607073)
  + Added support for the latest version of the XML Parser for Java (V4.0.5)
    (Feature #607057)
  - Fixed message logged for "No listener found for msg" warning (Bug #608063)
  + Updated STAX Monitor to use tabbed-panes for the various panels which
    allows you to select what is displayed via the View menu (Feature #605631)
  + Pass in a HashMap to STAXJob.generateEvent (Feature #608083)
  - Fixed synchronization problem for STAF commands submitted via submitAsync
    (Bug #613413)

-------------------------------------------------------------------------------

Version 1.2.1: 08/26/2002

  - Changed the makefile for STAX due to changes in the STAF Java service jar
    class loader (STAF Bug #597392).  Requires STAF 2.4.1 or later.
  - Fixed problem where STAXSTAFCommandAction was not generating a Stop event
    if the user terminated the job (Bug #1261)
  - Fixed RC 4002 when starting jobs via the STAX Monitor on high-end machines
    (Bug #1268)

-------------------------------------------------------------------------------

NOTE:  Support numbers and 4-digit bug numbers shown in the history for STAX do
       not correspond to SourceForge Bug and Feature numbers as STAX was not
       made available on SourceForge until Version 1.2.0.
       
Version 1.2.0: 08/05/2002

  - Fixed unhandled STAXTerminateThreadCondition error logged when a
    STAXPythonEvaluationError signal is raised on a function return (Bug #1252)
  - Fixed STAX Monitor bug where you couldn't display logs with > 3 "|" chars 
    on a multi-line entry (Bug #1244)
  + Enhanced the STAX service to load directly from a jar file, without
    CLASSPATH updates or the prerequisite of installing Jython or any
    additional jar files and updated to terminate all running jobs when the
    service is terminated. Requires STAF 2.4.0 or later. (STAF Feature #561673)
  - Updated sample1.xml to specify the location of the STAX.jar file in case it
    is not in the CLASSPATH.
  - Fixed Unix problem where the STAX Monitor could not display the Job
    User Log (Bug #1233)
  + Created a new STAXMon.jar file that only contains STAX Monitor files and
    removed the STAX Monitor files from STAX.jar.  Now, can start the STAX
    Monitor via 'java -jar STAXMon.jar'.  See the STAX User Guide for more
    information (Support #100110).

-------------------------------------------------------------------------------

Version 1.1.2: 06/18/2002

  - Fixed race condition between STAXProcessAction and STAXProcessActionFactory
    (Bug #1218)
  - Fixed Static handle error on starting STAX Monitor with STAF 2.2.0 (Bug 
    #1217)
  - Fixed NullPointerException that can occur when handling a condition
    that ends a job that uses functions with local scope (Bug #1161).
  - When attempting to import another XML file, if a parsing error occurs, 
    inlcude the XML file name in the error message (Bug #1166).
  + Added support to the STAX Monitor to view the Job and User Logs 
    (Support #100103).
  + Added support for the exact same file name for a process's stdout and
    stderr elements (Support #100101).
  + Added support for specifying one or more files containing Python code
    via a SCRIPTFILE option to the EXECUTE request (Support #100104).
  + Added option to not display &lt;No Monitor info> message (Support #100105).
  
-------------------------------------------------------------------------------

Version 1.1.1: 04/10/2002

  - Fix for &lt;process> and &lt;call> not correctly setting STAXResult to None.
  - Fixed STAX Monitor Job Parms File incompatabilities between Java 1.3 and
    Java 1.4 (Support #100092).
  - Fixed Java 1.4.0 problem where the STAX Monitor window was not getting
    focus when it starts (Bug #1098).
  - Fixed Java 1.4.0 problem with STAX Monitor table row heights (Bug #1096).
  + Added support to the STAX Monitor to retry the initial releasing of a held
    job when submitting a new job, in order to support slower STAX Service 
    machines (Support #100095).
  + Added new mode 'stdout' for process' &lt;stderr> element to support the new
    STDERRTOSTDOUT option which allows stderr to be redirected to the same file
    as stdout (Support #100096).
  + Added new parameter TEST to EXECUTE request which allows validating an
    XML Job File/Data and its parameters without actually submitting the job
    for execution (Support #100030).
  + Added a TEST button to the STAX Monitor's Start New Job panel, which 
    allows validating an XML Job File and its parameters without actually
    submitting the job for execution (Support #100030).
  + Added logging overall testcase totals for a job in the STAX Job Log
    (Support #100093).
  - Fixed bug where the log and message elementss if attribute was not being
    evaluated and checked if true before evaluating other values, like 
    message (Bug #1141).  
  - Fixed bug where STAFResult was not being set to "None" for the &lt;process>
    element (Bug #1143).
  - Fixed bug where a STAXPythonEvaluationException occurred when assigning
    a process's STAXResult to return file output containing one or more
    single quotes (Bug #1144).
  
-------------------------------------------------------------------------------

Version 1.1.0: 03/12/2002

  + Added &lt;returnstdout>, &lt;returnstderr>, and &lt;returnfile> elements
    to the &lt;process> element.
  + Added a mode attribute to the &lt;testcase> element which indicates if 
    information about the testcase is to be reported even if no tcstatus 
    elements have been encountered (Support #100091).
  - Display an error when Other Function in the STAX Monitor's Start New
    Job panel is selected but a function name is not specified (Bug #1036).
  + Changed the &lt;process> element's &lt;sequence> to &lt;process-action> with
    an if attribute (Support #100090).
  - Fixed STAX Monitor event mismatches (Bug #1031).
  + Updated sample1.xml to define function arguments and use new scope
    and requires attributes for a function.
  + Enhanced function scoping by introducing a 'local' function scope,
    adding the ability to pass arguments to a function, to define
    the arguments that can be passed to a function, and to return from
    a function. (Support #100032)
  + Updated the STAX Event generation information to make it more extensible
    (Support # 100086).
  + Added support for polling the STAX Monitor Process monitor information 
    more frequently (Support #100060).
  + Added support for interacting with processes (Support #100045)
  + Added a "mode" attribute to the &lt;command> element to indicate whether 
    the process's command should be executed with the shell option 
    (Support #100084) 
  + Updated the STAX Monitor command line options to use dashes 
    (Support #100082).
  + Added an Arguments field on the STAX Monitor's Start New Job panel, which
    allows you to pass arguments to the Start Function (Support #10083).
  + Added an &lt;import> element which allows importing of functions from 
    another STAX XML Job File (Support #10028).
  + Added static handle support to the STAX Job Monitor, so SET ALLOWMULTIREG
    is no longer required in the STAF.cfg file for the STAX Job Monitor 
    machine (Support #100073).
  + Added a "jobparms" command line parameter to the STAX Job Monitor.  This 
    tells the STAX Monitor to submit a new STAX Job with the jobParmFiles
    options and monitor the job, if applicable (Support #100075).
  - Fixed intermittent problem where the first row in STAX Monitor tables is
    slightly smaller in height than the other rows (Support #100048). 
  + Added a "closeonend" command line parameter to the STAX Job Monitor.  If 
    specified with the "job" or "jobparms" parameters, the STAX Job Monitor 
    will be closed when the job ends (Support #100077).
  + Added an "if" attribute to the message and log elements that can be used
    conditionally determine whether a message should be displayed/logged.
  + In the STAX Monitor's Start New Job dialog, the most recently accessed job
    parameter files are listed in the File menubar, and selecting a menu item
    will load all of it's job parameters (Support #100066).
  - Fixed problem where on Hold, Commands remain in the STAX Monitor view until 
    the block is released (Bug #887).
  - Fixed NullPointerException on paralleliterate/iterate if list being
    iterated is empty or None.  Now raising a STAXEmptyList signal with a
    default signalhandler that sends/logs a message and continues (Bug #948).
  = Modified STAX User's Guide to install Jython 2.1 (available 12/30/2001)
    instead of Jython 2.1 alpha/beta versions.  
  + Added elapsed time for Processes and Commands in the STAX Job Monitor tree 
    (Support #100057).
  + Added a confirmation dialog when deleting all scripts from the STAX Job  
    Monitor's Start New Job panel (Support #100062).
  + Added support to the STAX Job Monitor so that it can be started to Monitor  
    a particular job (Support #100058).
  + Added support for saving the STAX Job Monitor Start New Job parameters to a
    file so that they can later be reloaded (Support #100059).
  + Added a "Clear All Parameters" button to the STAX Job Monitor Start New
    Job panel which will clear all of the parameter fields (Support #100065).
  + Added elapsed time for Jobs in the main STAX Job Monitor panel (Support 
    #100056).
  + Added Testcase Pass/Fail totals to the STAX Job Monitor (Support #100061).
  - Fixed &lt;other> option for the &lt;process> element (Bug #940).
  - Fixed problem updating monitor properties when not root (Bug #886).
  - Fixed Process and STAFCmd so that they use the real host name if local is
    specified (Bug #890).

-------------------------------------------------------------------------------

Version 1.0.4: 12/13/2001

  + Modified the STAX Monitor's Message table to always scroll to the most
    recent message unless the user has scrolled to earlier messages.
  - Fixed STAX Monitor problem with monitoring already started jobs on Unix
    STAX servers (Testcase parsing error)
  - Added a println for TestProcess to display its loop count.
  - Fixed problem with local machine names when STAX Monitor is using a
    non-local STAX service machine
  - Fixed bug where the STAX Service fails to initialize on Linux systems with
    STAF 2.3 installed (get an ArrayOutOfBounds Exception).

-------------------------------------------------------------------------------

Version 1.03: 10/23/2001

  - Fixed bug where using a different case for the a process's &lt;location> vs 
    the case of the hostname resulted in STAX not knowing that the process
    completed.
  = Added a log warning message if a STAX process/request END message arrived 
    but there was no listener.
  - Fixed bug where the STAX Monitor was not saving the Start New Job data on 
    Linux systems.  
  - Fixed a STAX Monitor SplitPane sizing problem (where you always had to 
    move the dividers to see the Processes, etc) on Linux systems.
  + Added a confirmation dialog when attempting to terminate a job from the main
    STAX Monitor dialog.  
  
-------------------------------------------------------------------------------

Version 1.02: 10/04/2001

  - Fixed bug where an OutOfMemoryError occurred for long-running STAX jobs with
    many processes and/or stafcmds.
  - Fixed bug where a java.lang.NullPointerException occurred during the
    initialization of the STAX Service when trying to create a Python
    Interpreter when it needs to process an updated jar file.

-------------------------------------------------------------------------------

Version 1.01: 09/18/2001

  - Fixed bug where STAFProc did not display some errors that may occur when 
    the STAX Service is first initialized.  In STAX Version 1.0, this resulted 
    in RC 7 being returned when you performed any STAX command, such as "STAF 
    local STAX HELP" because the STAX Service did not really initialize 
    successfully.  The initialization errors are now displayed.  

-------------------------------------------------------------------------------

Version 1.0:  09/17/2001

  + Enhanced the following elements:
      + More variations are now allowed in the ordering of elements within 
        a &lt;process> element.
      + &lt;catch> element's exception attribute is now evaluated by Python 
        instead of being a literal
  + Enhanced the STAX Job Monitor.  This included:
      + Providing an "Active Jobs" first panel.
      + Redesigning the layout of the panel to submit a job.
      + Allowing you to start monitoring a job that is already running
      + Using a popup window to hold, terminate, or release a block when
        you right mouse click on it
      + Using better icons to differentiate blocks, processes, and stafcmds
      + Changing the color of the block icon to red when held, yellow when
        held by parent, and green when running
  + Enhanced the output of the QUERY JOB &lt;Job ID> PROCESS &lt;Location:Handle>.
    All of the process elements are now displayed and in a different order.
  + Made the number of physical threads used by the STAX service 
    configurable and defaulted the thread number to 5 instead of 2. 
  + Changed the names of a couple of STAX Variables:
      + STAXXmlFile    => STAXJobXMLFile
      + STAXXmlMachine => STAXJobXMLMachine 
  + Added a few new STAX Variables:
      + STAXJobSourceMachine
      + STAXJobSourceHandle
      + STAXJobSourceHandleName

-------------------------------------------------------------------------------

Version 1.0 Beta 4: 08/16/1998

  + Added the following elements:
     + &lt;try> / &lt;catch>
     + &lt;throw>
     + &lt;rethrow>
     + &lt;timer>
  + Changed the following elements:
     + &lt;eval> element name changed to &lt;script>
     + Made many updates to the elements that can be contained in the
       &lt;process> element.  This included combining some of the elements
       and adding a "if" attribute to all of the optional process elements.
  + Enhanced the STAX Job Monitor.  This included adding new colors and 
    the automatic changing of block names in the tree from green to red 
    when a block is held.
  + Added the ability to list/query processes and stafcmds to the LIST JOB
    and QUERY JOB requests.  
  + On the execute request, changed the EVAL parameter name to SCRIPT.

-------------------------------------------------------------------------------

Version 1.0 Beta 3: 08/01/2001

  + Many enhancements have been made to the STAX Monitor, including the ability 
    to submit a new job.  Also, many updates were made to the user interface.
  + Added a sample file, sample1.xml, that demonstrates some of the 
    capabilities of STAX.
  + No longer need to copy stax.dtd to the STAF\BIN directory.  Remove it if
    you installed Beta 1 or 2.
  + Changed the following elements:
    + &lt;function id="..."> changed to &lt;function name="...">
    + &lt;defaultfunction> is now called &lt;defaultcall>
    + &lt;eval> elements can now be contained by elements other than just &lt;stax>
      and &lt;sequence>
    + &lt;process> and &lt;stafcmd> now have an optional name attribute
    + &lt;testcasestatus status="pass" message="..."/> has been changed to
      &lt;tcstatus result="'pass'">...&lt;/tcstatus>
    + &lt;if expression="..."> and &lt;elseif expression="..."> have been changed to
      &lt;if expr="..."> and &lt;elseif expr="...">
    + &lt;iterate itemvar="..." in="..." indexvar="..."> has been changed to
      &lt;iterate var="..." in="..." indexvar="...">
    + &lt;paralleliterate itemvar="..." in="..." indexvar="..."> has been changed to
      &lt;paralleliterate var="..." in="..." indexvar="...">
    + &lt;loop indexvar="..."> has been changed to
      &lt;loop var="...">
  + Added support for the following elements:
    + &lt;log>
    + &lt;break>
    + &lt;continue>
    + &lt;nop>

-------------------------------------------------------------------------------

Version 1.0 Beta 2: 06/28/2001

  - Fixed bug some events got "lost" because STAF got bogged down with all the
    events generates by STAX.  Fixed by changing to use ReqSync instead of 
    ReqFireAndForget when generating events.

-------------------------------------------------------------------------------

Version 1.0 Beta 1: 06/26/2001

  + Initial release

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
