package com.xpto.cidades.integracao;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.runner.RunWith;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.File;
import java.net.URL;

@RunWith(Arquillian.class)
@RunAsClient
public abstract class AbstractTest {

    @ArquillianResource
    protected URL url;

    protected Client cliente = ClientBuilder.newClient();

    @Deployment
    public static Archive<?> createDeployment() {
        File[] arquivos = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .importCompileAndRuntimeDependencies()
                .resolve()
                .withTransitivity()
                .asFile();

        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addAsResource("test-persistence.xml", "META-INF/persistence.xml");
        deployment.addAsResource("project-defaults.yml");
        deployment.addAsResource("db/migration");
        deployment.addPackages(true, "com.xpto.cidades");
        deployment.addAsLibraries(arquivos);

        return deployment;
    }

}
