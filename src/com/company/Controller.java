package com.company;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Controller {
    private double volume;
    private double height;
    private double diametr;
    private double polutantT;
    private double maxT;
    private double pdk;
    private double Cf;
    private double M;

    public Controller() {

    }

    public Double countCoefD() {
        Double coefVm = countVm();
        Double coefF = countCoefF();
        return 7 * Math.sqrt(coefVm) * (1.0 + 0.28 * Math.pow(coefF, 1.0 / 3));
    }

    public Double countVm() {
        Double valueToPow = volume * countDeltaT() / height;
        return 0.65 * Math.pow(valueToPow, 1.0 / 3.0);
    }

    public Double countCoefF() {
        Double powSpeed = Math.pow(countAverageSpeed(), 2);
        Double powHeight = Math.pow(height, 2);
        return 1000.0 * (powSpeed * diametr) / (powHeight * countDeltaT());
    }

    public Double countCoefm() {
        Double coefF = countCoefF();
        return 1.0 / (0.67 + 0.1 * Math.sqrt(coefF) + 0.34 * Math.pow(coefF, 1.0 / 3.0));
    }

    public Double countAverageSpeed() {
        return (4.0 * volume) / (Math.PI * Math.pow(diametr, 2));
    }

    public Double countDeltaT() {
        return polutantT - maxT;
    }

    public ArrayList<Object> countResults() {
        ArrayList<Object> results = new ArrayList<>();
        results.add(countCm());
        results.add(countXm());
        results.add(countUm());
        results.add(countGDV());
        results.add(countCm() > countGDV());
        return results;
    }

    public Double countGDV() {
        Double m = countCoefm();
        Double deltaT = countDeltaT();
        return (pdk - Cf) * Math.pow(height, 2) / 200 * 1 * m * 1 * 1.0 * Math.pow(volume * deltaT, 1.0 / 3);
    }

    public Double countUm() {
        Double f = countCoefF();
        return countVm() * (1 + 0.12 * Math.sqrt(f));
    }

    public Double countXm() {
        Double d = countCoefD();
        return (5.0 - 1 / 4.0) * height * d;
    }

    public Double countCm() {
        Double valueToPow = volume * countDeltaT();
        return (200 * M * 1 * countCoefm() * 1 * 1) / (Math.pow(height, 2) * Math.pow(valueToPow, 1.0 / 3));
    }

    public void saveToCSV(JTable dataTable) {
        JFileChooser jFileChooser = new JFileChooser();
        int res = jFileChooser.showDialog(null, "Save file");
        if (res == JFileChooser.APPROVE_OPTION) {
            try (OutputStreamWriter excel =
                         new OutputStreamWriter(new FileOutputStream(jFileChooser.getSelectedFile()), StandardCharsets.UTF_8)) {
                jFileChooser.setSelectedFile(jFileChooser.getSelectedFile());
                TableModel model = dataTable.getModel();

                for (int i = 0; i < model.getColumnCount(); i++) {
                    excel.write(model.getColumnName(i) + "\t");
                }
                excel.write("\n");

                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        excel.write(model.getValueAt(i, j).toString() + "\t");
                    }
                    excel.write("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setDiametr(double diametr) {
        this.diametr = diametr;
    }

    public void setPolutantT(double polutantT) {
        this.polutantT = polutantT;
    }

    public void setMaxT(double maxT) {
        this.maxT = maxT;
    }

    public void setPdk(double pdk) {
        this.pdk = pdk;
    }

    public void setCf(double cf) {
        Cf = cf;
    }

    public void setM(double m) {
        M = m;
    }
}
