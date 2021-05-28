package net.schimweg.financeProcessor.plugin;

import net.schimweg.financeProcessor.ast.AstRoot;

import java.io.IOException;
import java.io.InputStream;

/**
 * Represents a Parser, accepting an input format and parsing it to an AstRoot object
 * Parsers will require a factory function creating the Parser from a NodeTypeFactory to be registered in a PluginManager
 * @see net.schimweg.financeProcessor.plugin.PluginManager
 */
public interface Parser {
    /**
     * Parses the inputStreams content into an AstRoot
     * @param inputStream The stream to read the input from
     * @return An AstRoot object parsed from the input stream's content
     * @throws IOException If anything goes wrong during the parse process
     */
    AstRoot parseTree(InputStream inputStream) throws IOException;
}
