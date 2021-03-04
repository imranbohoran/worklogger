package com.tib.worklogger.clients.cli;

import com.beust.jcommander.JCommander;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tib.worklogger.contract.NewWorkItemRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TextFileImporter {

    private static final HttpClient httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .connectTimeout(Duration.ofSeconds(10))
        .build();

    private final ObjectMapper objectMapper;

    public TextFileImporter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public static void main(String[] args) throws Exception {
        CLIArgParser workLogImporterArgs = new CLIArgParser();
        JCommander.newBuilder()
            .addObject(workLogImporterArgs)
            .build()
            .parse(args);
        System.out.println("Importing work log items from : " + workLogImporterArgs.fileName);

        new TextFileImporter().importTextFile(workLogImporterArgs.fileName);
    }

    public void importTextFile(String file) throws Exception {
        BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(file));
        bufferedReader.lines()
            .map(this::createNewWorkItemRequestBody)
            .map(this::invokeRequestToCreateWorkItem)
            .forEach(statusCode -> System.out.println("Response status: "+ statusCode));
    }

    private int invokeRequestToCreateWorkItem(String newWorkItemRequest) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString(newWorkItemRequest))
            .uri(URI.create("http://localhost:9000/worklog/work-item"))
            .header("Content-Type", "application/json")
            .build();

        try {
            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).statusCode();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return HttpURLConnection.HTTP_INTERNAL_ERROR;
        }
    }

    private String createNewWorkItemRequestBody(String line) {
        String[] values = line.split(",");
        CliDurationParser.CliDuration duration = CliDurationParser.parse(values[2]);
        try {
            return objectMapper.writeValueAsString(
                NewWorkItemRequest.Builder.create()
                    .addEventDate(LocalDateTime.parse(values[0], DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                    .addDescription(values[1])
                    .addDuration(duration.duration, duration.unit.toString())
                    .build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could create request body", e);
        }
    }
}
