package balancer;

import model.Server;

import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinLoadBalancer extends LoadBalancer {

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Server chooseServer() {
        counter.compareAndSet(Integer.MAX_VALUE, 0);
        int serverIndex = counter.getAndIncrement() % getAvailableServers().size();
        return getAvailableServers().get(serverIndex);
    }

}
