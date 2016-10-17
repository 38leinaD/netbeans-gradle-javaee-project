package org.netbeans.gradle.javaee.web;

import org.netbeans.modules.j2ee.deployment.devmodules.api.J2eeModule;
import org.netbeans.modules.j2ee.deployment.devmodules.api.ModuleChangeReporter;
import org.netbeans.modules.j2ee.deployment.devmodules.spi.J2eeModuleFactory;
import org.netbeans.modules.j2ee.deployment.devmodules.spi.J2eeModuleProvider;

/**
 *
 * @author daniel.platz
 */
public class ExperimentalWebModuleProvider extends J2eeModuleProvider {

    private final J2eeModule mod = J2eeModuleFactory.createJ2eeModule(new ExperimentalWebModule());
    
    private String serverInstanceId;
    
    @Override
    public J2eeModule getJ2eeModule() {
        return mod;
    }

    @Override
    public ModuleChangeReporter getModuleChangeReporter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setServerInstanceID(String severInstanceID) {
        this.serverInstanceId = severInstanceID;
    }

    @Override
    public String getServerInstanceID() {
        return serverInstanceId;
    }

    @Override
    public String getServerID() {
        return "Wildfly";
    }
    
}
