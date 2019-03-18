package package13;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import java.util.Map.Entry;
import org.iq80.leveldb.util.FileUtils;
import org.rocksdb.Checkpoint;
import org.rocksdb.ColumnFamilyDescriptor;
import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.ColumnFamilyOptions;
import org.rocksdb.DBOptions;
import org.rocksdb.Options;
import org.rocksdb.ReadOptions;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;
import org.rocksdb.Transaction;
import org.rocksdb.TransactionDB;
import org.rocksdb.TransactionDBOptions;
import org.rocksdb.WriteBatch;
import org.rocksdb.WriteOptions;


public class RocketsDBTest {

  private static final String dbPath = "/Users/tron/rocksdb/data12/";
  private String database;

  static {
    RocksDB.loadLibrary();
  }

  private WriteOptions writeOptions = new WriteOptions().setSync(true);

  RocksDB rocksDB;

  public RocketsDBTest(String database) throws RocksDBException {
    this.database = database;
  }

  public String getDbPath(){
    return dbPath + this.database;
  }

  public String getDbBakPath(int i){
    return dbPath + this.database + "bak" + i;
  }

  public boolean deleteDbBakPath(int i){
    return FileUtils.deleteRecursively(new File(getDbBakPath(i)));
  }

  public void init() {
    rocksDB = openDatabse();
  }

  private RocksDB openDatabse() {
    try {
      final Options options = new Options().setCreateIfMissing(true);
      // a factory method that returns a RocksDB instance
      RocksDB rocksDB = RocksDB.open(options, getDbPath());
      return rocksDB;

    } catch (RocksDBException e) {
      e.printStackTrace();
      return null;
    }
  }

  public TransactionDB openTsDatabase() {
    try (final Options options = new Options()
        .setCreateIfMissing(true);
        final TransactionDBOptions txn_db_options = new TransactionDBOptions();
        final TransactionDB txnDb =
            TransactionDB.open(options, txn_db_options, dbPath)) {
      try (final WriteOptions writeOptions = new WriteOptions();
          final ReadOptions readOptions = new ReadOptions()) {

        ////////////////////////////////////////////////////////
        //
        // Simple Transaction Example ("Read Committed")
        //
        ////////////////////////////////////////////////////////
        //readCommitted(txnDb, writeOptions, readOptions);
      }
    } catch (RocksDBException e) {
      System.out.println("s" + e);
    }
    return null;
  }

  public void testTransaction() throws RocksDBException {
    WriteOptions write_options = new WriteOptions();
    Transaction txn = openTsDatabase().beginTransaction(write_options);
    txn.put("key1".getBytes(), "val1".getBytes());
    txn.put("key2".getBytes(), "val2".getBytes());
    byte[] s = txn.getForUpdate(new ReadOptions(), "key1".getBytes(), true);
    System.out.println(new String(s));
    txn.commit();
  }

  //	RocksDB.DEFAULT_COLUMN_FAMILY
  public void testDefaultColumnFamily() throws RocksDBException {
    Options options = new Options();
    options.setCreateIfMissing(true);

    rocksDB = RocksDB.open(options, dbPath);
    byte[] key = "Hello".getBytes();
    byte[] value = "World".getBytes();
    rocksDB.put(key, value);

    List<byte[]> cfs = RocksDB.listColumnFamilies(options, dbPath);
    for (byte[] cf : cfs) {
      System.out.println(new String(cf));
    }

    byte[] getValue = rocksDB.get(key);
    System.out.println(new String(getValue));

    rocksDB.put("SecondKey".getBytes(), "SecondValue".getBytes());

    List<byte[]> keys = new ArrayList<>();
    keys.add(key);
    keys.add("SecondKey".getBytes());

    Map<byte[], byte[]> valueMap = rocksDB.multiGet(keys);
    for (Map.Entry<byte[], byte[]> entry : valueMap.entrySet()) {
      System.out.println(new String(entry.getKey()) + ":" + new String(entry.getValue()));
    }

    RocksIterator iter = rocksDB.newIterator();
    for (iter.seekToFirst(); iter.isValid(); iter.next()) {
      System.out.println(
          "iter key:" + new String(iter.key()) + ", iter value:" + new String(iter.value()));
    }

    rocksDB.remove(key);
    System.out.println("after remove key:" + new String(key));

    iter = rocksDB.newIterator();
    for (iter.seekToFirst(); iter.isValid(); iter.next()) {
      System.out.println(
          "iter key:" + new String(iter.key()) + ", iter value:" + new String(iter.value()));
    }

  }

  public void testCertainColumnFamily() throws RocksDBException {
    String table = "CertainColumnFamilyTest";
    String key = "certainKey";
    String value = "certainValue";

    List<ColumnFamilyDescriptor> columnFamilyDescriptors = new ArrayList<>();
    Options options = new Options();
    options.setCreateIfMissing(true);

    List<byte[]> cfs = RocksDB.listColumnFamilies(options, dbPath);
    if (cfs.size() > 0) {
      for (byte[] cf : cfs) {
        columnFamilyDescriptors.add(new ColumnFamilyDescriptor(cf, new ColumnFamilyOptions()));
      }
    } else {
      columnFamilyDescriptors.add(
          new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY, new ColumnFamilyOptions()));
    }

    List<ColumnFamilyHandle> columnFamilyHandles = new ArrayList<>();
    DBOptions dbOptions = new DBOptions();
    dbOptions.setCreateIfMissing(true);

    rocksDB = RocksDB.open(dbOptions, dbPath, columnFamilyDescriptors, columnFamilyHandles);
    for (int i = 0; i < columnFamilyDescriptors.size(); i++) {
      if (new String(columnFamilyDescriptors.get(i).columnFamilyName()).equals(table)) {
        rocksDB.dropColumnFamily(columnFamilyHandles.get(i));
      }
    }

    ColumnFamilyHandle columnFamilyHandle = rocksDB.createColumnFamily(
        new ColumnFamilyDescriptor(table.getBytes(), new ColumnFamilyOptions()));
    rocksDB.put(columnFamilyHandle, key.getBytes(), value.getBytes());

    byte[] getValue = rocksDB.get(columnFamilyHandle, key.getBytes());
    System.out.println("get Value : " + new String(getValue));

    rocksDB.put(columnFamilyHandle, "SecondKey".getBytes(), "SecondValue".getBytes());

    List<byte[]> keys = new ArrayList<byte[]>();
    keys.add(key.getBytes());
    keys.add("SecondKey".getBytes());

    List<ColumnFamilyHandle> handleList = new ArrayList<>();
    handleList.add(columnFamilyHandle);
    handleList.add(columnFamilyHandle);

    Map<byte[], byte[]> multiGet = rocksDB.multiGet(handleList, keys);
    for (Map.Entry<byte[], byte[]> entry : multiGet.entrySet()) {
      System.out.println(new String(entry.getKey()) + "--" + new String(entry.getValue()));
    }

    rocksDB.remove(columnFamilyHandle, key.getBytes());

    RocksIterator iter = rocksDB.newIterator(columnFamilyHandle);
    for (iter.seekToFirst(); iter.isValid(); iter.next()) {
      System.out.println(new String(iter.key()) + ":" + new String(iter.value()));
    }
  }



  public void read() throws RocksDBException {
    Options options = new Options();
    options.setCreateIfMissing(true);

    rocksDB = RocksDB.open(options, "rocks_cp1");
    System.out.println(new String(rocksDB.get("1".getBytes())));

  }

  public void testGetPut() {
    String key1 = "00000";
    byte[] key = key1.getBytes();
    String value1 = "00001";
    byte[] value = value1.getBytes();

    try {
      rocksDB.put(key, value);
      System.out.println(new String(rocksDB.get(key)));
    } catch (RocksDBException e) {
      e.printStackTrace();
    }
  }

  public void saveEntry(byte[] key, byte[] value){
    try {
      rocksDB.put(key, value);
      System.out.println(new String(rocksDB.get(key)));
    } catch (RocksDBException e) {
      e.printStackTrace();
    }
  }

  public void saveEntries(){
    try {
      for(int i=0; i<300; i++) {
        rocksDB.put(String.valueOf(i).getBytes(), String.valueOf(i).getBytes());
      }
    } catch (RocksDBException e) {
      e.printStackTrace();
    }
  }

  public void testIterator(){

    byte[] value = null;
    try {
      for(int i =0; i < 100; i++){
        rocksDB.put(String.valueOf(i).getBytes(), "value1".getBytes());
      }

      rocksDB.put("key1".getBytes(), "value1".getBytes());
      rocksDB.put("key2".getBytes(), "value2".getBytes());
      rocksDB.put("key3".getBytes(), "value3".getBytes());
      rocksDB.put("key4".getBytes(), "value4".getBytes());
      rocksDB.put("key5".getBytes(), "value5".getBytes());

      value = new byte[4];
      System.out.println(rocksDB.get("key1".getBytes(), value));  // rocksDB.get(byte[] key, byte[] value) 返回int 指示数据的长度，没有此key返回-1
      System.out.println(new String(value));


      /*final List<byte[]> keys = new ArrayList<>();
      try (final RocksIterator iterator = rocksDB.newIterator()) {
        for (iterator.seekToLast(); iterator.isValid(); iterator.prev()) {
          keys.add(iterator.key());
        }
      }

      Map<byte[], byte[]> values = rocksDB.multiGet(keys);
      System.out.println("Map.size():" + values.size());*/


    } catch (RocksDBException e) {
      e.printStackTrace();
    }

   // System.out.println(new String(value));
    //or int RocksDB.get(ReadOptions opt, byte[] key, byte[] value)
    RocksIterator iter = rocksDB.newIterator();
    RocksStoreIterator citer = new RocksStoreIterator(iter);

    while(citer.hasNext()){
      Entry<byte[], byte[]> entry = citer.next();
      System.out.println(new String(entry.getKey()) + ":" + new String(entry.getValue()));
    }
  }


  public void backup(int i) {
    // a static method that loads the RocksDB C++ library.

    // the Options class contains a set of configurable DB options
    // that determines the behaviour of the database.
    try (final org.rocksdb.Options options = new org.rocksdb.Options().setCreateIfMissing(true)) {

      // a factory method that returns a RocksDB instance
      try (final RocksDB db = RocksDB.open(options, "/Users/tron/rocksdb/data14/")) {
        Checkpoint.create(this.rocksDB).createCheckpoint(this.getDbBakPath(i));
      }

    } catch (RocksDBException e) {
      e.printStackTrace();
    }
  }


  public void batchWrite(){

  }


  public static void main(String[] args) throws RocksDBException {

    boolean flag = false;
    int new_number;
    int old_number;
    BackupNo backupNo = new BackupNo();
    int count = 0;
    RocketsDBTest blockStore = new RocketsDBTest("block");
    RocketsDBTest transStore = new RocketsDBTest("trans");
    blockStore.init();
    transStore.init();
    while(count<10) {
      try {
        flag = false;
        new_number = backupNo.getNumber();
        old_number = backupNo.getNumber();

        if(new_number==1){
          blockStore.deleteDbBakPath(1);
          transStore.deleteDbBakPath(1);  //删除旧备份
          blockStore.backup(1);
          transStore.backup(1); //1/
          backupNo.setNumber(1*10+1); //备份成功之后，记下完整的备份号。
          blockStore.deleteDbBakPath(2);
          transStore.deleteDbBakPath(2);
        }

        if(new_number==11){
          blockStore.deleteDbBakPath(2);
          transStore.deleteDbBakPath(2);
          blockStore.backup(2);
          transStore.backup(2);
          backupNo.setNumber(2*10+2); //备份成功之后，记下完整的备份号。
          blockStore.deleteDbBakPath(1);
          transStore.deleteDbBakPath(1);
        }

        if(new_number==2){
          blockStore.deleteDbBakPath(2);
          transStore.deleteDbBakPath(2);  //删除旧备份
          blockStore.backup(2);
          transStore.backup(2); //1/
          backupNo.setNumber(2*10+2); //备份成功之后，记下完整的备份号。
          blockStore.deleteDbBakPath(1);
          transStore.deleteDbBakPath(1);
        }

        if(new_number==22){
          blockStore.deleteDbBakPath(1);
          transStore.deleteDbBakPath(1);
          blockStore.backup(1);
          transStore.backup(1);
          backupNo.setNumber(1*10+1); //备份成功之后，记下完整的备份号。
          blockStore.deleteDbBakPath(2);
          transStore.deleteDbBakPath(2);
        }

        blockStore.saveEntries();
        transStore.saveEntries();
        //blockStore.saveEntry("block".getBytes(), "value".getBytes());
        //transStore.saveEntry("trans".getBytes(), "value".getBytes());
        flag = true;
        count++;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    //test.init();
    //test.testGetPut();
    //test.testIterator();
    //test.testDefaultColumnFamily();
    //test.testCertainColumnFamily();
    //test.backup();
    //test.read();
    //test.testTransaction();
  }

}
