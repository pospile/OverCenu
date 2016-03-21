package cz.pospichal.overcenu.Data;

public class PopularData {
    private String mFirm;
    private String mAdress;
    private String[] mUrls;
    private int mPrice;
    private int mFirm_id;

    public PopularData (String text1, String text2, String[] urls, int price, int firm_id){
        mFirm = text1;
        mAdress = text2;
        mUrls = urls;
        mPrice = price;
        mFirm_id = firm_id;
    }

    public int getmFirm_id(){
        return mFirm_id;
    }

    public int getmPrice(){
        return mPrice;
    }

    public String getUrl(int position){
        return mUrls[position];
    }

    public String getmFirm() {
        return mFirm;
    }

    public void setmFirm(String mFirm) {
        this.mFirm = mFirm;
    }

    public String getmAdress() {
        return mAdress;
    }

    public void setmAdress(String mAdress) {
        this.mAdress = mAdress;
    }
}
