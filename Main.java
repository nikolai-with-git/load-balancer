import balancer.LoadBalancer;
import balancer.RandomLoadBalancer;
import balancer.RoundRobinLoadBalancer;
import model.Request;
import model.Server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        LoadBalancer balancer = new RoundRobinLoadBalancer();
        balancer.addServer(new Server("Server A", true));
        balancer.addServer(new Server("Server B", true));
        balancer.addServer(new Server("Server C", false));
        balancer.addServer(new Server("Server D", true));

        //Add and remove new server in future
        Server s5 = new Server("Server E", true);
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(() -> balancer.addServer(s5), 7, TimeUnit.SECONDS);
        scheduledExecutorService.schedule(() -> balancer.removeServer(s5), 12, TimeUnit.SECONDS);

        int counter = 0;

        //Do multiple requests
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        while (true){
            for (int i = 0; i<8; i++) {
                Request newRequest = new Request(counter++);
                executorService.execute(() -> balancer.addRequest(newRequest));
            };
            Thread.sleep(500);
        }

//        while (true){
//            Request newRequest = new Request(counter++);
//            balancer.addRequest(newRequest);
//            Thread.sleep(500);
//        }

    }

}
