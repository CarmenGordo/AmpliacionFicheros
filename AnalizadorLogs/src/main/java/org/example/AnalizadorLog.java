package org.example;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnalizadorLog {

    // Método para analizar el archivo de logs y generar estadísticas
    public void analizarLogs(String archivoLog, String archivoInforme) {
        Map<String, Long> ocurrenciasPorNivel = new HashMap<>();
        List<String> errores = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(archivoLog))) {
            Stream<String> lineas = br.lines();

            // Contar ocurrencias de cada nivel de log
            ocurrenciasPorNivel = lineas
                    .map(linea -> linea.split(",")[1].trim()) // Obtener el nivel de log
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            // Reiniciar el BufferedReader para procesar de nuevo el archivo
            br.close();

            // Leer el archivo otra vez para encontrar los errores
            try (BufferedReader br2 = Files.newBufferedReader(Paths.get(archivoLog))) {
                lineas = br2.lines();
                errores = lineas
                        .filter(linea -> linea.contains("ERROR")) // Filtrar solo las líneas con errores
                        .map(linea -> linea.split(",")[2].trim()) // Obtener el mensaje del error
                        .collect(Collectors.toList());
            }

            // Identificar los 5 errores más comunes
            Map<String, Long> erroresComunes = errores.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            List<Map.Entry<String, Long>> erroresMasComunes = erroresComunes.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .limit(5) // Solo los 5 más comunes
                    .collect(Collectors.toList());

            // Generar el informe
            generarInforme(archivoInforme, ocurrenciasPorNivel, erroresMasComunes);

        } catch (IOException e) {
            System.err.println("Error al leer el archivo de logs: " + e.getMessage());
        }
    }

    // Método para generar el informe de estadísticas
    private void generarInforme(String archivoInforme, Map<String, Long> ocurrenciasPorNivel, List<Map.Entry<String, Long>> erroresMasComunes) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(archivoInforme))) {
            writer.write("---- Estadísticas del archivo de logs ----\n\n");

            // Escribir las ocurrencias de cada nivel de log
            writer.write("Ocurrencias por nivel de log:\n");
            for (Map.Entry<String, Long> entry : ocurrenciasPorNivel.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }

            writer.write("\nLos 5 errores más comunes:\n");
            for (Map.Entry<String, Long> entry : erroresMasComunes) {
                writer.write(entry.getKey() + " - " + entry.getValue() + " ocurrencias\n");
            }

            writer.write("\nInforme generado exitosamente.");
            System.out.println("Informe generado: " + archivoInforme);
        } catch (IOException e) {
            System.err.println("Error al escribir el informe: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        AnalizadorLog analizador = new AnalizadorLog();
        analizador.analizarLogs("logs.txt", "informe.txt");
    }
}
