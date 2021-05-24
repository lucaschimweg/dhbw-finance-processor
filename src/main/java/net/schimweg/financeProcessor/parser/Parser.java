package net.schimweg.financeProcessor.parser;

import net.schimweg.financeProcessor.ast.AstRoot;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public interface Parser {
    AstRoot parseTree(InputStream inputStream) throws IOException;
}
