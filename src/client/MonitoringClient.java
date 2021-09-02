package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import models.PatientDevice;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Allan Capistrano
 */
public class MonitoringClient {

    public static ArrayList<PatientDevice> requestPatients(Socket conn) {
        JSONObject json = new JSONObject();

        /* Definindo os dados que serão alterados. */
        json.put("method", "GET"); // Método HTTP
        json.put("route", "/patients"); // Rota

        try {
            ObjectOutputStream output
                    = new ObjectOutputStream(conn.getOutputStream());

            /* Enviando a requisição para o servidor. */
            output.writeObject(json);

            ObjectInputStream input = new ObjectInputStream(conn.getInputStream());

            /* Recebendo a lista de dispositivos dos pacientes no formato 
            JSON. */
            JSONObject response = (JSONObject) input.readObject();

            if ((int) response.get("statusCode") == 200) {
                JSONArray jsonArray = response.getJSONArray("data");

                ArrayList<PatientDevice> patientDevices = new ArrayList<>();

                /* Adicionando os pacientes dispositivos dos pacientes em uma 
                lista.*/
                for (int i = 0; i < jsonArray.length(); i++) {
                    patientDevices.add(
                            new PatientDevice(
                                    jsonArray.getJSONObject(i).
                                            getString("name"),
                                    jsonArray.getJSONObject(i).
                                            getFloat("bodyTemperatureSensor"),
                                    jsonArray.getJSONObject(i).
                                            getInt("respiratoryFrequencySensor"),
                                    jsonArray.getJSONObject(i).
                                            getFloat("bloodOxygenationSensor"),
                                    jsonArray.getJSONObject(i).
                                            getInt("bloodPressureSensor"),
                                    jsonArray.getJSONObject(i).
                                            getInt("heartRateSensor"),
                                    jsonArray.getJSONObject(i).
                                            getString("deviceId")
                            ));
                }

                // ORDENAR A LISTA DE PACIENTES PELOS GRAVES
                output.close();

                return patientDevices;
            }
        } catch (IOException ioe) {
            System.err.println("Erro ao tentar editar os dados dos sensores.");
            System.out.println(ioe);
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Classe JSONObject não foi econtrada.");
            System.out.println(cnfe);
        }

        return null;
    }
}
