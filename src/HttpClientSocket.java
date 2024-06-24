import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.Arrays;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


// Usamos uma camada de abstração http no soquete para facilitar a transferência dos dados
// Assim, podemos nos aproveitar do formato JSON para transmitir os dados já codificados
public class HttpClientSocket {

    // Defines que indicam o endereço do servidor
    private String servidor;
    private int porta;

    public HttpClientSocket(String servidor, int porta) {
        this.servidor = servidor;
        this.porta = porta;
    }

    // Essa função envia um GET request para o nosso servidor e salva o retorno json dela para 
    // Tratarmos adequadamente, usamos apenas um tipo de request, mas outras poderiam ser utilizadas 
    // como POST ou DELETE, mas queriamos manter o código simples e compacto
    public JsonNode enviarRequest(String caminho, Map<String, List<String>> parametros) {
        JsonNode respostaJson = null;
        try {
            // Construir a URL com os parâmetros
            StringBuilder queryParams = new StringBuilder();
            Set<Map.Entry<String, List<String>>> entrySet = parametros.entrySet();
            for (Map.Entry<String, List<String>> entry : entrySet) {
                String chave = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString());
                for (String valor : entry.getValue()) {
                    if (queryParams.length() > 0) {
                        queryParams.append("&");
                    }
                    queryParams.append(chave)
                               .append("=")
                               .append(URLEncoder.encode(valor, StandardCharsets.UTF_8.toString()));
                }
            }
            String fullPath = caminho + "?" + queryParams.toString();

            // Conectar ao servidor
            try (Socket socket = new Socket(servidor, porta);
                 OutputStream output = socket.getOutputStream();
                 PrintWriter writer = new PrintWriter(output, true);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                // Enviar a requisição HTTP GET
                writer.println("GET " + fullPath + " HTTP/1.1");
                writer.println("Host: " + servidor);
                writer.println("Connection: close");
                writer.println(); // Linha em branco para indicar o fim do cabeçalho

                // Receber a resposta do servidor
                StringBuilder respostaBuilder = new StringBuilder();
                String linha;
                boolean isBody = false;
                while ((linha = reader.readLine()) != null) {
                    if (isBody) {
                        respostaBuilder.append(linha).append("\n");
                    }
                    if (linha.isEmpty()) { // Linha em branco indica o fim dos cabeçalhos HTTP
                        isBody = true;
                    }
                }
                String resposta = respostaBuilder.toString();
                System.out.println("Resposta do servidor: \n" + resposta);

                // Parse da resposta JSON
                ObjectMapper objectMapper = new ObjectMapper();
                respostaJson = objectMapper.readTree(resposta);

            }

        } catch (UnknownHostException e) {
            System.err.println("Servidor desconhecido: " + servidor);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro de I/O ao conectar com o servidor: " + servidor);
            e.printStackTrace();
        }
        return respostaJson;
    }
}
