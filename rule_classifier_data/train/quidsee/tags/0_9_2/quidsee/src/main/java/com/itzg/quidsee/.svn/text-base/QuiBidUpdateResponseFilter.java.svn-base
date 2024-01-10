package com.itzg.quidsee;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.littleshoot.proxy.HttpResponseFilter;

final class QuiBidUpdateResponseFilter implements
		HttpResponseFilter {
	private final QuiBidListener listener;

	public QuiBidUpdateResponseFilter(QuiBidListener listener) {
		this.listener = listener;
	}

	@SuppressWarnings("unchecked")
	public HttpResponse filterResponse(HttpResponse response, String requestUri) {
		final Date timestamp = new Date();
		try {
			ObjectMapper mapper = new ObjectMapper();
			ChannelBuffer contentChannelBuffer = response.getContent();
			Map<String,Object> data = 
				mapper.readValue(contentChannelBuffer.toString(Charset.defaultCharset()), Map.class);
			
			Map<String,Object> auctions = (Map<String, Object>) data.get("a");
			if (auctions != null) {
				for (Entry<String, Object> entry : auctions.entrySet()) {
					Object valueObj = entry.getValue();
					if (valueObj instanceof Map) {
						Map<String, Object> auction = (Map<String, Object>) valueObj;
						Object bhObj = auction.get("bh");
						Object slewObj = auction.get("sl");
						Float slew = slewObj != null ? ((Number) slewObj).floatValue() : null;
						if (bhObj != null) {
//							System.out.println("v=" + auction.get("v") + ",p="
//									+ auction.get("p") + ",lb="
//									+ auction.get("lb") + ",av="
//									+ auction.get("av") + ",sl="
//									+ auction.get("sl"));
							List<Map<String, Object>> bids = (List<Map<String, Object>>) bhObj;
							for (Map<String, Object> bidMap : bids) {
								final Object bidTypeObj = bidMap.get("t");
								// single seems to be default if not present
								final BidType bidType = bidTypeObj != null ? BidType.values()[((Integer)bidTypeObj)-1] : BidType.SINGLE;
								QuiBid bid = new QuiBid(timestamp,
										Integer.parseInt(entry.getKey()),
										Float.parseFloat(((String) bidMap
												.get("a")).substring(1)),
										(String) bidMap.get("u"),
										slew, bidType);
								listener.handleNewBid(bid);
							}
						}
					}
				}
			}
		}
		catch (JsonMappingException e) {
			System.err.println(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}