package balancer;

import model.Server;

import java.util.Random;

public class RandomLoadBalancer extends LoadBalancer{

    private Random random = new Random();

    @Override
    public Server chooseServer() {
        int serverIndex = random.nextInt(getAvailableServers().size());
        return getAvailableServers().get(serverIndex);
    }
}
