#! /bin/bash

#define Timerange
PARAM_SYSTEM_NAME=$1
PARAM_AFTER="2007-05-20"
PARAM_BEFORE="2010-06-13"

#define global constant file names
FILENAME_SHAS_RELEVANT_ALL=shasRelevant_all.txt
FILENAME_SHAS_RELEVANT_SORTED=shasRelevant_sorted.txt
FILENAME_SHAS_RELEVANT_UNIQUE=shasRelevant_unique.txt

FILENAME_SHAS_RELEVANT_ALL_TIME=shasRelevant_all_time.txt
FILENAME_SHAS_RELEVANT_SORTED_TIME=shasRelevant_sorted_time.txt
FILENAME_SHAS_RELEVANT_UNIQUE_TIME=shasRelevant_unique_time.txt

FILENAME_CONFIGPATHS=uniqueConfigPaths.txt

# Read time Range before and after
if [ "$#" -gt 2 ]; then
	PARAM_AFTER=$2
	PARAM_BEFORE=$3
fi

# Check input params
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
input=${PATH_OUT}${FILENAME_CONFIGPATHS}
>${PATH_OUT}${FILENAME_SHAS_RELEVANT_ALL}
>${PATH_OUT}${FILENAME_SHAS_RELEVANT_ALL_TIME}

while IFS= read -r configPath
do
	git log --after="${PARAM_AFTER}" --before="${PARAM_BEFORE}" --pretty=format:"%H" --follow -- "${configPathPath}" >> ${PATH_OUT}${FILENAME_SHAS_RELEVANT_ALL}
	
	git log --after="${PARAM_AFTER}" --before="${PARAM_BEFORE}" --pretty=format:"%H;%ci" --follow -- "${configPathPath}" >> ${PATH_OUT}${FILENAME_SHAS_RELEVANT_ALL_TIME}
done < "$input"

sort ${PATH_OUT}${FILENAME_SHAS_RELEVANT_ALL} > ${PATH_OUT}${FILENAME_SHAS_RELEVANT_SORTED}

uniq ${PATH_OUT}${FILENAME_SHAS_RELEVANT_SORTED} > ${PATH_OUT}${FILENAME_SHAS_RELEVANT_UNIQUE}

sort ${PATH_OUT}${FILENAME_SHAS_RELEVANT_ALL_TIME} > ${PATH_OUT}${FILENAME_SHAS_RELEVANT_SORTED_TIME}

uniq ${PATH_OUT}${FILENAME_SHAS_RELEVANT_SORTED_TIME} > ${PATH_OUT}${FILENAME_SHAS_RELEVANT_UNIQUE_TIME}



