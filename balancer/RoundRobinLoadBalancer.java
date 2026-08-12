package balancer;

import model.Server;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinLoadBalancer extends LoadBalancer {

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Server chooseServer(List<Server> availableServers) {
        counter.compareAndSet(Integer.MAX_VALUE, 0);
        int serverIndex = counter.getAndIncrement() % availableServers.size();
        return availableServers.get(serverIndex);
    }

}
