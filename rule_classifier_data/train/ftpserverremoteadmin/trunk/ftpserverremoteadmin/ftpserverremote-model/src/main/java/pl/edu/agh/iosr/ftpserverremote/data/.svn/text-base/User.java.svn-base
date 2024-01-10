package pl.edu.agh.iosr.ftpserverremote.data;

/**
 *
 * @author Tomasz Sadura
 */
public class User extends Entity {

  private String name;
  private String password;
  private String rootDirectory;
  private String enabled = TRUE;
  private String writePermission = FALSE;
  private Integer maxLoginNumber = 0;
  private Integer maxLoginFromSameIP = 0;
  private Integer maxIdleTime = 0;
  private Integer maxUpload = 0;
  private Integer maxDownload = 0;

  public User() {
  }

  public String getName() {
    return name;
  }

  public void setName( final String name ) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword( final String password ) {
    this.password = password;
  }

  public String getRootDirectory() {
    return rootDirectory;
  }

  public void setRootDirectory( final String rootDirectory ) {
    this.rootDirectory = rootDirectory;
  }

  public Integer getMaxLoginNumber() {
    return maxLoginNumber;
  }

  public void setMaxLoginNumber( final Integer maxLoginNumber ) {
    this.maxLoginNumber = maxLoginNumber;
  }

  public Integer getMaxLoginFromSameIP() {
    return maxLoginFromSameIP;
  }

  public void setMaxLoginFromSameIP( final Integer maxLoginFromSameIP ) {
    this.maxLoginFromSameIP = maxLoginFromSameIP;
  }

  public Integer getMaxIdleTime() {
    return maxIdleTime;
  }

  public void setMaxIdleTime( final Integer maxIdleTime ) {
    this.maxIdleTime = maxIdleTime;
  }

  public Integer getMaxUpload() {
    return maxUpload;
  }

  public void setMaxUpload( final Integer maxUpload ) {
    this.maxUpload = maxUpload;
  }

  public Integer getMaxDownload() {
    return maxDownload;
  }

  public void setMaxDownload( final Integer maxDownload ) {
    this.maxDownload = maxDownload;
  }

  public String getEnabled() {
    return enabled;
  }

  public void setEnabled( final String enabled ) {
    this.enabled = enabled;
  }

  public String getWritePermission() {
    return writePermission;
  }

  public void setWritePermission( final String writePermission ) {
    this.writePermission = writePermission;
  }

  public void setEnabledBoolean( final boolean enabled ) {
    this.enabled = enabled ? TRUE : FALSE;
  }

  public boolean getEnabledBoolean() {
    return TRUE.equals( enabled );
  }

  public void setWritePermissionBoolean( final Boolean writePermission ) {
    this.writePermission = writePermission ? TRUE : FALSE;
  }

  public boolean getWritePermissionBoolean() {
    return TRUE.equals( writePermission );
  }
}
