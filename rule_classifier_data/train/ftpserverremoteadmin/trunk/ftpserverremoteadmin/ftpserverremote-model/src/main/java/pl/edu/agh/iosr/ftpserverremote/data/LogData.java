
package pl.edu.agh.iosr.ftpserverremote.data;

/**
 *
 * @author Tomasz Jadczyk
 */
public class LogData extends ServerData{

    private String level;
    private String message;
    private String stackTrace;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    
}
