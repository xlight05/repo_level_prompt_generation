/***
 * 
 * Time utility functions. Uses 
 * chart_currentTime_
 * 
 */

function timeline_findClosestIndexAbove(pxvalue, i, startIndex, endIndex){
	var ts = FROM_MILLIS + pxvalue*TIME_UNIT;
	var ind = timeline_findClosestIndex(ts, i, startIndex, endIndex);
	var intMicroSecOffset = 0;
	
	if (ind > 0 && PROCESS_DATA[i].data[ind-1].timestamp >= ts){
		top.opl_debug("I'm special");
		return ind-1;
	}
	top.opl_debug("Closest index is "+ind +" diff " +(PROCESS_DATA[i].data[ind].timestamp-ts));
	if (PROCESS_DATA[i].data[ind].javaClass == "com.ipbyran.opl.web.svg.data.FineDataPoint"){
		intMicroSecOffset = PROCESS_DATA[i].data[ind].micros/1000;
	}
	if (PROCESS_DATA[i].data[ind].timestamp+intMicroSecOffset >= ts ) 
		return ind;
	if(ind < PROCESS_DATA[i].data.length-1){
		if (PROCESS_DATA[i].data[ind].javaClass == "com.ipbyran.opl.web.svg.data.FineDataPoint"){
			intMicroSecOffset = PROCESS_DATA[i].data[ind+1].micros/1000;
		}
		if(PROCESS_DATA[i].data[ind+1].timestamp+intMicroSecOffset >= ts)
			return ind+1;
	}
	
	top.opl_debug("No index above " +pxvalue +" found");	
	return -1;	
}

function timeline_findClosestIndexBelow(pxvalue, i, startIndex, endIndex){
	var ts = FROM_MILLIS + pxvalue*TIME_UNIT;
	var ind = timeline_findClosestIndex(ts, i, startIndex, endIndex);
	top.opl_debug("Closest index is "+ind +" diff " +(PROCESS_DATA[i].data[ind].timestamp-ts));
	if( ind < PROCESS_DATA[i].data.length-1 && PROCESS_DATA[i].data[ind+1].timestamp <= ts)
		return ind+1;
	if (PROCESS_DATA[i].data[ind].timestamp <= ts ) 
		return ind;
	if (ind > 0 && PROCESS_DATA[i].data[ind-1].timestamp <= ts){
		top.opl_debug("I'm special");
		return ind-1;
	}	
	top.opl_debug("No index below " +pxvalue +" found");
	return -1;	
}

function timeline_findClosestIndex(ts, i, startIndex, endIndex){
	if (startIndex == endIndex || endIndex - startIndex == 1){
		if(ts > PROCESS_DATA[i].data[endIndex].timestamp){
			return endIndex;
		}
		return startIndex;
	}	
	var dp = PROCESS_DATA[i].data[Math.floor((startIndex+endIndex)/2)];
	if (!dp)
		return 0;
	var middleTs = dp.timestamp;
	if(dp.javaClass == "com.ipbyran.opl.web.svg.data.FineDataPoint"){
    	middleTs = middleTs+dp.micros/1000;
    }
    if (middleTs > ts){ // EndIndex too big
    	return timeline_findClosestIndex(ts, i, startIndex, Math.floor( (endIndex+startIndex)/2 ));
    }else if(middleTs < ts){ // StartIndex too small
    	return timeline_findClosestIndex(ts, i, Math.floor( (endIndex+startIndex)/2 ), endIndex);
	}else{
		return Math.floor( (endIndex+startIndex)/2 );
	}
}

function timeline_getTime(pxvalue, startTime, timeUnit,showMillis){
	var millis = startTime + pxvalue*timeUnit;
	var d = new Date(millis);
	var hour = d.getHours();
	var min = d.getMinutes();
	var s = d.getSeconds();
	if(showMillis){
		return timeline_formatMillis(hour,min,s,d.getMilliseconds());
	}else{
	return timeline_format(hour,min,s);
  }
}

function timeline_formatMillis(hour,min,s,millis){
	var time = timeline_format(hour,min,s);
	time += " and "
	if(millis<100) time+="0";
	if(millis<10) time+"0";
	time += millis+" ms";
	return time;
}

function timeline_format(hour,minute,second){
	var time = "";
  
	if(hour < 10)
		time += "0" + hour;
	else
		time += hour;
  
	if(minute < 10)
		time += ":0" + minute;
	else	
		time += ":" + minute;
	
	if(second > 59) // Corrects a rounding error when viewing from 00:00:00 to 23:59:59
		second = 59;
	
	if(second < 10)
		time += ":0" + second;
	else
		time += ":" + second; 
    
	return time;
}

function timeline_updateTime(idNumber, timeString){
	SVG_DOC.getElementById('chart_currentTime_'+idNumber).textContent = timeString;		
}

