
package com.a.hola.ui;

import org.apache.felix.scr.annotations.Component;

import com.hp.sdn.ui.misc.SelfRegisteringUIExtension;

/**
 * ahmed UI extension, which provides additional UI elements to the
 * HP SDN Controller GUI.
 */
@Component
public class UIExtension extends SelfRegisteringUIExtension {
    
    /** Create the core UI elements contributor. */
    public UIExtension() {
        super("hola", "com/a/hola/ui", UIExtension.class);
    }

}
