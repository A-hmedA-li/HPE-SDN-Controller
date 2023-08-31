package com.a.hola.rs;

import org.apache.felix.scr.annotations.Component;

import com.hp.sdn.adm.rsdoc.SelfRegisteringRSDocProvider;

/**
 * Self-registering REST API documentation provider.
 */
@Component
public class DocProvider extends SelfRegisteringRSDocProvider {
    
    public DocProvider() {
        super("PHD", "rsdoc", DocProvider.class.getClassLoader());
    }

}
