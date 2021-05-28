package net.schimweg.financeProcessor.plugin;

import net.schimweg.financeProcessor.execution.MaterializedResult;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An Encoder implements an output format used to output financial objects
 */
public interface Encoder {

    /**
     * Encodes the given results into the given stream
     * @param res The MaterializedResults, which are loaded fully into memory
     * @param stream The stream to write the encoded data to
     * @return The MIME-Type of the encoded data
     * @throws IOException Thrown when anything goes wrong during the encoding
     */
    String encode(MaterializedResult res, OutputStream stream) throws IOException;
}
