package net.schimweg.financeProcessor.parser;

import net.schimweg.financeProcessor.ast.AstRoot;

import java.io.Reader;

public interface Parser {
    AstRoot parseTree(Reader inputStream);
}
