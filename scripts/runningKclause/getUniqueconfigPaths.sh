#! /bin/bash

#define System path
PARAM_SYSTEM_NAME=$1

#define name of config file
PARAM_CONFIG_FILE=Config.in
if [ "$#" -gt 1 ]; then
	PARAM_CONFIG_FILE=$2
fi
echo ${PARAM_SYSTEM_NAME}
echo ${PARAM_CONFIG_FILE}

#define global constant file names
FILENAME_ALLSHAS=shasAll.txt
FILENAME_SORTEDCONFIGPATHS=sortedconfigPaths.txt
FILENAME_ALLCONFIGPATHS=allConfigPaths.txt
FILENAME_UNIQECONFIGPATHS=uniqueConfigPaths.txt

# Check input params
echo ${PARAM_SYSTEM_NAME}
echo ${PARAM_SYSTEM_NAME}

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

## build list of config paths
# read all shas in time range
input=${PATH_OUT}${FILENAME_ALLSHAS}
>${PATH_OUT}${FILENAME_ALLCONFIGPATHS}
while IFS= read -r sha
do
	echo "$sha"
	git reset --hard
	git clean -fxd
	git checkout ${sha}
	#OUTPUT="$(find ./ -name "Config.in")"
	find "./" -name ${PARAM_CONFIG_FILE} >> ${PATH_OUT}${FILENAME_ALLCONFIGPATHS}
	#${OUTPUT} >> ${PATH_OUT}${FILENAME_CONFIGPATHS}
	echo ${OUTPUT}
done < "$input"

sort ${PATH_OUT}${FILENAME_ALLCONFIGPATHS} > ${PATH_OUT}${FILENAME_SORTEDCONFIGPATHS}
uniq ${PATH_OUT}${FILENAME_SORTEDCONFIGPATHS} > ${PATH_OUT}${FILENAME_UNIQECONFIGPATHS}





