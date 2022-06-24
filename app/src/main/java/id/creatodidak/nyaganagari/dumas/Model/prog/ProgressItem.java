package id.creatodidak.nyaganagari.dumas.Model.prog;

public class ProgressItem {

    private final String timeline;
    private final String jam;
    private final String tanggal;
    private final String handler;

    public ProgressItem(String timeline, String jam, String tanggal, String handler) {
        this.timeline = timeline;
        this.jam = jam;
        this.tanggal = tanggal;
        this.handler = handler;
    }

    public String getTimeline() {
        return timeline;
    }

    public String getJam() {
        return jam;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getHandler() {
        return handler;
    }
}