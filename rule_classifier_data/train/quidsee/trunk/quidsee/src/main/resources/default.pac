function FindProxyForURL(url, host) {
      if (dnsDomainIs(host, ".quibids.com"))
      {
         return "PROXY 127.0.0.1:8181";
      }
      else {
      	return "DIRECT";
      }
}