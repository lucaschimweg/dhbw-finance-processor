package net.schimweg.financeProcessor.plugin;

import net.schimweg.financeProcessor.execution.MaterializedResult;

import java.io.IOException;
import java.io.OutputStream;

public interface Encoder {
    String encode(MaterializedResult res, OutputStream stream) throws IOException;
}
