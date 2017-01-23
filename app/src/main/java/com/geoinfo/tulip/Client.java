package com.geoinfo.tulip;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by mao on 1/22/17.
 */

public class Client  extends AsyncTask<Void, Void, Void> {

    String dstAddress;
    int dstPort;
    ServiceReq serviceReq;

    Client(String addr, int port, ServiceReq sreq) {
        dstAddress = addr;
        dstPort = port;
        serviceReq = sreq;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                    1024);
            byte[] buffer = new byte[1024];

            int bytesRead;
            InputStream inputStream = socket.getInputStream();
            PrintWriter out =
                    new PrintWriter(socket.getOutputStream(), true);

         /*
             * notice: inputStream.read() will block if no data return
          */
            serviceReq.serverResp = "";
            serviceReq.serverSuccess = false;
            out.println(serviceReq.clientReq);
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                serviceReq.serverResp += byteArrayOutputStream.toString("UTF-8");
            }
            serviceReq.serverSuccess = true;

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            serviceReq.serverResp = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            serviceReq.serverResp = "IOException: " + e.toString();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }

}
