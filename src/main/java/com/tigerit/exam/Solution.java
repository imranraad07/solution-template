package com.tigerit.exam;

import static com.tigerit.exam.IO.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * All of your application logic should be placed inside this class. Remember we
 * will load your application from our custom container. You may add private
 * method inside this class but, make sure your application's execution points
 * start from inside run method.
 */
class Table {

    private String name;
    private LinkedHashMap<String, Integer> colums;
    private int numOfRow;
    private int numOfCol;
    private int data[][];

    public int getColumId(String name) {
        return colums.get(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedHashMap<String, Integer> getColums() {
        return colums;
    }

    public void setColums(LinkedHashMap<String, Integer> colums) {
        this.colums = colums;
    }

    public int getNumOfRow() {
        return numOfRow;
    }

    public void setNumOfRow(int numOfRow) {
        this.numOfRow = numOfRow;
    }

    public int getNumOfCol() {
        return numOfCol;
    }

    public void setNumOfCol(int numOfCol) {
        this.numOfCol = numOfCol;
    }

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
    }
}

class Tuple {

    private int x;
    private int y;

    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public Tuple() {
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

class JoinQuery {

    private ArrayList<ArrayList<Integer>> resultSet;
    private ArrayList<String> columSet;
    private String tableName1;
    private String tableName2;
    private int tableId1;
    private int tableId2;
    private int columnId1;
    private int columnId2;
    private String queryName1;
    private String queryName2;

    public JoinQuery() {
        resultSet = new ArrayList<>();
        columSet = new ArrayList<>();
    }

    public String getQueryName1() {
        return queryName1;
    }

    public void setQueryName1(String queryName1) {
        this.queryName1 = queryName1;
    }

    public String getQueryName2() {
        return queryName2;
    }

    public void setQueryName2(String queryName2) {
        this.queryName2 = queryName2;
    }

    public String getTableName1() {
        return tableName1;
    }

    public void setTableName1(String tableName1) {
        this.tableName1 = tableName1;
    }

    public String getTableName2() {
        return tableName2;
    }

    public void setTableName2(String tableName2) {
        this.tableName2 = tableName2;
    }

    public ArrayList<ArrayList<Integer>> getResultSet() {
        return resultSet;
    }

    public void setResultSet(ArrayList<ArrayList<Integer>> resultSet) {
        this.resultSet = resultSet;
    }

    public ArrayList<String> getColumSet() {
        return columSet;
    }

    public void setColumSet(ArrayList<String> columSet) {
        this.columSet = columSet;
    }

    public int getTableId1() {
        return tableId1;
    }

    public void setTableId1(int tableId1) {
        this.tableId1 = tableId1;
    }

    public int getTableId2() {
        return tableId2;
    }

    public void setTableId2(int tableId2) {
        this.tableId2 = tableId2;
    }

    public int getColumnId1() {
        return columnId1;
    }

    public void setColumnId1(int columnId1) {
        this.columnId1 = columnId1;
    }

    public int getColumnId2() {
        return columnId2;
    }

    public void setColumnId2(int columnId2) {
        this.columnId2 = columnId2;
    }

}

public class Solution implements Runnable {

    private Table getTable() {
        Table myTable = new Table();

        String temp = readLine();

        myTable.setName(temp);

        String[] inputArray = readLine().split(" ");

        myTable.setNumOfCol(Integer.parseInt(inputArray[0]));
        myTable.setNumOfRow(Integer.parseInt(inputArray[1]));

        inputArray = readLine().split(" ");
        LinkedHashMap<String, Integer> myColumns = new LinkedHashMap<>();
        for (int j = 0; j < inputArray.length; j++) {
            myColumns.put(inputArray[j], j);
        }
        myTable.setColums(myColumns);

        myTable.setData(new int[myTable.getNumOfRow()][myTable.getNumOfCol()]);
        for (int j = 0; j < myTable.getNumOfRow(); j++) {
            inputArray = readLine().split(" ");
            for (int k = 0; k < myTable.getNumOfCol(); k++) {
                myTable.getData()[j][k] = Integer.parseInt(inputArray[k]);
            }
        }
        return myTable;
    }

    @Override
    public void run() {

        int testCases = readLineAsInteger();
        int currentCase = 1;
        while (testCases-- > 0) {
            printLine("Test: " + currentCase);
            currentCase++;

            int numOfTables = readLineAsInteger();

            HashMap<String, Integer> tableName = new HashMap<>();
            Table[] tables = new Table[numOfTables];

            String temp;
            String[] inputArray;

            for (int i = 0; i < numOfTables; i++) {
                tables[i] = getTable();
                tableName.put(tables[i].getName(), i);
            }

            int query = readLineAsInteger();
            String statements[] = new String[4];
            while (query-- > 0) {
                statements[0] = readLine();
                statements[1] = readLine();
                statements[2] = readLine();
                statements[3] = readLine();
                readLine();

                JoinQuery joinQuery = new JoinQuery();
                ArrayList<Tuple> rowColIds = null;
                int queryType = 3;

                inputArray = statements[1].split(" ");
                if (statements[0].equals("SELECT *")) {
                    if (inputArray.length == 2) {
                        queryType = 1;
                    } else {
                        queryType = 2;
                    }
                }

                if (queryType == 1) {
                    joinQuery.setQueryName1(inputArray[1]);
                    joinQuery.setTableName1(inputArray[1]);
                    inputArray = statements[2].split(" ");
                    joinQuery.setQueryName2(inputArray[1]);
                    joinQuery.setTableName2(inputArray[1]);
                } else {
                    joinQuery.setQueryName1(inputArray[2]);
                    joinQuery.setTableName1(inputArray[1]);
                    inputArray = statements[2].split(" ");
                    joinQuery.setQueryName2(inputArray[2]);
                    joinQuery.setTableName2(inputArray[1]);
                }

                joinQuery.setTableId1(tableName.get(joinQuery.getTableName1()));
                joinQuery.setTableId2(tableName.get(joinQuery.getTableName2()));
                inputArray = statements[3].split(" ");
                joinQuery.setColumnId1(tables[joinQuery.getTableId1()].getColums().get(inputArray[1].substring(joinQuery.getQueryName1().length() + 1)));
                joinQuery.setColumnId2(tables[joinQuery.getTableId2()].getColums().get(inputArray[3].substring(joinQuery.getQueryName1().length() + 1)));

                if (queryType == 3) {
                    rowColIds = new ArrayList<>();

                    inputArray = statements[0].split(" ");
                    for (int i = 1; i < inputArray.length; i++) {
                        String colNm;
                        if (inputArray[i].startsWith(joinQuery.getQueryName1() + ".")) {
                            if (i != inputArray.length - 1) {
                                colNm = inputArray[i].substring(joinQuery.getQueryName1().length() + 1, inputArray[i].length() - 1);
                            } else {
                                colNm = inputArray[i].substring(joinQuery.getQueryName1().length() + 1);
                            }
                            joinQuery.getColumSet().add(colNm);
                            rowColIds.add(new Tuple(joinQuery.getTableId1(), tables[joinQuery.getTableId1()].getColumId(colNm)));
                        } else {
                            if (i != inputArray.length - 1) {
                                colNm = inputArray[i].substring(joinQuery.getQueryName2().length() + 1, inputArray[i].length() - 1);
                            } else {
                                colNm = inputArray[i].substring(joinQuery.getQueryName2().length() + 1);
                            }
                            joinQuery.getColumSet().add(colNm);
                            rowColIds.add(new Tuple(joinQuery.getTableId2(), tables[joinQuery.getTableId2()].getColumId(colNm)));
                        }
                    }
                } else {
                    joinQuery.getColumSet().addAll(tables[joinQuery.getTableId1()].getColums().keySet());
                    joinQuery.getColumSet().addAll(tables[joinQuery.getTableId2()].getColums().keySet());
                }

                for (int rowTable1 = 0; rowTable1 < tables[joinQuery.getTableId1()].getNumOfRow(); rowTable1++) {
                    for (int rowTable2 = 0; rowTable2 < tables[joinQuery.getTableId2()].getNumOfRow(); rowTable2++) {
                        if (tables[joinQuery.getTableId1()].getData()[rowTable1][joinQuery.getColumnId1()] == tables[joinQuery.getTableId2()].getData()[rowTable2][joinQuery.getColumnId2()]) {
                            ArrayList<Integer> matches = new ArrayList<>();

                            if (queryType == 3) {
                                if (rowColIds != null) {
                                    for (Tuple entry : rowColIds) {
                                        if (entry.getX() == joinQuery.getTableId1()) {
                                            matches.add(tables[joinQuery.getTableId1()].getData()[rowTable1][entry.getY()]);
                                        } else {
                                            matches.add(tables[joinQuery.getTableId2()].getData()[rowTable2][entry.getY()]);
                                        }
                                    }
                                }
                            } else {
                                for (int colTable1 = 0; colTable1 < tables[joinQuery.getTableId1()].getNumOfCol(); colTable1++) {
                                    matches.add(tables[joinQuery.getTableId1()].getData()[rowTable1][colTable1]);
                                }
                                for (int colTable2 = 0; colTable2 < tables[joinQuery.getTableId2()].getNumOfCol(); colTable2++) {
                                    matches.add(tables[joinQuery.getTableId2()].getData()[rowTable2][colTable2]);
                                }
                            }

                            joinQuery.getResultSet().add(matches);
                        }
                    }
                }
                executeResults(joinQuery);
            }
        }
    }

    private void executeResults(JoinQuery joinQuery) {
        Collections.sort(joinQuery.getResultSet(), new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                int sz = o1.size();
                for (int i = 0; i < sz; i++) {
                    if (!Objects.equals(o1.get(i), o2.get(i))) {
                        return (o1.get(i)).compareTo(o2.get(i));
                    }
                }
                return 0;
            }
        });

        boolean flag = false;
        for (String next : joinQuery.getColumSet()) {
            if (flag) {
                System.out.print(" " + next);
            } else {
                System.out.print(next);
            }
            flag = true;
        }
        printLine("");
        for (ArrayList<Integer> currentRow : joinQuery.getResultSet()) {
            flag = false;
            for (Integer next : currentRow) {
                if (flag) {
                    System.out.print(" " + next);
                } else {
                    System.out.print(next);
                }
                flag = true;
            }
            printLine("");
        }
        printLine("");
    }
}
