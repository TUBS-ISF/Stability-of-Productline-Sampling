#! /bin/bash

# Go to main directory
cd ..
PATH_CURRENT=$PWD
PATH_DATA=${PATH_CURRENT}/data
PATH_BussyBox=${PATH_DATA}/busybox/models
PATH_Jar=${PATH_CURRENT}/tools/DimacsToXMLConverter/jar/dimacs2xml.jar

####### busyBox
for year in ${PATH_BussyBox}/*; do
	PATH_Year=${year}
	cd ${year}
	for version in *; do
		if [[ $version != *".tar.gz" ]];
			then echo ${version}
			
			KDIMACS=${PATH_Year}/${version}/kconfig.dimacs
			CLEAN_DIMACS=${PATH_Year}/${version}/clean.dimacs
			MODLE=${PATH_Year}/${version}/model.xml
			
			java -jar ${PATH_Jar} ${KDIMACS} ${CLEAN_DIMACS} ${MODLE}
		fi
	done
done



