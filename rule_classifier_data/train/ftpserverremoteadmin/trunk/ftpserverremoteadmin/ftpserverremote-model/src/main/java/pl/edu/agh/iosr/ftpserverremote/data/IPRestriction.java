package pl.edu.agh.iosr.ftpserverremote.data;

/**
 * 
 * @author Tomasz Sadura
 *
 */
public class IPRestriction extends Entity {

  private String hostname;
  private String ipPattern;
  private String permission = "true";

  public String getIpPattern() {
    return ipPattern;
  }

  public void setIpPattern( final String ipPattern ) {
    this.ipPattern = ipPattern;
  }

  public Boolean getPermissionBoolean() {
    return TRUE.equals( permission );
  }

  public void setPermissionBoolean( final Boolean permission ) {
    this.permission = permission ? TRUE : FALSE;
  }

  public String getPermission() {
    return permission;
  }

  public void setPermission( final String permission ) {
    this.permission = permission;
  }

  public String getHostname() {
    return hostname;
  }

  public void setHostname( final String hostname ) {
    this.hostname = hostname;
  }

}
