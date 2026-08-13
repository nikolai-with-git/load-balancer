package balancer;

import model.Server;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinLoadBalancer extends LoadBalancer {

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Server chooseServer(List<Server> availableServers) {
        int currentCounter = counter.getAndUpdate(c -> c == Integer.MAX_VALUE ? 0 : c+1);
        int serverIndex = currentCounter % availableServers.size();
        return availableServers.get(serverIndex);
    }

}
