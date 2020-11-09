#! /bin/bash

#define Timerange
PARAM_SYSTEM_NAME=$1
PARAM_AFTER="2007-05-20"
PARAM_BEFORE="2010-06-13"

#define global constant file names
FILENAME_ALLSHAS=shasAll.txt
FILENAME_ALLSHASTIMEREF=shasAllTimeRef.txt

# Read time Range before and after
if [ "$#" -gt 2 ]; then
	PARAM_AFTER=$2
	PARAM_BEFORE=$3
fi

# Check input params
echo ${PARAM_SYSTEM_NAME}
echo ${PARAM_SYSTEM_NAME}
echo ${PARAM_BEFORE}
echo ${PARAM_AFTER}

# Go to main directory
cd ..
PATH_CURRENT=$PWD

# Set paths to git repository and output directory
PATH_SYSTEM=${PATH_CURRENT}/systems/${PARAM_SYSTEM_NAME}/
PATH_OUT=${PATH_CURRENT}/data/${PARAM_SYSTEM_NAME}/gen/

# Create output directory
mkdir -p ${PATH_OUT}

# Go to main directory
cd ${PATH_SYSTEM}
#> ${PATH_OUT}shasAll.csv
# get shas of all commits
git log --after="${PARAM_AFTER}" --before="${PARAM_BEFORE}" --pretty=format:"%H" > ${PATH_OUT}${FILENAME_ALLSHAS}
git log --after="${PARAM_AFTER}" --before="${PARAM_BEFORE}" --pretty=format:"%H;%ci" > ${PATH_OUT}${FILENAME_ALLSHASTIMEREF}
echo "finish getting all shas"