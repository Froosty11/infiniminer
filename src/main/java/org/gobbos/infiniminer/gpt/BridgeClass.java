package org.gobbos.infiniminer.gpt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BridgeClass {
    public void BridgeClass() {
        //connect and confirm with
        System.out.println("BridgeClass bridged");
    }
    public void craftItems(String item1, String item2) {
        //craft items

    }
    public String makePostRequest(String v1, String v2) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL("https://api-inference.huggingface.co/models/meta-llama/Meta-Llama-3-8B-Instruct"); // replace with your URL
            conn = (HttpURLConnection) url.openConnection();

            // Set the request method to POST
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer ");

            // Enable input and output streams
            conn.setDoOutput(true);
            //I am making a Minecraft mod. This mod will allow you to give me the result of merging two items together. Examples: WATER + FIRE RESULT: STEAM // STEAM + WOODEN_PICKAXE RESULT: MOIST_PICKAXE. Now, I need you to give me the result of merging two items together. I want you to merge "OBSIDIAN" and "PICKAXE" together. Return the result in quotation marks.
            // Write the body of the POST request
            String jsonInputString = "{\"inputs\": \"<|begin_of_text|><|start_header_id|>user<|end_header_id|>I am making a Minecraft mod. This mod will allow you to give me the result of merging two items together. Examples: WATER + FIRE RESULT: STEAM // STEAM + WOODEN_PICKAXE RESULT: MOIST_PICKAXE. Now, I need you to give me the result of merging two items together. Return ONLY the following format: INPUT: \\\"" + v1 + "\\\" + \\\"" + v2 +"\\\"<|eot_id|><|start_header_id|>assistant<|end_header_id|>\"}";
            //String jsonInputString = "{\"inputs\": \"I am making a Minecraft mod. This mod will allow you to give me the result of merging two items together. Examples: WATER + FIRE RESULT: STEAM // STEAM + WOODEN_PICKAXE RESULT: MOIST_PICKAXE. Now, I need you to give me the result of merging two items together. Return ONLY the following format: INPUT: \\\"OBSIDIAN\\\" + \\\"PICKAXE\\\" \"}";
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response code
            int responseCode = conn.getResponseCode();

            System.out.println("Response code: " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder strB = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                strB.append(inputLine);
            }
            return strB.toString();

        } catch (Exception e) {
            e.printStackTrace();
            assert conn != null;
            try {
                if (conn.getResponseCode() > 400) {
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    String inputLine;
                    StringBuilder errorContent = new StringBuilder();
                    while ((inputLine = errorReader.readLine()) != null) {
                        errorContent.append(inputLine);
                    }
                    errorReader.close();
                    System.out.println("Error content: " + errorContent.toString());
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return "Error in making POST request";
        }
    }
}
