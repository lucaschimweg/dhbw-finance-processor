package net.schimweg.financeProcessor.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import net.schimweg.financeProcessor.ast.AstRoot;
import net.schimweg.financeProcessor.config.Config;
import net.schimweg.financeProcessor.execution.*;
import net.schimweg.financeProcessor.plugin.Encoder;
import net.schimweg.financeProcessor.plugin.Parser;
import net.schimweg.financeProcessor.plugin.PluginManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * Provides an HTTP API to execute calculations
 */
public class Server implements HttpHandler {
    private final HttpServer server;
    private final PluginManager pluginManager;
    private final Executor executor;
    private final Config config;

    /**
     * Create a new Server object
     * @param config The configuration object containing the port to listen on
     * @param manager The plugin manager used to find the parser and encoder for incoming requests
     * @param executor The executor to use for AST execution
     * @throws IOException Thrown when listening on the specified port fails
     */
    public Server(Config config, PluginManager manager, Executor executor) throws IOException {
        this.pluginManager = manager;
        this.executor = executor;
        this.config = config;
        server = HttpServer.create(new InetSocketAddress("localhost", config.port), 256);
        server.createContext("/", this);
    }

    /**
     * Starts the server (blocking)
     */
    public void start() {
        server.start();
    }

    /**
     * Handle an incoming HTTP request
     * @param exchange The incoming request and stubs for replying to it
     */
    @Override
    public void handle(HttpExchange exchange) {
        try {
            String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
            if (contentType == null) {
                sendError("No content type specified", 400, exchange);
                return;
            }

            Parser parser = pluginManager.getParserFor(contentType);
            if (parser == null) {
                sendError("Unsupported content type", 400, exchange);
                return;
            }

            Encoder encoder;
            String accept = exchange.getRequestHeaders().getFirst("Accept");
            if (accept == null || accept.equals("*/*")) {
                encoder = pluginManager.getEncoderFor(config.defaultEncoderType);
            } else {
                encoder = pluginManager.getEncoderFor(accept);
            }

            if (encoder == null) {
                sendError("Cold not find matching encoder", 400, exchange);
                return;
            }

            handleRequestWithTypes(exchange, parser, encoder);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleRequestWithTypes(HttpExchange exchange, Parser parser, Encoder encoder) {
        AstRoot root;
        try {
            root = parser.parseTree(exchange.getRequestBody());
        } catch (Exception e) {
            sendError("Error parsing AST", 400, exchange);
            return;
        }

        Result res;
        try {
            res = executor.execute(root);
        } catch (EvaluationException e) {
            e.printStackTrace();
            sendError("Error during execution: " + e.getMessage(), 500, exchange);
            return;
        }

        if (res == null) {
            sendError("Execution yielded no result", 500, exchange);
            return;
        }

        MaterializedResult materializedResult;
        try {
            Materializer m = new Materializer();
            materializedResult = m.materialize(res);
        } catch (EvaluationException e) {
            e.printStackTrace();
            sendError("Error during result materialization: " + e.getMessage(), 500, exchange);
            return;
        }

        ByteArrayOutputStream memoryStream = new ByteArrayOutputStream();
        String encodedContentType;
        try {
            encodedContentType = encoder.encode(materializedResult, memoryStream);
        } catch (Exception e) {
            sendError("Error during response encoding", 500, exchange);
            return;
        }

        sendResponseBody(exchange, memoryStream, encodedContentType);
    }

    private void sendResponseBody(HttpExchange exchange, ByteArrayOutputStream stream, String contentType) {
        exchange.getResponseHeaders().set("Content-Type", contentType);
        try {
            exchange.sendResponseHeaders(200, 0);
            exchange.getResponseBody().write(stream.toByteArray());
            exchange.getResponseBody().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendError(String error, int responseCode, HttpExchange exchange) {
        System.out.println("Handling error " + error);
        try {
            exchange.getResponseHeaders().set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(responseCode, 0);
            exchange.getResponseBody().write(error.getBytes(StandardCharsets.UTF_8));
            exchange.getResponseBody().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
