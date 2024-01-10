
function GeoCoder() {

	var uuid = widget.uuid;
	var service = widget.service;
    var location;
	
	this.getCoordinates = function() {
        location = encodeURIComponent(document.getElementById(uuid + "_location").value);
		var yahooService = encodeURIComponent("http://api.local.yahoo.com/MapsService/V1/geocode?appid=jmaki-key&location=" +location);
        var url = jmaki.webRoot + "/" + service + "?url=" + yahooService + "&xslURL=" + jmaki.webRoot + "/resources/xsl/yahoo-geocoder.xsl";
        jmaki.doAjax({url: url, callback: function(req) { var _req=req; postProcess(_req);}});
	}
	
    function postProcess(req) {
        if (req.readyState == 4) {
            if (req.status == 200) {
				var response = eval("(" + req.responseText + ")");
				jmaki.publish("geocoder", response.coordinates);
			}
		}
    }
}

var geocoder = new GeoCoder();

// add to the instance map for later refernece
jmaki.attributes.put(widget.uuid, geocoder);