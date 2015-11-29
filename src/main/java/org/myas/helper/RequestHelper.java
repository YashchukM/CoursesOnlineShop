package org.myas.helper;

import org.myas.command.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper class to use in servlet. Provides mapping for commands mentioned in views
 * to the business logic classes, implementing <code>Command</code> interface. This
 * class is created with use of Singleton pattern.
 */
public class RequestHelper {
    /**
     * Instance of this class
     */
    private static RequestHelper instance = null;

    /**
     * HashMap containing command mappings
     */
    private Map<String, Command> commands = new HashMap<>();

    /**
     * Default constructor, creates mappings.
     */
    private RequestHelper() {
        /*
         * Commands mapping (action in jsp form -> Command implementing class)
         */
        commands.put("logout", new NoCommand());
        commands.put("register", new RegisterCommand());
        commands.put("login", new LoginCommand());
        commands.put("addOrderPart", new AddOrderPartCommand());
        commands.put("deleteOrderPart", new DeleteOrderPartCommand());
        commands.put("addOrder", new AddOrderCommand());
        commands.put("payOrder", new PayOrderCommand());
        commands.put("activateProduct", new ActivateProductCommand());
        commands.put("addProduct", new AddProductCommand());
        commands.put("blacklistUser", new BlacklistUserCommand());
    }

    /**
     * @param request client request to determine command from.
     * @return according Command implementation.
     */
    public synchronized Command getCommand(HttpServletRequest request) {
        String action = request.getParameter("command");
        Command command = commands.get(action);

        if (command == null) {
            command = new NoCommand();
        }
        return command;
    }

    /**
     * The only way to get instance of RequestHelper class.
     * @return instance of RequestHelper class.
     */
    public synchronized static RequestHelper getInstance() {
        if (instance == null) {
            instance = new RequestHelper();
        }
        return instance;
    }
}
