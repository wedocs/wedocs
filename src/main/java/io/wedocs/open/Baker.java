package io.wedocs.open;

import io.wedocs.open.app.Oven;
import io.wedocs.open.common.JBakeException;
import io.wedocs.open.config.JBakeConfiguration;
import io.wedocs.open.config.JBakeConfigurationFactory;
import org.apache.commons.configuration.CompositeConfiguration;

import java.text.MessageFormat;
import java.util.List;

/**
 * Delegate class responsible for launching a Bake.
 *
 * @author jmcgarr@gmail.com
 */
public class Baker {

    /**
     * @param options The given cli options
     * @param config  The project configuration
     * @deprecated use {@link Baker#bake(JBakeConfiguration)} instead
     */
    @Deprecated
    public void bake(final LaunchOptions options, final CompositeConfiguration config) {
        JBakeConfiguration configuration = new JBakeConfigurationFactory().createDefaultJbakeConfiguration(options.getSource(), options.getDestination(), config, options.isClearCache());
        bake(configuration);
    }

    public void bake(final JBakeConfiguration config) {
        final Oven oven = new Oven(config);
        oven.bake();

        final List<Throwable> errors = oven.getErrors();
        if (!errors.isEmpty()) {
            final StringBuilder msg = new StringBuilder();
            // TODO: decide, if we want the all errors here
            msg.append( MessageFormat.format("JBake failed with {0} errors:\n", errors.size()));
            int errNr = 1;
            for (final Throwable error : errors) {
                msg.append(MessageFormat.format("{0}. {1}\n", errNr, error.getMessage()));
                ++errNr;
            }
            throw new JBakeException(msg.toString(), errors.get(0));
        }
    }
}
