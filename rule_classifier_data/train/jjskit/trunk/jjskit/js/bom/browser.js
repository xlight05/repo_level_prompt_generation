var browser = (function(){
	var agent = window.navigator.userAgent.toLowerCase();
	var appVersion = window.navigator.appVersion;
	var name = 'unknown';
	var engine;
	var engineVersion;
	var browserVersion;
	var browserVersionParsed;
	var engineVersion;
	if (agent.indexOf('opera')>-1){
		name='Opera';
		engine='Presto';
		if (agent.indexOf('presto')>-1) {
			browserVersion = /.*version\/(\d+(?:\.\d+)*.*)/i.exec(agent)[1];
			engineVersion = /.*presto\/(\d+(?:\.\d+)*).*/i.exec(agent)[1];
		} else {
			var tmp = /.*opera\s+(\d+(?:\.\d+)+)/i.exec(agent);
			browserVersion = tmp&&tmp[1]||appVersion.split(' ', 2)[0].trim();
			browserVersionParsed = new version(browserVersion);
			if(browserVersionParsed.lt('7')) {
				engineVersion = 'pre';
			} else if (browserVersionParsed.lt('9.0')) {
				engineVersion = '1.0';
			} else if (browserVersionParsed.lt('9.5')) {
				engineVersion = '2.0';
			} else if (browserVersionParsed.lt('9.6')) {
				engineVersion = '2.1';
			} else if (browserVersionParsed.lt('10.0')) {
				engineVersion = '2.1.1';
			} else if (browserVersionParsed.lt('10.5')) {
				engineVersion = '2.2.15';
			}
		}
	}
	else if (agent.indexOf('firefox')>-1) {
		name='Firefox';
		engine = 'Gecko';
		browserVersion = /.*firefox\/(.+)$/.exec(agent)[1];
		engineVersion = /.*gecko\/(\d+).*/.exec(agent)[1];
	}
	else if (agent.indexOf('msie')>-1) {
		name = 'IE';
		browserVersion = /.*MSIE ([^;]+);.*/i.exec(appVersion)[1];
		if (agent.indexOf('trident')>-1) {
			engine = 'Trident';
			engineVersion = /.*trident\/([^\; ]+).*/.exec(agent)[1];
		} else {
			engine = "MSHTML";
			engineVersion = browserVersion;
		}
	}
	else if (agent.indexOf('chrome')>-1) {
		name = 'Chrome';
		engine = 'Webkit';
		engineVersion = /.*webkit\/(\d+(?:\.\d+)*).*/.exec(agent)[1];
		browserVersion = /.*Chrome\/([^ ]+) .*/i.exec(appVersion)[1];
	}
	else if (agent.indexOf('safari')>-1) {
		name='Safari';
		engine = 'Webkit';
		engineVersion = /.*webkit\/(\d+(?:\.\d+)*).*/.exec(agent)[1];
		browserVersion = /.*version\/([^ ]+) .*/i.exec(appVersion)[1];
	}
	if(!engine){
		if (agent.indexOf('gecko')>-1) {
			engine = 'Gecko';
			engineVersion = /.*gecko\/(\d+).*/.exec(agent)[1];
		} else if (agent.indexOf('webkit')>-1) {
			engine = 'Webkit';
			engineVersion = /.*webkit\/(\d+(?:\.\d+)*).*/.exec(agent)[1];
		} else if (agent.indexOf('khtml')>-1) {
			engine = 'KHTML';
		} else {
			engine = 'unknown engine';
		}
	}
	if (!browserVersionParsed) {
		browserVersionParsed = new version(browserVersion);
	}
	engineVersion = new version(engineVersion);
	
	var os = 'unknown os';
	
	if (appVersion.indexOf("Win")!=-1) os="Windows";
	if (appVersion.indexOf("Mac")!=-1) os="MacOS";
	if (appVersion.indexOf("X11")!=-1) os="UNIX";
	if (appVersion.indexOf("Linux")!=-1) os="Linux";
	
	return {
		name:name,
		
		os:os,
		
		version:browserVersionParsed,
		
		engine:engine,
		
		engineVersion:engineVersion,
		
		isGecko:engine=='Gecko',
		
		isWebKit:engine=='Webkit',
		
		isPresto:engine==='Presto',
		
		isTrident:engine==='Trident',
		
		isMSHTML:engine==='MSHTML',
		
		isKHTML:engine==='KHTML' || agent.indexOf('khtml')>-1,// true also for webkit browsers
		
		isIE:name==='IE',
		
		isFF:name==='Firefox',
		
		isOpera:name==='Opera',
		
		isSafari:name==='Safari',
		
		isChrome:name==='Chrome',
		
		toString:function() {
			return name+'/'+browserVersionParsed+' build on '+engine+'/'+engineVersion+' running on '+os;
		}
	};
})();