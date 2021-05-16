package net.schimweg.financeProcessor.plugins;

import net.schimweg.financeProcessor.plugin.Plugin;

public class Main implements Plugin {

    @Override
    public void initialize(String yeah) {
        System.out.println(yeah);
    }
}
