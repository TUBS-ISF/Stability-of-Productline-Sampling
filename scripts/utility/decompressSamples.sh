#! /bin/bash

# Go to main directory
cd ../../
PATH_CURRENT=$PWD
PATH_DATA=${PATH_CURRENT}/data

# busybox full
PATH_BussyBox=${PATH_DATA}/busybox/samples

# busybox monthly
PATH_BussyBox_Monthly=${PATH_DATA}/busybox_monthlySnapshot/samples

####### busyBox monthly
for version in ${PATH_BussyBox_Monthly}/*; do
	for procedure in ${version}/*; do
		cd ${procedure}
		for run in *.tar.gz; do
			tar -xzvf ${run}
		done
	done
done

####### busyBox full
for version in ${PATH_BussyBox}/*; do
	for procedure in ${version}/*; do
		cd ${procedure}
		for run in *.tar.gz; do
			tar -xzvf ${run}
		done
	done
done
