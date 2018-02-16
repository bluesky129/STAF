
staf local monitor log message "Starting to execute build-solaris.cmd"

del C:\STAF_ISMP_InputFiles\build\*.* /s /q
rmdir C:\STAF_ISMP_InputFiles\build /s /q

staf local monitor log message "Adding required empty directories"

REM add required empty WIN32 build directories
md C:\STAF_ISMP_InputFiles\build\bin\win32
md C:\STAF_ISMP_InputFiles\build\bin-ipv4\win32
md C:\STAF_ISMP_InputFiles\build\bin-ipv6\win32
md C:\STAF_ISMP_InputFiles\build\lib\base\win32
md C:\STAF_ISMP_InputFiles\build\codepage\win32
md C:\STAF_ISMP_InputFiles\build\bin-log\win32
md C:\STAF_ISMP_InputFiles\build\bin-mon\win32
md C:\STAF_ISMP_InputFiles\build\bin-pool\win32
md C:\STAF_ISMP_InputFiles\build\bin-zip\win32
md C:\STAF_ISMP_InputFiles\build\include\win32
md C:\STAF_ISMP_InputFiles\build\bin-java\win32
md C:\STAF_ISMP_InputFiles\build\bin-jstaf\win32
md C:\STAF_ISMP_InputFiles\build\bin-rexx\win32
md C:\STAF_ISMP_InputFiles\build\lib-rexx\win32
md C:\STAF_ISMP_InputFiles\build\bin-tcl\win32
md C:\STAF_ISMP_InputFiles\build\bin-python\win32
md C:\STAF_ISMP_InputFiles\build\bin-perl\win32
md C:\STAF_ISMP_InputFiles\build\lib-perl56\win32
md C:\STAF_ISMP_InputFiles\build\lib-perl58\win32
md C:\STAF_ISMP_InputFiles\build\docs\common-python

REM add required empty AIX build directories
md C:\STAF_ISMP_InputFiles\build\bin\aix
md C:\STAF_ISMP_InputFiles\build\lib\base\aix
md C:\STAF_ISMP_InputFiles\build\lib-ipv4\aix
md C:\STAF_ISMP_InputFiles\build\lib-ipv6\aix
md C:\STAF_ISMP_InputFiles\build\codepage\aix
md C:\STAF_ISMP_InputFiles\build\lib-log\aix
md C:\STAF_ISMP_InputFiles\build\lib-mon\aix
md C:\STAF_ISMP_InputFiles\build\lib-pool\aix
md C:\STAF_ISMP_InputFiles\build\lib-zip\aix
md C:\STAF_ISMP_InputFiles\build\include\aix
md C:\STAF_ISMP_InputFiles\build\lib-java\aix
md C:\STAF_ISMP_InputFiles\build\lib-jstaf\aix
md C:\STAF_ISMP_InputFiles\build\lib-rexx\aix
md C:\STAF_ISMP_InputFiles\build\codepage\aix-optional

REM add required empty Linux build directories
md C:\STAF_ISMP_InputFiles\build\bin\linux
md C:\STAF_ISMP_InputFiles\build\lib\base\linux
md C:\STAF_ISMP_InputFiles\build\lib-ipv4\linux
md C:\STAF_ISMP_InputFiles\build\lib-ipv6\linux
md C:\STAF_ISMP_InputFiles\build\codepage\linux
md C:\STAF_ISMP_InputFiles\build\lib-log\linux
md C:\STAF_ISMP_InputFiles\build\lib-mon\linux
md C:\STAF_ISMP_InputFiles\build\lib-pool\linux
md C:\STAF_ISMP_InputFiles\build\lib-zip\linux
md C:\STAF_ISMP_InputFiles\build\include\linux
md C:\STAF_ISMP_InputFiles\build\lib-java\linux
md C:\STAF_ISMP_InputFiles\build\lib-jstaf\linux
md C:\STAF_ISMP_InputFiles\build\lib-rexx\linux
md C:\STAF_ISMP_InputFiles\build\lib-tcl\linux
md C:\STAF_ISMP_InputFiles\build\lib-python\linux
md C:\STAF_ISMP_InputFiles\build\bin-perl\linux
md C:\STAF_ISMP_InputFiles\build\lib-perl58\linux
md C:\STAF_ISMP_InputFiles\build\lib-perl56\linux
md C:\STAF_ISMP_InputFiles\build\lib-perl50\linux
md C:\STAF_ISMP_InputFiles\build\codepage\linux-optional
md C:\STAF_ISMP_InputFiles\build\docs\common-perl
md C:\STAF_ISMP_InputFiles\build\docs\common-tcl

REM add required empty hpux build directories
md C:\STAF_ISMP_InputFiles\build\bin\hpux
md C:\STAF_ISMP_InputFiles\build\lib\base\hpux
md C:\STAF_ISMP_InputFiles\build\lib-ipv4\hpux
md C:\STAF_ISMP_InputFiles\build\lib-ipv6\hpux
md C:\STAF_ISMP_InputFiles\build\codepage\hpux
md C:\STAF_ISMP_InputFiles\build\lib-log\hpux
md C:\STAF_ISMP_InputFiles\build\lib-mon\hpux
md C:\STAF_ISMP_InputFiles\build\lib-pool\hpux
md C:\STAF_ISMP_InputFiles\build\lib-zip\hpux
md C:\STAF_ISMP_InputFiles\build\include\hpux
md C:\STAF_ISMP_InputFiles\build\lib-java\hpux
md C:\STAF_ISMP_InputFiles\build\lib-jstaf\hpux
md C:\STAF_ISMP_InputFiles\build\codepage\hpux-optional

staf local monitor log message "Copying solaris files to input file directory"

md C:\STAF_ISMP_InputFiles\build
md C:\STAF_ISMP_InputFiles\build\bin\solaris

REM BIN solaris files
copy C:\STAF_ISMP_InputFiles\solaris\staf\LICENSE.htm C:\STAF_ISMP_InputFiles\build\*.*
copy C:\STAF_ISMP_InputFiles\solaris\staf\bin\STAF C:\STAF_ISMP_InputFiles\build\bin\solaris\*.*
copy C:\STAF_ISMP_InputFiles\solaris\staf\bin\STAFProc C:\STAF_ISMP_InputFiles\build\bin\solaris\*.*
copy C:\STAF_ISMP_InputFiles\solaris\staf\bin\STAFReg C:\STAF_ISMP_InputFiles\build\bin\solaris\*.*
copy C:\STAF_ISMP_InputFiles\solaris\staf\bin\STAFLoop C:\STAF_ISMP_InputFiles\build\bin\solaris\*.*
copy C:\STAF_ISMP_InputFiles\solaris\staf\bin\STAFExecProxy C:\STAF_ISMP_InputFiles\build\bin\solaris\*.*
copy C:\STAF_ISMP_InputFiles\solaris\staf\bin\FmtLog C:\STAF_ISMP_InputFiles\build\bin\solaris\*.*
copy C:\STAF_ISMP_InputFiles\solaris\staf\bin\STAFDefault.crt C:\STAF_ISMP_InputFiles\build\bin\solaris\*.*
copy C:\STAF_ISMP_InputFiles\solaris\staf\bin\STAFDefault.key C:\STAF_ISMP_InputFiles\build\bin\solaris\*.*
copy C:\STAF_ISMP_InputFiles\solaris\staf\bin\CAList.crt C:\STAF_ISMP_InputFiles\build\bin\solaris\*.*

REM LIB BASE solaris files
md C:\STAF_ISMP_InputFiles\build\lib\base\solaris
REM copy C:\STAF_ISMP_InputFiles\solaris\staf\lib\libSTAF.so C:\STAF_ISMP_InputFiles\build\lib\base\solaris\*.*
copy C:\STAF_ISMP_InputFiles\solaris\staf\lib\libSTAFLIPC.so C:\STAF_ISMP_InputFiles\build\lib\base\solaris\*.*
REM copy C:\STAF_ISMP_InputFiles\solaris\staf\lib\libSTAFOpenSSL.so C:\STAF_ISMP_InputFiles\build\lib\base\solaris\*.*
REM copy C:\STAF_ISMP_InputFiles\solaris\staf\lib\libSTAFTCP.so C:\STAF_ISMP_InputFiles\build\lib\base\solaris\*.*
copy C:\STAF_ISMP_InputFiles\solaris\staf\lib\libSTAFDSLS.so C:\STAF_ISMP_InputFiles\build\lib\base\solaris\*.*
copy C:\STAF_ISMP_InputFiles\solaris\staf\lib\libSTAFEXECPROXY.so C:\STAF_ISMP_InputFiles\build\lib\base\solaris\*.*
copy "C:\STAF_ISMP_InputFiles\solaris\lib\libstdc++.so.2.10.0" C:\STAF_ISMP_InputFiles\build\lib\base\solaris\*.*

REM LIB solaris IPv-specific files
md C:\STAF_ISMP_InputFiles\build\lib-ipv4\solaris
copy C:\STAF_ISMP_InputFiles\solaris\staf\lib\IPv4\libSTAF.so C:\STAF_ISMP_InputFiles\build\lib-ipv4\solaris\*.*
copy C:\STAF_ISMP_InputFiles\solaris\staf\lib\IPv4\libSTAFTCP.so C:\STAF_ISMP_InputFiles\build\lib-ipv4\solaris\*.*
md C:\STAF_ISMP_InputFiles\build\lib-ipv6\solaris
copy C:\STAF_ISMP_InputFiles\solaris\staf\lib\IPv6\libSTAF.so C:\STAF_ISMP_InputFiles\build\lib-ipv6\solaris\*.*
copy C:\STAF_ISMP_InputFiles\solaris\staf\lib\IPv6\libSTAFTCP.so C:\STAF_ISMP_InputFiles\build\lib-ipv6\solaris\*.*

REM CODEPAGE solaris files
md C:\STAF_ISMP_InputFiles\build\codepage\solaris
copy C:\STAF_ISMP_InputFiles\solaris\staf\codepage\alias.txt C:\STAF_ISMP_InputFiles\build\codepage\solaris\*.*
copy C:\STAF_ISMP_InputFiles\solaris\staf\codepage\ibm-437.bin C:\STAF_ISMP_InputFiles\build\codepage\solaris\*.*
copy C:\STAF_ISMP_InputFiles\solaris\staf\codepage\ibm-850.bin C:\STAF_ISMP_InputFiles\build\codepage\solaris\*.*

REM Log Service solaris files
md C:\STAF_ISMP_InputFiles\build\lib-log\solaris
copy C:\STAF_ISMP_InputFiles\solaris\staf\lib\libSTAFLog.so C:\STAF_ISMP_InputFiles\build\lib-log\solaris\*.*

REM Monitor Service solaris files
md C:\STAF_ISMP_InputFiles\build\lib-mon\solaris
copy C:\STAF_ISMP_InputFiles\solaris\staf\lib\libSTAFMon.so C:\STAF_ISMP_InputFiles\build\lib-mon\solaris\*.*

REM Respool service solaris files
md C:\STAF_ISMP_InputFiles\build\lib-pool\solaris
copy C:\STAF_ISMP_InputFiles\solaris\staf\lib\libSTAFPool.so C:\STAF_ISMP_InputFiles\build\lib-pool\solaris\*.*

REM Zip service solaris files
md C:\STAF_ISMP_InputFiles\build\lib-zip\solaris
copy C:\STAF_ISMP_InputFiles\solaris\staf\lib\libSTAFZip.so C:\STAF_ISMP_InputFiles\build\lib-zip\solaris\*.*
copy C:\STAF_ISMP_InputFiles\solaris\staf\lib\libSTAFZlib.so C:\STAF_ISMP_InputFiles\build\lib-zip\solaris\*.*

REM C++ solaris files
md C:\STAF_ISMP_InputFiles\build\include\solaris
copy C:\STAF_ISMP_InputFiles\solaris\staf\include\STAFOSTypes.h C:\STAF_ISMP_InputFiles\build\include\solaris\*.*
md C:\STAF_ISMP_InputFiles\build\include\common
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAF.h C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAF_fstream.h C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAF_iostream.h C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFDataTypes.h C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFDataTypesInlImpl.cpp C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFDynamicLibrary.h C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFDynamicLibraryInlImpl.cpp C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFError.h C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFEventSem.h C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFEventSemInlImpl.cpp C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFException.h C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFInlImpl.cpp C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFMutexSem.h C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFMutexSemInlImpl.cpp C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFRefPtr.h C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFString.h C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFStringInlImpl.cpp C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFThread.h C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFTimestamp.h C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFTimestampInlImpl.cpp C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFUtil.h C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFFileSystem.h C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFFileSystemInlImpl.cpp C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFLogService.h C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFMonitorService.h C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFResPoolService.h C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFProcess.h C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFProcessInlImpl.cpp C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFTrace.h C:\STAF_ISMP_InputFiles\build\include\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFTraceInlImpl.cpp C:\STAF_ISMP_InputFiles\build\include\common\*.*

REM JAVA LIB solaris files
md C:\STAF_ISMP_InputFiles\build\lib-java\solaris
copy C:\STAF_ISMP_InputFiles\solaris\staf\lib\libJSTAF.so C:\STAF_ISMP_InputFiles\build\lib-java\solaris\*.*
copy C:\STAF_ISMP_InputFiles\solaris\staf\lib\libJSTAFSH.so C:\STAF_ISMP_InputFiles\build\lib-java\solaris\*.*

REM JSTAF LIB solaris files
md C:\STAF_ISMP_InputFiles\build\lib-jstaf\solaris
copy C:\STAF_ISMP_InputFiles\solaris\staf\lib\JSTAF.zip C:\STAF_ISMP_InputFiles\build\lib-jstaf\solaris\*.*
copy C:\STAF_ISMP_InputFiles\solaris\staf\lib\JSTAF.jar C:\STAF_ISMP_InputFiles\build\lib-jstaf\solaris\*.*
copy C:\STAF_ISMP_InputFiles\solaris\staf\lib\STAFAnt.jar C:\STAF_ISMP_InputFiles\build\lib-jstaf\solaris\*.*

REM DOCS COMMON files
md C:\STAF_ISMP_InputFiles\build\docs\common
copy C:\STAF_ISMP_InputFiles\win32\staf\docs\History C:\STAF_ISMP_InputFiles\build\docs\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\docs\STAFCMDS.htm C:\STAF_ISMP_InputFiles\build\docs\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\docs\STAFFAQ.htm C:\STAF_ISMP_InputFiles\build\docs\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\docs\STAFHome.htm C:\STAF_ISMP_InputFiles\build\docs\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\docs\STAFRC.htm C:\STAF_ISMP_InputFiles\build\docs\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\docs\STAFUG.htm C:\STAF_ISMP_InputFiles\build\docs\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\docs\STAFGS.pdf C:\STAF_ISMP_InputFiles\build\docs\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\docs\STAFDiag.htm C:\STAF_ISMP_InputFiles\build\docs\common\*.*

REM SAMPLES/DEMOS COMMON files
md C:\STAF_ISMP_InputFiles\build\samples\common
xcopy /s C:\STAF_ISMP_InputFiles\win32\staf\samples\*.* C:\STAF_ISMP_InputFiles\build\samples\common\*.*

REM SERVICE DEV INCLUDE COMMON files
md C:\STAF_ISMP_InputFiles\build\include\service-dev\common
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFCommandParser.h C:\STAF_ISMP_InputFiles\build\include\service-dev\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFCommandParserInlImpl.cpp C:\STAF_ISMP_InputFiles\build\include\service-dev\common\*.*
copy C:\STAF_ISMP_InputFiles\win32\staf\include\STAFServiceInterface.h C:\STAF_ISMP_InputFiles\build\include\service-dev\common\*.*

REM SERVICE DEV LIB COMMON files
md C:\STAF_ISMP_InputFiles\build\lib\service-dev\common
copy C:\STAF_ISMP_InputFiles\win32\staf\lib\service.def C:\STAF_ISMP_InputFiles\build\lib\service-dev\common\*.*

REM OPTIONAL CODEPAGES solaris
md C:\STAF_ISMP_InputFiles\build\codepage\solaris-optional
copy C:\STAF_ISMP_InputFiles\solaris\staf\codepage\*.* C:\STAF_ISMP_InputFiles\build\codepage\solaris-optional\*.*

staf local monitor log message "Starting the solaris ISMP build"

cd C:\IS11.5MP
InstallShieldMultiPlatformCommandLineBuild.exe "C:\IS11.5MP\Projects\STAF\STAF.uip" -build
REM cd C:\InstallShieldX\Universal Installer
REM InstallShieldUniversalCommandLineBuild.exe "C:\InstallShieldX\Universal Installer\Projects\STAF\STAF.uip" -build
REM cd C:\ISMP503
REM java -cp .;C:\ISMP503\lib\idewizards.jar;C:\ISMP503\lib\ProjectWizard.jar;C:\ISMP503\ppk\win32ppk.jar;C:\ISMP503\ppk\linuxppk.jar;C:\ISMP503\ppk\solarisppk.jar;C:\ISMP503\ppk\hpuxppk.jar;C:\ISMP503\ppk\aixppk.jar;C:\ISMP503\ppk\os2ppk.jar;C:\ISMP503\ppk\cimppk.jar;C:\ISMP503\ppk\as400ppk.jar;C:\ISMP503\ppk\webppk.jar;C:\ISMP503\classes;C:\ISMP503\classes\MyCustomBeans.jar;C:\ISMP503\lib\ide.jar;C:\ISMP503\lib\wizard.jar;C:\ISMP503\lib\product.jar;C:\ISMP503\lib\platform.jar;C:\ISMP503\lib\help.jar;C:\ISMP503\lib\swing.jar;C:\ISMP503\lib\jhall.jar;C:\ISMP503\lib\parser.jar;C:\ISMP503\lib\xt.jar;C:\ISMP503\lib\icebrowserbean.jar;C:\ISMP503\lib\icebrowserlitebean.jar;C:\ISMP503\ppk\macosxppk.jar;C:\ISMP503\ppk\genericunixppk.jar;C:\ISMP503\i18n com.installshield.isje.ISJE .\Projects\STAF\STAF.xml -build

staf local monitor log message "Copying the solaris ISMP binaries to the STAF_ISMP_OutputFiles\disk1 directory"

cd C:\STAF_ISMP_InputFiles

copy C:\STAF_ISMP_OutputFiles\disk1\STAF324-setup-solaris-x86.bin C:\STAF_Installers\solaris-x86\*.*
copy C:\STAF_ISMP_OutputFiles\disk1\STAF324-setup.jar C:\STAF_Installers\solaris-x86\STAF324-setup-solaris-x86.jar

staf local monitor log message "Completed execution of build-solaris.cmd"







