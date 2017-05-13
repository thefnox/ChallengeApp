package team10.hkr.challengeapp.Controllers;

import java.net.CookieManager;

/**
 * Created by Charlie on 13.05.2017.
 */

public class AppSingleton {

    private static AppSingleton session;
    CookieManager manager = new CookieManager();

    private AppSingleton() {

    }
    public static AppSingleton getInstance() {
        if(session == null) {
            session = new AppSingleton();
        }
        return session;
    }
    public CookieManager getCookieManager() {
        return manager;
    }
}
