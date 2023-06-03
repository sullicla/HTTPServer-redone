package edu.uw.info314.xmlrpc.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServerNew {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("HTTP Server listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleRequest(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRequest(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String requestLine = in.readLine();
        if (requestLine != null) {
            String[] requestParts = requestLine.split(" ");
            String method = requestParts[0];
            String requestURI = requestParts[1];

            if ("GET".equals(method)) {
                handleGetRequest(requestURI, out);
            } else if ("POST".equals(method)) {
                handlePostRequest(requestURI, in, out);
            } else if ("PUT".equals(method)) {
                handlePutRequest(requestURI, in, out);
            } else if ("DELETE".equals(method)) {
                handleDeleteRequest(requestURI, out);
            } else if ("OPTIONS".equals(method)) {
                handleOptionsRequest(out);
            } else if ("HEAD".equals(method)) {
                handleHeadRequest(requestURI, out);
            } else {
                sendBadRequestResponse(out);
            }
        }

        clientSocket.close();
    }

    private static void handleGetRequest(String requestURI, PrintWriter out) {
        try {
            File file = new File(requestURI);
            if (file.exists() && file.isFile()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                reader.close();

                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/plain");
                out.println("Content-Length: " + content.length());
                out.println();
                out.println(content.toString());
            } else {
                sendNotFoundResponse(out);
            }
        } catch (IOException e) {
            sendServerErrorResponse(out);
        }
    }

    private static void handlePostRequest(String requestURI, BufferedReader in, PrintWriter out) {
        try {
            if ("text/plain".equals(getContentType(requestURI))) {
                FileWriter fileWriter = new FileWriter(requestURI, true);
                String line;
                while ((line = in.readLine()) != null) {
                    fileWriter.write(line);
                }
                fileWriter.close();

                out.println("HTTP/1.1 200 OK");
                out.println();
            } else {
                sendUnsupportedMediaTypeResponse(out);
            }
        } catch (IOException e) {
            sendServerErrorResponse(out);
        }
    }

    private static void handlePutRequest(String requestURI, BufferedReader in, PrintWriter out) {
        try {
            if ("text/plain".equals(getContentType(requestURI))) {
                FileWriter fileWriter = new FileWriter(requestURI);
                String line;
                while ((line = in.readLine()) != null) {
                    fileWriter.write(line);
                }
                fileWriter.close();

                out.println("HTTP/1.1 200 OK");
                out.println();
            } else {
                sendUnsupportedMediaTypeResponse(out);
            }
        } catch (IOException e) {
            sendServerErrorResponse(out);
        }
    }

    private static void handleDeleteRequest(String requestURI, PrintWriter out) {
        File file = new File(requestURI);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                out.println("HTTP/1.1 200 OK");
                out.println();
            } else {
                sendServerErrorResponse(out);
            }
        } else {
            sendNotFoundResponse(out);
        }
    }

    private static void handleOptionsRequest(PrintWriter out) {
        out.println("HTTP/1.1 200 OK");
        out.println("Allow: GET, POST, PUT, DELETE, OPTIONS");
        out.println("Content-Length: 0");
        out.println();
    }

    private static void handleHeadRequest(String requestURI, PrintWriter out) {
        File file = new File(requestURI);
        if (file.exists() && file.isFile()) {
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/plain");
            out.println("Content-Length: " + file.length());
            out.println();
        } else {
            sendNotFoundResponse(out);
        }
    }

    private static String getContentType(String fileName) {
        if (fileName.endsWith(".txt")) {
            return "text/plain";
        } else if (fileName.endsWith(".html")) {
            return "text/html";
        } else if (fileName.endsWith(".json")) {
            return "application/json";
        } else {
            return "application/octet-stream";
        }
    }

    private static void sendBadRequestResponse(PrintWriter out) {
        out.println("HTTP/1.1 400 Bad Request");
        out.println("Content-Type: text/html");
        out.println();
        out.println("<html><body><h1>400 Bad Request</h1></body></html>");
        out.println("<img src=\"https://http.cat/400\" alt=\"400 Bad Request\">");
    }

    private static void sendNotFoundResponse(PrintWriter out) {
        out.println("HTTP/1.1 404 Not Found");
        out.println("Content-Type: text/html");
        out.println();
        out.println("<html><body><h1>404 Not Found</h1></body></html>");
        out.println("<img src=\"https://http.cat/404\" alt=\"404 Not Found\">");
    }

    private static void sendServerErrorResponse(PrintWriter out) {
        out.println("HTTP/1.1 500 Internal Server Error");
        out.println("Content-Type: text/html");
        out.println();
        out.println("<html><body><h1>500 Internal Server Error</h1></body></html>");
        out.println("<img src=\"https://http.cat/500\" alt=\"500 Internal Server Error\">");
    }

    private static void sendUnsupportedMediaTypeResponse(PrintWriter out) {
        out.println("HTTP/1.1 415 Unsupported Media Type");
        out.println("Content-Type: text/html");
        out.println();
        out.println("<html><body><h1>415 Unsupported Media Type</h1></body></html>");
        out.println("<img src=\"https://http.cat/415\" alt=\"415 Unsupported Media Type\">");
    }
}
