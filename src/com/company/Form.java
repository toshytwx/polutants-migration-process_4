package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

public class Form extends JDialog {
    private JPanel contentPane;
    private JButton buttonCount;
    private JButton buttonExit;
    private JTable incomingData1;
    private JTable incomingData2;
    private JTable outData;
    private JButton buttonSave;
    private static int tablesCount;
    private Controller controller;

    public Form(Controller controller) {
        setTitle("© 2018 TM-51 Antonkin Dmytro All Rights Reserved");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCount);
        this.controller = controller;


        tuneActionListeners();
        incomingData1.setModel(tuneIncomingDataTables());
        tuneControllerVariables(controller);
        incomingData2.setModel(tuneIncomingDataTables());
        outData.setModel(tuneOutDataTable(fillDataVectors()));
    }

    private void tuneControllerVariables(Controller controller) {
        controller.setMaxT((Double) incomingData1.getModel().getValueAt(7, 2));
        controller.setPolutantT((Double) incomingData1.getModel().getValueAt(6, 2));
        controller.setVolume((Double) incomingData1.getModel().getValueAt(4, 2));
        controller.setDiametr((Double) incomingData1.getModel().getValueAt(3, 2));
        controller.setHeight((Double) incomingData1.getModel().getValueAt(2, 2));
        controller.setCf((Double) incomingData1.getModel().getValueAt(8, 2));
        controller.setM((Double) incomingData1.getModel().getValueAt(5, 2));
        controller.setPdk((Double) incomingData1.getModel().getValueAt(9, 2));
    }

    private void tuneActionListeners() {
        buttonCount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCount();
            }
        });

        buttonExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveToCSV(outData);
            }
        });
    }

    private TableModel tuneOutDataTable(ArrayList<Object> objects) {
        DefaultTableModel model = new DefaultTableModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(getColumnsHeaders());
        for (int i = 0; i < 5; i++) {
            model.addRow(getRows(tablesCount, i, objects));
        }
        return model;
    }

    private DefaultTableModel tuneIncomingDataTables() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(getColumnsHeaders());
        for (int i = 0; i < 10; i++) {
            model.addRow(getRows(tablesCount, i, fillDataVectors()));
        }
        tablesCount++;
        return model;
    }

    private ArrayList<Object> fillDataVectors() {
        ArrayList<Object> dataVector = new ArrayList<>();
        switch (tablesCount) {
            case 0: {
                dataVector.add("Попіл");
                dataVector.add(306.0);
                dataVector.add(35.0);
                dataVector.add(1.6);
                dataVector.add(11.3);
                dataVector.add(3.4);
                dataVector.add(125.0);
                dataVector.add(19.1);
                dataVector.add(0.0);
                dataVector.add(0.05);
                break;
            }
            case 1: {
                dataVector.add(controller.countDeltaT());
                dataVector.add(controller.countAverageSpeed());
                dataVector.add(1.0);
                dataVector.add(180.0);
                dataVector.add(1.0);
                Double coeff = controller.countCoefF();
                dataVector.add(controller.countCoefm());
                dataVector.add(1.0);
                dataVector.add(coeff);
                dataVector.add(controller.countVm());
                dataVector.add(controller.countCoefD());
                break;
            }
            case 2: {
                dataVector.add(0.0);
                dataVector.add(0.0);
                dataVector.add(0.0);
                dataVector.add(0.0);
                dataVector.add(false);
                break;
            }
        }
        return dataVector;
    }

    private Vector getRows(int tablesCount, int rowId, ArrayList<Object> dataVector) {
        Vector rowData = new Vector(3);
        if (tablesCount == 0) {
            switch (rowId) {
                case 0:
                    generateRowElement(rowData,"Назва забруднюючої речовини", "-", dataVector.get(rowId));
                    break;
                case 1:
                    generateRowElement(rowData,"Час роботи установки", "годин/рік", dataVector.get(rowId));
                    break;
                case 2:
                    generateRowElement(rowData,"Висота димової труби", "м", dataVector.get(rowId));
                    break;
                case 3:
                    generateRowElement(rowData,"Діаметр гирла труби", "м", dataVector.get(rowId));
                    break;
                case 4:
                    generateRowElement(rowData,"Об’єм викидів забруднюючої речовини", "м3/с", dataVector.get(rowId));
                    break;
                case 5:
                    generateRowElement(rowData,"Валовий викид забруднюючої речовини", "г/с", dataVector.get(rowId));
                    break;
                case 6:
                    generateRowElement(rowData,"Температура викиду", "С", dataVector.get(rowId));
                    break;
                case 7:
                    generateRowElement(rowData,"Температура найжаркішого місяця року", "С", dataVector.get(rowId));
                    break;
                case 8:
                    generateRowElement(rowData,"Фонова концентрація", "мг/м3", dataVector.get(rowId));
                    break;
                case 9:
                    generateRowElement(rowData,"Гранично допустима концентрація максимально разова (ГДКм.р.)", "мг/м3", dataVector.get(rowId));
                    break;
            }
        } else if (tablesCount == 1) {
            switch (rowId) {
                case 0:
                    generateRowElement(rowData,"Різниця температур газів, що викидаються", "С", dataVector.get(rowId));
                    break;
                case 1:
                    generateRowElement(rowData,"Середня швидкість виходу газів з джерела викидів", "м/с", dataVector.get(rowId));
                    break;
                case 2:
                    generateRowElement(rowData,"Коефіцієнт, який враховує вплив рельєфу", "-", dataVector.get(rowId));
                    break;
                case 3:
                    generateRowElement(rowData,"Кліматичний коефіцієнт А", "-", dataVector.get(rowId));
                    break;
                case 4:
                    generateRowElement(rowData,"Коефіцієнт F", "-", dataVector.get(rowId));
                    break;
                case 5:
                    generateRowElement(rowData,"Коефіцієнт m", "-", dataVector.get(rowId));
                    break;
                case 6:
                    generateRowElement(rowData,"Коефіцієнт n", "-", dataVector.get(rowId));
                    break;
                case 7:
                    generateRowElement(rowData,"Конструктивні параметри для розрахунку (f)", "-", dataVector.get(rowId));
                    break;
                case 8:
                    generateRowElement(rowData,"Конструктивні параметри для розрахунку (Vm)", "-", dataVector.get(rowId));
                    break;
                case 9:
                    generateRowElement(rowData,"Коефіцієнт d", "-", dataVector.get(rowId));
                    break;
            }
        } else if (tablesCount == 2) {
            switch (rowId) {
                case 0:
                    generateRowElement(rowData,"Величина максимального забруднення См забруднюючої речовини приземного шару атмосфери", "мг/м3", dataVector.get(rowId));
                    break;
                case 1:
                    generateRowElement(rowData,"Відстань Хм від джерела до координат максимуму концентрації", "м", dataVector.get(rowId));
                    break;
                case 2:
                    generateRowElement(rowData,"Небезпечна швидкість вітру Uм", "м/с", dataVector.get(rowId));
                    break;
                case 3:
                    generateRowElement(rowData,"Гранично допустимий викид в атмосферу (ГДВ)", "г/с", dataVector.get(rowId));
                    break;
                case 4:
                    generateRowElement(rowData,"Порівняння См з ГДКм.р.", "-", dataVector.get(rowId));
                    break;
            }
        }
        return rowData;
    }

    private void generateRowElement(Vector row, String paramName, String measurementUnit, Object value) {
        row.addElement(paramName);
        row.addElement(measurementUnit);
        row.addElement(value);
    }

    private Vector getColumnsHeaders() {
        Vector colHdrs = new Vector(3);
        colHdrs.addElement("Параметри");
        colHdrs.addElement("Одиниці вимірювання");
        colHdrs.addElement("Значення");
        return colHdrs;
    }

    private void onCount() {
        outData.setModel(tuneOutDataTable(controller.countResults()));
        JOptionPane.showMessageDialog(this, "За год работы котельной ПДВ составит: " + controller.countGDV() * 3600 * 5760.0 / 1000000 + " т/год", "Result", JOptionPane.PLAIN_MESSAGE);
    }

    private void onCancel() {
        dispose();
    }
}
