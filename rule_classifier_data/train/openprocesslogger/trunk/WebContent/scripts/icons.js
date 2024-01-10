/***
 * This function provides the icons for the logging task list. Can be replaced by specific set of icons. These are the IPB icons.
 * 
 * Used in analyze.js and logging.js for to retreive tasklist icons
 * 
 */
function getImage(running){
  var img = document.createElement("img");
  var src;
  if(running){
     src = "images/ipb_running.gif";
  }else{
     src = "images/ipb_still.gif";
  }
  img.setAttribute("src", src);
  img.setAttribute("width", "24");
  img.setAttribute("height", "24");
  return img;
}