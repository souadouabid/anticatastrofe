package com.app.Managers;

import java.util.Vector;

public class BackEndManager {
    //Atributes
    private static volatile BackEndManager bem_instance;
    private static volatile SingletonFactory factory_instance;
    //private static volatile DataLayerManager dlm_instance;

    private BackEndManager() {
        factory_instance.getInstance();
    }

    //Synchronization between threads
    public static BackEndManager getInstance() {
        BackEndManager result = bem_instance;
        if (result != null) return result;
        synchronized (BackEndManager.class) {
            if (bem_instance == null) {
                bem_instance = new BackEndManager();
            }
            return bem_instance;
        }
    }

    public void sendCommand(String subscriber, String action, Vector<String> parameters){
        //DataLayerManager dlm_instance = (DataLayerManager) factory_instance.getManager("data_layer");
        /* Dubte amb subscriber:
            Bàsicament no sé quants o quins han de ser els subscribers.
            Per exemple, el mapa és un subscriber?
            En cas que ho sigui, què ha de fer primer?
                Anar al subscriber per contactar amb el domini per veure si ho pot resoldre el domini?
                O contactar amb DataLayerManager per adquirir les dades d'allà?
                I, en cas que sigui la primera, la comunicació en sí.
         */

    }
}
