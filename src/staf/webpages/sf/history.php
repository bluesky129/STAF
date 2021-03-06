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

<center><h1>STAF History</h1></center>
<PRE>
-------------------------------------------------------------------------------
History Log for STAF  
  
  Legend:
   - Fixes
   + Features
   = Internal changes
   * Changes required to migrate from one release to another

  1) To get more information on each bug, use one of the following urls,
     replacing Ticket# with the Bug#:
       https://sourceforge.net/p/staf/bugs/Ticket#/
     or use the old format for bugs created before June 2013:
       https://sourceforge.net/support/tracker.php?aid=Ticket#  
     Examples:
       https://sourceforge.net/p/staf/bugs/1484/
       https://sourceforge.net/support/tracker.php?aid=3608059
  2) To get more information on each feature, use one of the following urls,
     replacing Ticket# with the Feature#:
       https://sourceforge.net/p/staf/feature-requests/Ticket#/
     or use the old format for features created before June 2013:
       https://sourceforge.net/support/tracker.php?aid=Ticket#
     Examples:
       https://sourceforge.net/p/staf/feature-requests/723/
       https://sourceforge.net/support/tracker.php?aid=3606436
    
--------------------------------------------------------------------------------

Version 3.4.25: 06/28/2016

  + Changed the STAF Windows build system and InstallAnywhere build system from
    Windows Server 2008 x86 to Windows Server 2008 R2 x86_64 (Feature #773)
  + Upgraded the STAF IA installers to use InstallAnywhere 2015 Premier Edition
    Service Pack 1 (SP1) with IBM Hotfix A (Feature #776)
  - Fixed a typo in the description of the DIRECTORY option for the ZIP
    service's ADD request in the STAF User's Guide (Bug #1572)
  - Updated the help text for RC 21 and the STAF FAQ with a note about when
    STAFProc is run as an Administrator on Windows with UAC enabled, RC 21 is
    returned if a STAF service request is not also run as an Administrator
    (Bug #1573)
  + Upgraded the STAF IA installers to use IBM HotFix B for InstallAnywhere
    2015 SP1 which contains a fix for a Solaris installer bug (Feature #777)
  - Fixed problems in the BuildSTAF.xml STAX job when a build fails to make
    sure an email is sent, a testcase fail is posted, and to not try to
    install/test the bad build (Bug #1574)
  + Changed the ssl interface to use the TLS V1.2 protocol (which required
    building STAF using OpenSSL 1.0.2g instead of 0.9.8e) and disabling the
    SSLv2, SSLv3, TLSv1, and TLSv1.1 protocols to fix a "TLS V1.2 Protocol
    Detection" TCP/IP Vulnerability (Feature #774) 
  - Reissued the default certificate for the SSL network interface with a
    stonger hashing algorithm (SHA-256) to fix TCP/IP vulnerability scan issue
    "SSL Certificate Signed Using Weak Hashing Algorithm". All STAF systems
    that communicate using the ssl interface with the default certificate
    should be upgraded to V3.4.25+ so that they use the same certificate in
    order to avoid RC 16 with error message "STAFConnectionProviderConnect:
    Error in client SSL handshake" (Bug #1575)
  - Fixed many compiler warnings seen when building STAF on Mac OS X 10.10 and
    other operating systems (Bug #1576)
  - Changed sMaxLineLength's type from int to unsigned int in STAFConfigService
    to resolve warnings when compiling STAF on Windows 64-bit and other 64-bit
    operating systems (Bug #1579)
    
--------------------------------------------------------------------------------

Version 3.4.24.1: 01/06/2015

  - Fixed a problem for Windows 8.1, Windows Server 2012 R2, Windows 10, and
    later where STAF was incorrectly setting the Windows version to 6.2 and
    incorrectly setting the STAF/Config/OS/Name variable to Win8
    (Bugs #1568 and #1569)
       
--------------------------------------------------------------------------------

Version 3.4.24: 12/15/2015

  - Updated section "4.4.2 JSTAF service proxy library" in the STAF User's Guide
    to state that STAF Java support on Mac OS X requires Java 8.0 or newer
    (Bug #1560)
  - Reissued the default certificate for the SSL network interface with a longer
    RSA key length (2048 bits instead of 1024) to fix TCP/IP vulnerability
    scan failures caused by a weak RSA key.  All STAF systems that communicate
    using the ssl interface with the default certificate should be upgraded to
    V3.4.24+ so that they use the same certificate in order to avoid RC 16 with
    error message "STAFConnectionProviderConnect: Error in client SSL handshake"
    (Bug #1561)
  - Updated links and fixed typos in STAF Service Developer's Guide (Bug #1567)
              
--------------------------------------------------------------------------------

Version 3.4.23: 09/29/2015

  + Added support for Windows 8.1 and Windows Server 2012 R2 (Feature #768)
  - Fixed compiler error at STAFRWSemCommon.cpp:136 when building STAF on
    Mac OS X 10.10 after upgrading to Xcode 6.4 (Bug #1555)
  + Changed STAF Java support on Mac OS X to require Java 8 or later instead of
    Java 6 (Feature #770)  
  + Added support for Perl 5.16 and 5.18 (default) on Mac OS X and removed
    support for Perl 5.8 and 5.10 on Mac OS X since Mac OS X 10.10 only provides
    support for Perl 5.16 and later (Feature #756)
  - Updated the "Java Support" column on the "Download STAF" web page to
    indicate the minimum Java version required to use STAF Java support
    (Bug #1557)
  + Upgraded the STAF installers to use InstallAnywhere Premier Edition 2015
    which supports the latest operating systems, including Windows 10
    (Feature #758)
  + Added support for Windows 10 (Feature #769)
  + Released STAF installers for Mac OS X 10.10 (Yosemite) after completing the
    upgrade of the Mac OS X STAF build system to 10.10 (Feature #756)
    
--------------------------------------------------------------------------------

Version 3.4.22: 06/26/2015

  - Added a STAF FAQ entry explaining why running some commands like "reg query"
    via a PROCESS START request may return different output when using a win32
    version of STAF on a Windows 64-bit system (Bug #1548)
  + Added support for Perl 5.18 on Linux AMD64 and Linux 32-bit (Feature #764)
  + Improved STAF configuration file error messages by adding the line number
    of the invalid line (Feature #763)
  + Changed to use Windows 7 64-bit for the Windows AMD64 STAF codepage build
    and test system instead of Windows Server 2008 x64 (Feature #765)
  + Provided OpenSSL support for STAF on linux-ppc64le (Feature #766)
  + Changed to use RHEL 6.6 for the Linux 32-bit STAF build system instead of
    RHEL 5.11 (Feature #767)
  - Improved the performance of LOG QUERY/PURGE requests that specify the LAST
    option with a large value (Bug #1550)
  - Fixed error "Constant name HASH has invalid characters" when using STAF
    Perl support with Perl 5.6 (Bug #1552)
              
--------------------------------------------------------------------------------

Version 3.4.21: 03/31/2015

  + Upgraded the Mac OS X STAF build system from Snow Leopard to Yosemite
    (Feature #756)
  - Fixed a problem where a PROCESS START request that changed the system clock
    to an earlier date/time could cause the request to hang on some Unix
    systems (Bug #1535)
  + Added support for STAF on Linux PPC64 LE (Little Endian) (Feature #743)
  - Fixed a problem in the STAF automated build when creating the docs tar file
    on linux-amd64 (Bug #1539)
  - Fixed a problem using the USE_PYTHON_SYSTEM_PATH install option for STAFInst
    on Solaris and FreeBSD where it wasn't detecting the correct Python version
    in the system path (Bug #1542)  
  - Fixed a problem using the USE_PYTHON_SYSTEM_PATH install option where the
    install (both InstallAnywhere and STAFInst) sometimes did not correctly
    detect if Python V3.x was in the system path (Bug #1541)
  + Added support for Python 3.2 on Windows 32-bit and AMD64, Linux 32-bit and
    AMD64, and FreeBSD (Feature #759)
  + Added support for Python 3.3 on Windows 32-bit and AMD64, Linux 32-bit and
    AMD64, and FreeBSD (Feature #760)
  + Added support for Python 3.4 on Windows 32-bit and AMD64, Linux 32-bit and
    AMD64, and FreeBSD (Feature #754)
    
--------------------------------------------------------------------------------

Version 3.4.20: 11/07/2014

  - Added a STAF FAQ entry explaining how to resolve a problem where STAFProc
    fails to start because it cannot determine the TCP/IP host name (Bug #1531)
  + Changed the zLinux 32- and 64-bit STAF build systems from zLinux SLES 10.0
    to zLinux RHEL 5.4 (Feature #755)
  - Provided a new default certificate for the SSL network interface as the old
    default certificate expired on Oct 17 2014.  If you use the ssl interface,
    you should upgrade all STAF systems to V3.4.20+ to avoid RC 16 with error
    message "STAFConnectionProviderConnect: Error in client SSL handshake"
    (Bug #1532)
    
--------------------------------------------------------------------------------

Version 3.4.19: 09/24/2014

  - Updated SourceForge project links to use the new url format in the web pages
    and documentation to fix some "404 - NOT FOUND" link errors (Bug #1528)
  - Improved how the Log service handles corrupt log record formats so that
    QUERY and PURGE requests return RC 4007 (Invalid file format) with an error
    message indicating the record# of the first corrupt log record (Bug #1527)
  + Upgraded the Linux PPC64 build machine from SLES 10.4 to SLES 11.2
    (Feature #725)
  + Upgraded the Linux i386 and Linux AMD64 (x86_64) build machines from RHEL
    5.10 to RHEL 5.11 (Feature #750)    
  + Upgraded the STAF IA installers to use InstallAnywhere 2013 Premier with
    Flexera Public Hotfix C (Feature #751)
  - Added a note to the STAF User's Guide that a SHUTDOWN request returns before
    STAFProc completes shutting down and added a SHUTDOWN example (Bug #1530)
    
--------------------------------------------------------------------------------

Version 3.4.18: 06/30/2014

  - Fixed undefined reference errors building STAF on Ubuntu Linux (which uses
    a different gcc version) by updating the STAF gcc and master makefiles so
    that the g++ commands put the object files and libraries being linked in
    the order that they depend on each other (Bug #1518)
  + Changed the STAF Windows build system and InstallAnywhere build system from
    Windows XP to Windows Server 2008 (x86) because WinXP is no longer supported
    (Feature #745)
  + Upgraded the STAF installers to use InstallAnywhere 2013 Premier (with
    IBM Hotfix B) instead of InstallAnywhere 2012 SP 1 Hotfix H (Feature #744)
  - Fixed a problem where querying a corrupted log file via the LOG service
    on a Windows 64-bit system would cause STAFProc to crash (Bug #1520)
  - Fixed some formatting errors and updated some examples in the STAF Java
    User's Guide (Bug #1522)
  - Updated and added some links in the STAF Perl, Python, and Tcl User Guides
    (Bug #1523)
  - Changed references in error messages and documentation from Sun to Oracle
    Java and removed references in documentation to obsolete platforms
    (Bug #1524)
  - Fixed a problem in BuildSTAF.xml where this STAX job would incorrectly
    terminate with a STAXPythonEvaluationError if a project's build failed
    (Bug #1525)
  + Changed STAF Java support on z/OS 32-bit and 64-bit to require Java 6.0
    instead of Java 1.4.2 (Feature #748)
    
--------------------------------------------------------------------------------

Version 3.4.17: 03/30/2014

  - Updated STAF FAQ entry "3.3.1 Explain startup error: Error constructing
    service, JSTAF" with more current information (Bug #1512)
    
--------------------------------------------------------------------------------

Version 3.4.16: 12/16/2013

  + Provided the ability to set the destination for STAF tracing to both a file
    and stdout/stderr (Feature #293)
  - Added an entry to the STAF FAQ describing why you might get RC 2 (Unknown
    service) submitting a request to a STAF external service such as the LOG,
    MONITOR, RESPOOL, or ZIP service (Bug #1509)
  + Upgraded the Linux i386 & Linux AMD64 (x86_64) build machines from RHEL 5.9
    to RHEL 5.10 Server (Feature #737)

--------------------------------------------------------------------------------

Version 3.4.15: 09/30/2013

  - Fixed a problem where the STAFAddPrivacyDelimiters function sometimes
    returns incorrect data as seen by running PythonTest.py (Bug #1332)
  - Added a note to section "Upgrading STAF" in the STAF Installation Guide
    stating that a STAF upgrade does not automatically use the same settings
    that were selected by the previous STAF install (Bug #1497)
  + Removed support for Windows IA64 which has been sunset (Feature #735)
  + Changed the STAF installer from InstallAnywhere 2011 FP 4 to InstallAnywhere
    2012 SP 1 Hotfix H which supports the latest operating systems and requires
    Java 5 or newer to install using a STAF InstallAnywhere NoJVM installer file
    (Feature #730)

--------------------------------------------------------------------------------

Version 3.4.14: 06/28/2013

  - Updated the STAF Installation Guide in section 11.1 on starting STAFProc
    at reboot on Linux RHEL 6 so that the sample upstart .conf file provided
    starts with "expect daemon" (Bug #3613051)
  - Updated the STAF User's Guide in section "8.5.3 COPY DIRECTORY", sub-section
    "Examples", to remove 3 examples that use the TYPE option as it is not a
    valid option for a FS COPY DIRECTORY request (Bug #1492)
  + Added javadoc comments for all of the STAF Java classes in JSTAF.jar
    (Feature #610)
  - Updated the automated STAF Build job to delete the STAFSource.tar file on
    the build machine immediately after extracting it to free space (Bug #1493) 

--------------------------------------------------------------------------------

Version 3.4.13: 03/29/2013

  - Fixed automated STAF builds to work when buildType is set to 'debug' instead
    of 'retail' (Bug #3602426)
  + Upgraded the Linux i386 & Linux AMD64 (x86_64) build machines from RHEL 5.8
    to RHEL 5.9 Server and updated the Linux PPC64-32 and PPC64-64 build machine
    to the latest operating system updates for SLES 10.4 (Feature #3606436)
  - Fixed a problem in STAF Java functions convertDurationString and
    convertSizeString in STAFUtil.java where they were not returning an error
    if the specified duration/size value was negative (Bug #3608059)
  - Updated section "5.1.2 Running STAFProc on Windows with User Account
    Controls (UAC) Enabled" in the STAF User's Guide with how to run programs
    like the STAX Monitor if the STAFProc is being run as an Administrator on
    Windows due to UAC being enabled (Bug #3606977)  

--------------------------------------------------------------------------------

Version 3.4.12.1: 01/17/2013

  - Fixed a problem on Windows 64-bit systems where STAFProc.exe (or STAF.exe)
    could crash due to a codepage conversion issue when non-English characters
    are used in a STAF service request or returned in a result.  Updated the
    STAF V3.4.12 download for Windows AMD64 (aka x64) and Windows IA64 to
    contain this fix (Bugs #2076450, #3490877, #3046761, #1728621)
  
--------------------------------------------------------------------------------

Version 3.4.12: 12/14/2012

  - Fixed a problem when a request is submitted to an unknown service using sync
    option ReqQueueRetain or ReqRetain where the request was never marked as
    complete (Bug #3575761)
  - Applied IBM HotFix C for InstallAnywhere 2011 FP4 provided by Flexera so
    the STAF installer for Windows IA64 is now built using it (Bug #3577418)
  - Changed the STAF installer to use InstallAnywhere 2011 FP4 for Solaris x86
    and FreeBSD and updated the STAF Installation Guide to document workarounds
    for a Java OutOfMemoryError on Solaris x86 and FreeBSD (Bug #577406)
  + Updated STAF InstallAnywhere installers to support Solaris 11
    (Feature #3532316)  
  = Changed to build STAF Java support on all HP-UX platforms using latest
    fixpack for HP-UX IBM Hybrid Java 1.4.2 instead of Sun Java 1.4.2
    (Feature #3595359)
  - Changed the log settings for the STAF InstallAnywhere builds to not output
    the installer's stdout/stderr data to the console (Bug #3595652)

-------------------------------------------------------------------------------

Version 3.4.11: 09/28/2012

  - Added a check to verify that STAF_INSTANCE_NAME is valid when first
    starting STAFProc and, if not, output an error and exit (Bug #3529571)
  - Added checks to the InstallAnywhere GUI and console installs to verify that
    the specified "STAF Instance Name" is valid (Bug #3529553)
  - Fixed a problem where the Log service was not installed on Linux AMD64 if
    file STAF3410-setup-linux-amd64.bin (or -NoJVM.bin) was used. Rebuilt and
    replaced these Linux AMD64 IA installer files on SourceForge (Bug #3553307)
  - Updated the STAF Python User's Guide to describe a workaround for a problem
    importing PySTAF on Mac OS X 10.7 when using Python 2.7 or later
    (Bug #3553248)
  - Fixed a problem where a FS COPY FILE/DIRECTORY request that uses the TEXT
    or TEXTEXT option could cause STAFProc to crash (Bug #3557472)
  - Fixed a problem starting STAFProc on Solaris 11 when the STAF Solaris
    Sparc 32-bit installer file was used to install STAF caused by providing
    incompatible OpenSSL 0.9.8e libraries (Bug #3560820)
  + Added support for Windows Server 2012 and set its STAF/Config/OS/Name
    variable value to WinSrv2012 (Feature #3565640)
  + Changed STAF builds for Solaris Sparc 32-bit, Sparc 64-bit, x86, and x64 to
    provide support for Java 5.0 and later (removed Java 1.4.2 support)
    (Feature #3567132)
  + No longer provide STAF builds for Mac OS X 10.4 and 10.5 (Feature #3567142)
  + Changed the STAF installer from InstallAnywhere 2010 to InstallAnywhere
    2012 FP 4 which supports newer operating systems like Windows Server 2012
    and Windows 8 (Feature #3512959)
  
-------------------------------------------------------------------------------

Version 3.4.10: 06/29/2012

  + Updated the STAF Installation Guide with more information about how to run
    an InstallAnywhere installer in GUI mode when using telnet or ssh to access
    a Unix system (Feature #607462)
  + Upgraded the AIX 32- and 64-bit build machine from AIX 5.3 to 6.1 because
    AIX 5.3 reached End Of Life.  This means that STAF 3.4.10 for AIX can be
    installed on AIX 6.1 or later or IBM i 7.1 or later (Feature #3474564)
  - Changed the libcrypto.so.0.9.8 shared library provided with STAF for Linux
    (i386 and amd64) to not request an executable stack to fix a problem
    starting STAFProc with SELinux in the enforcing mode (Bug #3296322)
  - Fixed a typo when prompting for the "Default STAF Instance Name" during an
    InstallAnywhere console install (Bug #3522620)
  - Added a check to make sure each STAFTCP interface is configured to use a
    unique port number (Bug #3523091)  
  + Added a RELEASE option to the ResPool service's REQUEST ENTRY option to
    provide the ability to perform an atomic release and request of an entry
    you own which allows you to to re-gain ownership of the entry before any
    lower-priority pending requests (Feature #3037805)
  - Fixed a problem with the HELP service where a SERVICE request without the
    required ERROR option returned the wrong RC and result (Bug #3523350)
  - Fixed a problem creating the STAFEnv.sh file where it could incorrectly set
    the STAF_INSTANCE_NAME environment variable to nothing on some Unix systems
    such as Mac OS X during a GUI InstallAnywhere STAF install (Bug #3529407)
  - Added a STAF FAQ entry about a Java service's JVM being limited to a
    maximum heap size of ~2G on a 32-bit system (Bug #3528876)
  - Fixed a problem in the ZIP service for Unix 64-bit systems where unzipping
    a file using the RESTOREPERMISSIONS option could result in not all of the
    unzipped files having their correct permissions restored (Bug #3530590)
  - Fixed a typo in an error message about an invalid DEFAULTINTERFACE in the
    STAF Configuration file (Bug #3534985)
  + Added a CONFIG service that provides the ability to save the current STAF
    configuration to a file (Feature #3124418)
  + Added javadoc comments for some of the STAF Java classes provided in
    JSTAF.jar (Feature #2807825)
      
-------------------------------------------------------------------------------

Version 3.4.9: 03/29/2012

  - Fixed a problem in the format of the output of a QUEUE GET/PEEK request
    without the FIRST or ALL option (Bug #3468072)
  - Fixed a buffer overrun problem in the STAFStringCountSubStrings function
    if the substring is longer than the STAFString (Bug #3462319)
  - Fixed a memory leak constructing the STAF Local IPC Connection Provider on
    Unix systems (Bug #3467922)
  - Fixed an uninitialized value error in function STAFEventSemWait on Unix
    (Bug #3470394)
  - Fixed a problem on Unix systems where the keepalive socket option was not
    being enabled which meant that a request like "PROCESS START WAIT"
    submitted to a remote system could hang indefinitely if the remote system
    crashed or if a STAF SHUTDOWN request was not submitted before the remote
    system shutdown or rebooted.  Now the socket will send keepalive messages
    on the session so that if one side of the connection is terminated the
    other side will be notified after the keepalive time which is 2 hours by
    default for most operating systems. (Bug #2978990)
  - Added information to the STAF Installation Guide on how to start STAFProc
    on Linux Fedora 15 or later systems during boot-up (Bug #3477847) 
  + Upgraded the FreeBSD build machine's version from 7.3 to 7.4 because 7.3
    reached End Of Life (Feature #3474572)
  + Updated the STAF Installation Guide with more information on how to start
    STAFProc on HP-UX systems during boot-up (Feature #923172)
  - Fixed a STAFProc segfault crash that can occur on a SuSE Linux system when
    a request is submitted to or from a machine that does not have a reverse
    DNS entry (Bugs #3484017, #3271192)
  + Updated the STAF Installation Guide with information on how to start
    STAFProc on Solaris systems during boot-up using SMF (Feature #2926338)
  + Upgraded the Linux AMD64 (x86_64) build machine from RHEL 4.9 to RHEL 5.8
    Server because RHEL 4 reached End of Life (Feature #3474569)
  + Upgraded the Linux i386 build machine from RHEL 4.9 to RHEL 5.8 Server
    because RHEL 4 reached End of Life (Feature #3474566)
  + Added information to the STAF Installation Guide on how to shutdown/start
    STAFProc on FreeBSD and RHEL 4/5 systems when rebooted (Feature #3502872)
  + Provided better support for Windows 8 Previews by adding changes to
    recognize Windows 8 so that the STAF/Config/OS/Name variable is "Win8" and
    provided STAF installers for Windows 8 Preview x86 and x64 that use
    InstallAnywhere 2011 FP 3 plus fixes (Feature #3436065)

-------------------------------------------------------------------------------

Version 3.4.8: 12/08/2011

  - Added a STAF FAQ entry to explain how to enable STAF tracing to debug slow
    performance sending a STAF PING request to a remote machine (Bug #3414415)
  + Changed ZIP service to use zlib 1.2.5 instead of 1.2.3 (Feature #3410708)
  - Fixed a memory leak in RealSTAFSubmit (Bug #3423892)
  + Changed the PROCESS service so that if sending a STAF/Process/End message
    to a remote requesting machine's host name fails with RC 16, it retries
    sending the message to its IP address (Feature #1435389)
  - Fixed the STAF console install on Windows AMD64 so that it lets you choose
    which Perl version to use as the default (Bug #3445568)
  - Removed some extraneous whitespace at the end of some lines in STAFInst
    (Bug #3447387)
  + Added support for Perl 5.12 on Windows 32-bit, Windows AMD64, Linux 32-bit,
    and Linux AMD64 (Feature #3220822)
  + Added support for Perl 5.14 on Windows 32-bit, Windows AMD64, Linux 32-bit,
    and Linux AMD64 (Feature #3428612)

-------------------------------------------------------------------------------

Version 3.4.7: 09/28/2011

  - Changed makefile.gcc to use the CC_CC variable instead of hard coding g++
    so can easily override CC_CC with a specific gcc version (Bug #3371645)
  + Added the ability for a PROCESS START request to create the directory path
    for stdout/stderr files (Feature #3371781)
  + Updated the "9.0 Log Utilities" section in the STAF User's Guide to talk
    about the STAF Log Formatter which formats a STAF log as html or text
    (Feature #3368230)
  - Fixed a typo in the STAF User's Guide on how to dynamically set
    MaxReturnFileSize (Bug #3389096)
  - Fixed a problem on z/OS where could get stuck in a continuous loop logging
    "Error accepting on server socket, socket RC: 122" causing high CPU
    utilization by STAFProc (Bug #3389203)
  - Fixed a problem where a wait timeout (e.g. in requests like QUEUE GET WAIT
    &lt;Timeout> and DELAY DELAY &lt;DelayTime>) could occur a little (&lt; 1 second)
    prematurely on Unix systems (Bug #3392658)
  - Fixed a problem building STAF codepage support using ucm2bin so that it can
    find the alias.txt file when STAF is not installed (Bug #3405267)
  - Removed default values for DOCBOOK_ROOT and SAXON_ROOT from the top level
    makefile so can skip building DocBook documentation for STAX (Bug #3406866)
  - Updated STAF Developer's Guide to say that the STAF zip project has only
    been tested using zLib V1.2.3 (Bug #3406572)
  - Fixed a problem building the java project using Java 1.6+ on some operating
    systems like Linux AMD64 by setting JAVA_V12_OS_NAME (Bug #3410231)
  + Added a ONCE option to the LifeCycle service's REGISTER request to provide
    the ability to only execute a STAF service request once (Feature #3414524)

-------------------------------------------------------------------------------

Version 3.4.6: 06/23/2011

  - Fixed a problem where if many requests are submitted simultaneously to the
    STAFLocalIPCConnProvider on Windows 2003 or later could get error:
    STAFConnectionProviderConnect: Failed to connect to the server named pipe,
    osRC=231 (Bug #3289194)
  - Updated the STAF Tcl User's Guide with more information on the default
    version of Tcl support installed for each supported OS (Bug #3307614)
  - Updated the STAF Installation Guide with some missing info (Bug #3313458)    
    
-------------------------------------------------------------------------------

Version 3.4.5.1: 05/03/2011

  - Fixed a problem where STAFProc failed to start on IBM i 32-bit and 64-bit
    systems after we migrated the aix and aix64 builds from AIX 5.1 to AIX 5.3
    in STAF V3.4.3 (Bug #3137039)

-------------------------------------------------------------------------------

Version 3.4.5: 03/31/2011

  - Added a description of the CANCEL request for ResPool service to section
    8.14.1 in the STAF User's Guide (Bug #3172896)
  + Provided the ability to cancel a pending request for a mutex semaphore by
    adding a CANCEL MUTEX request to the SEM service (Feature #3175231)
  - Updated the STAF Installation Guide with instructions for starting STAF
    during boot on SLES 10 and SLES 11 (Bug #3213049)
  + Changed the effect of the NEWCONSOLE option for processes run on Unix
    systems, so that if a process's stdout/err is not redirected, it will be
    unavailable instead of redirected to STAFProc's console (Feature #3182764)
  - Fixed a problem where multiple FS GET ENTRY CHECKSUM requests running
    simultaneously could cause STAFProc to crash (Bug #3154472) 
  + Added an APPEND option to TRACE SET DESTINATION TO FILE to indicate the
    trace file should be appended to (Feature #3134043)

-------------------------------------------------------------------------------

Version 3.4.4: 12/14/2010

  - Fixed a problem where no error was returned if an invalid method for
    stopping a process was specified such as WM_CLOSE on Unix (Bug #3086749)
  + Added two methods for stopping a process on Unix systems (SIGINTALL and
    SIGTERMALL) to the PROCESS service (Feature #3043764)
  - Fixed a typo in the STAF Python User's Guide (Bug #3087942)
  - Fixed a problem where any STAF service request would hang on z/OS 64-bit
    systems (Bug #3079034)
  - Added a FAQ entry about how to change the system date/time to a prior
    date/time via a PROCESS START request (Bug #3074573)
  - Added information to the STAF Installation Guide about starting STAF
    during system boot on RHEL6 (Bug #3102420)
  - Fixed problem where the STAFMutexSem.h file was not being installed by
    the STAFInst installer (Bug #3123644)
  - Changed STAF Registration service to specify the tcp interface when
    attempting to connect to the STAF Registration machine (Bug #3123742)  
  - Fixed "./STAF343-setup-solaris-sparc.bin: !: not found" warning when
    installing STAF on Solaris (Bug #3124721)
  - Updated the STAF Installation Guide with info on starting STAF during boot
    on Mac OS X Snow Leopard (Bug #3126947)
  + Added the ability to move/rename files and directories on a machine by
    adding a MOVE request to the FS service (Feature #968429)  
  + Added support for Tcl 8.4, Tcl 8.5, and Tcl 8.6 on Windows 32-bit/AMD64
    and Linux 32-bit/AMD64 (Feature #3087454)

-------------------------------------------------------------------------------

Version 3.4.3: 09/28/2010

  + Added support for Mac OS X 10.6+ Universal binary, which includes support
    for i386, x86_64, and ppc (Feature #1961092)
  + Migrated the Solaris Sparc 32-bit build from Solaris 2.8 to Solaris 10
    (Feature #3017653)
  + Migrated the FreeBSD build from 6.1 to 7.3 (and a FreeBSD 4.10 build will
    no longer be provided) (Feature #3025905)
  + Migrated the Linux ppc64-32 build from SLES8 to SLES9 (Feature #3025976)
  - Fixed a problem on Linux where gethostbyname_r() could fail with rc=11 or
    rc=2 indicating to try again, but instead of retrying, the request would
    fail with RC 16 (Bug #3043152)
  - Added a note to the STAF Installation Guide about updating the
    /etc/rc.staf file to have execute permission (Bug #3052334)
  + Migrated aix and aix64 builds from AIX 5.1 to AIX 5.3 (Feature #3025970)
  - Fixed typo in Description returned by HELP ERROR 35 request (Bug #3057021)
  - Added an error message when a SERVICE FREE request fails with RC 25 because
    you did not specify the FORCE option and you are not the originator of the
    request you are trying to free (Bug #3057040)
  + Added support for Python 2.7 (Feature #3035999)
  - Fixed typo in STAF User's Guide in a PROCESS START example (Bug #3067546)
  + Added support for Python 3.0 and Python 3.1 (Feature #2981028)

-------------------------------------------------------------------------------

Version 3.4.2: 06/28/2010

  - Added a note to the STAF User's Guide about when removing a service that
    has pending requests, it may take another minute or so for the service
    termination process to complete (Bug #2993937)
  + Added the ability to return an error on a VAR SET request if a variable
    already exists and to return its current value by adding a FAILIFEXISTS
    option (Feature #2983345)
  - Improved the VAR service's error handling when deleting multiple variables
    in a single request and when getting a variable (Bug #3000698)
  + Added support for 64-bit IBM i (aka i5/OS, OS/400) (Feature #2948129)
  + Added ability to get a system's current date-time via the MISC WHOAREYOU
    request (Feature #3016687)
  + Added support for limiting the output of service result tracing via the
    TRACE SET MAXSERVICERESULTSIZE request (Feature #1379042)

-------------------------------------------------------------------------------

Version 3.4.1: 03/30/2010

  - Fixed an error compiling the TCP connection provider with SSL support on
    Linux when using a later gcc version like 4.1.2 (Bug #2915063)
  - Added more updates to build STAF on OpenSolaris x86 with Sun Studio C++
    (Bug #2925899)
  - Fixed a problem compiling a STAF service written in C using a C compiler
    on Unix due to including cstdlib instead of stdlib.h (Bug #2493041)
  - Changed ConnectionProviderStop message "Timed out waiting for run thread
    to wake up" to a warning instead of an error (Bug #1956286)
  - Fixed build problems verifying if required OpenSSL libraries exist when
    OPENSSL_ROOT is set to a directory ending in /, or if multiple instances
    of these libraries exist in its lib subdirectory tree (Bug #2928372)
  + Migrated the Solaris Sparc 32-bit build to a Solaris 8 machine (this 
    build now only supports Solaris 8 or later) (Feature #2925803)
  + Migrated the zLinux 31-bit (zlinux-32) and zLinux 64-bit (zlinux-64)
    builds to a SLES10 system (these builds now only support SLES10 or later
    and RHEL5 or later) (Feature #2910110)
  - Fixed problems registering a Java service on Windows if the STAF root
    directory name contains one or more spaces, C:\Program Files\STAF, or if
    a JVM executable's path name contains one or more spaces (Bug #2932924)
  - Fixed a problem in the ResPool service where a request for a particular
    resource entry could be given ownership of the wrong resource entry when
    garbage collection occurred (Bug #2935792)
  + Provided the ability to specify a priority when submitting a resource
    request to the ResPool service (Feature #2900777)    
  + Added information to the STAF Installation Guide about starting STAF
    during system reboot on Mac OS X (Feature #2937308)
  - Fixed problems resolving STAF variables in the TOFILE option on a FS COPY
    FILE request, especially if it contains a ^{  (Bug #2944209)
  - Improved error handling when reading files returned for a process and when
    generating a process completion message (Bug #2946074)
  + Migrated the Linux IA32 build to a RHEL4 machine (Feature #2918931)
  + Improved the robustness of garbage collection for handles used by the
    ResPool, SEM, and DELAY services (Feature #1686352)
  - Fixed a problem on RHEL4 where Java STAF client requests failed with
    undefined symbol: : _ZNSt8ios_base4InitD1Ev (Bug #2949399)
  + Provided support for setting environment variables for STAFEXECPROXY
    (Feature #2832927)
  - Fixed "undefined symbol: boot_DynaLoader" error when loading Perl services
    on Linux IA64 (Bug #2952809)
  + Provided the ability to cancel a pending request for a resource pool entry
    by adding a CANCEL request to the ResPool service (Feature #2942593)
  - Fixed an error building STAF using gcc 4.3 or later on Unix (Bug #2955372)
  - Fixed errors when running TestPython.py on Unix (Bug #2945603)
  + Updated the STAF installers to use InstallAnywhere 2010 (Feature #2882366)
  + Updated the InstallAnywhere bundled JVMs to Java 6.0 SR7, except for
    Windows IA64 and Linux IA64 which were updated to Java 1.4.2 SR13FP4
    (Feature #2927782)
  + Added support for HP-UX i11 v3 or later when configured with expanded node
    and host name support and the node name exceeds 8 characters so that
    STAFProc no longer fails with error "uname(), STAF RC: 10, OS RC: 72"
    (Feature #2974748)  
  - Fixed problem where the jre directory would sometimes not be installed
    during an upgrade install (Bug #2972267)
  - Provided the ability to get more info when get RC 21 (STAF Not Running) by
    setting environment variable STAF_DEBUG_RC_21=1 (Bug #1741849)
  + For Unix, provided the ability to override /tmp by setting env variable
    STAF_TEMP_DIR for where STAF stores socket files for the local interface
    (Feature #2800695)
  - Documented a workaround for a problem where the InstallAnywhere uninstaller
    fails to remove the STAF files and Start menu entries (Bug #2965761)
  
-------------------------------------------------------------------------------

Version 3.4.0: 12/14/2009

  - Fixed a problem where if a STAF handle is unregistered/deleted, any pending
    QUEUE GET WAIT requests it had submitted were not cancelled (Bug #2861597)
  - Fixed a problem that could occur if the request number exceeded 2G as some
    Java service requests could then fail with RC 47 and errors like "Invalid
    value because it is not an integer: -560339110" (Bug #2873132)
  + Added the ability to reuse STAF request and handle numbers, as well as the
    ability to get request and handle summary information by adding a SUMMARY
    option to the SERVICE services's LIST REQUESTS request and to the HANDLE
    service's LIST HANDLES request (Feature #2878346)
  + Improved how STAF converts strings to numbers and enhanced/standardized
    conversion error messages (Feature #2880602)
  - Changed the Trust service to resolve STAF variables in the MACHINE, USER,
    and LEVEL options in its SET, GET, and DELETE requests (Bug #2886658)
  - Improved how to determine if the STDOUT and STDERR file names specified on
    a PROCESS START request are the same (Bug #2893403)
  - Fixed a OpenSSL build problem on Unix systems by allowing you to set
    OPENSSL_VERSION to the OpenSSL version to use if multiple versions of
    OpenSSL libraries are installed in $(OPENSSL_ROOT)/lib (Bug #2892882)
  - For all services, improved error messages for invalid command requests
    (Bug #2895347)
  + Added garbage collection for DELAY requests submitted to the DELAY service
    (Feature #2881945)
  + Added an IGNOREERRORS option to the VAR RESOLVE request so you can resolve
    strings that contain an unescaped { that does not denote a STAF variable
    and changed the PROCESS service to use this option when resolving
    variables in option values specified on a START request (Feature #2881935)
  - Fixed problem where Tcl unmarshalling would fail if the content started
    with a dash (Bug #2910232)

-------------------------------------------------------------------------------

Version 3.3.5: 09/30/2009

  - Fixed a problem when starting a process on Unix (without the SHELL option)
    where it wasn't correctly handling empty parameters specified within the
    PARMS option value (Bug #2821570)
  - Updated the Install Guide for Linux IA UI install problems (Bug #2825993)
  - Updated the STAF User's Guide to say that a FS GET ENTRY SIZE request does
    not return the total size of a directory (Bug #2691879)    
  + Provided the ability to get summary information for a directory, such as
    its total size and the number of files and subdirectories it contains, by
    adding a SUMMARY option to a FS LIST DIRECTORY request (Feature #2826807)
  - Fixed problems handling file sizes >= 4G in the output from a FS LIST
    DIRECTORY LONG request and if sorting by size (Bug #2829211)
  + Added the 64-bit size to the output from a FS QUERY/GET ENTRY and FS LIST
    DIRECTORY LONG DETAILS request so you no longer have to combine the lower
    and upper 32-bit sizes to get the size of files >= 4G (Feature #2830374)
  = Changed so that no longer use deprecated Java methods (Bug #1505690)
  - Updated the stafif makefile to enable building IPV6 support (Bug #1323306)
  - Updated the STAF Service Developer's Guide to show how to use STAFEXECPROXY
    when registering a Perl service to prevent STAFProc crashes (Bug #2113056)
  - Changed the FS service on Unix to check when errno EOVERFLOW is set by the
    stat() function as that also indicates the entry exists (Bug #2843521)
  - Fixed a problem where RC 7 was returned by an AUTHENTICATE request if a
    blank value was specified for CREDENTIALS or DATA (Bug #2845102)
  - Updated the STAF Installation Guide to recommend using /etc/inittab when
    starting STAFProc during reboot on Linux (Bug #2837911)
  - Updated the STAF Python User's Guide to correct the link to the STAF
    Developer's Guide (Bug #2856479)
  - Added a FAQ entry to document error "lbJSTAF.so: undefined symbol:
    _ZNSt8ios_base4InitD1Ev, version GLIBCXX_3.4" when using the STAF Java
    libraries on Linux AMD64 with IBM Java 6.0 SR5 (Bug #2845196)
  + Added the ability on Windows for a FS QUERY ENTRY request to provide the
    full, long path name of the specified file system entry in the correct
    case and to use this "real" name to determine if file names on Windows
    match (Feature #2846295)
  - Fixed a problem on Windows where a FS QUERY ENTRY or COPY request returned
    RC 48 (DoesNotExist) when specifying the name of an existing file that was
    currently in use (Bug #2863718)

-------------------------------------------------------------------------------

Version 3.3.4.1: 07/13/2009

  - Fixed RC 22 when sending local requests on Unix platforms (Bug #2819871)
  - Fixed problem on z/OS 64-bit vhere most service requests would hang
    (Bug #2818657)

-------------------------------------------------------------------------------

Version 3.3.4: 06/30/2009

  + Changed the ZIP service to support large zip files > 2G, but &lt; 4G
    (Feature #2637947)
  + Added timeouts to the read/write connection-oriented APIs to resolve
    communication hangs (Feature #667514)
  - Updated the AIX STAF InstallAnywhere installers to include a Java 5.0
    bundled JVM (Bug #2760017)
  - Fixed intermittent hangs that could occur submitting any STAF service
    request by changing SSL_connect() to use a non-blocking socket to prevent
    a SSL handshake from exceeding the connection timeout (Bug #2727266)
  + Added support for STAF on HP-UX PA-RISC 64-bit (Feature #2540001)
  + Added the ability to get the checksum for a file by adding a CHECKSUM
    option to the FS GET ENTRY request (Feature #2573802)
  - Added support for building STAF on Solaris using the Sun Studio C/C++ (CC)
    compilers (Patch #2316610)
  - Fixed a SIGSEGV that could occur when shutting down STAF on Solaris, and
    possibly other operating systems (Bug #2789250)
  - Fixed a problem where STAFProc could randomly crash when using the secure
    tcp interface (Bug #2789132)
  - Fixed a problem in the C++ unmarshall() method where data containing
    multi-byte characters could not be unmarshalled (Bug #2791290)
  - Fixed a problem on 32-bit Unix systems where the STAF/Config/Mem variables
    were 0 when total memory was 4GB or more (Bug #2791329)
  - Fixed a problem on Windows systems where the STAF/Config/Mem variables
    showed memory &lt; 2GB on 32-bit machines and &lt; 4GB on 64-bit machines when
    total memory size was really larger (Bug #1780505)
  + Added support for Windows 7 and Windows Server 2008 R2 (Feature #2502202)
  + Improved the ability to stop processes, and their child processes, on
    Windows by adding a new SigKillAll stop method which uses the taskkill
    command and made this the default (Feature #2519125)
  = Moved the Linux PPC64-64 STAF build to a new build machine (Bug #2793438)
  - Fixed a SIGSEGV that could occur on AIX 32-bit machines when multiple
    STAF EXECPROXY services are removed (Bug #1851096)
  - Updated the STAF Installation Guide to clarify when to use the Solaris x86
    or Solaris AMD64/Opteron version of STAF (Bug #2788851)
  + Changed FS COPY/LIST DIRECTORY and DELETE requests so that specifying an
    empty string (e.g. :0:) for the NAME or EXT option matches only those
    entries with an empty name or an empty extension (Feature #2797111)
  - Fixed a problem in the Process service where a process started on Windows
    could have an incorrect value for the USERPROFILE environment variable if
    a process was previously started as a different user (Bug #2796479)
  - Fixed a problem on Windows where a FS LIST DIRECTORY request could return
    an empty list if you specify the absolute name of a non-empty directory,
    but without a drive letter, like /temp (Bug #2800726)
  - Fixed a problem where a FS LIST/CREATE DIRECTORY request didn't return an
    error if you specified the name of an existing file system entry that is
    not a directory (Bug #2801917)    
  - Fixed a problem where the STAFHTTPSLS.jar file was not being installed
    on Windows (Bug #2810343)
  + Added Python support for more operating systems and more Python versions
    (Feature #688784)
  + Added the ability to specify a maximum size for files returned by a PROCESS
    START request to help prevent out of memory problems (Feature #2638614)
  + Added the ability to specify a maximum size for files returned by a FS GET
    FILE request to help prevent out of memory problems (Feature #2804367)

-------------------------------------------------------------------------------

Version 3.3.3: 03/30/2009

  - Modified the STAFLogViewer's "File" menu order and added a separator line
    (Bug #2412279)
  - Updated the STAF Installation Guide's section on installing STAF as a
    Windows service by adding a note on how to fix a Java services RC 6 problem
    that can occur when you log off Windows (Bug #2494682)
  - Made some improvements to the STAFDemo to make it a better example such as
    by using the existing STAFLogViewer class (Bug #2489076)
  - Improved the error handling in the STAF unmarshall methods to not cause an
    error when invalid marshalled data is input (Bugs #2515811 and #2582649)
  - Clarified that a FS COPY FILE request can copy only one file, but that a
    FS COPY DIRECTORY request can copy multiple selected files and supports
    wildcards (Bug #2543983)
  - Changed HANDLE service's AUTHENTICATE and CREATE requests to only verify
    the local machine submitted the request, not to check for trust level 5
    (Bug #2561191)
  - Updated STAF Perl/Java/Python/Tcl User Guides to add SHELL option to some
    PROCESS START request examples (Bug #2591010)
  + Prevent the "Error binding server socket" error when restarting STAFProc
    by setting the SO_REUSEADDR socket option on (Feature #2607469)
  + Added support to recognize requests submitted to localhost aliases as
    local requests (Feature #2407852)
  - Added a FAQ entry about Expect scripts failing on Linux when STAFProc has
    been started during system reboot (Bug #2590759)
  + Improved error handling and recovery in all the interfaces so that while
    making a connection, a request won't hang, and if possible, the connection
    will be re-attempted (Feature #2569883)
  - Updated STAFEnv.sh to ignore the STAF instance name argument if it is
    equal to "start" (Bug #2597062)
  - Fixed the STAF unmarshall methods so that it no longer gets into an
    infinite loop if certain invalid marshalled data is input (Bug #2634703)
  + Improved installation options for STAF Perl support (Feature #2565366)
  - Updated the STAF Developer's Guide instructions for building OpenSSL on
    Windows (Bug #2669504)
  - Fixed a problem where queuing a message by handle name doesn't return an
    error if one or more handles's queue are full or if no handles exist with
    the handle name (Bug #2672793)
  + Added support for creating a reference to a static handle in the Perl
    STAFHandle->new() function (Feature #2685625)
  - Removed support for old operating systems, Win95/98/Me/NT and AIX 4.3.3,
    that have been sunset (Bug #2692247)
  = Moved the Linux AMD64 STAF build to a new build machine (Bug #2698493)
  + Added support for getting multiple messages off a handle's queue by adding
    the ALL and FIRST options to the QUEUE service's GET/PEEK requests which
    can improve performance (Feature #2616498)
  - Fixed a problem on some Windows versions where a RESPOOL CREATE request
    that specified a pool name containing an invalid character, like a colon,
    didn't fail, even though the pool was not created correctly (Bug #2710588)
  + Added a retry if sending a process end notification message fails with
    a communication error, return code 22 (Feature #2703830)
  - Updated the STAF InstallAnywhere installers to include a Java 5.0
    bundled JVM (Bug #2710109)
    
-------------------------------------------------------------------------------

Version 3.3.2: 12/08/2008

  - Improved how FS COPY requests handle codepage conversion errors (RC 39)
    when copying a file in text mode, including changing a FS COPY DIRECTORY
    request to continue to copy files after this error occurs (Bug #2155926)
  - Updated the STAF Installation Guide to add a missing "&" to the end of
    some commands to start STAFProc on reboot in section 11.1 (Bug #2155306)
  - Updated STAF FAQ to say the FS service doesn't currently support copying
    files >= 4GB on any operating system (Bug #2173011)    
  - Documented error "STAFProc: relocation error: undefined symbol:
    _ZNSs4_Rep20_S_empty_rep_storageE" when starting STAFProc on Linux
    (Bug #2136095)
  - Documented error "/usr/sfw/lib/libstdc++.so.6: wrong ELF class: ELFCLASS32"
    when starting STAFProc on Solaris Sparc 64-bit (Bug #2136189)
  - Updated Perl service support to fix a segmentation fault on Unix and
    resolve a minor memory leak (Patch #2141122)
  - Fixed an error in QUEUE service's help for a DELETE request (Bug #2192811)
  + Added support for more time representations (e.g. seconds, minutes, hours,
    days, weeks) in addition to milliseconds for the DELAY, WAIT, and TIMEOUT
    options (Feature #2182713)
  - Fixed a line conversion problem on a FS COPY request when copying a file
    in text mode if the line endings in the file are not the same as the line
    ending for the source machine's operating system (Bug #1040786)
  + Updated the FS service to detect whether a file system entry is a symbolic
    link and to provide its link target (Feature #1906269)
  - Fixed a problem to allow STAF Java services to be registered using a java
    executable that is a symbolic link (Bug #2235576)  
  - Fixed multi-threading problem in Perl services where STAF::DelayedAnswer
    never returned (Bug #2212492)
  - Improved the error message displayed by STAFLogViewer if the specified
    query request contains the TOTAL or STATS option (Bug #2379502)
  - Fixed a STAFLogViewer problem where it wasn't handling the -serviceName
    parameter correctly when invoked as a Java application (Bug #2390741)
  - Updated the STAF FAQ entry on how to fix firewall issues on Linux by
    updating the iptables file to allow traffic via the ports STAF uses for
    its tcp and ssl interfaces (Bug #2390901)      
  + Added the ability to save logs as text or html files via the STAFLogViewer
    and provided a new Java class, STAFLogFormatter, which provides this
    capability (Feature #2278018)
  - Fixed a segfault in STAFHandleManager::handleProcessTerminated seen on
    Linux machines (Patch #2390692)    
  + Added STAF Perl 5.8 support for Linux AMD64, Linux IA64, AIX 32-bit,
    and Solaris Sparc 32/64-bit (Feature #688780)
  + Added support for Perl 5.10 on Windows 32-bit, Windows AMD64,
    Linux 32-bit, Linux AMD64, AIX 32-bit, Solaris Sparc 32/64-bit,
    and Mac OS X i386/PPC (Feature #1948077)

-------------------------------------------------------------------------------

Version 3.3.1: 09/24/2008

  + Added STAF Perl 5.8 support for Windows AMD64 (Feature #688780)
  - Fixed a deadlock issue that could occur if a service loader submitted a
    request to a service loaded by a service loader (Bug #2020819)
  - Renamed the STAF Python library on Mac OS X from PYSTAF.dylib to PYSTAF.so
    (Bug #2021768)
  + Changed the HP IA64 builds to support HP B.11.23 or later
    (Feature #2015083)
  - Added more information about starting STAF during AIX reboot to the STAF
    Installation Guide (Bug #2025014)
  - Added an example of using embedded quotes to the STAF Ant Task User's
    Guide (Bug #1949772)
  + Added a STAFWrapData Ant task which allows you to create a length
    delimited representation of a string (Feature #2028218)
  - Fixed problem during Windows upgrade install where the system would
    automatically reboot during the install if any STAF files were in use
    (Bug #2014528)
  - Fixed problem where the FreeBSD InstallAnywhere installer was not creating
    the "staf" and "fmtlog" links to "STAF" and "FmtLog" (Bug #2036243)
  - Fixed the HELP and documentation for the PROCESS service's START request to
    show the ENV and VAR options can be specified multiple times (Bug #2040369)
  - Updated the STAFEnv.sh file to fix a problem that can occur on HP-UX if the
    PATH, SHLIB_PATH, or CLASSPATH env variables do not exist (Bug #2050189)
  - Fixed a problem in garbage collection that occurred if an IPv6 address is
    specified for the MACHINE option in a STAF_CALLBACK request (Bug #2053903)
  + Added the ability to auto-unmarshall results when a service request is
    submitted via a C++, Java, Perl, or Python program (Feature #1296407)
  - Fixed a problem starting STAFProc on Windows AMD64 that occurred if the
    Microsoft Visual C++ 2005 Runtime Libraries aren't installed (Bug #2078808)
  + Provided a .bin InstallAnywhere installer for Mac OS X which supports
    silent and console installs (Feature #2034343)
  + Added support for building STAF using GCC 4.3.x (Feature #1896426)
  - Fixed a problem where interface cycling was not updating the connection
    provider argument (Bug #2104237)
  - Updated the FS service to check if an authenticator requires a secure
    interface and to not send authentication info if not secure (Bug #2104558)
  - Handle private data specified for the CREDENTIALS option in an
    AUTHENTICATE request submitted to the HANDLE service (Bug #2104593)      
  + Added support for starting STAFProc minimized on Windows, and provided
    a startSTAFProc batch/script file to set up the STAF environment
    variables and start STAFProc (Feature #1597184)
  - Fixed "file too short" error when starting STAFProc on Unix after
    upgrading to STAF V3.3.0 (Bug #2031530)
  - Fixed a problem where the STAFLogViewer and STAFJVMLogViewer were not
    cleaning up STAF static handles they created (Bug #2116623)
    
-------------------------------------------------------------------------------

Version 3.3.0: 06/27/2008

  + Added Java version information to the JVM Log header (Feature #1906357)
  + Added argument verification checks to Python's STAFHandle constructor and
    improved documentation on standard and static handles (Feature #1906812)
  - Fixed incorrect or missing PLSTAF library during silent install
    (Bug #1913924)
  + Provided a programmatic way to retrieve the latest STAF releases
    (Feature #1878550)
  + Added the ability to request a particular entry in a resource pool to the
    ResPool service (Feature #1229262)
  - Fixed a problem on Unix machines that occurred when terminating a process
    that created many STAF handles as this could result in many new threads
    being created, increasing STAFProc's memory usage (Bug #1931665)
  + Added STAF system variable STAF/Config/Processor/NumAvailable to provide
    the number of available processors (Feature #1902352)
  - Fixed some problems detected by compiler warnings in STAFExecProxyLib, the
    Zip service, and the Monitor service (Bug #1955148)
  - Improved the error message you get starting STAFProc on Unix when the
    STAF_INSTANCE_NAME contains a "/" or if you don't have write permissions
    to the /tmp directory (Bug #1914379)
  - Updated TCP and Unix Local IPC connection providers to handle SOCEINTR
    when calling the recv and send socket functions (Bug #1963200)
  - Fixed a problem compiling the STAF Perl support on some 64-bit operating
    systems (Bug #1877842)
  - Fixed a STAFException at com.ibm.staf.STAFHandle.STAFRegister when using
    Java 1.6 on HP-UX PA-RISC machines (Bug #1990004)
  - Reordered how STAFProc initializes and terminates internal services, SLSes,
    authenticators, and external services so that internal services are
    initialized first and terminated last (Bug #2001286)
  + Added the ability to have a secure tcp interface for STAF (that uses
    OpenSSL) and added this as the default interface in the STAF.cfg file
    (Feature #940264)
  - Fixed a problem on Unix where STAFEventSem and STAFMutexSem were
    incorrectly setting the timespec nanoseconds field (Bug #2002112)
  + Provided an install.properties file that contains information about the
    STAF version/platform/installer, and added a MISC LIST PROPERTIES request
    to retrieve the contents of this file (Feature #1958778)
  + Migrated the STAF installers from InstallShield MultiPlatform to
    InstallAnywhere 2008 VP 1 (Feature #1576795)

-------------------------------------------------------------------------------

Version 3.2.5: 02/26/2008

  - Changed the STAF custom class loader for Java services to define package
    information provided by the manifest(s) in the service jar file and in any
    nested jar files (Bug #1864255)
  - Fixed a problem where the PROCESS STOP command did not require one of the
    following options "ALL | WORKLOAD | HANDLE" as documented (Bug #1868757)
  - Documented workaround for problems building Java, Zip, and Perl when
    using Cygwin GNU Make 3.81 (Bug #1866177)
  + Added a KILL PID request to the PROCESS service to provide the ability to
    kill any process by specifying its process id (Feature #1845716)  
  - Improved error message on Windows when STAFCONVDIR is not set
    (Bug #1871377)
  - Fixed a problem where the LifeCycle service did not use the DATADIR
    operational parameter if set in the STAF Config file (Bug #1872399)
  + Added the ability to purge all records in a log file (Feature #1883803)
  - Changed STAFProc to run the STAF Registration program asynchronously so it
    doesn't delay a STAF shutdown request (Bug #1891246)
  - Fixed some problems where STAF registration information was not being sent
    (Bug #1893179)
  + Changed STAF license from the Common Public License (CPL) 1.0 to the
    Eclipse Public License (EPL) 1.0 (Feature #1893042)  
    
-------------------------------------------------------------------------------

Version 3.2.4: 12/12/2007

  + Added support for STAF on z/OS 64-bit (Feature #1788591)
  - Fixed a problem on Solaris where the ZIP ADD request could kill STAFProc 
    (Bug #1810445)
  + Added support for STAF on Solaris AMD64 with 64-bit Java (Feature #1808436)
  - Fixed a problem where the Java service jar class loader's findResources()
    method was always returning an empty Enumeration (Bug #1813672)
  - Added entries to the STAF/STAX FAQ to describe errors that can occur if the
    maximum number of open files is exceeded (Bug #1816493)
  + Added the STAFMarshall and STAFFormatObject APIs for Perl and documented
    them in the STAF Perl User Guide (Feature #1433821)
  - Improved the performance of the Perl STAFUnmarshall() method, especially
    for large marshalled data strings (Bug #1820708)
  - Added tips for how to debug a STAF Java service to the STAF Service
    Developer's Guide, including how to debug using Eclipse (Bug #1820790)
  + Added support for STAF on Windows Server 2008 (Feature #1797122)
  - Fixed a problem on a ZIP ADD request if zip an empty directory and specify
    the same relativeto path as the directory being added (Bug #1272025)
  - Fixed a problem on a ZIP ADD request so that it won't add an entry for the
    zip file (or it's backup) in the zip file being created/updated
    (Bug #1388274)
  - Documented that the pool name specified on a RESPOOL CREATE POOL request
    is also used as a file name (Bug #1826813)
  + Added a new internal service named LifeCycle which allows you to run one or
    more STAF service requests when STAFProc starts up and/or shuts down
    (Feature #1647207)
  - Added information to the STAF Service Developer's Guide about using
    maxAllowed=0 for a Java STAFCommandParser option to indicate that the
    option can be specified an unlimited number of times (Bug #1842337)
  + Provided a generic "Exec Proxy" service library (Feature #1827601)
  - Fixed a problem so that a LIST/QUERY request for the HANDLE service now
    shows the correct pid (instead of 0) for static handles that are associated
    with a process (Bug #1845722)
  - Fixed a SIGSEGV that could occur during a FS COPY request and cause
    STAFProc to crash (Bug #1847935)
  + Provided support for Perl services (Feature #544063)
  - Improved error handling when can't create a new thread (Bug #1814684)
  - Fixed a problem where a ZIP ADD request on FreeBSD 4.x could cause
    STAFProc to crash (Bug #1670380)
  - Fixed hang when registering Java services on Solaris x64 and sparc64
    (Bug #1768002)

-------------------------------------------------------------------------------

Version 3.2.3: 08/28/2007

  + Added a reference for the STAFCommandParser and STAFCommandParseResult
    classes for Java and C++ to Appendix A and C of the STAF Service Developer
    Guide (Feature #976983)
  - Documented a workaround for "JVM not found" error when using the .jar
    STAF installer on Windows Vista (Bug #1723687)
  - Fixed "Bareword STAFHandle::kReqSync not allowed" error when registering
    the sample Perl service (Bug #1729684)
  = Fixed a problem where having the STAFReg service registered would cause
    STAFProc to hang during Windows shutdown (Bug #1730982)
  - Fixed some compiler warnings, including one that was a comparison error
    when determining how to check trust for a FS COPY request (Bug #1731557)
  - Fixed problem where the Windows STAF installer was not installing the
    correct codepages (Bug #777230)
  - Fixed problem where STAFInst was installing the z/OS codepage file
    ibm-1047.bin on all Unix platforms (Bug #1732320)
  - Fixed problem where incorrect title was being displayed for STAF in the
    HP "swlist" utility (Bug #1732997)
  - Fixed problem with the STAF C++ command parser ignoring option values
    that were missing an ending double quote (Bug #1733810)
  + Added support for STAF on Mac OS X i386 and ppc (Feature #651053)
  - Improved error handling for ZIP ADD/DELETE requests when replacing the
    original zip file with an updated zip file (Bug #1735817)
  - Fixed error compiling the STAF Secure TCP connection provider, e.g. using
    export STAF_USE_SSL=1 (Bug #1737527)
  - Improved error messages if run out of memory in JSTAFSH.HandleRequest
    (Bug #1738076)  
  - Fixed problems registering the Event service in the Getting Started with
    STAF document (Bug #1733816)
  - Fixed error "Could not connect to the server named pipe: 2, Error code:10"
    that could occur on Windows Vista and 2003 machines (Bug #1723053)
  - Fixed STAF startup instructions for Solaris in STAF User's Guide and FAQ
    (Bug #1514636)
  - Fixed a problem running VAR LIST requests simultaneously (Bug #1744442)
  - Document that the directory path specified for the TOFILE value on a FS
    COPY request and for the ZIPFILE value on a ZIP ADD request must already
    exist (Bug #1752193)
  - Fixed a problem registering a Java service on Mac OS X (Bug #1752860)  
  - Fixed some errors in the "STAFHandle and STAFResult" example in the C++
    API section of the STAF User's Guide (Bug #1754106)
  - Fixed a garbage collection problem when a handle requests both a mutex
    semaphore and a ResPool resource (Bug #1753840)
  - Fixed a garbage collection problem in the SEM and ResPool services when a
    handle requested more than one mutex or resource (Bug #1753842)
  - Fixed a problem in the ResPool service where it was not deleting it's
    callback notifications for garbage collection (Bug #1753844)
  - Fixed a problem where STAX sometimes did not release mutex semaphores that
    were requested within the STAX job when the job terminated (Bug #1744469)
  - Fixed a problem where callback notifications for garbage collection did not
    use a valid endpoint if using a different ports/interfaces (#1119643)
  - Fixed a problem in the HANDLE service's QUERY HANDLE request to return an
    error if the handle specified does not exist (Bug #1755775)
  - Fixed a garbage collection problem when using static handles created by
    the PROCESS service per the STATICHANDLENAME option (Bug #1757183)
  - Fixed a performance problem in STAFStringReplace seen when a FS GET FILE
    request converts end of line characters in a large file (Bug #1652904)
  - Fixed a problem on a FS DELETE ENTRY directory CHILDREN TYPE ALL CONFIRM
    request on Windows so it doesn't remove an empty directory (Bug #1762040)
  - Fixed problem where a PING PING request would return RC 7 if the request
    contained whitespace (Bug #1764131)
  - Updated the FAQ to document a STAFProc startup error when running STAF
    V3.2.2 or later on HPUX IA64 with an operating system version earlier
    than 11.31 (Bug #1765725)
  - Fixed a NullPointerException in the STAF InstallShield installer when
    changing the "Update Environment" option on Unix platforms (Bug #1764836)
  + Added ability to automatically test STAF by installing a temporary instance
    of STAF and running STAX jobs to test STAF (Feature #627046)
  - Fixed a problem on Unix systems where the STAF/Config/Mem values were
    always returned as zero (Bug #1113807)
  - Fixed java.lang.UnsupportedClassVersionError when using the .bin STAF
    installer for HPUX IA64 (Bug #1771761)
  - Fixed inconsistency with the help text and documentation for an UNZIP
    request (Bug #1740596)  
  + Added ability to unzip multiple directories and multiple files in a single
    UNZIP request to the ZIP service (Feature #1076095)  
  - Fixed a problem where named monitors were not being deleted if the BEFORE
    option was specified on the MONITOR DELETE request (Bug #1773337)
  - Fixed a problem where a FS DELETE request for a non-empty directory on
    HP-UX machines returned RC 20 instead of RC 50 (Bug #1773520)
  - Added the STAFLoop executable to the STAF install packages (Bug #1773558)
  + Added support for deleting Named Monitors (Feature #1741843) 
  - Added new STAFString constructors to handle int, unsigned short, short, 
    unsigned long, long, unsigned __int64, and __int64 (Bug #1776475)
  - Updated the help for RC 16 to mention a firewall blocking communication
    as one of the possible causes of this error (Bug #1777857)
  - Changed the Log, Monitor, ResPool, and Zip services to return a better RC
    when an unexpected STAFException occurs (Bug #1778943)
  - Fixed "JVM not found" error when using the STAF .jar installer
    (Bug #1774894)
  + Changed to use zLib v1.2.3 (instead of v1.2.1) when building the ZIP
    service (Feature #1776507)
  + Provided a STAF Diagnostics Guide (Feature #1670910)
  - Decreased default thread stack size on Linux to help resolve OutOfMemory
    issues when STAF creates a new thread (Bug #1688297)
  - Fixed a problem if a EVENT WAIT TIMEOUT request to the SEM service timed
    out so that it would check for a match in the waiting list using its UUID
    (Bug #1682463)  
  - Fixed miscellaneous typos in the STAF User's Guide (Bug #1783577)

-------------------------------------------------------------------------------

Version 3.2.2: 05/17/2007

  - Updated the STAF Java, Python, Perl, and Tcl User Guides to clarify the
    descriptions for some marshalling context functions (Bug #1670341)
  - Fixed a problem on Solaris-x86 where the Zip service libraries were not
    being installed (Bug #1672035)
  - Fixed a problem on non-English Windows machines where STAF would not start
    automatically after logging in (Bug #1636095)
  - Changed to return a better RC and error message for a codepage conversion
    error on a FS GET FILE request (Bug #1656179)
  - Improved the error message when gethostbyname() fails (Bug #1675364)
  + Added a Java class (TestJSTAF) to verify that the version of Java you are
    using works with the STAF Java support (Feature #1676683)
  - Fixed UnsatisfiedLinkError for win32ppk.dll during STAF install on
    Solaris-x86 (Bug #1677664)
  - Fixed a problem where the Sample and Demo files were not being installed
    by the STAF321 InstallShield installer files (Bug #1680962)
  - Added STAFTest.xml tests for installed files LICENSE.htm, STAFEnv and
    sample/demo files (Bug #1680967)
  - Added a trace error message if STAFThreadStart returns a non-zero return
    code (Bug #1670918)
  + Documented the environment variables, QIBM_MULTI_THREADED and
    QIBM_JAVA_PASE_STARTUP, required to access STAF in QSH on iSeries and
    added them to the STAFEnv.sh script for iSeries (Feature #1669415)
  - Fixed a problem with garbage collection for the ResPool and SEM services if
    the hostname is not lower-case (Bug #1682461)
  - Fixed a problem handling multiple waiters on an event semaphore that waits
    forever (Bug #1690067)
  - Added a FAQ entry about using "shift" when calling STAFEnv.sh during Unix
    startup to resolve RC 21 errors (Bug #1693075)
  - Upgraded the HP-UX IA64 build machine to HP-UX B.11.31 ia64 with aCC
    version "HP C/aC++ B3910B A.06.12 [Nov 03 2006]" and fixed a problem
    building STAF using this aCC compiler (Bug #1697216)  
  - Added examples of service logging to the sample STAF services and the
    STAF Service Developer's Guide (Bug #1697794)
  - Fixed a "CreateFileMapping(), RC=5" problem starting STAFProc on Windows
    2003 Server if logged in as a user, not an Administrator (Bug #1706833)
  + Added the ability to specify no garbage collection when requesting a
    resource pool entry via the RESPOOL service or when requesting a mutex
    semaphore via the SEM service (Feature #1707719)
  - Changed to not resolve STAF variables in the ENTRY option value in a
    RESPOOL RELEASE ENTRY request (Bug #1709002)
  - Provided more information if a bad_alloc exception occurs (Bug #1688297)
  - Fixed a problem on Unix where all temporary STAF socket files in /tmp were
    not always removed on shutdown, which could cause a problem if STAF was
    restarted as a non-root user (Bug #1707001)
  + Added support for Solaris Sparc 64-bit (Feature #1712075)
  - Fixed a problem where a FS COPY request that uses the TEXT/TEXTEXT option
    to copy a file with length 0 in text mode would fail with RC 22 on 64-bit
    machines and STAFProc would get killed (Bug #1718618)
  = Changed the HP-UX PA-RISC 32-bit build machine to an HP-UX 11.11 machine
    (Bug #1720107)
  
-------------------------------------------------------------------------------

Version 3.2.1: 02/28/2007

  - Changed the MONITOR service's LOG request to mask any private data in a
    message (Bug #1592399)
  - Updated STAF User's Guide to indicate that InstallShield 11.5 (used by
    STAF V3.2.0+) does not support Solaris 2.6 so you must use the tar.gz STAF
    installer file (Bug #1595973)
  - Documented the TCLLIBPATH environment variable better in the STAF Tcl User
    Guide and provided a complete Tcl script example (Bug #1597060)
  - Fixed problem on Linux where TCLLIBPATH was being set to the STAF bin
    directory instead of the STAF lib directory (Bug #1600495)
  + Added the formatObject and isMarshalledData APIs for Tcl and documented all
    the marshalling related APIs in the STAF Tcl User Guide (Feature #1213827)
  - Fixed a RC 5 error submitting a STAF request from an "outsider" program
    that is run as a different user than STAFProc on Unix (Bug #1606378)
  - Documented for a FS COPY request that specifying "TOMACHINE local" can
    improve performance when copying to/from the same machine (Bug #1608550)
  + Added support for Named Monitors (Feature #627809)
  - Fixed problem where a STAF install upgrade over a STAF 2.x version did
    not create the default STAF 3.x configuration file (Bug #1604524)
  - Fixed a problem importing PYSTAF on Windows if built for Python 2.5+ by
    changing the STAF extension module's name from PYSTAF.dll to PYSTAF.pyd
    (Bug #1627385)  
  - Fixed problem where the Perl Submit2/submit2 sync options in PLSTAF.pm
    could not be accessed (Bug #1632455)
  - Added information on the Submit2 and submit2 methods to the STAF Perl
    User's Guide (Bug #1630857)
  - Fixed a problem using a temporary stdout/stderr file when starting a
    process on a HP-UX machine where STAFProc was started as a non-root user
    (Bug #1634349)
  - Updated the STAF Perl User's Guide by adding more unmarshalling examples,
    regrouped the APIs in PLSTAF, and added an Examples section (Bug #1638103)
  - Fixed typos describing the Results for DIAG LIST and FS LIST DIRECTORY
    requests in the STAF User's Guide (Bug #1638999)  
  + Added support for Solaris x86 (Feature #1075496)
  + Provided the ability to view a JVM log for any STAF Java service via the
    STAFJVMLogViewer class provided in JSTAF.jar (Feature #1633551)
  - Provided a better error message when registering a service on Windows if
    the library (aka DLL) specified does not exist (Bug #1644808)
  - Added descriptions of the STAFLogViewer and STAFJVMLogViewer utilities
    to the "Log Utilities" section in the STAF User's Guide (Bug #1639605)
  - Fixed some minor errors in the STAFLogViewer class (Bug #1645608)  
  - Added STAF FAQ entry about the AIX C++ runtime level used to build STAF
    (Bug #1656399)
  - Improved error messages when a service loader service encounters an error
    when attempting to dynamically load a service (Bug #1650918)
  - Fixed the ServiceComplete tracepoint to work with remote requests and added
    the result length to it's message (Bug #1658349)
  + Added FROMRECORD and TORECORD options to the LOG service's QUERY and PURGE
    requests and record number to the QUERY LONG output (Feature #1656875)
  - Added support for FS COPY requests to copy large files whose size is
    2G or more, but less than 4G (Bug #1522599)
  + Added support for building both Linux PPC64-32 and PPC64-64
    (Feature #961832)
  + Added support for STAF on FreeBSD 4.10 and 6.1+ (Feature #578893)
  - Fixed problem on iSeries where STAFInst was not installing the STAF
    executable (Bug #1614316)

-------------------------------------------------------------------------------

Version 3.2.0.1: 11/17/2006

  - Fixed STAF install/upgrade problems by handling cases where the STAF
    uninstaller is not in _uninst and by disabling the STAFFilesInUse
    custom bean (Bug #1598615)

-------------------------------------------------------------------------------

Version 3.2.0: 10/31/2006

  - Fixed install failure on Windows Me and Windows NT (Bug #1524852)
  - Fixed a problem where STAFProc fails to start on Linux PPC64-32 / IA64,
    and HP-UX IA64-32 with a SIGSEGV due to a connection provider name
    resolution issue (Bugs #1556689 and #1371304)
  - Fixed a problem where the Process service's temp file creation for stdout/
    stderr may fail if the default data directory is overridden (Bug #1556715)
  - Updated the STAF FAQ to add information about running the Windows ftp
    executable via a PROCESS START request (Bug #1546780)
  - Updated the STAF User's Guide to make sure that we document all the options
    for PROCESS service requests that resolve variables (Bug #1548850)
  - Enabled the keepalive option for client sockets in the TCP connection
    provider to fix a problem where some copy requests that had failed with
    RC 22 were never being removed from the output of FS LIST COPYREQUESTS on
    the "TO" machine (Bug #1559514)
  - Improved the performance for C++, Python and Jython marshall and
    formatObject methods and for the Java marshall method (Bug #1559277)
  - Fixed a typo in C++ example for creating a STAF handle (Bug #1567835)
  - Added info to the STAF FAQ about SHLIB_PATH and "set -u" in the HP-UX
    profile (Bug #1569958)
  - Fixed problems in C++, Java, Python, and Jython marshall and formatObject
    methods handling maps with non-existant map classes (Bug #1437654)
  - Fixed problems in Python and Jython marshall and formatObject methods to
    handle a map class object that doesn't contain all the keys defined in its
    map class definition (Bug #1280017)
  - Fixed a problem that caused STAF requests to hang due to a deadlock issue
    in STAFHandleManager::handleProcessTerminated() (Bug #1571224)  
  - Added an entry to our FAQ about LD_LIBRARY_PATH not being set on RHEL4-U4
    when directly logging into the desktop (Bug #1559586)
  + Provided STAF return code constants for Jython and added to Jython version
    2.1-staf-v3 provided with STAX, Cron, and EM (Feature #1571762)
  + Upgraded the STAF InstallShield installers to use InstallShield
    MultiPlatform 11.5 (Feature #1545393)
  - Removed the libstdc++ and libgcc libraries from the Linux and Solaris
    STAF installers (Bug #1554498)
  - Provided more documentation on the trust levels required for the machines
    involved in a FS COPY request in the STAF User's Guide (Bug #1584732)
  - Provided a PROCESS START example in the STAF User's Guide for a Windows
    .bat file that uses the /B option on a EXIT comand to show how to get the
    real exitCode assigned to the process RC (Bug #1584756)
  + Added support for Windows Vista with UAC enabled.  Changed the local IPC
    connection provider to use named pipes for interprocess communication
    instead of global shared memory on Windows Vista (Feature #1517278)
  - Fixed a problem on Windows where logging in via Remote Desktop Connection
    would terminate the existing instance of STAFProc (Bug #1553533)

-------------------------------------------------------------------------------

Version 3.1.5: 08/28/2006

  - Improved handling of an RC 4010 from the QUERY request in the STAFLogViewer
    (Bug #1520214)
  - Incorrect key name for STAF/Service/Log/ListLocalSettings (Bug #1520259)
  - Changed STAFInst to provide a better error message if the source and target
    directories are the same (Bug #1513636)
  + Updated to recognize Windows Vista as an operating system so that variable
    STAF/Config/OS/Name=WinVista, not "Unknown WinNT" (Feature #1517278)
  - Fixed problem where a ResPool REQUEST POOL request could throw an unhandled
    exception (Bug #1525753)  
  - Allow any font to be selected in the STAFLogViewer (Bug #1530962)
  - Change STAFStringToUInt() to handle numbers above base 10 (Bug #1533482)
  - Fixed gethostbyname_r() failure on Linux when /etc/host.conf contains
    'multi on' (Bug #1535870)
  + Added a MACHINE option to the PING service (Feature #1038463)
  - Fixed a substantial memory leak in STAF Java support where the result
    buffer from a STAF request was not being freed (Bug #1172182)
  - Allow users to have executable/library symbolic links created during
    STAFInst install (Bug #1518950)
  - Updated the STAF Service Developer's Guide to document the requirement to
    use a 1.5 JVM to register Java STAF services that were compiled with
    Java 1.5 (Bug #1522013)
  - Documented an error registering Java services using the GNU compiler for
    Java on Linux in the FAQ and Getting Started Using STAF (Bug #1497922)
  - Fixed a SIGSEGV in STAFHandleManager::handleProcessTerminated() caused by
    a locking issue (Bug #1526713)
  - Changed the SIGSEGV signal handler for Unix to abort so that STAFProc
    doesn't get into an infinite loop generating SIGSEGV errors (Bug #1542222)
  - Changed FS COPY to use interface cycling (if enabled) when connecting to
    the TOMACHINE (Bug #1543243)
  - Fixed a small memory leak registering/unregistering external C++ services
    (Bug #1359340)  
  - Updated the InstallShield installers to only display existing directories
    where STAF is installed if the bin/STAFProc executable exists
    (Bug #1481432)
  - Updated the STAF Developer's Guide to indicate building Perl 5.6/5.8
    support requires the Perl 5.8 bin directory in your PATH (Bug #1544854)
  - Updated the STAF Developer's Guide to add the Windows build requirement of
    having the Cygwin Python package installed (Bug #1491617)
  - Fixed some memory leaks and some other errors in STAFProc (Bug #1544974)
  - Fixed a problem starting a process where creating a temporary file name for
    stdout/stderr could fail.  Also, fixed a problem where temp files were
    created in {STAF/DataDir} instead of {STAF/DataDir}/tmp (Bug #1537002)
  - When reading in the STAF configuration file, allow STAF services to
    resolve any PARMS options for variables (Bug #1546244)

-------------------------------------------------------------------------------

Version 3.1.4.1: 07/14/2006

  - Fixed RC 19 (File write error) for FS COPY requests copying to a Linux
    PPC64-64 machine running STAF 3.1.4 (Bug #1522732)

-------------------------------------------------------------------------------

Version 3.1.4: 06/27/2006

  - Fixed a problem in STAFInst where it didn't handle creating a new link
    correctly if a link already existed (Bug #1471995)
  - Fixed a RC 22 problem on a FS COPY FILE request (Bug #1459698)
  - Removed silent install documentation for optionalCodepageSupport 
    (Bug #1476432)
  - Added a RHEL4 example to the STAF FAQ Linux iptables configuration section
    (Bug #1478034)
  - Changed the instructions for installing STAF on OS/400 to run STAFInst
    after untarring the installer file (Bug #1486002)  
  + Added a Full setup type for the InstallShield installers (Feature #1477900)
  - Corrected the User's Guide setupTypes.selectedSetupTypeId silent install
    option (Bug #1486878)
  - Fixed a problem where the InstallShield Minimal install type resulted in
    all files being installed (Bug #1485126)
  - Improved the error messages returned in the result when a PROCESS START
    request fails to start a process (Bug #572193)
  + Improved errors messages returned by the resolve variable methods and
    changed to return RC 47 (Invalid Value) instead of RC 7 (Invalid Request
    String) when resolving a variable that should contain a number value, but
    isn't numeric (Feature #1503117)
  + Added ability to specify the focus (e.g. minimized, foreground) for windows
    opened when starting a process on a Windows system (Feature #1495665)
  - Upgraded the Java versions bundled with STAF to ibmjre142sr5 (for Win32,
    Linux, AIX, and Win64), sunjre142_12 (for Solaris) and hpjre142_10
    (for HP PA-RISC) (Bug #1486828)
  + Provide a STAFLogViewer class in JSTAF.jar (Feature #1511822)
  + Added a TODIRECTORY option to the FS COPY FILE request (Feature #1512811)
  
-------------------------------------------------------------------------------

Version 3.1.3.1: 04/07/2006

  - Fixed Windows problem where during a silent install of STAF, the RC would
    always be 0 even if the install failed (Bug #1460093)
  - Fixed FS COPY request so that if fails due to being out of space on AIX,
    now get RC 19 (File Write Error) instead of RC 0 or RC 22 (Bug #1461730)
  - Fixed a memory leak when enumerating a directory (Bug #1463861)
  + Added a RECURSE option to the FS LIST DIRECTORY request to provide the
    ability to list the contents of a directory recursively (Feature #1461609)
  - Changed the STAFInst installer to no longer create links in /usr/bin and
    /usr/lib (Bug #1360178)

-------------------------------------------------------------------------------

Version 3.1.3: 03/24/2006

  - Changed to strip leading whitespace from a request value.  Previously, this
    would result in an RC 7, Invalid Request String (Bug #1407668)
  + Added a performance enhancement for FS COPY DIRECTORY requests that can
    significantly speed up copying files in binary mode (Feature #1413919)
  - Improved help message for error code 21, STAF Not Running (Bug #1426804)
  - Changed the result for LOG LIST request that lists log files to provide the
    upper 32-bit size in addition to the lower 32-bit size (Bug #1379849)
  - Fixed "Can't find STAFInst.mfs" error when running STAFInst from a 
    directory other than the STAFInst root (Bug #1427934)
  - Changed STAF C++ command parser to provide an error message along with RC 7
    if :Length: exceeds the length of the data (Bug #464827)
  - Updated STAF Developer's Guide to include instructions for building IPv6
    support and reformatted the Build section (Bug #1429282)
  + Changed to generate trace error messages instead of using couts/cerrs
    (Feature #626903)
  - Fixed incorrect return description for STAFSocketIsValidSocket
    (Bug #1439792)
  + Improved log lock granularity (Feature #1438151)
  - Documented garbage collection performed by ResPool and SEM services in the
    STAF User's Guide (Bug #1433754)
  - Fixed problem in SEM service where garbage collection was not being done
    for pending requests for mutex semaphores (Bug #1442163)
  - Fixed problem in SEM and RESPOOL services where pending requests that are
    garbage collected never complete (Bug #1442762)
  - Improved shutdown of STAF such that some extraneous errors are no longer
    output in the JVM logs and STAFProc output (Bug #1436187)
  - Fixed intermittent RC 21 on Solaris Opteron (Bug #1441422)
  - Improved error message when registering a Java service using a "bad" JVM
    (Bug #1422950)
  - Added detection of a cyclic copy for a FS COPY DIRECTORY RECURSE request
    (Bug #858366)
  - Updated list of operating systems supported by STAF in STAF User's Guide
    (Bug #1452437)
  - Handle "Connection terminated unexpectedly" trace messages better
    (Bug #1451680)
  + Changed machine trust level to be case-insensitive (Feature #1442047)
  - Fixed problem where a remote STAF request may never complete if STAFProc is
    shutdown on the remote machine (Bug #1450213)
  - Changed to provide better error messages for exceptions generated by
    STAFFSGetEntry (Bug #1420783)
  - Added instructions to the STAF User's Guide on how to use the "qsh" shell
    on AS/400 (Bug #1428630)
  + Added support to handle STAF V3 and V2 versions of a Java service packaged
    in a single jar file (Feature #1457107)
  - Fixed problem where the default STAF.cfg file was not being created
    (Bug #1368716)
  - Improved description of RC 25 to indicate that it's an insufficient trust
    issue (Bug #1457375)
  - Fixed a socket binding error starting STAF on Unix (Bug #1187649)   
  - Fixed problem with STAF Java services on Windows AMD 64 (Bug #1225139)

-------------------------------------------------------------------------------

Version 3.1.2: 01/16/2006

  + Added a ServiceComplete tracepoint (Feature #1373628)
  - Fixed ServiceException during the Windows install (Bug #1369304)
  - Fixed ProductException during the Linux install (Bug #1369302)
  - Fixed problem where STAFProc could become unresponsive when lots of FS
    COPY requests are performed and improved performance for managing FS COPY
    request data (Bug #1397074)

-------------------------------------------------------------------------------

Version 3.1.1: 12/07/2005

  + Added support for Solaris on AMD Opteron 64-bit (Feature #1305592)
  - Fixed a problem in the table formatting done by STAF.exe where the last
    column in a table wasn't using as much space as it should (Bug #1323194)
  - Documented in the STAF Developer's Guide that building STAF Perl 5.6
    support requires both Perl 5.6 and 5.8 to be installed (Bug #1326247)
  - Fixed a performance problem in the Java STAF Command Parser (Bug #1329463)
  - Made various fixes to makefiles when building STAF documentation
    (Bug #1336795)
  + Added automatic interface cycling (enabled by default) when specifying an
    endpoint without an interface to make it easier to communicate between
    STAF machines using different interfaces/ports (Feature #1341028)
  - Updated TRUST service requests to strip the @port from the MACHINE value
    if specified (Bug #1340861)
  - Fixed problem in ZIP service handling zipfiles containing > 32k entries
    (Bug #1347778)
  = Changed ZIP service to use unsigned short instead of short as the type for
    2-byte fields as defined in the .ZIP File Format Spec (Bug #1352376)
  - Fixed FS service problem in a CREATE DIRECTORY FULLPATH request if specify
    a directory name that starts with \\computername\sharename on Windows
    (Bug #1305912)
  - Fixed FS COPY hang problem when source file is located on a mapped drive
    and the mapped drive is disconnected (Bug #1353461)
  - Fixed problem where FS DELETE ENTRY RECURSE returns RC 22 on Windows if
    length of an entry exceeded MAXPATH (Bug #1295334)
  - Fixed problem where FS DELETE ENTRY RECURSE returns RC 10 on Windows if
    specify a file instead of a directory for the ENTRY (Bug #788475)  
  + Upgraded to InstallShield Universal 10.5 SP2 with hotfix_f
    (Feature #1359293)
  - Fixed problem where the operating system was not being recognized during
    STAF install after upgrading to IS 10.5 SP2 (Bug #1363128)
  - Fixed segmentation fault during install on AIX 5.3 (Bug #1194003)
  - Fixed problem where the STAFEnv script was not being created during the
    STAF install after upgrading to IS 10.5 SP2 (Bug #1373600)
  + Added a new Debug tracepoint for the TRACE service (Feature #1370267)
  - Fixed problem registering Java services on HPUX-IA64 (Bug #1371022)
  + Included the process id (PID) for the JVM in the JVM Log (Feature #1370252)
  + Added the PID when listing/querying a process or handle (Feature #1356848)
  + Added more information on the display-short-name property when defining a
    key for a map class in used for marshalling (Feature #1373573)
  = Changed the Linux IA-32 build machine to a RedHat 8.0 machine
    (Bug #1374880)
  - Fixed problem in ResPool service where a pending request whose handle no
    longer exists was not being garbage collected (Bug #1373442)  
  - Fixed problem where removing services would kill STAFProc on Linux
    (Bug #1070250)
  - Fixed problem where STAFProc was unkillable on Linux (Bug #1195497)
 
-------------------------------------------------------------------------------

Version 3.1.0: 09/30/2005

  - Fixed problem in the STAF executable's tabular pretty print method that
    occurred if the length of an entry in the table is 0 (Bug #1263123)
  - Documented what it means if you specify local for the TOMACHINE option in a
    FS COPY request (Bug #1263436)
  - Removed the default selection for the License Agreement panel in the
    InstallShield installers (Bug #1266242)
  - Fixed FS COPY request so that if a write error occurs copying a file or
    directory (e.g. Disk Full), you now get an RC 19 (File Write Error) instead
    of RC 0 (no error) or RC 22 (Bug #1262633)
  - Fixed FS COPY problem so that an error is returned if you try to copy a
    file or directory over itself (Bug #1006907)
  + Provided the ability to mask passwords and other sensitive data
    (Feature #622392)
  - Added a comment to the Python User's Guide about the error that occurs when
    using environment variable PYTHONCASEOK (Bug #1285055)
  + Added Java utility methods that compare STAF Versions and can verify that a
    required version of STAF (or a STAF service) is running (Feature #1292268)
  + Created a STAF Ant task (Feature 1156242)
  - Fixed a STAF Java support problem on HP-UX (Bug #1308994)

-------------------------------------------------------------------------------

Version 3.0.3.1: 09/22/2005

  - Fixed problem where the AIX 64-bit build was not correctly building
    the 64-bit Java libraries (Bug #1298945)

-------------------------------------------------------------------------------

Version 3.0.3: 08/15/2005

  = Changed to not use enum as a Java variable name so can compile using 
    Java 5.0 since enum is now a Java keyword (Bug #1241613)
  - Pass NULL to AttachCurrentThread in STAFJavaServiceHelper.cpp to resolve a
    JVM crash with IBM Java 5.0 (Bug #1243199)
  - Fixed ZIP service's UNZIP request to return an error if a FILE specified
    does not exist (Bug #1245354)
  + Added an "ADD" request to the ZIP service (equivalent to a "ZIP ADD"
    request) and deprecated the "ZIP ADD" request (Feature #1085859)
  - Restructured STAFDemo to reduce the number of STAF requests submitted to
    start a process (Bug #1250303)
  - Changed to only allow registering a network interface with a unique lower-
    case name (Bug #1250410)
  + Added a LIST COPYREQUESTS command to the FS service so you can list file
    and/or directory copy requests that are in progress (Feature #809485)  
  - Fixed problem during 2.x -> 3.x upgrade install (with the 2.x version still 
    running) where some binary files were missing after the reboot 
    (Bug #1247355)
  - Updated STAF Users's Guide by adding a section on environment variable
    settings for STAF and running multiple instances of STAF (Bug #1243242)
  - Fixed problem with STAF C++ command parser's instanceName() and
    instanceValue() methods (Bug #1252798)
  - Fixed typo in VAR RESOLVE statements in "Getting Started with STAF"
    document (Bug #1254543)
  - Fixed 99% CPU utilization problem with STAFProc that can occur on Unix
    systems if STAF has been running for 49+ days (Bug #1256803)
  - Added descriptions of tracepoints and more trace message examples to the
    Trace service section of the STAF User's Guide (Bug #1256242)
  - Fixed intermittent FS Copy hang problem and added recovery code for read
    or write failures (Bug #988110)

-------------------------------------------------------------------------------

Version 3.0.2: 07/19/2005

  - Fixed problem where STAFProc fails to start with a STAFInvalidParmException
    if IPv4/IPv6 support was selected during the STAF install (Bug #1234997)
  + Provided ability to list and dynamically set operational settings for STAF
    like CONNECTATTEMPTS, MAXQUEUESIZE, and DEFAULTSTOPUSING (Feature #1227303)
  + Added support for 64-bit AIX (Feature #1218936)
  
-------------------------------------------------------------------------------

Version 3.0.1.1: 07/11/2005

  - Fixed SIGSEGV starting STAFProc on HP-UX IA-64 machines (Bug #1195499)

-------------------------------------------------------------------------------

Version 3.0.1: 06/27/2005

  - Fixed typo in the STAFInst help for the acceptlicense option (Bug #1195499)
  - Fixed problem in the Java Command Parser where :0: was not being handled
    correctly as an option value (Bug #1198553)
  - Fixed error in the STAF User's Guide SEM section, and in the SEM HELP
    result (Bug #1200224)
  - Fixed Perl support problem using the setKeyProperty() method for class
    STAF::STAFMapClassDefinition (Bug #1200875)
  + Created a new STAF Java User's Guide to document all the STAF Java APIs
    and removed section 6.5 Java from STAF User's Guide.  Added more details on
    the new classes/functions for unmarshalling/marshalling (Feature #1203668)
  - Fixed Python support problem where only one thread could run at a time
    (Bug #1201047)
  + Provide ability to query parameters/options for any service/authenticator
    to the SERVICE service and added a LIST SETTINGS request to the RESPOOL
    service (Feature #989754)  
  - For the Linux AMD64 package, include libstdc++.so.6 instead of
    libstdc++.so.5 (Bug #1216686)
  - Fixed problem in FS service where an entry whose name ends in a period was
    not being handled properly (Bug #1225586)
  - Fixed RC 22 problem using FS service to list or query the root directory
    of a Windows network share such as \\server\service (Bug #1225876)
  - Changed PLSTAF.pm to contain "use 5.006", not "use 5.008" (Bug #1194483)
  - Provided more information in the error text when registering a service or
    authenticator if an invalid name is specified (Bug #1226516)
  - Improved readability of the process sendNotification trace warning message
    (Bug #1227210)
  + Improved STAF 2.x/3.x interoperability by changing STAF 3 to unmarshall
    a result string into an easy-to-read "verbose format" before sending it
    back to a STAF 2.x machine (Feature #1227096)
  - Added an indicator for when the maximum number of table format lines has
    been reached in the STAF executable (Bug #1196925)

-------------------------------------------------------------------------------

Version 3.0.0.1: 04/29/2005

  - Fixed problem in FS COPY DIRECTORY request to resolve STAF variables in the
    TODIRECTORY value on the target system (Bug #1187605)
  + Added support for AMD64 on Windows (Feature #915243)
  - Fixed problem where STAF configuration statements that did not have a line
    ending were being ignored (Bug #1192041)
  
-------------------------------------------------------------------------------

Version 3.0.0: 04/21/2005

  - Added support for iso8859-15 as alias for codepage ibm-923 (Bug #1076948)
  - Fixed "java.io.FileNotFoundException: \STAFEnv.bat" error during upgrade
    installation (Bug #1089879)
  - Fixed FS COPY trust problem using multiple tcp interfaces (Bug #1098099)
  - Improved error message provided for FS COPY/GET trust error (Bug #1028633)
  - Fixed Solaris uninstall failure with message "LoggedSoftwareObject"
    (Bug #1100388)
  - Changed machine trust specifications to default the interface to *
    (wildcard) if the interface is not specified (Bug #1101283)
  - Improved the error message for a RC 16 to include the endpoint for the 
    requested target machine to aid in debugging (Bug #1101866)
  + Added an option to STAFInst for specifying the default TCP libraries to
    use (IPv4 only or IPv4/IPv6) (Feature #1075638)
  - Changed FS COPY to use incoming interface/port if TOMACHINE specifies no 
    port or interface (Bug #1101852)
  - Fixed HPUX .bin installer error when verifying JVM (Bug #1105510)
  - Added install support for Java 1.5.x (Bug #1105514)
  - Clarified the definitions of the "NAME" and "EXT" portions of a filename
    for FS COPY/LIST DIRECTORY commands in the STAF User Guide (Bug #1084739)
  - Fixed PYSTAF ImportError when using the STAF Python library on Linux
    (Bug #974507)
  - Fixed problem in services/log/PySTAFLog.py example (Bug #1044826)
  - Removed Java 1.1 support in STAFJavaServiceHelper.cpp in order to fix a
    build error on Linux AMD64 (Bug #1114820)
  - Improved error message provided for RC 25 (insufficient trust) by all
    services (Bug #1054858)
  - Changed the TCP socket created by STAF to be non-inheritable to fix a
    hang problem that can occur when submitting a request (Bug #1118295)
  - Fixed some process completion and shutdown notification problems and
    changed trace messages to use the endpoint, not machine (Bug #1118940)
  - Added STAF::wrapData() Perl method which was inadvertently removed in
    3.0.0 Beta 4 when the Perl service changes were made (Bug #1119433)
  = Removed the zxJDBC code from our distribution of Jython (Bug #1118221)
  - Fixed problem with disabling tracing for services (Bug #1121160) 
  - Fixed problem with PYSTAF.dll not being a valid Windows image
    (Bug #1122905)
  - Changed the queue message structure so that the machine field contains
    the endpoint (Bug #144167)
  - Added InstallShield support for IBM Java 1.4.2 (Bug #1150221)
  - Added resolve variable methods to the STAFUtil class for use by Java
    services (Bug #1151440)
  - Added a note to the STAF User's Guide that STAF-enabled programs written
    in C must be linked with the C++ compiler (Bug #1153704)
  - Updated the STAF InstallShield installers to bundle newer JVMs which
    resolve security issues (Bug #1149985)
  - Fixed UnsatisfiedLinkError during .jar installation on Unix (Bug #1156092)
  + Added license information to the InstallShield and STAFInst installers
    (Feature #1101944)
  - Changed license from GPL to CPL for all source code (Bug #1149491)  
  - Fixed problem building jython along with dependent service (Bug #1156934)
  - Added more examples for the PROCESS service's START request in the STAF
    User's Guide (Bug #1160201)
  - Fixed problem where STAFProc hung if line in config file was too long and
    increased the maximum length for a line to 2048 characters (Bug #1160287)
  - Fixed command parser hang if ending double quote not found (Bug #1150901)
  + Added a LONG option for a SERVICE LIST REQUEST (Feature #1165660)
  - Provide libstdc++.so.5 in Linux IA64 and AMD64 builds (Bug #1165597)
  + Marshalling updates (Feature #740150)
    - Added Python formatObject pretty printing API
    - Added documentation for marshalling APIs to the STAF Python User's Guide
  = Moved STAF_MIN macro to STAFUtil.h and added STAF_MAX macro
    (Feature #1174981)
  - Provided a way to not have environment variables updated during the STAF
    installation, and documented the commonly-used silent install options
    (Bug #1175138)
  - Fixed problem doing an upgrade install if the same version of STAF is
    already installed at another location on the machine (Bug #1181756)
  - Fixed STAFProc crash on AIX and HPUX when running ZIP ADD (Bug #1181083)
  - Include LICENSE.htm in all installations (Bug #1184010)
  - Provide AIX 4.3.3 (IPv4 only) and AIX 5.1 (IPv4/IPv6) builds (Bug #1152619)
  - Fixed a remote logging problem with the machine nickname (Bug #1186326)
  + Update delegated services to function correctly (Feature #1074255)

-------------------------------------------------------------------------------

Version 3.0.0 Beta 7: 12/14/2004

  - Fixed problem where the STAF and STAFTCP library files were not being
    installed during -silent or -console installs (Bug #1076914)
  - Fixed "zero bytes when unzipping JAR archives" issue (Bug #1076948)
  - Install STAFDataTypes.h and STAFDataTypesInlImpl.cpp in the include 
    directory (Bug #1071233)
  + Marshalling updates (Feature #740150)
    - Added toString and formatObject APIs to the STAFMarshallingContext
      class to provide "pretty print" capabilities for marshalled data.
    - Updated pretty print verbose format to not show quotes/commas by default
  - Updated docs for displaying raw output from STAF command (Bug #1080912)  
  + Added support for uninstalling upgrade versions of STAF that are not
    recognized by InstallShield 10.5 (Feature #1024694)
  + Added operational parameter STRICTFSCOPYTRUST to change default to do
    lenient trust checking on a FS COPY request (Feature #1081727)
  = Added physicalInterfaceID to request structure for C++/Java, etc. services
    (Feature #550251) 
  - Fixed unzip symbolic link issue (Bug #1084676)
  + Added "move Zip archive handling out of STAFZipFile class"
    (Feature #1084669)

-------------------------------------------------------------------------------

Version 3.0.0 Beta 6: 11/19/2004

  + Communication Interface Enhancements (Feature #550251)
    - Changed the options provided when listing/querying interfaces to be
      a map of the options, instead of a list of the options
    - Removed the CONNECTTIMEOUT operational setting and added an option named
      CONNECTTIMEOUT that can be specified when configuring a TCP interface.
    - Fixed problem where the CONNECTTIMOUT value was not being used.
    - Fixed trust problem with FS COPY request if you specify your hostname as
      the TOMACHINE system and fixed a problem with the error message
    - Changed FS COPY APIs which means that Beta 6 cannot copy to/from
      previous STAF V3.0 Betas (but can copy to/from STAF V2.x machines).
    - Changed MACHINE operational parameter to be named MACHINENICKNAME
      instead to better reflect its usage
    - Fixed some local trust issues
    + Added WhoAreYou request to the MISC service
  - Removed line separators from message text to display better.
  + Updated STAF Service Developer's Guide to discuss using marshalled data
    structures to represent multi-valued results and updated the sample Java
    and C++ services to return marshalled results for LIST/QUERY requests
    (Feature #1059691)
  + Marshalling updates (Feature #740150)
    - Added Python marshalling and unmarshalling APIs
    - Added ability to specify "short" column headings for a map class that
      will be used by the STAF command if the column heading is longer than
      the longest data in the column in a table format
    - Added wrapping to the STAF command table format so that long data for
      a field will be wrapped within the column
    - Added a -verbose option to the STAF command which can be used to get the
      result in a verbose format (without setting STAF_PRINT_MODE=verbose)  
    - Added LONG option to the PROCESS LIST request to include workload
  + Added a default maximum number of records of 100 to be used when querying
    a log file if you do not specify FIRST/LAST/ALL/TOTAL/STATS.  This setting
    is configurable (DefaultMaxQueryRecords setting) and added an ALL option
    to the LOG QUERY request (Feature #1040232)
  - Fixed problem with the FmtLog utility not supporting the new log record id
    format which includes the user and endpoint fields (Bug #1062606)
  + Provided a STAF V3.0 Migration Guide which discusses changes that STAF V2.x
    clients will have to make (Feature #1062488)
  + Changed LOG QUERY request so that it only returns date-time, level, and
    message and added a LONG option which returns all fields, added a ENDPOINT
    option to query by endpoint (Feature #1064711)
  + Updated STAFInst to create the STAFEnv.sh script file during installation
    (Feature #1064587)
  - Fixed memory leak in local connection providers (Bug #1069481)
  + Updated STAF install to use InstallShield 10.5 (Feature #1024694)
  + Added machine polling support for Garbage Collected Handles
    (Feature #464845)
  + When starting a process, set a handle variable that contains the endpoint
    for the originating system (Feature #1069765)
  
-------------------------------------------------------------------------------

Version 3.0 Beta 5: 10/30/2004

  - Changed how line endings in a file are determined during a FS GET FILE
    request (Bug #1040001)
  - Fixed ZIP service inflate file problem for InfoZip archive (Bug #1033654)
  + Remove all STAFReg.cmp files during installation (Feature #1042451)
  + Added TYPE option to QUEUE service's QUEUE/GET/PEEK/DELETE requests
    (Feature #1044711)
  + Changed services to return init/term result strings (Feature #584049)
  + Changed request and process complete queued messages so that the message
    being queued is a marshalled map and so that the type of the queued message
    is STAF/RequestComplete or STAF/Process/End, respectively (Feature #740150)
  + Add ability to specify a port when submitting a STAF request, e.g.
    tcp://client1.austin.ibm.com@6500 (Feature #930713)  
  + Communication Interface Enhancements (Feature #550251)
    - Removed the USELONGNAMES operational setting
    - Removed the STAF/Config/EffectiveMachine system variable
    - Added the STAF/Config/MachineNickname system variable
    - Changed the service request interface for all services and changed the
      min/max interface levels to be 30 for all STAF V3.0.0 services
    - Updated FS Copy File/Directory APIs to determine trust based on machine
      and user  
    - Added endpoint information to various services to provide more
      information about the originator of a request  
  + Update all tarballs to always unpack into a single directory 
    (Feature #930468)
  + Improve unzip's performance on large files (Feature #1055682)
  - Fixed problem loading Java services on Linux (Bug #953334)
  + Allow installation of multiple different versions of STAF on a single
    machine (Feature #627811)
  + Support installation upgrades (Feature #627811)  

-------------------------------------------------------------------------------

Version 3.0 Beta 4: 09/29/2004

  + Communication Interface Enhancements (Feature #550251)
    - Removed the MACHINE request from the MISC service since you can no longer
      configure STAF to use long vs short machine names so it no longer serves
      any purpose
  + Added a HELP request to the simple services:  DELAY, ECHO, and PING
    (Feature #983742)
  + Changed STAFInst so that FmtLog is installed during a Recommended
    installation (Feature #986818)
  - Fixed problems deleting symlinks on a FS DELETE request (Bug #604347)
  - Fixed problems providing correct error information on a FS DELETE request
    (Bug #999677)
  - Fixed problem with environment variables when starting a process on Windows
    (Bug #999053)
  - Improved error message on Unix when starting STAFProc without staf/bin in 
    PATH (Bug #824522)
  - Fixed problem accessing files with a timestamp of Feb. 29, 2000
    (Bug #1000886)
  - Updated Reg service to work on Windows (Bug #1008888)
  - Fixed problem running zip service causes bus error (core dump)
    (Bug #994218)
  + Added STARTSWITH/CSSTARTSWITH options to LOG QUERY/PURGE reqeusts
    (Feature #1010240)
  + Added support for new format for multi-valued results (marshalled results):
    - Provided marshalling/unmarshalling apis for C/C++, Java, and Perl
      (unmarshalling only for Perl)
    - Updated all internal services which have multi-valued results.
    - Updated external services provided with STAF: LOG, MONITOR, and RESPOOL
    - Updated STAF Demo to handle marshalled results from LOG/MONITOR requests
    (Feature #740150)  
  + Added support for Perl services (Feature #544063)
  - Fixed problem that zip service can't read permission info in the latest 
    InfoZip archive (Bug #1012202)
  - Fixed problem on Windows when starting a process using the default SHELL
    option to preserve quoting in command/parms (Bug #1025075)
  + Added garbage collection for the Sem and ResPool services so that when
    handles terminate without freeing semaphores or resource entries, these
    services will automatically free them (Feature #464845)

-------------------------------------------------------------------------------

Version 3.0 Beta 3: 06/28/2004

  - Fixed RC 22 on HP-UX for all local requests (Bug #951417)
  - Fixed problem where the STAFInst script did not have execute permission
    (Bug #944947)
  + Communication Interface Enhancements (Feature #550251)  
    - Added support for the DEFAULTINTERFACE operational parameter
    - Added support to allow multiple copies of STAF to run on the same system
    - Changed to use "://" instead of ":" used in previous Betas to separate
      interface and machine identifier, e.g. local://local,
      tcp://server1.austin.ibm.com
  + User Level Security (Feature #627135)
    - Changed to use "://" instead of ":" to separate authenticator and user
      identifier, e.g. SampleAuth://User1
  + Removed deprecated messages in STAF and the Log, Respool, and Monitor
    services and removed settings for old/new return codes (Feature #935899)
  + Removed the following deprecated utilities and executables:
    GenWl, STAF.cmd and CSTAF (Feature #935896)
  + Added support for Windows IA64 (Feature #914308)
  - Fixed problem where the AIX STAF install failed with "Null" error
    (Bug #965002)
  + When a "InProcess" service (e.g. C++ or REXX) is removed, un-register its
    handle (Feature #966079)
  - Fixed problem where the Linux STAF Python and TCL support was not being
    installed via STAFInst (Bug #968922)
  + Updated QUERY/LIST request string syntax for PROCESS and HANDLE services 
    (Feature #627830)
  + Added RUNNING and COMPLETED options to PROCESS LIST (Feature #971250)
  + Add LIST/QUERY requests to MISC service to show information on which
    interfaces are enabled (Feature #464832)
  - Fixed Java Support on AIX (Bug #951438)
  - Removed reference to STAF/Service/Log/Retry variable in STAF User's
    Guide (Bug #978549)
  + Added support for IPv6 to the TCP interface (Feature #914310)  
  + Standarized request string syntax for SEM service (Feature #979770)    

-------------------------------------------------------------------------------

Version 3.0 Beta 2: 04/29/2004

  - Fixed STAFStringConstruct exception when dealing with ZIP archives whose
    "Extra Field" contains unreadable charactors (Bug #928442)
  - Added ISMP Uninstaller support for IBM Java 1.4.1 (Bug #913707)
  - Fixed codepage makefile problem (Bug #932433)
  + Added support for sending variables accross the network (Feature #464843)
  - Fixed STAF User Guide error in autoboot install section (Bug #935317)
  - Fixed problem where STAF receives a SIGSEGV 11 and crashes on Unix systems
    when starting a process that uses temporary files for stdout/stderr
    (Bug #881930)
  + Added a separate Trace service and removed trace behavior from Misc service 
    (Feature #922658)
  - Updated Linux build to use GCC 3.3.3 to resolve SIGSEGV problems    
    (Bug #936685)
  + Added support for HP-UX IA64 (both 32-bit and 64-bit) (Feature #914317)
  + Provided a DATADIR operational setting to specify a writeable location and
    changed STAF and its services to write all data to it (Feature #592875)
  
-------------------------------------------------------------------------------

Version 3.0 Beta 2: 04/29/2004

  - Fixed STAFStringConstruct exception when dealing with ZIP archives whose
    "Extra Field" contains unreadable charactors (Bug #928442)
  - Added ISMP Uninstaller support for IBM Java 1.4.1 (Bug #913707)
  - Fixed codepage makefile problem (Bug #932433)
  + Added support for sending variables accross the network (Feature #464843)
  - Fixed STAF User Guide error in autoboot install section (Bug #935317)
  - Fixed problem where STAF receives a SIGSEGV 11 and crashes on Unix systems
    when starting a process that uses temporary files for stdout/stderr
    (Bug #881930)
  + Added a separate Trace service and removed trace behavior from Misc service 
    (Feature #922658)
  - Updated Linux build to use GCC 3.3.3 to resolve SIGSEGV problems    
    (Bug #936685)
  + Added support for HP-UX IA64 (both 32-bit and 64-bit) (Feature #914317)
  + Provided a DATADIR operational setting to specify a writeable location and
    changed STAF and its services to write all data to it (Feature #592875)
  
-------------------------------------------------------------------------------

Version 3.0 Beta 1: 04/02/2004

  + Added communication interface enhancements including:
    - Allowing multiple pluggable network communication interfaces
    - Removing the constraints on network name specifications
      - Support mixed long and short names for machine names on requests
      - Support IP addresses for machine names on requests
    - Allowing trust specifications to contain wildcards and IP addresses
    (Feature #550251)
  + Added user level security, in addition to the existing machine level
    security including:
    - Allowing pluggable authenticators and providing a sample authenticator
    - Allowing trust specifications for users, including support for wildcards
    (Feature #627135)
  - Fixed RC 22 STAFConnectionReadSTAFString: Error reading from socket
    error on a remote request to a Windows system (Bug #926825)  
  
-------------------------------------------------------------------------------

Version 2.6.1: 04/01/2004

  - Resolved variables for LIST TRIGGER/SOURCE request to the DIAG service
    (Bug #914288)
  - Corrected the nested jar file section of the STAF Service Developer's
    Guide to show how to correctly nest the jar files (Bug #913155)
  - Fixed problem where STAFHandle.submit/submit2 core dumps Java if a null
    value is passed to it (Bug #917232)
  - Added a delay for a random time before the next connection retry attempt to
    help avoid RC 16 recv: 111 errors and added a new operational parameter
    CONNECTRETRYDELAY to make the maximum delay time configurable (Bug #915342)
  + Added libstdc++-libc6.2-2.so.3 to Linux build/install (Feature #923476)
  - Fixed Perl problem where all STAF calls were made from the most recently
    created STAF handle (Bug #926738)
  + As an aid for migrating to STAF V3.0, instrumented the VAR service to
    record diagnostics data since the syntax of all of its requests will be
    changing in STAF V3.0 (Feature #464843)
    
-------------------------------------------------------------------------------

Version 2.6.0: 03/03/2004

  + Added information to the STAF User's Guide on how to have STAF 
    automatically start as a Windows service during reboot (Feature #889847)
  - Fixed problem "WsnInitialContextFactoy Class Not Found" (Bug #889770)
  + Added information to the STAF User's Guide on how to have STAF
    automatically start during reboot on Unix (Feature #464848)
  + Removed error messages displayed in STAFProc window if can't register
    with automate.austin.ibm.com (Feature #853521)
  + Provided a new internal Diagnostics service, called DIAG, which allows
    you to record diagnostics data and interact with the diagnostics data
    collected (Feature #893634)
  + Added support for Perl 5.8 on Windows and Linux (Feature #890822)
  + Added a new external Zip service, called ZIP, which allows you to 
    Zip/Unzip/List/Delete PKZip/WinZip/Jar compatible archives
    (Feature #890827)
  + Added support for command separator in STAF global variable pool
    (Feature #556432)
  + As an aid for migrating to STAF V3.0, instrumented STAF requests that will
    be changing in STAF V3.0 to record diagnostics data (Feature #853620)
  + Changed the Windows ISMP installer to be a console launcher, so that
    silent installations will not return until the install actually completes
    (Feature #902942)
  - Fixed problem where infinite event/mutex semaphores would time out
    inadvertently on Solaris, resulting in STAF shutting down (Bug #890837)  
  + Added a notify key to the Process Service (Feature #626917)
  - Fixed problem where a STAFException with large message text causes
    STAFProc to terminate abnormally (Bug #906259)
  + Increased default maximum record size for LOG service from 1024 to
    100,000 bytes (Feature #908645)

-------------------------------------------------------------------------------

Version 2.5.2: 01/27/2004

  - Fixed problem "Could not open file /usr/local/staf/codepage/iso88591.bin"
    (Bug #815979)
  - Fixed error in Log service where level User7 was shown as UseR7
    (Bug #816930)
  - Added notes to STAF Users's Guide silent install section to logout/login
    on Unix and to restart on Windows 95/98/ME systems (Bug #819624)
  - Miscellaneous updates to the STAF Service Developer's Guide (Bug #820959)
  = Created an aix421 package (Bug #821438)
  + Added new operational parameter CONNECTATTEMPTS to specify the maximum
    number of times to attempt to connect to a remote system (Feature #827639)
  - Added a new environment variable called STAF_REPLACE_NULLS used by the
    STAF executable to replace null characters in the result string to prevent
    truncation (Bug #863127)
  - Unregister Help service errors for Log, Respool, and Monitor services
    (Bug #878447)
  - Delete stdout/stderr files if PROCESS START command fails to start the
    process (Bug #885014)
  - Fixed RC:10 error on HP-UX if PROCESS START uses temporary stdout or
    stderr files (Bug #883296)

-------------------------------------------------------------------------------

Version 2.5.1: 09/26/2003

  - Removed libC.a and libC_r.a from AIX packaging (Bug #791557)
  + Added support to start a process using RETURNSTDOUT/ERR without having
    having to specify a STDOUT/STDERR filename (Feature #523404)  
  - Fixed FS COPY DIRECTORY RC 22 problem when copying a directory from a
    STAF 2.5.0 machine to a STAF 2.4.5 or lower machine (Bug #810650)

-------------------------------------------------------------------------------

Version 2.5.0: 07/28/2003

  - Fixed PROCESS START request bug on Unix systems where it returned RC 46
    instead of 0 with option IGNOREDISABLEDAUTH specified (Bug #711634)
  - Added help text for error code 51, Directory Copy Error (Bug #719284)
  - Fixed UTF8 conversion problem when accessing a string that contains DBCS
    characters (e.g. via FS GET FILE), but the system is English (Bug #719998)
  - Fixed a Java submit2 error where the result was incorrectly being converted
    from UTF8 to the current codepage (Bug #723415)
  - Fixed Latin-1 codepage conversion hang problem for strings containing DBCS
    characters (Bug #729827)
  - Fixed how STAF determines the codepage on non-English Linux systems
    (Bug #730469)
  - Added try/catch block for process sendNotification exceptions (Bug #740156)
  - Fixed codepage converter exception found when get a STAX parsing exception
    message containing Chinese or other DBCS/MBCS text (Bug #740164)
  - Added more information to the error message when registering a Java service
    and the java executable is not found in the path (Bug #609975)
  - Fixed FS QUERY request bug where it returned an error if the path specified
    had one or more trailing slashes (Bug #726956)
  - Reduced memory use when returning files via a process start request
    (Bug #711604)
  + Added a new codepage variable called STAF/Config/CodePage (Feature #750306)
  + Added support for Windows 2003 (Feature #749572) 
  - Fixed "JVM not found" error with STAF Jar installation (Bug #725261)
  - Fixed problem where ISMP STAF Uninstall fails with "No suitable JVM found" 
    error (Bug #709711)
  - Added a 0-arg constructor for Java STAFResult (Bug #754377)
  - Fixed bug where FS command line not checking all command options
    (Bug #737123)
  + Added support for converting line ending characters on a FS GET FILE for
    text files and added support for displaying in hex (Feature #526463)
  + Added support for converting line ending characters on a FS COPY FILE/
    DIRECTORY for text files and added support for codepage conversion on 
    text file copies (Feature #526463)
  + Allowed substitution of a userid/password in the shell option used when
    starting a process (Feature #751503)
  + Updated STAF builds to use InstallShield MultiPlatform 5.0 
    (Feature #750249)
  - Removed support for PASSWD and SHADOW as process authentication modes
    (Bug #758214)
  - Removed STAF 1.2 checks during Windows installation (Bug #759558)
  - Fixed bug where STAFProc prevents Windows system shutdown (Bug #737123)
  - Fixed RC 22 problem on Unix systems for PROCESS START (no SHELL option)
    requests containing non-English characters (Bug #675502)
  - Fixed problem creating relative paths using a FS CREATE DIRECTORY request
    on Unix systems (Bug #769141)
  + Added support for z/OS V1.4+ (Feature #463682)
  - Fixed problem where a custom install location could not be specified 
    during a silent STAF installation (Bug #776459)
  - Fixed Chinese codepage mapping error for the line-feed (x0D) character
    discovered on a FS GET/COPY TEXT request (Bug #777196) 
  + Install all language support in a Typical STAF installation
    (Feature #778988)
  - Documented how to get around codepage translation problems on Windows
    systems whose locale (e.g. French) sets the ANSI and OEM codepages to
    different values (Bug #775356)
  - Fixed problem deleting a service jar file on Windows after the Java service
    has been dynamically removed via a SERVICE REMOVE request (Bug #779861)
  
-------------------------------------------------------------------------------

Version 2.4.5: 03/27/2003

  + Added Copy Directory request to FS service (Feature #562568)
  - Fixed RC 22 when sending Async requests to non-existant services
    (Bug #704659)
  - Fixed wrong RC (10 instead of 50) when submitting a FS DELETE request for
    a non-empty directory on Win95 and Solaris (Bug #703776)
  - Fixed FS CREATE DIRECTORY bug where it returned RC 10 even though the
    directory was created if the directory name had a trailing slash and
    FULLPATH was specified (Bug #671971)
  - Fixed RC 4007, Invalid file format, query problem in the Log service and
    improved the Log service's performance (Bug #676437)
  + Changed FS GET FILE required trust level to 4 (Feature #709645)
  - Fixed bug where we used the wrong file pointer when determining file size.
    Also removed old linker flag that was causing exceptions not to be caught
    on Linux PPC-64 (Bug #709723)
  - Fixed typo in STAF Python User's Guide example (Bug #710457)
  - Display RC/Result for all STAFDemo errors (Bug #710535)

-------------------------------------------------------------------------------

Version 2.4.4: 03/11/2003

  - Fixed STDIN option on process service start requests (Bug #658842)
  + Added support to allow retrieval of request start times (Feature #656412)
  - Fixed TODAY option on LOG requests (Bug #613357)
  - Updated STAF User Guide, section "7.2 Option Value Formats", on how to use
    the name of an option as the value of an option (Bug #669975)
  - Fixed wrong RC for the Monitor service's query request (Bug #671443)
  = Updated internal service interface to pass a structure instead of
    individual parameters (Feature #668090)
  - Fixed wildcard matching, used by FS service (Bug #677529)
  - Fixed reference to invalid log levels in STAF User Guide (Bug #681041)
  - Updated PROCESS service help to include RETURNxxx options (Bug #681739)
  - Fixed MONITOR service's QUERY request to resolve variables (Bug #682609)
  - Fixed RESPOOL service's REMOVE ENTRY request to return correct RC if
    the entry is owned (Bug #684081)
  - Fixed DELAY and ECHO services trust level checking (Bug #694472)
  - Updated documentation for HANDLE service to include the [STATIC] option in
    the QUERY ALL request (Bug #698339)
  - Fixed link problem with libJSTAF.sl on HP-UX (Bug #699495)
  - Fixed Windows 95 STAFProc startup problem (Bug #696973)
  - Captured stdout/stderr for the JVM processes for diagnostic purposes when
    STAF Java services encounter a problem (Bug #681081)

-------------------------------------------------------------------------------

Version 2.4.3: 12/10/2002

  - Fixed STAF Perl User's Guide Example 3.2.2 (Bug #640697)
  - Fixed STAF Perl User's Guide Example 4.3.3+ (Bug #640715)
  + Added support for codepage ibm-936 (Feature #647977)
  + Added support for building Perl 5.8 support (Feature #648698)
  - Fixed problem where superfluous threads were being started by STAF
    executable (Bug #648545)
  + Added new log method to the STAFLog Java wrapper API to support specifying
    level as a String, such as "info" or "Error" (Feature #651209)
  - Fixed "Too many open files" error installing the shared_jython
    directory (Bug # 651693)    

-------------------------------------------------------------------------------

Version 2.4.2.2: 11/14/2002

  - Fixed StringIndexOutOfBounds exception which was occurring in STAX if you 
    returned a file containing null characters (Bug #605664)
  - Fixed OutOfMemory error when running Java services (Bug #635794)

-------------------------------------------------------------------------------

Version 2.4.2.1: 10/31/2002

  - Fixed Japanese codepage conversion problem for backslash (Bug #621527)
  - Fixed incomplete shared_jython directory problem (Bug #623800)

-------------------------------------------------------------------------------

Version 2.4.2: 10/08/2002

  + Added a symbolic link libSTAF.a to libSTAF.so on AIX (Feature #601478)
  - Fixed SHLIB_PATH not set on HP-UX (Bug #604180)
  - Fixed bug where STAFProc would start if an invalid configuration file was
    specified (Bug #607048)
  - Fixed bug in Java service jar class loader for STAX XMLParseError "Can't
    find bundle for base name org.apache.xerces.impl.msg.XMLMessages"
    (Bug #614659)
  - Fixed problem with default process stop using method not being used  
    (Bug #617866)
  - Fixed Log service problem where FIRST option returns one more record than 
    specified (Bug #613354)
  - Fixed MBCS codepage conversion problem for backslash (Bug #617232)
  - Added support for additional options (%C, %T, %W, %x, %X) when specifying
    a shell on Windows (Bug #620005)
  - Fixed SET PROCESSAUTHMODE bug on Unix (Bug #620407)
                                                               
-------------------------------------------------------------------------------

Version 2.4.1: 08/23/2002

  - Fixed Java service jar class loader (Bug #597392)
  - Fixed "JVM not found" bug during the jar file ISMP installation 
    (Bug #592783)
  - Fixed ISMP installation exceptions when using Blackdown's Java
    (Bug #580332)
  - Fixed Jar installation failure on Windows XP with Java 1.4 (Bug #598448)
  - Decreased timeout when shutting down STAF (Bug #595269)
  + Provided ability to specify a shell to use when starting a process, and
    to specify a default shell to use via the STAF.cfg file (Feature #565465)
  - Fixed problem tracing to STDERR (Bug #599356)

-------------------------------------------------------------------------------

Version 2.4.0.2: 08/15/2002

  - Fixed typo in User's Guide JSTAF examples (Bug #593272)
  + Added a new trace point, Deprecated, which is causes a trace message to
    be generated for deprecated options that STAF detects (Feature #594218)
  + Provided a port of STAF to PACE on OS/400 (Feature #528694)
  + Fixed Fatal Error on AS400 when loading Java services (Bug #595296)
  + Fixed bug where FmtLog was not being installed during Unix ISMP installs
    (Bug #595652)

-------------------------------------------------------------------------------

Version 2.4.0.1: 08/07/2002

  - Fixed bug on HP-UX which required fully qualified path names for shared
    libraries (Bug #592293)
  - Updated docs to indicate use of SHLIB_PATH on HP-UX (Bug #592296)
  - Fixed problem with HP-UX not keeping reference counts on loaded libraries
    (Bug #592844).
  - Fixed HP Installation bug where JSTAF.jar was not being installed
    (Bug #592141)
  - Fixed HP Installation bug where an incorrect link to /lib/java12/libJSTAF
    was being created (Bug #592171)

-------------------------------------------------------------------------------

Version 2.4.0: 08/05/2002

  - Fixed shared library initialization bug on HP-UX (Bug #590177)
  + Added case insensitive contains for Queue service GET/PEEK/DELETE and
    case sensitive contains for Log service QUERY/PURGE requests (Feature
    #464833)
  + Made process management and tracing APIs part of the OS porting layer
    (Feature #585593)
  - Only list services with an init RC of 0 (Bug #584047)
  - Fixed Unix problem by moving sys/types.h include to top of STAFOSTypes.h
    (Bug #567667)
  - Fixed ucm2bin file converter to find last period in file name (Bug #567424)
  + Updated STAFProc to do a proper shutdown when terminated via SIGTERM,
    SIGINT, SIGQUIT, CTRL+C, and CTRL+Break (Feature #464828)
  - Updated the Java build information for Java 1.2+ in the Developer's
    Guide (Bug #575231)
  + Added TCL build information to the Developer's Guide (Feature #575225)
  - Fixed win32 problems with the TCL makefile (Bug #572864)
  + A stack trace is now returned in the STAFResult bufer when Java services
    throw an exception (feature #464840)
  * The ALLOWMULTIREG configuration setting has been removed.  This setting is
    now permanantly "on".  The configuration file parser will continue to
    recognize the option (but will ignore it) until the V3.0 release of STAF.
  + Made STAFDemo more self-contained (Feature #520493)
  + Enhanced the useability of Java services (Feature #561673)
      1) Ability to load services directly from jar files (i.e., without
         CLASSPATH updates)
      2) Removed need to update dynamic library path for Java services
      3) Java services are now loaded in a JVM separate from STAFProc
      4) Java services may be loaded into isolated JVMs or share the same JVM
  * The options available when registering Java services has changed
    substantially.  Please read section 4.4.3 of the STAF User's Guide for a
    list of the current available options.
  + Added Python build info to Developer's Guide (Feature #572900)
  + Added Perl build info to Developer's Guide (Feature #572860)
  - Fixed Perl process start wait timeout error (Bug #572243)
  - Fixed Perl makefile so that it builds correctly on win32 (Bug #572571)
  - Fixed Windows trap when querying log files (Bug #570293)
  - If the STAFDemo can't start the process, display the RC and result (Bug 
    #569064)
  - Fixed bug where a SEM MUTEX request was being added to the front of the
    pending requests list instead of to the back (Bug #565023)
  + Added support for dynamically adding/removing services (Feature #464868)
  + Added support for Service Loader Services (Feature #464867)
  + Added ONLYHANDLE option to only list handle variables (Feature #464830)
  - Updated Java API docs for static handles (Bug #513446)

-------------------------------------------------------------------------------

Version 2.3.2: 06/03/2002

  + Added support for whitespace around machine and service by stripping
    the whitespaces (Feature #464846) 
  - Fixed bug where CONFIRM option for a LOG PURGE request was not working
    (Bug #523949)
  - Fixed bug where STAFInst fails when run under csh and tcsh (Bug #545577)
  - Fixed invalid reference in User's Guide in Process STOP RC (Bug #513386)
  - Updated Variable Service in User's Guide to clarify you should almost
    always use RESOLVE, not GET, to retrieve a variable value (Bug #517765)
  - Fixed problem where could not escape a left brace, {, in a resolvable
    string. Now, can use a caret, ^, to escape a { or ^. (Bug #562495) 

-------------------------------------------------------------------------------

Version 2.3.1: 04/11/2002

  - Fixed trap/hang on Linux SMP (Bug #538488)
  - Fixed problem starting a process remotely using a statichandlename
    (Bug #505081)
  - Fixed SIGSEGV on Linux PPC 64 when using Java Services (Bug #524502)
  - Fixed problem starting a process with a statichandlename where the var
    parameters were not being set as the primary variable pool (Bug #530537)
  + Added support for Python (Feature #513993)
  + Added trace points for warning and info (Feature #531940)
  - Fixed problem starting a process as a different user on Windows NT/2000/XP
    (Bug #487221)
  - Fixed problem redirecting stdout and stderr to the same file when starting
    a process. Added new option stderrtostdout. (Bug #513452)
  - Fixed problem where the system classpath and the OPTION classpaths for a
    Java service were not being merged when using Java 1.2+.  Added support for
    multiple OPTION J2=-Djava.class.path parameters. (Bug #532645)
  - Fixed problem where a process start request specifying a shell command
    like "date; grep ab ab" would fail because it was trying to verify that
    the first subword is a valid command. Unix only. (Bug #541732)
  - Fixed SIGSEGV on Unix systems when a process start shell command's length
    is 36. (#542679)

-------------------------------------------------------------------------------

Version 2.3.0: 12/13/2001

  - Fixed another multi-thread bug on Solaris
  - Removed superfluous (and erroneous) constant from STAFOSTypes.h
  - Fixed bug running Java STAF applications on HP/UX
  - Fixed OS HANDLE leak (win32 only) (Bug #456606)
  + Added a STAFQueueMessage class to Java support
  - Fixed multi-thread bug with gethostbyname() (Bug #460757)
  - Fixed bug with permissions of files created via the PROCESS service's
    STDOUT[APPEND] and STDERR[APPEND] options (unix only) (Bug #461613)
  + Added support for arbitrary shell commands via a new SHELL option to the
    PROCESS services's START command (Feature #461616)
  - Fixed bug where processes STARTed with STDIN/OUT/ERR redirected could not
    delete the redirection files (win32 only) (Bug #462669)
  - Fixed bug where processes STARTed with STDOUT/STDERR didn't have the files
    properly truncated (Bug #462672)
  + Added support for Irix (Feature #463681)
  - Fixed bug when lots of STAF handles exist (win32 only) (Bug #466975)
  - Fixed bug with multi-handle registration on Win95/98/Me (Bug #466976)
  - Fixed problem running Java services on many JVMs (Bug #464869)
  + Added support for returning stdout, stderr, and arbitrary text files when
    a process completes (Feature #464467)
  - Fixed bug where sometimes got RC 6 in STAFRWSemWriteUnlock (Bug #478357)
  + Converted the ResPool service from REXX to C++ (Feature #464864)
  - Changed several Monitor Service return codes from kSTAFInvalidRequestString
    to kSTAFInvalidValue (Bug #478900)
  - Added WinXP support for STAF/Config/OS/Name variable (Bug #478479)
  + Added a variety of file system related commands to FS service (Feature
    #461618 and #461619)
  - Fixed bug sending process end notifications on Linux (Bug #464807)
  + Added support for using static handles from Java (Feature #464857)
  - Fixed bug where processes started with a command & parms whose length
    was > 1024 caused the buffer to overrun and get a segfault (Bug #491608)
  + Increased performance on Windows by 20%
  + Extended AIX support from 4.3.3+ to 4.2.1+

-------------------------------------------------------------------------------

Version 2.2.0: 06/19/2001

  + Added support for HP-UX
  - Fixed bug where stdin/out/err were not displayed when none of them were
    being redirected (win32 only)
  - Fixed bug where incorrect timestamps were being returned by the file
    system APIs (win32 only)
  - Fixed Handle leak bug (win32 only)
  - Fixed bug in STAFLog.rxl where importing 'All' didn't import STAFInitLog
  + Moved HELP service inside STAFProc so that is always available
  * Due to the above move, you should not try to register the old REXX-based
    HELP service
  + Enhanced HELP service so external services can register their error codes
    with it
  + Updated Log and Monitor services to register error codes with HELP service
  + Added a STAFUtilFormatString function, ala printf(), to simplify creating
    STAF request strings.  This is exposed as the formatString() method on the
    STAFHandle class.
  - Fixed bug in Monitor Service where Machine names were case-sensitive
  - Fixed bug where bad handles were returned to STARTed processes (win32 only)
  - Fixed problem prematurely closing socket connections
  - Fixed multi-threading problem on Solaris
  - Fixed bugs when logging and querying log files using bit-strings
  - Fixed standard/daylight savings time bug with Timestamps
  + Enhanced tracing support.  You can now trace only certain services, trace
    requests to other systems, and trace registrations.  Additionally, more
    "Error" conditions are now traced.
  + Improved FS COPY FILE performance
  * Changed default trust level to 3 (was 2)
  = Changed default INITIALTHREADS to 10 (was 5)
  + Added support for "static" handles.  This allows full integration with
    shell-script applications.
  - Fixed file-locking bug preventing use of Log service on Win95/98/Me
  + General performance improvements: 5-30% on Unix, 30-70% on Win32
  - Fixed bug where pending handles from WAITed on processes were not freed
  - Fixed timing bug which resulted in ghost processes
  = Now officially check whether STAF is already running on win32
  - Fixed bug preventing STAF from working on Win95
  - Fixed bug with HANDLE logs when using remote logging
  - Fixed bug listing machines in log service when using USELONGNAMES

-------------------------------------------------------------------------------

Version 2.1.0: 03/02/2001

  + Enhanced STAF command line handling of quoted parameters.  It should now be
    significantly easier to enter commands containing quoted strings from the
    command line.
  + Added support for asynchronous requests (see STAFSubmit2 in the User's
    Guide)
  + The Log and Monitor services have been rewritten in C++, improving their
    performance substantially and allowing them to operate on all supported
    STAF platforms.
  * Standardized all C/C++ API return codes and exceptions.  Existing C/C++
    applications should work unchanged, but new (or recompiled) applications
    may need some mild cleanup.
  + Added support for starting processes as different userids
  + Added support for redirecting stdin/stdout/stderr on started processes
  + STAFProc's environment variables are now exposed through STAF variables
    of the form STAF/Env/&lt;Name>
  - Fixed bug where environment variables weren't being overwritten when
    starting a process on some unix systems
  - Fixed multi-processor bug on win32
  + Added STAF variables for the STAF version number and the configuration file
    being used
  - Fixed bug in error message handling of Rexx services
  - Fixed bug where Java STAFUtil class wasn't public
  - Fixed bug where Java STAFMonitor class constructor wasn't public
  - Fixed bug preventing execution on WinMe and incorrect identification of
    Win2000
  + Unix shared libraries can now be specified like other platforms (i.e.,
    without the 'lib' and '.so')
  + Reduced unix disk and memory requirements
  + The Service service has been updated so that you may list the service
    requests currently being handled by STAF

-------------------------------------------------------------------------------

Version 2.01: 10/11/2000

  - Fixed bug where STAFCommandParseResultGetOptionValue() returned
    incorrect value for non-existant option (this manifested itself as
    a problem using the Event service)
  - Fixed timing problem with FS copy (this manifested itself as a problem
    submitting jobs to WorkFlow Manager)
  - Fixed service termination order

-------------------------------------------------------------------------------

Version 2.0: 09/13/2000

  + Now supported on Linux and Solaris
  + Removed internal STAF dependency on service implementation language   
  * Service registration in STAF.cfg has changed to support the previous
    enhancement (see the configuration section of the STAF User's Guide)
  + Added support for multiple line configuration statements in STAF.cfg
  + Added support for processes to register multiple times (see the
    discussion of ALLOWMULTIREG in the configuration section of the STAF
    User's Guide)
  + We now provide a C++ STAFHandle class
  * Renamed STAFHandle typedef to STAFHandle_t to support the above C++
    STAFHandle class
  + We now provide a set of C/C++ APIs to handle operating system abstraction
    and UTF8 string support
  + The STAFCommandParser is now available for C/C++ services
  = Removed dependency on Visual Age compiler
  - Fixed bug in ResPool that prevented "in use" resources from being deleted
    with the FORCE option
  + The Win32 version now uses InstallShield to do the installation
  + Added service interface level 2 for Java services
  + STAF now uses UTF-8 internally.  This enables round-trip data integrity
    between different codepages/languages.
  + Each copy of STAF is now automatically registerd with a central system in
    Austin (although you can opt out).  This allows us to better determine
    our user base.
  + Added ability to STOP processes "gracefully".
  + Added ability to start processes with or without a new console window
    (Windows only)
  * Moved to a unified STAF command line executable.  The older STAF.cmd and
    CSTAF are still provided, but will be removed in a future version of STAF.

-------------------------------------------------------------------------------

Version 1.21: 11/03/1999

  + Now supported on AIX
  + Added INITIALTHREADS and THREADGROWTHDELTA options to the STAF.cfg SET
    command
  - Fixed problem where PROCESS FREE ALL (or WORKLOAD) could return the
    wrong number of total processes
  - Fixed internal synchronization problem where a process could be removed
    from the process list before it was added, resulting in an exception

-------------------------------------------------------------------------------

Version 1.20: 05/19/1999

  + Added Java services support
  - Fixed bug when performing a QUEUE GET WAIT across midnight
  + Added GET command to FS service
  + Added STAF/Config/Sep/Line, STAF/Config/Sep/File, and STAF/Config/Sep/Path
    variables
  - Fixed service initialization order bug
  + STAFProc now looks in the current directory and
    {STAF/Config/STAFRoot}\bin for STAF.cfg
  + Now allow external services to accept parameters at Init
  - Fixed SEM EVENT WAIT bug where RC:37 could be returned when there are
    multiple waiters and a POST and RESET are done quickly back to back
  + Now allow QUEUE GET/PEEK to specify multiple PRIORITIES, HANDLES,
    MACHINES, NAMES, and CONTAINS
  + Added PULSE option to EVENT command of SEM service
  + Added a folder and icons for STAFProc and HTML Documents, and a link to
    the STAF Web Site for both WIN and OS/2 platforms
  + Added an install record which is created when STAFInst is run, in order
    to document installation history and parameters
  - Fixed a bug in Log in the Query/Purge code using the BEFORE option
  = Modified Help, Monitor, Log and RLog to conform to the new REXX
    STAF_SERVICE_INTERFACE_LEVEL, new call structure, and new variable
    naming convention: STAF/Service/&lt;serviceName>
  - Fixed bug where machine requesting an FS COPY from another machine was
    required to give the sending machine TRUST LEVEL 4
  + You may now delegate a service to a service with a different name on the
    delegatee machine
  + Added the ability to RESOLVE multiple strings in one call
  + Added the ability to perform VAR service commands on the variable pool
    of a given handle
  + All service command options that resolve variables will now resolve
    from the handle's variable pool before resolving globally
  + Added USEPROCESSVARS option to PROCESS START
  * Service registration in the STAF Configuration File has been changed (and
    simplified).
  + Updated GenWL to support global processes and process references,
    inclusion and exclusion of specific machines and processes, and other
    features (see User's Guide full more information)
  - Fixed bug when first character of length delimited data is a colon
  + Added connection timeout configuration parameter
  * Changed names of preset STAF variables to fit within hierarchical
    naming convention
  * Merged MAXREQUESTS, MAXQUEUESIZE, and USELONGNAMES configuration statements
    into one SET configuration statement
  + Added RESPOOL service to standard distribution


-------------------------------------------------------------------------------

Version 1.11: 07/31/1998

  + Now supported on Win95, Win98, and WinNT 4.0
  + Added STAFProc initialization and termination messages
  - Added termination handler for uncaught exceptions

-------------------------------------------------------------------------------

Version 1.10: 05/19/1998

  * The MACHINE and INTERFACE statements in the STAF configuration file
    are now mutually exclusive
  + Official STAF Web Site created: http://automate.austin.ibm.com/staf/
  + Added an index to the STAF User's Guide
  + Added a Services Command Reference to the STAF User's Guide and web
  + Created a seperate STAF API Return Codes document on web
  + Added additional examples/samples to the STAF User's Guide
  + Created a STAF Frequently Asked Questions web document
  + Added STAF future direction to web site
  + Added tracing to STAF (trace command added to MISC service)
  - Fixed a bug when queuing when neither HANDLE nor NAME is specified
  - Fixed intermittent RC:21 error
  - Fixed DBCS bug reading log files in STAFLog.cmd, this required a
    new log record format (backward compatability maintained)
  - Fixed install bug when updating CONFIG.SYS
  - Fixed install bug when updating STARTUP.CMD
  - Fixed GenWL query bug with monitor
  - Fixed DBCS 0x5C translation bug.  Now converts around "\" character
  - Fixed a bug in external services where if passed a null NAME
    or REQUEST was causing a Rexx error.
  - Fixed internal API Level bug with internalSTAFSubmit()
  + Internal parsing changing to support multiple options within OptionNeed

-------------------------------------------------------------------------------

Version 1.00: 04/14/1998

  NOTE: This version is the first official release of STAF.  No one should
        be running a version of STAF prior to 1.0.  Every attempt will be
        made to keep all subsequent versions of STAF backward compatable
        with 1.0.

  * The Java APIs have been overhauled and are not compatible with the
    earlier Java APIs.  This is primarily due to some name changes.
       You need to import com.ibm.staf.*
       You need to import com.ibm.staf.wrapper.* if you use STAFLog
       Class StafHandle changed to STAFHandle
       Class StafException changed to STAFException
       Class StafLog changed to STAFLog
       The return code from STAFLog.log() changed from int to STAFResult
  + The REXX STAF APIs now default to using the STAFHandle variable, thus, it
    is no longer necessary to specify the handle variable name on
    STAFRegister, nor the handle value on STAFSubmit or STAFUnRegister.
  + It is no longer an error to register an already registered process
  + If you register, unregister, and re-register a STAF STARTed process, you
    will now receive the same handle each time you register
  + The handle information for a STAF STARTed process is now retained until
    the process has been FREEd, instead of STOPed
  - Fixed bug in GenWL where machine and global variables could not contain
    spaces
  + Added an EffectiveMachine global variable
  * The Machine global variable now refers to the long name of the machine
  - During a STAF installation, do not replace STAF.CFG if it already exists
    and STAFCFG is NOT specified in the response file
  + Added AUTOSTART response file option in the STAF installation to
    add a START STAF command in the STARTUP.CMD file
  + Added REPMOD response file option in the STAF installation to
    replace in-use STAF EXE/DLL files

  * Version 1.00 is not compatable with version 0.30

-------------------------------------------------------------------------------

Version 0.30: 03/30/1998

  - Fixed a bug in Monitor where the record format changed and it was
    querying 1 more character than it should.

  + New STAF installation process
  + Added TITLE option to PROCESS START.  This support was also added to GenWL.
  + STAF Variable names are now case insensitive
  + Added timeout option to PROCESS START WAIT
  + Added Active External and Active DLL Services
  + Added levels to all internal APIs for future expansion
  + Added SEM Service
  + Added QUEUE Service
  + Added queueing support
  + Added notifications on STAF START, SHUTDOWN, and PROCESS end
  + Added NLV support

  * Version 0.30 is not compatable with version 0.20
  * STAF Variable names are now case insensitive.

-------------------------------------------------------------------------------

Version 0.20: 03/09/1998

  - Fixed bug writing over sockets with a buffer greater than 4096
  - Fixed error code on recursive variable resolution.  Was incorrectly 0.
  - Fixed intermittent RC:6 calling into STAFProc
  - Fixed broken pipe bug on Warp V3 systems
  - Support for IP addresses instead of names
  - Now handle exceptions on cases where 6 was returned.  Now return better
    error codes and more data on problem.
  - Fixed bug where two services could have the same name.

  + Added access control model
  + Added SERVICE service
  + Added TRUST service
  + Added STAFRLog service (remote logging)
  + Added STAFHelp service
  + Added USELONGNAMES configuration option
  + Added FmtLog utility (format log)
  + Added STAFErr (Rexx STAF Common Error Header)
  + Variable resolution of machine and service on STAFSubmit()
  + Variable resolution in config file
  + PROCESS service variable resolution
  + FS service variable resolution
  + HANDLE service variable resolution
  + Removed forcing of 4000+ return codes on external services
  + STAFLog - Unique variable id, variable resolution, PURGE, STATS
  + STAFMon - Unique variable id, variable resolution
  + REXX Services are now tokenized on startup, enhancing performance
  + Added STAFRoot variable
  + TCP/IP isolation - TCP/IP is no longer required on the machine if the
    TCP/IP interface is not used
  + REXX isolation - REXX is no longer required on the machine if no REXX
    services or APIs are used

  * Version 0.20 is not compatable with version 0.10
  * The VAR service command REMOVE changed to DELETE
  * Access control has been added, you may need to set trust levels.
  * Log configuration variable name changes, reference the STAF User's Guide
  * Monitor configuration variable name changes, reference the STAF User's
    Guide

-------------------------------------------------------------------------------

Version 0.10: 02/16/1998

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
