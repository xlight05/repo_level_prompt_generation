package pl.edu.agh.iosr.ftpserverremote.data;

/**
 * 
 * @author Tomasz Sadura
 *
 */
public class Admin extends Entity {

  private String login;
  private String password;

  public String getLogin() {
    return login;
  }

  public void setLogin( final String login ) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword( final String password ) {
    this.password = password;
  }
}
