package pl.edu.agh.iosr.ftpserverremote.data;

/**
 *
 * @author Tomasz Sadura
 */
public abstract class Entity {

  static final String TRUE = Boolean.TRUE.toString();
  static final String FALSE = Boolean.FALSE.toString();

  private Long id;
  private Long serverId = 0L;

  public Entity() {
  }

  public Long getId() {
    return id;
  }

  public Long getServerId() {
    return serverId;
  }

  public void setId( final Long id ) {
    this.id = id;
  }

  public void setServerId( final Long serverId ) {
    this.serverId = serverId;
  }

}
