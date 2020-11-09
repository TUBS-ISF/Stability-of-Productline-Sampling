#! /bin/bash

# Go to main directory
cd ../../
PATH_CURRENT=$PWD
PATH_DATA=${PATH_CURRENT}/data

# used for busybox full history
PATH_BussyBox=${PATH_DATA}/busybox/models
# used for busy box monthly snapshots
PATH_BussyBox_Monthly=${PATH_DATA}/busybox_monthlySnapshot/models

####### used for busyBox
for year in ${PATH_BussyBox}/*; do
	cd ${year}
	for run in *.tar.gz; do
		tar -xzvf ${run}
	done
done

###### used for busybox monthly snapshots
cd ${PATH_BussyBox_Monthly}
for year in *.tar.gz; do
		tar -xzvf ${year}
done
