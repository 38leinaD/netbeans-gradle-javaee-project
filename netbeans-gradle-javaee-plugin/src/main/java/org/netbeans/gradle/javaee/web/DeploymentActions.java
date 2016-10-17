/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.gradle.javaee.web;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.netbeans.api.io.IOProvider;
import org.netbeans.api.io.InputOutput;
import org.netbeans.gradle.project.api.nodes.GradleProjectContextActions;
import org.netbeans.modules.j2ee.deployment.devmodules.api.Deployment;
import org.openide.util.Exceptions;

/**
 *
 * @author daniel.platz
 */
public class DeploymentActions implements GradleProjectContextActions {

    @Override
    public List<Action> getContextActions() {
        return Arrays.asList((Action) new DeployAction("Deploy"));
    }

    private static class DeployAction extends AbstractAction {

        public DeployAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            Deployment deployer = Deployment.getDefault();
            String siid = deployer.getServerInstanceIDs()[0];

            ExperimentalWebModuleProvider p = new ExperimentalWebModuleProvider();
            p.setServerInstanceID(siid);

            String deploy;
            try {
                deploy = deployer.deploy(p, Deployment.Mode.RUN, "", "", true);
            } catch (Deployment.DeploymentException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

}
