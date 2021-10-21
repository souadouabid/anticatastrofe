package com.app.Managers;

import java.util.Vector;

public class FrontEndManager {
    //Attributes
    private static volatile FrontEndManager fem_instance;
    private static volatile SingletonFactory factory_instance;


    private FrontEndManager() {
        factory_instance.getInstance();
    }

    //Synchronization between threads
    public static FrontEndManager getInstance() {
        FrontEndManager result = fem_instance;
        if (result != null) return result;
        synchronized (FrontEndManager.class) {
            if (fem_instance == null) {
                fem_instance = new FrontEndManager();
            }
            return fem_instance;
        }
    }

    public void sendCommand(String subscriber, String action, Vector<String> parameters) {
        BackEndManager bem_instance = (BackEndManager) factory_instance.getManager("back_end");
        bem_instance.sendCommand(subscriber,action,parameters);
    }
}