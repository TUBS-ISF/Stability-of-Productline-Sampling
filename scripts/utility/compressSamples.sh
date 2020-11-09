#! /bin/bash

# Go to main directory
cd ../../
PATH_CURRENT=$PWD
PATH_DATA=${PATH_CURRENT}/data/

# used for busy box monthly
PATH_BussyBox_Monthly=${PATH_DATA}/busybox_monthlySnapshot/samples/
# used for busy box full history
PATH_BussyBox=${PATH_DATA}/busybox/samples/

####### used for busyBox monthly
#	for version in ${PATH_BussyBox_Monthly}/*; do
#		for procedure in ${version}/*; do
#			echo ${procedure}
#			cd ${procedure}
#			rm *.tar.gz
#			for run in *; do
#				tar -zcvf ${run}.tar.gz ${run}
#			done
#		done
#	done

####### used for busyBox full history
for version in ${PATH_BussyBox}/*; do
	for procedure in ${version}/*; do
		echo ${procedure}
		cd ${procedure}
		#echo ${procedure}
		rm *.tar.gz
		for run in *; do
			#echo ${run}
			tar -zcvf ${run}.tar.gz ${run}
		done
	done
done
