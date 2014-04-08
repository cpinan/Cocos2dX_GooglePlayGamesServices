LOCAL_PATH := $(call my-dir)
CLASSES_PATH := $(LOCAL_PATH)/../../Classes

include $(CLEAR_VARS)

LOCAL_MODULE := game_shared

LOCAL_MODULE_FILENAME := libgame

LOCAL_SRC_FILES := hellocpp/main.cpp \
$(subst $(LOCAL_PATH)/,,$(wildcard $(CLASSES_PATH)/*/*.cpp)) \
$(subst $(LOCAL_PATH)/,,$(wildcard $(CLASSES_PATH)/*.cpp)) \
$(subst $(LOCAL_PATH)/,,$(wildcard $(CLASSES_PATH)/*.c)) \
                   
LOCAL_C_INCLUDES := $(LOCAL_PATH)/../../Classes                   

LOCAL_WHOLE_STATIC_LIBRARIES := cocos2dx_static cocosdenshion_static cocos_extension_static
            
include $(BUILD_SHARED_LIBRARY)

$(call import-module,CocosDenshion/android) \
$(call import-module,cocos2dx) \
$(call import-module,extensions)
