public class Batch {
    private String batchId;
    private String batchName;
    private String teacherId;

    public Batch(String batchId, String batchName, String teacherId) {
        this.batchId = batchId;
        this.batchName = batchName;
        this.teacherId = teacherId;
    }

    public String getBatchId() {
        return batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public String getTeacherId() {
        return teacherId;
    }
}