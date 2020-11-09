#! /bin/bash

echo "####################### start execution" $(date -u) "#######################"
# Read name of system and kconfig root file
PARAM_SYSTEM_NAME=$1
PARAM_CONFIG_FILE=Config.in
if [ "$#" -gt 1 ]; then
	PARAM_CONFIG_FILE=$2
fi
echo ${PARAM_SYSTEM_NAME}
echo ${PARAM_CONFIG_FILE}

#define file paths
#real File
FILENAME_SHAS=shasRelevant_unique.txt
#testing file 
#FILENAME_SHAS=shasRelevant_unique_test.txt


# Go to main directory
cd ..
PATH_CURRENT=$PWD

# Set paths to kclause tool, system, and output directory
PATH_EXEC=${PATH_CURRENT}/tools/Kclause/
PATH_SYSTEM=${PATH_CURRENT}/systems/${PARAM_SYSTEM_NAME}/
PATH_OUT_BASE=${PATH_CURRENT}/data/${PARAM_SYSTEM_NAME}/models/
PATH_IN=${PATH_CURRENT}/data/${PARAM_SYSTEM_NAME}/gen/
PATH_OUT_SHA=""
PATH_FOLDER_YEAR=""

#iterate over shas 
input=${PATH_IN}${FILENAME_SHAS}

#track time 
date -u >> ${PATH_OUT_BASE}timetracking.txt
#create folder structure for output
while IFS= read -r sha
do	
	cd ${PATH_SYSTEM}
	echo "##### start analysis for sha: ${sha} #####"
	# switch git revision to commit id
	git reset --hard
	git clean -fxd
	git checkout ${sha}

	#define file for commit message
	FILENAME_COMMIT_MESSAGE=${sha}.txt
	#get commit message for current sha (pretty printed so that commit date is first line)
	git show --pretty=format:"%ci" ${sha} > ${PATH_OUT_BASE}${FILENAME_COMMIT_MESSAGE}

	#get first line of commit message (date of commit)
	line=$(head -n 1 ${PATH_OUT_BASE}${FILENAME_COMMIT_MESSAGE})
	line=$(echo ${line} | tr -s ":" "-")

	#split commit date into three parts and store as array 
	declare -a parts
	parts=($line)

	#define folder names YEAR and DATE
	date=${parts[0]}
	hour=${parts[1]}

	#split date down to year
	dateSplit=$(echo ${date} | tr -s "-" " ")
	echo ${date}
	declare -a dateParts
	dateParts=($dateSplit)
	year=${dateParts[0]}

	#define folder names for year and date_time
	FOLDERNAME_YEAR=${year}
	FOLDERNAME_DATE=${date}"_"${hour}

	#set folder paths for year and date
	PATH_FOLDER_YEAR=${PATH_OUT_BASE}${FOLDERNAME_YEAR}/
	PATH_OUT_SHA=${PATH_FOLDER_YEAR}${FOLDERNAME_DATE}/

	#create output folder for current sha
	mkdir -p ${PATH_OUT_SHA}
	

	#move file commit message
	mv -f ${PATH_OUT_BASE}${FILENAME_COMMIT_MESSAGE} ${PATH_OUT_SHA}${FILENAME_COMMIT_MESSAGE}
	
	echo "##### Output folder structure created and commit message written #####"
	echo "##### start kconfig analysis #####"
	
	# Copy kclause executables
	cp -fr ${PATH_EXEC}check_dep ${PATH_SYSTEM}check_dep
	cp -fr ${PATH_EXEC}dimacs.py ${PATH_SYSTEM}dimacs.py

	# Go to main directory
	#cd ${PATH_SYSTEM}

	# Execute kclause and write results to output dir
	./check_dep --dimacs ${PARAM_CONFIG_FILE} | tee ${PATH_OUT_SHA}kconfig.kmax | python2 dimacs.py -d --include-nonvisible-bool-defaults --remove-orphaned-nonvisibles | tee ${PATH_OUT_SHA}kconfig.dimacs
	
	echo "##### finish kconfig analysis for sha " ${sha} "#####"
	
done < "$input"
echo "########################################## Analysis finished ###########################"
#time tracking 
date -u >> ${PATH_OUT_BASE}timetracking.txt
echo "############################################" >> ${PATH_OUT_BASE}timetracking.txt
#while IFS= read -r sha
#do	
	# get commit date for current sha and build folder structure
	
	# get commit message for current sha
	
	# checkout revision for current sha

#done < "$input"


# Copy kclause executables
#cp -fr ${PATH_EXEC}check_dep ${PATH_SYSTEM}check_dep
#cp -fr ${PATH_EXEC}dimacs.py ${PATH_SYSTEM}dimacs.py

# Go to main directory
#cd ${PATH_SYSTEM}

# Execute kclause and write results to output dir
#./check_dep --dimacs ${PARAM_CONFIG_FILE} | tee ${PATH_OUT}kconfig.kmax | python2 dimacs.py -d --include-nonvisible-bool-defaults --remove-orphaned-nonvisibles | tee ${PATH_OUT}kconfig.dimacs
