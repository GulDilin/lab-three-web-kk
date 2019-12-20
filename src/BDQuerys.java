public enum BDQuerys {
    GET_ALL("SELECT * FROM dots"),
    DELETE_DOTS("DELETE FROM dots");

    private String textQuery;
    private BDQuerys(String textQuery){
        this.textQuery = textQuery;
    }

    public String getTextQuery() {
        return textQuery;
    }
}
