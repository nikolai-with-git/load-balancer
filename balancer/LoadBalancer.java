package balancer;

import model.Request;
import model.Server;

import java.util.concurrent.*;

public abstract class LoadBalancer {

    private ThreadPoolExecutor threadPoolExecutor;
    private CopyOnWriteArrayList<Server> servers;

    private CopyOnWriteArrayList<Server> availableServers;

    public abstract Server chooseServer();

    public LoadBalancer(){
        threadPoolExecutor = new ThreadPoolExecutor(1,8,10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));
        availableServers  = new CopyOnWriteArrayList<>();
        servers = new CopyOnWriteArrayList<>();

        startHealthCheck();
    }

    public void addServer(Server server){
        this.servers.addIfAbsent(server);
        System.out.println(server.getName() + " was added to list");
    }

    public void removeServer(Server server){
        this.servers.remove(server);
        this.availableServers.remove(server);
        System.out.println(server.getName() + " was removed from list");
    }

    public void addRequest(Request request){
        threadPoolExecutor.execute(() -> routeRequest(request));
    }

    private void routeRequest(Request request) {
        if (availableServers.isEmpty()){
            System.out.println("There are no available servers");
            return;
        }
        Server server = chooseServer();
        server.handleRequest(request);
    };

    private void startHealthCheck(){
        if (servers.isEmpty()){
            System.out.println("There are no servers in list");
        }

        System.out.println("Start health check");
        Runnable healthCheckTask = () -> {
            for (Server server : servers){
                if (server.isAvailable()){
                    availableServers.addIfAbsent(server);
                }else{
                    System.out.println(server.getName() + " is not available");
                    availableServers.remove(server);
                }
            }
        };

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(healthCheckTask, 0, 5, TimeUnit.SECONDS);
    }


    public CopyOnWriteArrayList<Server> getAvailableServers() {
        return availableServers;
    }
}
