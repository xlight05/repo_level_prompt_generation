
var _globalScope = this;

if (typeof jmaki == 'undefined') {
    var jmaki = new Jmaki();
}

function Jmaki() {
    var _this = this;
    var libraries = [];
    var widgets = [];
    this.attributes = new Map();
	
	var topics = new Map();

	/**
	*  Subscribe to a new topic
	*/
	this.subscribe = function(name, listener) {
		var topic = topics.get(name);
		// create the topic if it has not been created yet
		if (!topic) {
			topic = [];
			topics.put(name, topic);
		}
		// make sure that a listener is only added once
		for (var i in topic) {
			if (i == listener) {
				return;
			}
		}
		topic.push(listener);
	}
	
  	/**
	*  Unscribe a listener to a topic
	*/
	this.unsubscribe = function(name, listener) {
		var topic = topics.get(name);
		// create the topic if it has not been created yet
		if (topic) {
			for (var i = 0; i < topic.length; i++) {
				if (topic[i] == listener) {
					topic.splice(i,1);
					break;
				}
			}
		}
	}  
	
   /**
	*  Publish an event to a topic
	*/
	this.publish = function(name, args) {
		if (typeof name == 'undefined' || typeof args == 'undefined') return;
		var topic = topics.get(name);
		// create the topic if it does not exist
		if (!topic) {
			topic = [];
			topics.put(name, topic);
		}
		// notify the listeners
		for (var index in topic) {
			topic[index](args);
		}
	}
				         
    this.addLibrary = function(lib) {
        var added = false;
        for (var l=0; l < libraries.length; l++) {
            if (libraries[l] == lib) {
                added = true;
            }
        }
        if (!added) {
            libraries.push(lib);
            var head = document.getElementsByTagName("head")[0];
            var scriptElement = document.createElement("script");
            scriptElement.type="text/javascript";
            _globalScope.setTimeout(function() {scriptElement.src = lib;},0);
            head.appendChild(scriptElement);
        }
    }

    this.addWidget = function(widget) {
        widgets.push(widget);
    }
    
    this.bootstrapWidgets = function() { 
        for (var l=0; l < widgets.length; l++) {
            this.loadWidget(widgets[l]);
        }
    }
    
    this.serialize = function(obj) {
        var props = [];
        for (var l in obj) {
            var prop;
            if (typeof obj[l] == "object") { 
                prop = this.serialize(obj[l]);
            } else {
                prop = "'" + obj[l] + "'";
            }
            props.push(l + ": " + prop);
        }
        return "{" + props.concat() + "}";
    }
    
    function getXHR () {
        if (window.XMLHttpRequest) {
            return new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            return new ActiveXObject("Microsoft.XMLHTTP");
        }
    }
    this.doAjax= function(args) {
        if (typeof args == 'undefined') return;
       var _req = getXHR();
       var method = "GET";
       if (typeof args.method != 'undefined') {
            method=args.method;
            if (method.toLowerCase() == 'post') {
                _req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            }
       }
       var async = true;
       var callback;
       if  (typeof args.asynchronous != 'undefined') {
            async=args.asynchronous;
       }
       if  (typeof args.callback != 'undefined' &&
            typeof args.callback == 'function') {
           callback = args.callback;
       }
       var body = null;
       if (typeof args.body != 'undefined') {
            async=args.body;
       }
       if (async == true) _req.onreadystatechange = function() {callback(_req);};
       _req.open(method, args.url, async); 
       
       _req.send(body);
       if (!async && callback) callback(_req);
    }
    
    // to do keep tabs for multiple declarations
    this.loadScript = function(target) {
        var req = getXHR();
        req.open("GET", this.webRoot + target, false);
        try {
            req.send(null);
        } catch (e){
            // log error
        }
        if (req.status == 200) {
             return window.eval(req.responseText);
        }
    }
    
    // add a style
    this.loadStyle = function(target) {
        var styleElement = document.createElement("link");
        styleElement.type = "text/css";
        styleElement.rel="stylesheet"
        styleElement.href = this.webRoot + target;
        if (document.getElementsByTagName('head').length == 0) {
            var headN = document.createElement("head");
            document.documentElement.insertBefore(headN, document.documentElement.firstChild);
        }
        document.getElementsByTagName('head')[0].appendChild(styleElement);
    }
    
    this.loadWidget = function(widget) {
        var req = getXHR();
        req.onreadystatechange = processRequest;
        req.open("GET", widget.script, true);
        req.send(null);

        function processRequest () {
            if (req.readyState == 4) {
                // status of 200 signifies sucessful HTTP call
                if (req.status == 200) {
                    var script = "var widget=" + _this.serialize(widget) + ";";
                    if (typeof widget.onload != 'undefined') {
                        script +=  widget.onload + "()"; 
                    }
                    script += req.responseText;
                    _globalScope.setTimeout(script,0);
                }
            }
        }
    }
    
    this.clearWidgets = function() {
        widgets = [];
    }
    
    function Map() {
		var map = {};
        
        this.put = function(key,value) {
			map[key] = value;
		}
        
        this.get = function(key) {
            return map[key];
        }
		
		this.remove =  function(key) {
			delete map[key] ;
		}
        
        this.clear = function() {
			map = {};
        }
    }
}

var oldLoad = window.onload;

window.onload = function() {
    jmaki.bootstrapWidgets();
    if (typeof oldLoad  == 'function') {
        oldLoad();
    }
}