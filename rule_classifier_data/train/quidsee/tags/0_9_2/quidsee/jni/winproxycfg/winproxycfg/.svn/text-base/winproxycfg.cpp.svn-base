// winproxycfg.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"


int setProxy(int argc, _TCHAR* argv[]) {
	if (argc < 1) {
		fprintf(stderr, "Set command missing url argument\n");
		return 2;
	}

	_TCHAR* urlToSet = argv[0];

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

	if (!InternetQueryOptionW(NULL, INTERNET_OPTION_PER_CONNECTION_OPTION, &optionList, &nSize)) {
		printf("InternetQueryOption failed! (%d)\n", GetLastError());
	}

	INTERNET_PER_CONN_OPTION optionToSet[2];
	INTERNET_PER_CONN_OPTION_LIST setOptionList;

	optionToSet[0].dwOption = INTERNET_PER_CONN_AUTOCONFIG_URL;
	optionToSet[0].Value.pszValue = urlToSet;

	optionToSet[1].dwOption = INTERNET_PER_CONN_FLAGS;
	optionToSet[1].Value.dwValue = PROXY_TYPE_AUTO_PROXY_URL;

	setOptionList.dwSize = sizeof(INTERNET_PER_CONN_OPTION_LIST);
	setOptionList.pszConnection = NULL;
	setOptionList.dwOptionCount = 2;
	setOptionList.dwOptionError = 0;
	setOptionList.pOptions = optionToSet;

	if (!InternetSetOption(NULL, INTERNET_OPTION_PER_CONNECTION_OPTION, &setOptionList, nSize)) {
		fprintf(stderr, "Could not set option (%d)", GetLastError());
		return 2;
	}

	_tprintf(L"%d %s", optionToQuery[1].Value.dwValue, optionToQuery[0].Value.pszValue);

	return 0;
}

int restoreProxy(int argc, _TCHAR* argv[]) {
	if (argc < 1) {
		fprintf(stderr, "Restore command missing original values argument\n");
		return 3;
	}
	_TCHAR* prevValues = argv[0];

	DWORD flags;
	TCHAR url[100];
	if (_stscanf(prevValues, L"%d %99s", &flags, url) == 2) {
		INTERNET_PER_CONN_OPTION optionToSet[2];
		INTERNET_PER_CONN_OPTION_LIST setOptionList;

		optionToSet[0].dwOption = INTERNET_PER_CONN_AUTOCONFIG_URL;
		optionToSet[0].Value.pszValue = url;

		optionToSet[1].dwOption = INTERNET_PER_CONN_FLAGS;
		optionToSet[1].Value.dwValue = flags;

		setOptionList.dwSize = sizeof(INTERNET_PER_CONN_OPTION_LIST);
		setOptionList.pszConnection = NULL;
		setOptionList.dwOptionCount = 2;
		setOptionList.dwOptionError = 0;
		setOptionList.pOptions = optionToSet;

		if (!InternetSetOption(NULL, INTERNET_OPTION_PER_CONNECTION_OPTION, &setOptionList, sizeof(INTERNET_PER_CONN_OPTION_LIST))) {
			fprintf(stderr, "Could not set option (%d)", GetLastError());
			return 3;
		}

		return 0;
	}
	else {
		fprintf(stderr, "Invalid value to restore. Needs to be <flag> <url>.\n");
		return 3;
	}
}

int _tmain(int argc, _TCHAR* argv[])
{
	if (argc < 2) {
		fprintf(stderr, "Not enough arguments\n");
		return 1;
	}

	if (_tcscmp(argv[1], L"set") == 0) {
		return setProxy(argc-2, &argv[2]);
	}
	else if (_tcscmp(argv[1], L"restore") == 0) {
		return restoreProxy(argc-2, &argv[2]);
	}
	return 0;
}
