# Stability-of-Productline-Sampling
This Repository contains assests for the Paper Stability of Productline Sampling in Continuous Integration

## How to sections
The following sections describe how to use the data and the tools provided in this Repository.
The descriptions are ordered according which steps need to be done first. This means Description Step two requires step one and so on.

### unzipping the feature models
All data is compressed as a .tar.gz archive. To use the models you must decompress them.
Under *./scripts/utility/* we provide the *decompressModels.sh* bash script to do that automatically.

#### decompressing busybox models
To decompress the models of BusyBox full hitory do the following:
1. open a console and go into *./scripts/utility*
2. open the *./decompressModels.sh* file with a text editor
3. uncomment the lines directly under the *#full* keywords
4. save the file and make it executable.
5. execute *./decompressModels.sh*. The script will automatically decompress all models for the full history of BusyBox

#### decompressing busybox_monthly models
To decompress the models of BusyBox monthly snapshots do the following:
1. open a console and go into *./scripts/utility*
2. open the *./decompressModels.sh* file with a text editor
3. uncomment the lines directly under the *#monthly snapshots* keywords
4. save the file and make it executable.
5. execute *./decompressModels.sh*. The script will automatically decompress all models for the monthly snapshots of BusyBox

#### Content after decompressing the data
After decompressing the archieves the data folders of each version of BusyBox and BusyBox monthly will contain five files.
+ *./clean.dimacs* represents the variability model prepared for converion into a FeatureIDE feature model
+ *./<COMMITHASH>* contains the commit mesaage for this version of BusyBox
+ *./kconfig.dimacs* contains boolean constraints representing the variability model of this version in the dimacs format.
+ *./kconfig.kmax* is an intermediate file created by kclause when transforming kconfig to dimacs
+ *./model.xml* is the feature model of the version in FeatureIDE feature model format

### unzipping the samples
All data is compressed as a .tar.gz archive. To use the models you must decompress them.
Under *./scripts/utility/* we provide the *decompressSamples.sh* bash script to do that automatically.

#### decompressing busybox samples
To decompress the samples of BusyBox full hitory do the following:
1. open a console and go into *./scripts/utility*
2. open the *./decompressSamples.sh* file with a text editor
3. uncomment the lines directly under the *#busybox full* keywords
4. save the file and make it executable.
5. execute *./decompressModels.sh*. The script will automatically decompress all samples for the full history of BusyBox

#### decompressing busybox_monthly samples
To decompress the samples of BusyBox full hitory do the following:
1. open a console and go into *./scripts/utility*
2. open the *./decompressSamples.sh* file with a text editor
3. uncomment the lines directly under the *#busybox full* keywords
4. save the file and make it executable.
5. execute *./decompressModels.sh*. The script will automatically decompress all samples for the full history of BusyBox

### Analysing Stability of Productline evolutions
We provide a pre-defined script to analyze the sampling stability of busybox and busybox_monthly at *./scripts/stabilityAnalysis/executeStabCalcHG.sh*.
This script starts the java application *stabCalcHG.jar* located at *./tools/* with different parameters, so that a stability value for all sample histories of busybox and busybox_monthly is calculated.
The following describes how to use the script.
1. Check if all feature models are unzipped
2. Check if all samples are unzipped
3. Open a terminal and switch to *./scripts/stabilityAnalysis/*
4. Make *executeStabCalcHG.sh* executable
5. Execute *executeStabCalcHG.sh* 

## Licensing
The source code in this repository is licensed under the [GNU General Public License v3.0](https://github.com/TUBS-ISF/BCS-Case-Study/blob/master/LICENSE). The data files contained in this repository are licensed under the [CC-BY-4.0](https://github.com/TUBS-ISF/BCS-Case-Study/blob/master/cc-by-4.0.md). You are free to use, share, and adapt the data and source code in this repository as long as you give appropriate credit to the data owner.

## Note
The development on this repository is discontinued. The provided data and tools maybe out of date. Please see the following repositories for more up to date data:
* [BusyBox-Case-Study](https://github.com/TUBS-ISF/busybox-case-study)
* [BusyBox-Case-Study-Daily](https://github.com/TUBS-ISF/busybox-daily-case-study)
* [BusyBox-Case-Study-Monthly](https://github.com/TUBS-ISF/busybox-monthly-case-study)
* [VaMos21-Tools](https://github.com/TUBS-ISF/SamplingStabilityVaMoS21_Tools)

If you need any help using the provided models you can reach out to via via mail: *t.pett@tu-braunschweig.de*
