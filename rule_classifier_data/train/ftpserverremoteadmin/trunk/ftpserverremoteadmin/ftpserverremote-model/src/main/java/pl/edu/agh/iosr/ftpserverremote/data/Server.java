package pl.edu.agh.iosr.ftpserverremote.data;

/**
 * 
 * @author Tomasz Sadura
 *
 */
public class Server extends Entity {

  private String hostname;
  private Integer port;

  @Override
  public void setServerId( final Long serverId ) {
    super.setServerId( serverId );
  }

  @Override
  public Long getServerId() {
    return getId();
  }

  public String getHostname() {
    return hostname;
  }

  public void setHostname( final String hostname ) {
    this.hostname = hostname;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort( final Integer port ) {
    this.port = port;
  }

  public String getFullName() {
    return hostname + ":" + port;
  }

}
