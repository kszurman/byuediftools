#!/bin/sh

# JSAP: Java Simple Argument Parser (martiansoftware.com/jsap/)
jsap_jar="java_libs/JSAP-2.1.jar"

# JHDL (jhdl.org)
jhdl_jar="java_libs/JHDL_provisional.jar"

# BL-TMR Tool sources path
bltmr_dir=./

javac_opt="-J-Xmx8G -J-Xms1024m -J-Xss512m"

# Build with java, check for errors
echo -n "Building from source..."
find . -name '*.class' -print | xargs rm -v
mv java_build.log java_build.log.1
for file in `find . -name '*.java' -print`; do\
    javac ${javac_opt} -cp ${bltmr_dir}:${jsap_jar}:${jhdl_jar} $file &>> java_build.log; done
# Check for error in the build
if [ "$?" -eq 0 ]; then
  echo "Java build succeeded"
else
  echo "Java build failed!"
  exit
fi
echo "done."

# All done!

