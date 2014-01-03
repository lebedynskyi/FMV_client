package com.music.fms.network;

import java.io.*;
import java.net.HttpURLConnection;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 12/12/13
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */

public class NetworkResponse {
    public HttpURLConnection connection;
    private int responseCode = -1;

    NetworkResponse(HttpURLConnection connection) {
        this.connection = connection;
    }

    public String readResponse() throws IOException {
        int respCode = getResponseCode();

        InputStream inputStream;
        if (respCode >= 200 && respCode < 300) {
            inputStream = connection.getInputStream();
        } else inputStream = connection.getErrorStream();

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuilder responseBuilder = new StringBuilder();
        String s;
        while ((s = reader.readLine()) != null) {
            responseBuilder.append(s);
        }
        return responseBuilder.toString();
    }

    public byte[] readData() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        readData(buffer);
        return buffer.toByteArray();
    }

    public void readData(OutputStream outStream) throws IOException {
        InputStream is = connection.getInputStream();
        byte[] data = new byte[2048];
        int nRead;
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            outStream.write(data, 0, nRead);
        }
        outStream.flush();
    }

    public int getResponseCode() throws IOException {
        if (responseCode == -1) {
            responseCode = connection.getResponseCode();
        }
        return responseCode;
    }

    public InputStream getStream() throws IOException {
        return connection.getInputStream();
    }

    public InputStream getErrStream() throws IOException {
        return connection.getInputStream();
    }
}
