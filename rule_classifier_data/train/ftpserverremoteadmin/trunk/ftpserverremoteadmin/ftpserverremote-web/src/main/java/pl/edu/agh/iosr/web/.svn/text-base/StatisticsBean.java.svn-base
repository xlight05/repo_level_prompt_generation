package pl.edu.agh.iosr.web;

import java.util.List;
import javax.faces.model.SelectItem;
import pl.edu.agh.iosr.DefaultContext;
import pl.edu.agh.iosr.ftpserverremote.data.Server;
import pl.edu.agh.iosr.ftpserverremote.data.StatisticsData;
import pl.edu.agh.iosr.ftpserverremote.serverdata.ServerDataProvider;
import pl.edu.agh.iosr.web.provider.ServerProvider;

/**
 *
 * @author Tomasz Jadczyk
 */
public class StatisticsBean {

    private List<StatisticsData> statistics;
    private String serverLabel;
    private SelectItem[] serverLabels;

    public StatisticsBean() {
        reload();
    }

    public List<StatisticsData> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<StatisticsData> statistics) {
        this.statistics = statistics;
    }

    public String getServerLabel() {
        return serverLabel;
    }

    public void setServerLabel(String serverLabel) {
        this.serverLabel = serverLabel;
    }

    public SelectItem[] getServerLabels() {
        return ServerProvider.getCurrentContextServers();
    }

    public void setServerLabels(SelectItem[] serverLabels) {
        this.serverLabels = serverLabels;
    }

    public void reload() {
        setServerLabels(ServerProvider.getCurrentContextServers());
        
        if (serverLabel == null && getServerLabels().length > 0) {
            serverLabel = getServerLabels()[0].getValue().toString();
        }
        
        if (serverLabel != null) {
            String srvName = serverLabel;
            Server server = DefaultContext.getInstance().getServer(srvName);
            setStatistics(ServerDataProvider.getServerStats(server));
        }
    }
 
}
