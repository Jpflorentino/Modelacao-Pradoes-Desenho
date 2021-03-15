package org.isel.boardstar.html;

public class Select extends Element {
    public Select(String name, Element... childs) {
        super("select", childs);
        addAtribute("name", name);
    }
}
