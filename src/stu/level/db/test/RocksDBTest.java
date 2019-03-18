package stu.level.db.test;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.rocksdb.BlockBasedTableConfig;
import org.rocksdb.BloomFilter;
import org.rocksdb.CompressionType;
import org.rocksdb.InfoLogLevel;
import org.rocksdb.Options;
import org.rocksdb.ReadOptions;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;
import org.rocksdb.Statistics;

/**
 * @program: java-learn
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2019-01-10
 **/

public class RocksDBTest {

  public String dataBaseName;
  public String parentName;
  public boolean alive;
  public RocksDB database;
  ReadWriteLock resetDbLock;


  public RocksDBTest(String dataBaseName, String parentName) {
    this.dataBaseName = dataBaseName;
    this.parentName = parentName;
  }

  public Path getDbPath() {
    return Paths.get(parentName, dataBaseName);
  }

  public RocksDB getDatabase() {
    return database;
  }

  public void initDb() {
    ReadOptions readOpts;
    resetDbLock = new ReentrantReadWriteLock();

    try (Options options = new Options()) {

      // most of these options are suggested by https://github.com/facebook/rocksdb/wiki/Set-Up-Options
      // general options
      options.setStatistics(new Statistics());
      options.setStatsDumpPeriodSec(60);
      options.setCreateIfMissing(true);
      //options.setCompressionType(CompressionType.LZ4_COMPRESSION);
      //options.setBottommostCompressionType(CompressionType.LZ4_COMPRESSION);
      options.setLevelCompactionDynamicLevelBytes(true);
      options.setNumLevels(6);
      options.setMaxOpenFiles(-1);
      //options.setMaxBytesForLevelBase(670);
      //System.out.println("maxBytesForLevelMultiplier:" + options());
      // 设置每一层的压缩算法
      List<CompressionType> compressionTypeList = new ArrayList<>();
      compressionTypeList.add(CompressionType.NO_COMPRESSION);
      compressionTypeList.add(CompressionType.NO_COMPRESSION);
      compressionTypeList.add(CompressionType.ZSTD_COMPRESSION);
      compressionTypeList.add(CompressionType.ZSTD_COMPRESSION);
      compressionTypeList.add(CompressionType.ZSTD_COMPRESSION);
      compressionTypeList.add(CompressionType.ZSTD_COMPRESSION);
      options.setCompressionPerLevel(compressionTypeList);
      options.setMaxBytesForLevelMultiplier(20.0);
      System.out.println("maxBytesForLevelMultiplier:" + options.maxBytesForLevelMultiplier());
      //options.setMaxBytesForLevelMultiplier();//max_bytes_for_level_multiplie
      options.setMaxBytesForLevelBase(512*1024*1024);
      System.out.println("maxBytesForLevelBase:" + options.maxBytesForLevelBase());
      System.out
          .println("level0FileNumCompactionTrigger:" + options.level0FileNumCompactionTrigger());
      options.setTargetFileSizeBase(256*1024*1024);
      System.out.println("targetFileSizeBase:" + options.targetFileSizeBase());
      System.out.println("targetFileSizeMultiplier:" + options.targetFileSizeMultiplier());
      //options.setTargetFileSizeMultiplier();

      options.setIncreaseParallelism(1);
      options.setMaxBackgroundCompactions(32);
      options.setLogger(new org.rocksdb.Logger(options) {
        @Override
        protected void log(InfoLogLevel level, String message) {
          //System.out.println("[RocksDB] " + level + ": " + message);
        }
      });
      // table options
      final BlockBasedTableConfig tableCfg;
      options.setTableFormatConfig(tableCfg = new BlockBasedTableConfig());
      tableCfg.setBlockSize(16 * 1024);
      tableCfg.setBlockCacheSize(32 * 1024 * 1024);
      tableCfg.setCacheIndexAndFilterBlocks(true);
      tableCfg.setPinL0FilterAndIndexBlocksInCache(true);
      tableCfg.setFilter(new BloomFilter(10, false));

      // read options
      readOpts = new ReadOptions();
      readOpts = readOpts.setPrefixSameAsStart(true)
          .setVerifyChecksums(false);

      try {
        System.out.println("Opening database");

        final Path dbPath = getDbPath();
        if (!Files.isSymbolicLink(dbPath.getParent())) {
          Files.createDirectories(dbPath.getParent());
        }

        try {
          database = org.rocksdb.RocksDB.open(options, dbPath.toString());
        } catch (RocksDBException e) {
          System.out.println(e.getMessage());
          throw new RuntimeException("Failed to initialize database", e);
        }

        alive = true;

      } catch (IOException ioe) {
        System.out.println(ioe.getMessage());
        throw new RuntimeException("Failed to initialize database", ioe);
      }

      System.out.println("<~ RocksDbDataSource.initDB(): " + dataBaseName);
    } finally {
      //resetDbLock.writeLock().unlock();
    }
  }

  public Set<byte[]> allKeys() throws RuntimeException {
    resetDbLock.readLock().lock();
    Set<byte[]> result = Sets.newHashSet();
    try (final RocksIterator iter = database.newIterator()) {
      for (iter.seekToFirst(); iter.isValid(); iter.next()) {
        result.add(iter.key());
      }
      return result;
    } finally {
      resetDbLock.readLock().unlock();
    }
  }

  public List<Data> getBytesData(int number, int keysize) {
    List<Data> bytesList = new ArrayList<>();
    for (int i = 0; i < number; i++) {
      byte[] key = Data.generateBytes(keysize);
      byte[] value = Data.generateBytes(keysize);
      Data data = new Data(key, value);
      bytesList.add(data);
    }
    return bytesList;
  }

  public static void main(String[] args) {
    RocksDBTest rocks = new RocksDBTest("haoyu", "sun");
    rocks.initDb();
    try {

     /* List<Data> list = rocks.getBytesData(1000000, 1024);
      System.out.println("data generate success!");
      for (Data data : list) {
        rocks.getDatabase().put(data.key, data.value);
        //rocks.getDatabase().delete(String.valueOf(i).getBytes());
      }
      System.out.println("data write finish!");
*/
      try {
        Thread.sleep(40000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      System.out.println(rocks.getDatabase().getProperty("rocksdb.sstables"));
      System.out.println(rocks.getDatabase().getProperty("rocksdb.dbstats"));

      //System.out.println(rocks.getDatabase().getProperty("rocksdb.num-immutable-mem-table"));
      //System.out.println(rocks.getDatabase().getProperty("rocksdb.num-entries-active-mem-table"));
      System.out.println(rocks.getDatabase().getProperty("rocksdb.size-all-mem-tables"));
      System.out.println(rocks.getDatabase().getProperty("rocksdb.cur-size-all-mem-tables"));

      System.out.println("total-sst-files-size:" + rocks.getDatabase()
          .getProperty("rocksdb.total-sst-files-size"));
      System.out.println(
          "live-sst-files-size:" + rocks.getDatabase().getProperty("rocksdb.live-sst-files-size"));
      System.out.println(
          "l0 file size:" + rocks.getDatabase().getProperty("rocksdb.num-files-at-level0"));
      System.out.println(
          "l1 file size:" + rocks.getDatabase().getProperty("rocksdb.num-files-at-level1"));
      System.out.println(
          "l2 file size:" + rocks.getDatabase().getProperty("rocksdb.num-files-at-level2"));
      System.out.println(
          "l3 file size:" + rocks.getDatabase().getProperty("rocksdb.num-files-at-level3"));
      System.out.println(
          "l4 file size:" + rocks.getDatabase().getProperty("rocksdb.num-files-at-level4"));
      System.out.println(
          "l5 file size:" + rocks.getDatabase().getProperty("rocksdb.num-files-at-level5"));

      //System.out.println(rocks.allKeys().size());
      /*for (byte[] a : rocks.allKeys()) {
        System.out.println(new String(a) + ":" + new String(rocks.getDatabase().get(a)));
      }*/
    } catch (RocksDBException e) {
      System.out.println(e.getMessage());
    }

  }
}