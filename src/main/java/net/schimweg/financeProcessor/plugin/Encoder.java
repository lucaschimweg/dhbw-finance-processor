package net.schimweg.financeProcessor.plugin;

import net.schimweg.financeProcessor.execution.Result;

import java.io.IOException;
import java.io.OutputStream;

public interface Encoder {
    String encode(Result res, OutputStream stream) throws IOException;
}
