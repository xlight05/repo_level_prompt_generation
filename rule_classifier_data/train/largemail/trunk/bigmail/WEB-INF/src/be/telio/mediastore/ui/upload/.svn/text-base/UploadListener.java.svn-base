/* Licence:
*   Use this however/wherever you like, just don't blame me if it breaks anything.
*
* Credit:
*   If you're nice, you'll leave this bit:
*
*   Class by Pierre-Alexandre Losson -- http://www.telio.be/blog
*   email : plosson@users.sourceforge.net
*/
/*
*  Changed for Part 2, by Ken Cochrane
*  http://KenCochrane.net , http://CampRate.com , http://PopcornMonsters.com
*/
package be.telio.mediastore.ui.upload;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Original : plosson on 06-janv.-2006 15:05:44 - Last modified  by $Author: vde $ on $Date: 2004/11/26 22:43:57 $
 * @version 1.0 - Rev. $Revision: 1.2 $
 */

public class UploadListener implements OutputStreamListener

{   //	logging
	Logger log = Logger.getLogger(this.getClass());
    private HttpServletRequest request;
    private long delay = 0;
    private long startTime = 0;
    private int totalToRead = 0;
    private int totalBytesRead = 0;
    // totalFiles increases when getting OutputStream.
    private int fileIndex = -1;
   
    public UploadListener(HttpServletRequest request, long debugDelay)
    {
        this.request = request;
        this.delay = debugDelay;
        totalToRead = request.getContentLength();
        this.startTime = System.currentTimeMillis();
        
    }

    public void start()
    {
        fileIndex ++;
        updateUploadInfo("start");
    }

    public void bytesRead(int bytesRead)
    {
        totalBytesRead = totalBytesRead + bytesRead;
        updateUploadInfo("progress");

        try
        {
        	//set delay time in CustomMultipartRequestHandler.handleRequest()
            Thread.sleep(delay);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void error(String message)
    {
        updateUploadInfo(message);
    }

    public void done()
    {
        updateUploadInfo("done");
    }
    /*
     *  @author Original : plosson on 06-janv.-2006 15:05:44 - Last modified  by $Author: kof4ever $ on $Date: 2006/12/05 11:49:57 $
     *  @return elapsed time from starting uploding in milliseconds
     */      
    private long getDelta()
    {
        return (System.currentTimeMillis() - startTime);
    }

    private void updateUploadInfo(String status)
    {
        log.debug("inside updateUploadInfo ");
        long delta = getDelta();
        log.debug("updateUploadInfo delta =  " + delta+" fileIndex="+fileIndex);
        request.getSession().setAttribute("uploadInfo", new UploadInfo(fileIndex, totalToRead, totalBytesRead,delta,status));
        log.debug("leaving updateUploadInfo ");
    }
    
}
