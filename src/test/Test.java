package test;

import common.Constants;
import datatypes.DT_Text;
import datatypes.base.DT;
import datatypes.base.DT_Numeric;
import storage.StorageManager;
import storage.model.DataRecord;
import storage.model.InternalCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dakle on 15/4/17.
 */
public class Test {

    public void run(int numberOfTestCases) {
        try {
            switch (numberOfTestCases) {
                case 1:
                    fetchTableColumns(Constants.SYSTEM_COLUMNS_TABLENAME);

                case 2:
                    fetchSelectiveTableColumns(Constants.SYSTEM_COLUMNS_TABLENAME);

                case 3:
                    selectAll(Constants.SYSTEM_TABLES_TABLENAME);
                    break;

                case 4:
                    deleteTableName(Constants.SYSTEM_COLUMNS_TABLENAME);

                case 5:
                    deleteTableColumns(Constants.SYSTEM_COLUMNS_TABLENAME);
            }
        }
        catch (Exception e){}
    }

    public void fetchTableColumns(String tableName)  throws Exception{
        StorageManager manager = new StorageManager();
        InternalCondition condition = new InternalCondition();
        List<DataRecord> records = manager.findRecord(Constants.DEFAULT_CATALOG_DATABASENAME, Constants.SYSTEM_COLUMNS_TABLENAME, condition, false);
        for (DataRecord record : records) {
            for(Object object: record.getColumnValueList()) {
                System.out.print(((DT) object).getValue());
                System.out.print("    |    ");
            }
            System.out.print("\n");
        }
    }

    public void fetchSelectiveTableColumns(String tableName) throws Exception {
        StorageManager manager = new StorageManager();
        InternalCondition condition = new InternalCondition();
        List<Byte> selectionIndexList = new ArrayList<>();
        selectionIndexList.add((byte) 0);
        selectionIndexList.add((byte) 5);
        selectionIndexList.add((byte) 2);
        List<DataRecord> records = manager.findRecord(Constants.DEFAULT_CATALOG_DATABASENAME, Constants.SYSTEM_COLUMNS_TABLENAME, condition, selectionIndexList, false);
        for (DataRecord record : records) {
            for(Object object: record.getColumnValueList()) {
                System.out.print(((DT) object).getValue());
                System.out.print("    |    ");
            }
            System.out.print("\n");
        }
    }

    private void selectAll(String tableName) throws Exception {
        StorageManager manager = new StorageManager();
        InternalCondition condition = new InternalCondition();
        List<DataRecord> records = manager.findRecord(Constants.DEFAULT_CATALOG_DATABASENAME, Constants.SYSTEM_TABLES_TABLENAME, condition,false);
        for (DataRecord record : records) {
            for(Object object: record.getColumnValueList()) {
                System.out.print(((DT) object).getValue());
                System.out.print("    |    ");
            }
            System.out.print("\n");
        }
    }

    private void deleteTableName(String tableName) throws Exception {
        StorageManager manager = new StorageManager();
        List<Byte> columnIndexList = new ArrayList<>();
        columnIndexList.add((byte) 1);
        List<Object> valueList = new ArrayList<>();
        valueList.add(new DT_Text(tableName));
        List<Short> conditionList = new ArrayList<>();
        conditionList.add(DT_Numeric.EQUALS);
        System.out.println(manager.deleteRecord(Constants.DEFAULT_CATALOG_DATABASENAME, Constants.SYSTEM_TABLES_TABLENAME, columnIndexList, valueList, conditionList,true));
    }

    private void deleteTableColumns(String tableName) throws Exception {
        StorageManager manager = new StorageManager();
        List<Byte> columnIndexList = new ArrayList<>();
        List<Object> valueList = new ArrayList<>();
        List<Short> conditionList = new ArrayList<>();
        System.out.println(manager.deleteRecord(Constants.DEFAULT_CATALOG_DATABASENAME, Constants.SYSTEM_COLUMNS_TABLENAME, columnIndexList, valueList, conditionList,false));
    }
}
