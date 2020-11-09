#! /bin/bash

# Read name of system and kconfig root file
PARAM_SYSTEM_NAME=$1
PARAM_CONFIG_FILE=Config.in
if [ "$#" -gt 1 ]; then
	PARAM_CONFIG_FILE=$2
fi
echo ${PARAM_SYSTEM_NAME}
echo ${PARAM_CONFIG_FILE}

# Go to main directory
cd ..
PATH_CURRENT=$PWD

# Set paths to kclause tool, system, and output directory
PATH_EXEC=${PATH_CURRENT}/tools/Kclause/
PATH_SYSTEM=${PATH_CURRENT}/systems/${PARAM_SYSTEM_NAME}/
PATH_OUT=${PATH_CURRENT}/data/${PARAM_SYSTEM_NAME}/dimacs/

# Create output directory
mkdir -p ${PATH_OUT}

# Copy kclause executables
cp -fr ${PATH_EXEC}check_dep ${PATH_SYSTEM}check_dep
cp -fr ${PATH_EXEC}dimacs.py ${PATH_SYSTEM}dimacs.py

# Go to main directory
cd ${PATH_SYSTEM}

# Execute kclause and write results to output dir
./check_dep --dimacs ${PARAM_CONFIG_FILE} | tee ${PATH_OUT}kconfig.kmax | python dimacs.py -d --include-nonvisible-bool-defaults --remove-orphaned-nonvisibles | tee ${PATH_OUT}kconfig.dimacs
