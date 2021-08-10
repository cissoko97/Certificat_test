package org.ck.TestCallRest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws MalformedURLException {
        URL certificat = Main.class.getClassLoader().getResource("certificat/NPPO-CMR.cer");
        URL keystore = Main.class.getClassLoader().getResource("certificat/NPPO.keystore");

        String KEYSTORE_SERVER_PASSWORD = "GUCE@ONPV";

        // Configure the stores with certificates
        // true for self-signed certificates, false in production
        System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");

        // Trusted certificates, IPPC HUB certificate should be here
        System.out.println(certificat.getPath());
        System.setProperty("javax.net.ssl.trustStore", certificat.getPath());
        System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_SERVER_PASSWORD);


        // Private Key store, with NPPO certificate
        System.setProperty("javax.net.ssl.keyStore", keystore.getPath());
        System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_SERVER_PASSWORD);
        //System.setProperty("javax.net.ssl.keyStoreType","PKCS12");


        // Uncomment next line to have handshake debug information
        System.setProperty("javax.net.debug", "all");
        try {
            URL url = new URL("https://uat-hub.ephytoexchange.org/hub/DeliveryService");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            int statusCode = con.getResponseCode();

            System.out.println("Status code de la requête " + statusCode);
            /*BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println("Body de la reponse " + content);*/
            con.disconnect();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
