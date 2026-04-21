package com.julia.videocompressor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.nio.file.Path;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

public class VideoCompressorFrame extends JFrame {
    private final JTextField ffmpegField = new JTextField("ffmpeg", 28);
    private final JTextField inputField = new JTextField(28);
    private final JTextField outputField = new JTextField(28);
    private final JComboBox<CompressionPreset> presetCombo =
            new JComboBox<>(CompressionPreset.values());
    private final JTextArea logArea = new JTextArea(16, 54);
    private final JProgressBar progressBar = new JProgressBar();
    private final JButton compressButton = new JButton("Compactar");
    private final JButton browseFfmpegButton = new JButton("Buscar");
    private final JButton browseInputButton = new JButton("Selecionar");
    private final JButton browseOutputButton = new JButton("Destino");

    public VideoCompressorFrame() {
        setTitle("Compactador MP4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(760, 520));

        inputField.setEditable(false);
        outputField.setEditable(false);
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        progressBar.setIndeterminate(false);

        JPanel formPanel = buildFormPanel();
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("Saida do processo"));

        add(formPanel, BorderLayout.NORTH);
        add(logScroll, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        configureBrowseButtons();

        addRow(panel, gbc, 0, "FFmpeg:", ffmpegField, browseFfmpegButton);
        addRow(panel, gbc, 1, "Video MP4:", inputField, browseInputButton);
        addRow(panel, gbc, 2, "Salvar em:", outputField, browseOutputButton);
        addRow(panel, gbc, 3, "Preset:", presetCombo, new JLabel("Leve = melhor qualidade"));

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        panel.add(progressBar, gbc);

        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(compressButton, gbc);

        compressButton.addActionListener(e -> startCompression());

        return panel;
    }

    private void addRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            java.awt.Component field,
            java.awt.Component action) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(field, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        panel.add(action, gbc);
    }

    private void configureBrowseButtons() {
        browseFfmpegButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Selecione o ffmpeg.exe");
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                ffmpegField.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });

        browseInputButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Selecione um video MP4");
            chooser.setFileFilter(new FileNameExtensionFilter("Videos MP4", "mp4"));

            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                Path inputPath = chooser.getSelectedFile().toPath();
                inputField.setText(inputPath.toString());
                outputField.setText(buildSuggestedOutput(inputPath).toString());
            }
        });

        browseOutputButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Escolha onde salvar");
            chooser.setSelectedFile(new java.io.File(outputField.getText().isBlank()
                    ? "video_comprimido.mp4"
                    : outputField.getText()));

            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                outputField.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });
    }

    private Path buildSuggestedOutput(Path inputPath) {
        String fileName = inputPath.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        String baseName = dotIndex > 0 ? fileName.substring(0, dotIndex) : fileName;
        return inputPath.resolveSibling(baseName + "_compactado.mp4");
    }

    private void startCompression() {
        logArea.setText("");
        setFormEnabled(false);
        progressBar.setIndeterminate(true);
        appendLog("Iniciando compactacao...");

        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                FfmpegRunner runner = new FfmpegRunner();
                runner.compress(
                        ffmpegField.getText().trim(),
                        Path.of(inputField.getText().trim()),
                        Path.of(outputField.getText().trim()),
                        (CompressionPreset) presetCombo.getSelectedItem(),
                        this::publish);
                return null;
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                for (String line : chunks) {
                    appendLog(line);
                }
            }

            @Override
            protected void done() {
                progressBar.setIndeterminate(false);
                setFormEnabled(true);

                try {
                    get();
                    appendLog("Compactacao concluida com sucesso.");
                    JOptionPane.showMessageDialog(
                            VideoCompressorFrame.this,
                            "Video compactado com sucesso.",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    String message = extractErrorMessage(ex);
                    appendLog("Erro: " + message);
                    JOptionPane.showMessageDialog(
                            VideoCompressorFrame.this,
                            message,
                            "Erro na compactacao",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        worker.execute();
    }

    private void appendLog(String message) {
        logArea.append(message + System.lineSeparator());
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private void setFormEnabled(boolean enabled) {
        ffmpegField.setEnabled(enabled);
        inputField.setEnabled(enabled);
        outputField.setEnabled(enabled);
        presetCombo.setEnabled(enabled);
        compressButton.setEnabled(enabled);
        browseFfmpegButton.setEnabled(enabled);
        browseInputButton.setEnabled(enabled);
        browseOutputButton.setEnabled(enabled);
    }

    private String extractErrorMessage(Exception ex) {
        Throwable cause = ex.getCause();
        if (cause != null && cause.getMessage() != null && !cause.getMessage().isBlank()) {
            return cause.getMessage();
        }
        if (ex.getMessage() != null && !ex.getMessage().isBlank()) {
            return ex.getMessage();
        }
        return "Erro inesperado durante a compactacao.";
    }
}
