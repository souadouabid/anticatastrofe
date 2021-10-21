package com.app.Managers;

public class SingletonFactory {
    //Atributes
    private static volatile SingletonFactory factory_instance;
    private static volatile DataLayerManager dlm_instance;
    private static volatile FrontEndManager fem_instance;
    private static volatile BackEndManager bem_instance;

    private SingletonFactory() {

    }

    //Synchronization between threads
    public static SingletonFactory getInstance() {
        SingletonFactory result = factory_instance;
        if (result != null) return result;
        synchronized (SingletonFactory.class) {
            if (factory_instance == null) {
                factory_instance = new SingletonFactory();
            }
            return factory_instance;
        }
    }

    public static <T> T getManager(String name) {
        if (name.equals("front_end")) {
            if (fem_instance == null) fem_instance.getInstance();
            return (T) fem_instance;
        }
        else if (name.equals("back_end")) {
            if (bem_instance == null) bem_instance.getInstance();
            return (T) bem_instance;
        }
        else if (name.equals("data_layer")){
            if (dlm_instance == null) dlm_instance.getInstance();
            return (T) dlm_instance;
        }
        return (T) null;
    }
}
