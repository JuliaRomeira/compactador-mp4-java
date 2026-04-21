package com.julia.videocompressor;

public enum CompressionPreset {
    LEVE("Leve", 28, "fast"),
    MEDIO("Medio", 32, "medium"),
    FORTE("Forte", 36, "slow");

    private final String label;
    private final int crf;
    private final String speed;

    CompressionPreset(String label, int crf, String speed) {
        this.label = label;
        this.crf = crf;
        this.speed = speed;
    }

    public int getCrf() {
        return crf;
    }

    public String getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return label + " (CRF " + crf + ")";
    }
}
