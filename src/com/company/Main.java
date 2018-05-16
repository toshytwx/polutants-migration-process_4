package com.company;

public class Main {

    public static void main(String[] args) {
        Controller controller = new Controller();
        Form dialog = new Form(controller);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
