package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.*;
import java.util.Vector;

public class Form extends JDialog {
    private JPanel contentPane;
    private JButton buttonCount;
    private JButton buttonExit;
    private JTable incomingData1;
    private JTable incomingData2;
    private JTable outData;
    private static int tablesCount;

    public Form() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCount);

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

        incomingData1.setModel(tuneIncomingDataTables());
        incomingData2.setModel(tuneIncomingDataTables());
        outData.setModel(tuneOutDataTable());
    }

    private TableModel tuneOutDataTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(getColumnsHeaders());
        for (int i = 0; i < 5; i++) {
            model.addRow(getRows(tablesCount, i));
        }
        return model;
    }

    private DefaultTableModel tuneIncomingDataTables() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(getColumnsHeaders());
        for (int i = 0; i < 10; i++) {
            model.addRow(getRows(tablesCount, i));
        }
        tablesCount++;
        return model;
    }

    private Vector getRows(int tablesCount, int rowId) {
        Vector rowData = new Vector(3);
        if (tablesCount == 0) {
            switch (rowId) {
                case 0:
                    generateRowElement(rowData,"Назва забруднюючої речовини", "-", "Попіл");
                    break;
                case 1:
                    generateRowElement(rowData,"Час роботи установки", "годин/рік", 306.0);
                    break;
                case 2:
                    generateRowElement(rowData,"Висота димової труби", "м", 35.0);
                    break;
                case 3:
                    generateRowElement(rowData,"Діаметр гирла труби", "м", 1.6);
                    break;
                case 4:
                    generateRowElement(rowData,"Об’єм викидів забруднюючої речовини", "м3/с", 11.3);
                    break;
                case 5:
                    generateRowElement(rowData,"Валовий викид забруднюючої речовини", "г/с", 3.4);
                    break;
                case 6:
                    generateRowElement(rowData,"Температура викиду", "С", 125.0);
                    break;
                case 7:
                    generateRowElement(rowData,"Температура найжаркішого місяця року", "С", 19.1);
                    break;
                case 8:
                    generateRowElement(rowData,"Фонова концентрація", "мг/м3", 0.0);
                    break;
                case 9:
                    generateRowElement(rowData,"Гранично допустима концентрація максимально разова (ГДКм.р.)", "мг/м3", 0.05);
                    break;
            }
        } else if (tablesCount == 1) {
            switch (rowId) {
                case 0:
                    generateRowElement(rowData,"Різниця температур газів, що викидаються", "С", 0.0);
                    break;
                case 1:
                    generateRowElement(rowData,"Середня швидкість виходу газів з джерела викидів", "м/с", 0.0);
                    break;
                case 2:
                    generateRowElement(rowData,"Коефіцієнт, який враховує вплив рельєфу", "-", 1.0);
                    break;
                case 3:
                    generateRowElement(rowData,"Кліматичний коефіцієнт А", "-", 180.0);
                    break;
                case 4:
                    generateRowElement(rowData,"Коефіцієнт F", "-", 1.0);
                    break;
                case 5:
                    generateRowElement(rowData,"Коефіцієнт m", "-", 0.0);
                    break;
                case 6:
                    generateRowElement(rowData,"Коефіцієнт n", "-", 1.0);
                    break;
                case 7:
                    generateRowElement(rowData,"Конструктивні параметри для розрахунку (f)", "-", 0.0);
                    break;
                case 8:
                    generateRowElement(rowData,"Конструктивні параметри для розрахунку (Vm)", "-", 0.0);
                    break;
                case 9:
                    generateRowElement(rowData,"Коефіцієнт d", "-", 0.0);
                    break;
            }
        } else if (tablesCount == 2) {
            switch (rowId) {
                case 0:
                    generateRowElement(rowData,"Величина максимального забруднення См забруднюючої речовини приземного шару атмосфери", "мг/м3", 0.0);
                    break;
                case 1:
                    generateRowElement(rowData,"Відстань Хм від джерела до координат максимуму концентрації", "м", 0.0);
                    break;
                case 2:
                    generateRowElement(rowData,"Небезпечна швидкість вітру Uм", "м/с", 0.0);
                    break;
                case 3:
                    generateRowElement(rowData,"Гранично допустимий викид в атмосферу (ГДВ)", "г/с", 0.0);
                    break;
                case 4:
                    generateRowElement(rowData,"Порівняння См з ГДКм.р.", "-", false);
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
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
