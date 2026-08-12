package balancer;

import model.Server;

import java.util.List;
import java.util.Random;

public class RandomLoadBalancer extends LoadBalancer{

    private Random random = new Random();

    @Override
    public Server chooseServer(List<Server> availableServers) {
        int serverIndex = random.nextInt(availableServers.size());
        return availableServers.get(serverIndex);
    }
}
