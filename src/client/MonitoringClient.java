package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import models.PatientDevice;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.ComparePatients;

/**
 * Client de monitoramento.
 *
 * @author Allan Capistrano
 */
public class MonitoringClient {

    /**
     * Requisita para o servidor a lista completa dos pacientes.
     *
     * @param conn Socket - Conexão com o servidor.
     * @return ArrayList<PatientDevice> - Lista dos dispositivos dos pacientes.
     */
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

            /* Somente se o status da resposta for OK (200). */
            if ((int) response.get("statusCode") == 200) {
                JSONArray jsonArray = response.getJSONArray("data");

                ArrayList<PatientDevice> patientDevices = new ArrayList<>();

                /* Adicionando os dispositivos dos pacientes em uma lista.*/
                for (int i = 0; i < jsonArray.length(); i++) {
                    PatientDevice temp = new PatientDevice(
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
                    );
                    patientDevices.add(temp);
                }
                output.close();

                Collections.sort(patientDevices, new ComparePatients());

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
