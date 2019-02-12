import interfaces.BillingService;
import objects.User;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class BillingServiceImpl extends UnicastRemoteObject implements BillingService {

    private List<User> users;

    public BillingServiceImpl() throws RemoteException
    {
        super();
        users = new ArrayList<>();
    }

    @Override
    public User getUser(User user) throws RemoteException {
        System.out.println("get user " + user.getId());
        return users.contains(user) ? users.get(users.indexOf(user)) : null;
    }

    @Override
    public void addUser(User user) throws RemoteException {
        if (!users.contains(user)) {
            user.setId(users.size() + 1);
            users.add(user);
            System.out.println("add user " + users.size());
        }
    }


    public static void main (String[] args) throws Exception
    {
        try {
            System.setProperty("java.rmi.server.hostname", "192.168.0.14");
            Registry registry = LocateRegistry.createRegistry(8101);
            registry.rebind("BillingService", new BillingServiceImpl());
            System.err.println("***Server running***");
        } catch (RemoteException e) {
            System.err.println("RemoteException : "+e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Exception : " + e.getMessage());
            System.exit(2);
        }
    }
}
