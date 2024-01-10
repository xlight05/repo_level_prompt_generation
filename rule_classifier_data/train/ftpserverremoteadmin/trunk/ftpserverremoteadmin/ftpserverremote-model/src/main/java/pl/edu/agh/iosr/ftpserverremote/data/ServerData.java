/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.iosr.ftpserverremote.data;

import java.io.Serializable;

/**
 *
 * @author Tomasz Jadczyk
 */
public class ServerData implements Serializable {

    private Long id;
    private Long serverId;
    private String serverHostname;

    public ServerData() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getServerHostname() {
        return serverHostname;
    }

    public void setServerHostname(String serverIp) {
        this.serverHostname = serverIp;
    }
}
