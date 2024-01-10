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

import uk.ltd.getahead.dwr.WebContextFactory;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Original : plosson on 06-janv.-2006 12:19:08 - Last modified  by $Author: vde $ on $Date: 2004/11/26 22:43:57 $
 * @version 1.0 - Rev. $Revision: 1.2 $
 */
public class UploadMonitor
{     //	logging
	Logger log = Logger.getLogger(this.getClass());
    public UploadInfo getUploadInfo()
    {
        log.debug("inside getUploadInfo() ");
        HttpServletRequest req = WebContextFactory.get().getHttpServletRequest();
        log.debug("[getUploadInfo] - got the req ");
        if (req.getSession().getAttribute("uploadInfo") != null){
            log.debug("[getUploadInfo] - return uploadInfo from session ");
            return (UploadInfo) req.getSession().getAttribute("uploadInfo");
        }else{
            log.debug("[getUploadInfo] - return uploadInfo ");
            return new UploadInfo();
        }
    }
}
