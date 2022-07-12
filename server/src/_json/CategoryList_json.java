package _json;

import bank.Customer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CategoryList_json {
    public Set<String> categories;

    public CategoryList_json(Set<String> categories) {
        this.categories = categories;
    }

    public CategoryList_json() {
        Set<String> init = new HashSet<>();
        init.add("default");
        categories = init;
    }
}
