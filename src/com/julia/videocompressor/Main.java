package com.julia.videocompressor;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VideoCompressorFrame frame = new VideoCompressorFrame();
            frame.setVisible(true);
        });
    }
}
