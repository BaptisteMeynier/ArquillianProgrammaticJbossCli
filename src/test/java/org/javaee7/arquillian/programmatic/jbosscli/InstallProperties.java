package org.javaee7.arquillian.programmatic.jbosscli;

import org.jboss.as.arquillian.api.ServerSetupTask;
import org.jboss.as.arquillian.container.ManagementClient;
import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.scriptsupport.CLI;
import org.jboss.as.process.protocol.StreamUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class InstallProperties implements ServerSetupTask {

    public static final String REMOVE_PROPERTIES = "src/test/resources/jboss-remove-properties.cli";
    public static final String PRESENCE_SCRIPT = "src/test/resources/jboss-check-presence.cli";
    public static final String ADD_PROPERTIES = "src/test/resources/jboss-add-properties.cli";

    @Override
    public void setup(ManagementClient managementClient, String containerId) throws Exception {
        final String host = managementClient.getWebUri().getHost();
        if (processCliFile(host, new File(PRESENCE_SCRIPT)) != 0) {
            processCliFile(host, new File(ADD_PROPERTIES));
        }
    }

    @Override
    public void tearDown(ManagementClient managementClient, String s) throws Exception {
        final String host = managementClient.getWebUri().getHost();
        if (processCliFile(host, new File(PRESENCE_SCRIPT)) == 0) {
            processCliFile(managementClient.getWebUri().getHost(), new File(REMOVE_PROPERTIES));
        }
    }


    private int processCliFile(String host, File file) {
        CLI cli = CLI.newInstance();
        try {
            cli.connect(host, 9990, null, null);
            CommandContext commandContext = cli.getCommandContext();
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                while (commandContext.getExitCode() == 0 && !commandContext.isTerminated() && line != null) {
                    commandContext.handleSafe(line.trim());
                    line = reader.readLine();
                }
                return commandContext.getExitCode();
            } catch (Throwable e) {
                throw new IllegalStateException("Failed to process file '" + file.getAbsolutePath() + "'", e);
            } finally {
                StreamUtils.safeClose(reader);
            }
        } finally {
            cli.disconnect();
        }
    }
}
