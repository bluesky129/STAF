from PySTAF import *
import sys
import time

counter = 10

try:
    handle = STAFHandle("STAFTestcase")
except STAFException, e:
    print "Error registering with STAF, RC: %d" % e.rc
    sys.exit(e.rc)

print "Using handle " + str(handle.handle);

if (len(sys.argv) > 1):
    counter = sys.argv[1]

for i in range(int(counter)):
    print "Loop #" + str(i)
    result = handle.submit("local", "monitor",
        "log message " + wrapData("Loop #" + str(i)))
    time.sleep(1)

sys.exit(0)
