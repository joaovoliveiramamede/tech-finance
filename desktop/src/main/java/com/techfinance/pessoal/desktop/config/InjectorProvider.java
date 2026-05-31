package com.techfinance.pessoal.desktop.config;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class InjectorProvider {
    
    private static final Injector injector =
        Guice.createInjector(new DesktopModule());
    
    public static Injector getInjector() {
        return injector;
    }

}