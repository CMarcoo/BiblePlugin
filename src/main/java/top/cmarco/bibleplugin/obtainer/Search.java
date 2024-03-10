package top.cmarco.bibleplugin.obtainer;

public abstract class Search {
    protected final String search;

    public Search(String search) {
        this.search = search;
    }

    public String getSearch() {
        return search;
    }


}
