// This is the main DLL file.

#include "StdAfx.h"

#include <jni.h>
#include <strsafe.h>
#include "../../com_itzg_quidsee_proxy_WindowsProxyConfigurator.h"

jfieldID getUrlField(JNIEnv *env, jobject obj) {
	return env->GetFieldID(env->GetObjectClass(obj), "savedAutoConfigURL", "Ljava/lang/String;");
}

jfieldID getFlagsField(JNIEnv *env, jobject obj) {
	return env->GetFieldID(env->GetObjectClass(obj), "savedFlags", "I");
}

JNIEXPORT jboolean JNICALL Java_com_itzg_quidsee_proxy_WindowsProxyConfigurator_setAutoConfigUrl
  (JNIEnv *env, jobject obj, jstring url) {
	INTERNET_PER_CONN_OPTION optionToQuery[2];
	INTERNET_PER_CONN_OPTION_LIST optionList;
	DWORD nSize = sizeof(INTERNET_PER_CONN_OPTION_LIST);

	// also need to retrieve the auto url config flag
	optionToQuery[0].dwOption = INTERNET_PER_CONN_AUTOCONFIG_URL;
	optionToQuery[1].dwOption = INTERNET_PER_CONN_FLAGS;

	optionList.dwSize = sizeof(INTERNET_PER_CONN_OPTION_LIST);
	optionList.pszConnection = NULL;
	optionList.dwOptionCount = 2;
	optionList.dwOptionError = 0;
	optionList.pOptions = optionToQuery;

	if (!InternetQueryOption(NULL, INTERNET_OPTION_PER_CONNECTION_OPTION, &optionList, &nSize)) {
		printf("InternetQueryOption failed! (%d)\n", GetLastError());
	}

	jfieldID flagsFieldId =  getFlagsField(env,obj);
	jfieldID urlFieldId =  getUrlField(env,obj);
	if (optionToQuery[0].Value.pszValue != NULL) {
		// Hmm, jchar* is interchangeable with LPWSTR, right? They're both unicode
		env->SetObjectField(obj, urlFieldId, env->NewString((jchar*)optionToQuery[0].Value.pszValue, lstrlen(optionToQuery[0].Value.pszValue)));
	}
	else {
		env->SetObjectField(obj, urlFieldId, NULL);
	}

	env->SetIntField(obj, flagsFieldId, optionToQuery[1].Value.dwValue);

	INTERNET_PER_CONN_OPTION optionToSet[2];
	INTERNET_PER_CONN_OPTION_LIST setOptionList;

	optionToSet[0].dwOption = INTERNET_PER_CONN_AUTOCONFIG_URL;
	const jchar *urlChars = env->GetStringChars(url, NULL);
	optionToSet[0].Value.pszValue = (LPWSTR)urlChars;

	optionToSet[1].dwOption = INTERNET_PER_CONN_FLAGS;
	optionToSet[1].Value.dwValue = PROXY_TYPE_AUTO_PROXY_URL;

	setOptionList.dwSize = sizeof(INTERNET_PER_CONN_OPTION_LIST);
	setOptionList.pszConnection = NULL;
	setOptionList.dwOptionCount = 2;
	setOptionList.dwOptionError = 0;
	setOptionList.pOptions = optionToSet;

	if (!InternetSetOption(NULL, INTERNET_OPTION_PER_CONNECTION_OPTION, &setOptionList, nSize)) {
		env->ThrowNew(env->FindClass("java.lang.Exception"), "Unable to set proxy url value");		
	}
	env->ReleaseStringChars(url, urlChars);

	return 1;
}


JNIEXPORT void JNICALL Java_com_itzg_quidsee_proxy_WindowsProxyConfigurator_restoreAutoConfigUrl
  (JNIEnv *env, jobject obj) {
	jfieldID urlFieldId =  getUrlField(env,obj);
	jfieldID flagsFieldId =  getFlagsField(env,obj);

	INTERNET_PER_CONN_OPTION optionToSet[2];
	INTERNET_PER_CONN_OPTION_LIST setOptionList;

	optionToSet[0].dwOption = INTERNET_PER_CONN_AUTOCONFIG_URL;
	jstring jstrUrlFieldValue = static_cast<jstring>(env->GetObjectField(obj, urlFieldId));
	const jchar *urlChars = env->GetStringChars(jstrUrlFieldValue, NULL);
	optionToSet[0].Value.pszValue = (LPWSTR)urlChars;

	optionToSet[1].dwOption = INTERNET_PER_CONN_FLAGS;
	optionToSet[1].Value.dwValue = env->GetIntField(obj, flagsFieldId);

	setOptionList.dwSize = sizeof(INTERNET_PER_CONN_OPTION_LIST);
	setOptionList.pszConnection = NULL;
	setOptionList.dwOptionCount = 2;
	setOptionList.dwOptionError = 0;
	setOptionList.pOptions = optionToSet;

	if (!InternetSetOption(NULL, INTERNET_OPTION_PER_CONNECTION_OPTION, &setOptionList, sizeof(INTERNET_PER_CONN_OPTION_LIST))) {
		env->ThrowNew(env->FindClass("java.lang.Exception"), "Unable to restore proxy url value");		
	}

	env->ReleaseStringChars(jstrUrlFieldValue, urlChars);
}
