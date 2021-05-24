package net.schimweg.financeProcessor.plugins.json;

import com.google.gson.Gson;
import net.schimweg.financeProcessor.execution.MaterializedResult;
import net.schimweg.financeProcessor.plugin.Encoder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class JsonEncoder implements Encoder {
    @Override
    public String encode(MaterializedResult res, OutputStream stream) throws IOException {
        try (OutputStreamWriter w = new OutputStreamWriter(stream)) {
            new Gson().toJson(res, w);
            return "application/json";
        }
    }
}
