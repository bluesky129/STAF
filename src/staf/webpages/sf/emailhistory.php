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

<center><h1>Email Service History</h1></center>
<PRE>
-------------------------------------------------------------------------------
History Log for Email Service  
  
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

Version 3.3.9: 06/26/2015

  - Remove the leading comma from the To:, Cc:, and Bcc: values that are
    written to the SMTP message headers (Bug #1551)       
      
-------------------------------------------------------------------------------

Version 3.3.8: 06/30/2013

  - Fixed a problem where a SEND request would fail with a NullPointerException
    if the reply from the mail server was null instead of returning proper
    error message (Bug #3614453)

-------------------------------------------------------------------------------

Version 3.3.7: 03/29/2013

  - Fixed a problem where a SEND request occasionally hangs (e.g. when the mail
    server is very busy). Now it will log a timeout message in the Email service
    log and retry using the backup mail servers if specified (Bug #3607204) 
  - Changed to log a Warning instead of an Error if a Socket IO Exception
    occurs when attempting to send a message and added logging an Error message
    only if all attempts to send the message failed (Bug #3607711)
  + Added the ability to specify a timeout for the socket used to communicate
    with a mail server on a SEND request (Feature #3607715)  

-------------------------------------------------------------------------------

Version 3.3.6: 09/28/2011

  - Documented that non-ASCII text (non-English characters) cannot be correctly
    represented in the message body or subject of a email (Bug #3397654)
  
-------------------------------------------------------------------------------

Version 3.3.5: 12/06/2010

  + Added support for SMTP authentication via the AUTHUSER and AUTHPASSWORD
    options (Feature #3032716)

-------------------------------------------------------------------------------

Version 3.3.4: 08/24/2010

  + Added CC and BCC options for the SEND request (Feature #2955145)

-------------------------------------------------------------------------------

Version 3.3.3: 12/15/2009

  - Added information to the Email Service User's Guide about using telnet
    to debug mail server problems (Bug #2136862)
  - Improved error messages for invalid command requests and added exception
    catching when handling a service request (Bug #2895347)

-------------------------------------------------------------------------------

Version 3.3.2: 09/24/2008

  - Fixed problem where the "To:" field with multiple recipients would
    sometimes not have a "," delimiter between the recipients (Bug #1918028)
  + Changed the makefile to dynamically create the MANIFEST.MF file for the
    Email service (Feature #1013104)    

-------------------------------------------------------------------------------

Version 3.3.1: 02/26/2008

  + Changed STAF license from the Common Public License (CPL) 1.0 to the
    Eclipse Public License (EPL) 1.0 (Feature #1893042)  

-------------------------------------------------------------------------------

Version 3.3.0: 11/09/2007

  - Fixed problem where escaped newline characters in the Email message were
    being converted to newline characters (Bug #1676120)
  - Display exception stack trace for internal exceptions thrown during an
    Email SEND request (Bug #1676850)
  - Added information about possible corruption that can occur in the Email
    message if it contains lines longer than 990-characters, and added
    examples on how to prevent this from occurring (Bug #1690918)
  = Renamed Manifest.mf to MANIFEST.MF so can build this service on Unix
    machines (Bug #1732343)
  - Include the ATTACHMENTMACHINE in the service log entry for SEND
    requests (Bug #1776564)
  - Record any FS GET FILE errors for SEND requests in the service log
    (Bug #1776568)
  - Fixed problems with the line ending characters used when communicating
    with the mail server, and removed the LINEEND setting (Bug #1807234)
  - Fixed hang during Email SEND requests (Bug #1818820)
  - Added machine/requestNumber header information to the beginning of the
    Email service log records (Bug #1828628)
  = Improved handling of multiple recipients (Bug #1829068)
  - Fixed a problem where SET PORT didn't change the port and improved error
    checking for non-numeric PORT and blank MAILSERVER (Bug #1829123)
  + Added ability to configure backup mail servers so that if an IOException
    occurs on a SEND request, the service will retry using the backup mail
    servers (Feature #1828492)

-------------------------------------------------------------------------------

Version 3.2.0: 11/28/2006

  + Added RESOLVEMESSAGE and NORESOLVEMESSAGE options to the service
    configuration PARMS and the SEND request to indicate whether the MESSAGE
    option should be resolved for STAF variables (Feature #1246356)
  - Updated to resolve PARMS values for STAF variables (Bug #1546244) 
  + Added a SET request to allow modification of the Email service parameters
    (Feature #1591616)
  + Added a CONTENTTYPE option to support both text/plain and text/html
    content types in the message body (Feature #1249651)
  - Return an error if the FS GET for a SEND FILE request fails (Bug #1604077)

-------------------------------------------------------------------------------

Version 3.1.4: 08/16/2006

  - Fixed problem where on some SMTP servers an extra attachment called
    "att000xx.txt" would be included in the Email message (Bug #1541600)

-------------------------------------------------------------------------------

Version 3.1.3: 02/27/2006

  - Added a LINEEND configuration parameter to allow additional line ending
    characters required by some mail servers (Bug #1398788)

-------------------------------------------------------------------------------

Version 3.1.2: 11/29/2005

  - Fixed multi-threading problem in Email service (Bug #1368689)

-------------------------------------------------------------------------------

Version 3.1.1: 08/25/2005

  - Fixed problem where emails were not being sent when using certain SMTP
    servers (Bug #1160687)

-------------------------------------------------------------------------------

Version 3.1.0: 06/11/2005

  + Added LIST SETTINGS request to show the service settings such as mail
    server and port (Feature #989754)
  + Added support for text and binary attachments (Feature #627800)
  + Added a NOHEADER option for the SEND request to indicate that the "Do not
    respond" header should not be included at the beginning of the email body
    (Feature #1215947)
  + Added a FROM option for the SEND request to allow specification of the
    sender of the email (Feature #944426)
  + Added logging to the Email service (Feature #1155343)
  + Added a 4001 return code for IOExceptions and 44xx/45xx SMTP return codes
    (Feature #1017852)
   
-------------------------------------------------------------------------------

Version 3.0.0: 04/21/2005

  - Improved error message provided for RC 25 (Access Denied) for all requests
    (Bug #1054858)
  - Changed to use STAFUtil's common resolve variable methods (Bug #1151440)
  - Changed to use requester's endpoint instead of machine as the default
    location for submitting a FS GET FILE request (Bug #1153649)
  - Changed license from GPL to CPL for all source code (Bug #1149491)  

-------------------------------------------------------------------------------

Version 3.0.0 Beta 7a: 02/02/2005

  - Fixed problem where emails were no longer being sent (Bug #1115008)
  - Fixed problem where the email delivery message was not being written to
    the JVM log (Bug #1115028)

-------------------------------------------------------------------------------

Version 3.0.0 Beta 7: 12/13/2004

  = Recompiled
  
-------------------------------------------------------------------------------

Version 3.0.0 Beta 5: 10/30/2004

  + Changed to return STAFResult from init/term methods (Feature #584049)
  + Changed to use new STAFServiceInterfaceLevel30 (Feature #550251)

-------------------------------------------------------------------------------

Version 3.0.0 Beta 4: 09/29/2004

  - Fixed additional problems where SEND requests would fail if the MAILSERVER
    was using strict checking (Bug #1032993)

-------------------------------------------------------------------------------

Version 3.0.0 Beta 3: 06/28/2004

  - Fixed problem where SEND requests would fail if the MAILSERVER was using
    strict checking (Bug #972876)

-------------------------------------------------------------------------------

Version 3.0.0 Beta 2: 04/29/2004

  + Updated to use STAFServiceInterfaceLevel5, only supported in STAF V3.0.0,
    and to use new syntax for the VAR service (Feature #464843) 
    
-------------------------------------------------------------------------------

Version 1.1.0: 08/23/2003

  - Send complete FROM address in Email service (Bug #791638, available in 
    Email service V1.1.0)
  + Added a FILE option to the Email service, to email the contents of a text 
    file (Feature #792897, available in Email service V1.1.0)
  - Fixed problem where Email service was not correctly resolving option 
    variables (Bug #787072, available in Email service V1.1.0)

-------------------------------------------------------------------------------

Version 1.0.1: 05/02/2003

-------------------------------------------------------------------------------

Version 1.0.0: 10/07/2002

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
