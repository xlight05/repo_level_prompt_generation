package pl.edu.agh.iosr.ftpserverremote.iprestrictor;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.ftpserver.ftplet.FtpException;
import junit.framework.TestCase;

/**
 * 
 * @author Tomasz Sadura
 *
 */
public class DBIPRestrictorTest extends TestCase {

  public void testRestrictions() throws FtpException, UnknownHostException {
    final DBIPRestrictor ipRestrictor = new DBIPRestrictor();

    ipRestrictor.setDataSource( new FakeDataSource( new String[][] {
        {
            "213.180.130.200", "true"
        }, {
            "212.77.100.101", "false"
        }
    } ) );
    ipRestrictor.setSqlSelectAll( "SELECT IPPattern, Permission FROM IPRestriction WHERE serverId='0' OR serverId='{serverId}' ORDER BY Id" );
    ipRestrictor.configure();

    assertTrue( ipRestrictor.hasPermission( InetAddress.getByName( "213.180.130.200" ) ) );
    assertFalse( ipRestrictor.hasPermission( InetAddress.getByName( "212.77.100.101" ) ) );

    assertTrue( Boolean.TRUE );
  }
}
