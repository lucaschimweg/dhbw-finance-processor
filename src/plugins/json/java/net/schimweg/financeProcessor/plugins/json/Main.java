package net.schimweg.financeProcessor.plugins.json;

import net.schimweg.financeProcessor.plugin.Plugin;
import net.schimweg.financeProcessor.plugin.PluginManager;

public class Main implements Plugin {
    @Override
    public void initialize(PluginManager pluginManager) {
        pluginManager.addParserType("application/json", JsonParser::new);
        pluginManager.addEncoderType("application/json", JsonEncoder::new);

        pluginManager.addDataProviderType("json_file", JsonDataProvider::new);
    }
}
