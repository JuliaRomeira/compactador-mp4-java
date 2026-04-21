package com.julia.videocompressor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FfmpegRunner {
    public void compress(
            String ffmpegPath,
            Path inputFile,
            Path outputFile,
            CompressionPreset preset,
            Consumer<String> logConsumer) throws IOException, InterruptedException {

        validate(ffmpegPath, inputFile, outputFile);

        List<String> command = new ArrayList<>();
        command.add(ffmpegPath);
        command.add("-y");
        command.add("-i");
        command.add(inputFile.toString());
        command.add("-vcodec");
        command.add("libx264");
        command.add("-preset");
        command.add(preset.getSpeed());
        command.add("-crf");
        command.add(String.valueOf(preset.getCrf()));
        command.add("-acodec");
        command.add("aac");
        command.add(outputFile.toString());

        logConsumer.accept("Comando: " + String.join(" ", command));

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logConsumer.accept(line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("FFmpeg terminou com codigo " + exitCode + ".");
        }
    }

    private void validate(String ffmpegPath, Path inputFile, Path outputFile) {
        if (ffmpegPath == null || ffmpegPath.isBlank()) {
            throw new IllegalArgumentException("Informe o caminho do FFmpeg.");
        }

        if (inputFile == null || !Files.exists(inputFile)) {
            throw new IllegalArgumentException("Selecione um arquivo MP4 valido.");
        }

        if (outputFile == null) {
            throw new IllegalArgumentException("Informe o arquivo de saida.");
        }

        Path parent = outputFile.getParent();
        if (parent == null || !Files.exists(parent)) {
            throw new IllegalArgumentException("A pasta de saida nao existe.");
        }
    }
}
