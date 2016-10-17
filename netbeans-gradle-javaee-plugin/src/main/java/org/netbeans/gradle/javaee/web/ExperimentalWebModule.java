package org.netbeans.gradle.javaee.web;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.project.Project;
import org.netbeans.gradle.project.NbGradleProject;
import org.netbeans.modules.j2ee.dd.api.web.WebApp;
import org.netbeans.modules.j2ee.deployment.devmodules.api.J2eeModule;
import org.netbeans.modules.j2ee.deployment.devmodules.spi.J2eeModuleImplementation2;
import org.netbeans.modules.j2ee.metadata.model.api.MetadataModel;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 * See WebModuleImpl and BaseEEModuleImpl of Maven
 *
 * Important files: WarDeploymentConfiguration InitialServerFileDistributor
 *
 * @author daniel.platz
 */
public class ExperimentalWebModule implements J2eeModuleImplementation2 {

    private static final Logger logger = Logger.getLogger(ExperimentalWebModule.class.getName());

    private final Project project;
    
    public ExperimentalWebModule(Project project) {
        this.project = project;
    }
    
    protected NbGradleProject getGradleProject() {
        return project.getLookup().lookup(NbGradleProject.class);
    }

    @Override
    public J2eeModule.Type getModuleType() {
        logger.log(Level.SEVERE, "getModuleType()");
        return J2eeModule.Type.WAR;
    }

    @Override
    public String getModuleVersion() {
        logger.log(Level.SEVERE, "getModuleVersion()");
        return WebApp.VERSION_3_1;
    }

    @Override
    public String getUrl() {
        logger.log(Level.SEVERE, "getUrl()");
        return "C:\\Users\\daniel.platz\\dev\\sandbox\\webapp\\build\\libs\\webapp.war";
    }

    @Override
    public FileObject getArchive() throws IOException {
        logger.log(Level.SEVERE, "getArchive()");
        return FileUtil.toFileObject(new File(getUrl()));
    }

    @Override
    public Iterator<J2eeModule.RootedEntry> getArchiveContents() throws IOException {
        FileObject fo = getContentDirectory();
        if (fo != null) {
            return new ContentIterator(fo);
        }
        return null;
    }
    
    
    private static final class ContentIterator implements Iterator<J2eeModule.RootedEntry> {
        private ArrayList<FileObject> ch;
        private FileObject root;
        
        private ContentIterator(FileObject f) {
            this.ch = new ArrayList<FileObject>();
            ch.add(f);
            this.root = f;
        }
        
        @Override
        public boolean hasNext() {
            return ! ch.isEmpty();
        }
        
        @Override
        public J2eeModule.RootedEntry next() {
            FileObject f = ch.get(0);
            ch.remove(0);
            if (f.isFolder()) {
                f.refresh();
                for (FileObject fo : f.getChildren()) {
                    ch.add(fo);
                }
            }
            return new FSRootRE(root, f);
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    private static final class FSRootRE implements J2eeModule.RootedEntry {
        private FileObject f;
        private FileObject root;
        
        FSRootRE(FileObject rt, FileObject fo) {
            f = fo;
            root = rt;
        }
        
        @Override
        public FileObject getFileObject() {
            return f;
        }
        
        @Override
        public String getRelativePath() {
            return FileUtil.getRelativePath(root, f);
        }
    }

    /**
     * This call is used in in-place deployment. Returns the directory staging
     * the contents of the archive. This directory is the one from which the
     * content entries returned by {@link #getArchiveContents} came from.
     *
     * @return FileObject for the content directory
     */
    @Override
    public FileObject getContentDirectory() throws IOException {
        return FileUtil.toFileObject(new File("C:\\Users\\daniel.platz\\dev\\sandbox\\webapp\\out\\webapp.war"));
    }

    @Override
    public <T> MetadataModel<T> getMetadataModel(Class<T> arg0) {
        logger.log(Level.SEVERE, "getMetadataModel()");
        return null;
    }

    @Override
    public File getResourceDirectory() {
        logger.log(Level.SEVERE, "getResourceDirectory()");
        return new File("C:\\Users\\daniel.platz\\dev\\sandbox\\webapp\\build\\libs\\setup");
    }

    @Override
    public File getDeploymentConfigurationFile(String name) {
        logger.log(Level.SEVERE, "getDeploymentConfigurationFile(" + name + ")");

        return new File("C:\\Users\\daniel.platz\\dev\\sandbox\\webapp\\build\\libs\\" + name);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        logger.log(Level.SEVERE, "addPropertyChangeListener()");
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        logger.log(Level.SEVERE, "removePropertyChangeListener()");
    }

}
