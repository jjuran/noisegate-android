OPT_ANDROID = $(HOME)/opt/android

ANDROID_SDK = $(OPT_ANDROID)/sdk

ANDROID_TOOL = $(ANDROID_SDK)/tools/android

INSTALL_SDK_TEXT = "\n\#\#\# Please install the Android SDK to ~/opt/android/sdk\n"

default : build

build-prereqs : android-sdk android/local.properties

android/local.properties:
	@echo "# local.properties not found, creating it"
	echo "sdk.dir=$(HOME)/opt/android/sdk" > android/local.properties

android-sdk:
	@test -x $(ANDROID_TOOL) || echo $(INSTALL_SDK_TEXT)
	@test -x $(ANDROID_TOOL) || exit 1


build : build-prereqs
	(cd android; ant debug)

install : build
	(cd android; ant installd)

.SECONDARY:
