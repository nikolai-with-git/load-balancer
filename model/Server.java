package model;

import java.util.Objects;

public class Server {
    private String name;
    private boolean isAvailable;

    public Server(String name, boolean isAvailable){
        this.name = name;
        this.isAvailable = isAvailable;
    }

    public void handleRequest(Request request){
        System.out.println(name + " handles request with id" + request.getId());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return Objects.equals(name, server.name);
    }

    @Override
    public String toString() {
        return "Server{" +
                "isAvailable=" + isAvailable +
                ", name='" + name + '\'' +
                '}';
    }
}
