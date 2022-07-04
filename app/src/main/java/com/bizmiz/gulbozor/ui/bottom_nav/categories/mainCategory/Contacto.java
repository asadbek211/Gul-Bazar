package com.bizmiz.gulbozor.ui.bottom_nav.categories.mainCategory;

public class Contacto {
    private String txtCategory;

    private String childRaw1;
    private String childRaw2;
    private String childRaw3;

    public Contacto(String txtCategory, String childRaw1, String childRaw2, String childRaw3) {
        this.txtCategory = txtCategory;
        this.childRaw1 = childRaw1;
        this.childRaw2 = childRaw2;
        this.childRaw3 = childRaw3;
    }

    public String getChildRaw1() {
        return childRaw1;
    }

    public void setChildRaw1(String childRaw1) {
        this.childRaw1 = childRaw1;
    }

    public String getChildRaw2() {
        return childRaw2;
    }

    public void setChildRaw2(String childRaw2) {
        this.childRaw2 = childRaw2;
    }

    public String getChildRaw3() {
        return childRaw3;
    }

    public void setChildRaw3(String childRaw3) {
        this.childRaw3 = childRaw3;
    }

    public String getTxtCategory() {
        return txtCategory;
    }

    public void setTxtCategory(String txtCategory) {
        this.txtCategory = txtCategory;
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "txtCategory='" + txtCategory + '\'' +
                '}';
    }
}
