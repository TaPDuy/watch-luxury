package nhom9.watchluxury.data.remote.service;

public class ServiceHolder {

    private Service service;

    public ServiceHolder() {
    }

    public ServiceHolder(Service service) {
        this.service = service;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
