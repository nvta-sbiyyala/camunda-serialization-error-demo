package com.example.serialization;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

class Foo implements Serializable {
    private String text;
    private int number;
    private String nullable;
    private List<String> items;

    Foo(String text, int number, String nullable, List<String> items) {
        this.text = text;
        this.number = number;
        this.nullable = nullable;
        this.items = items;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Foo foo = (Foo) o;
        return getNumber() == foo.getNumber() && Objects.equals(getText(), foo.getText()) && Objects.equals(getNullable(), foo.getNullable()) && Objects.equals(getItems(), foo.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText(), getNumber(), getNullable(), getItems());
    }
}
