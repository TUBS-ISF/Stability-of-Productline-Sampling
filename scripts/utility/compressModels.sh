#! /bin/bash

# Go to main directory
cd ../../
PATH_CURRENT=$PWD
PATH_DATA=${PATH_CURRENT}/data

PATH_BussyBox=${PATH_DATA}/busybox/models
PATH_BussyBox_monthly=${PATH_DATA}/busybox_monthlySnapshot/models

####### busyBox
cd ${PATH_BussyBox}
for year in ${PATH_BussyBox}/*; do
	echo ${year}
	cd ${year}
		rm *.tar.gz
	for run in *; do
		tar -zcvf ${run}.tar.gz ${run}
	done
done

####### busyBox monthly
cd ${PATH_BussyBox_monthly}
rm *.tar.gz
for year in *; do
	tar -zcvf ${year}.tar.gz ${year}
done
