package deso1.nguyenminhduc.dlu_21a100100097;

public class food {
    private String mafood;
    private String tenfood;
    private double gia;
    private String imageUrl;

    public food(String mafood, String tenfood, double gia, String imageUrl) {
        this.mafood = mafood;
        this.tenfood = tenfood;
        this.gia = gia;
        this.imageUrl = imageUrl;
    }
    public String getMafood() {
        return mafood;
    }
    public void setMafood(String mafood) {
        this.mafood = mafood;
    }
    public String getTenfood() {
        return tenfood;
    }
    public void setTenfood(String tenfood) {
        this.tenfood = tenfood;
        }
    public double getGia() {
        return gia;
    }
    public void setGia(int gia) {
        this.gia = gia;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;

    }
}
