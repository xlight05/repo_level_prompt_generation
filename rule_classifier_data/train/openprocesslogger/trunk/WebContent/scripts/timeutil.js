function timeutil_formatTime(hour,minute){
  var time = "";
  
  if(hour < 10)
    time += "0" + hour;
  else
    time += hour;
  
  if(minute < 10)
    time += ":0" + minute;
  else
    time += ":" + minute;
   
  return time;
}
function timeutil_formatTimeFromD(d){
	return timeutil_formatTimeDetailed(d.getHours(), d.getMinutes(), d.getSeconds(), d.getMilliseconds());
}

function timeutil_formatTimeFromN(d){
	return timeutil_formatTimeNormal(d.getHours(), d.getMinutes(), d.getSeconds());
}

function timeutil_formatTimeNormal(hour,minute,second){
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

function timeutil_formatTimeDetailed(hour,minute,second, millis){
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
    
    time += " and "
	if(millis<100) time+="0";
	if(millis<10) time+"0";
	time += millis+" ms";
	
	return time;
}

function timeutil_formatDate(year, month, day){
	var date = "";
	date += year +"-";
	if(month<9){
		date += "0"
	}
	date += (month+1); // Date 0-11
	date += "-";
	if(day < 10){
    date += "0";
	}
	date += day;
	return date;
}

function timeutil_formatDateFromD(fDate){
	var date = "";
	date += fDate.getFullYear() +"-";
	if(fDate.getMonth()<9){
		date += "0"
	}
	date += (fDate.getMonth()+1); // Date 0-11
	date += "-";
	if(fDate.getDate() < 10){
    date += "0";
	}
	date += fDate.getDate();
	return date;
}

function timeutil_formatTs(ts){
	var d = new Date(ts);
	return timeutil_formatDateFromD(d) +" " +timeutil_formatTimeFromD(d);
}

function timeutil_getTimeMillis(startDate, startTime){
  var resultTime;
  if(startDate && !startTime){ // Date but no time, set date and 00:00:00
    resultTime = +(new Date(startDate.getFullYear(),startDate.getMonth(),startDate.getDate(),0,0,0)).getTime();
  }else if(startTime){ // time set
    if(!startDate) startDate = new Date(); // If date not set, assume today
  	resultTime = +(new Date(startDate.getFullYear(),startDate.getMonth(),startDate.getDate(),startTime.getHours(),startTime.getMinutes(),0)).getTime();
  }else{
  	resultTime = new Date(); // If not set, assume now
  }
  return resultTime;
}