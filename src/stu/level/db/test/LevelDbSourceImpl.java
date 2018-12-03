package stu.level.db.test;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-10-11
 **/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.iq80.leveldb.CompressionType;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBException;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.WriteBatch;
import org.iq80.leveldb.WriteOptions;
import static org.fusesource.leveldbjni.JniDBFactory.factory;

public class LevelDbSourceImpl {
  private String dataBaseName;
  private DB database;
  private boolean alive;
  private String parentName;
  private ReadWriteLock resetDbLock = new ReentrantReadWriteLock();


  public LevelDbSourceImpl(String parentName, String name) {
    this.dataBaseName = name;
    this.parentName = Paths.get(
        parentName
    ).toString();
  }

  public Path getDbPath() {
    return Paths.get(parentName, dataBaseName);
  }

  public boolean isAlive() {
    return alive;
  }

  public void initDB() {
    resetDbLock.writeLock().lock();
    try {
      if (isAlive()) {
        return;
      }
      if (dataBaseName == null) {
        throw new NullPointerException("no name set to the dbStore");
      }

      Options dbOptions = createDefaultDbOptions();

      try {
        openDatabase(dbOptions);
        alive = true;
      } catch (IOException ioe) {
        throw new RuntimeException("Can't initialize database", ioe);
      }
    } finally {
      resetDbLock.writeLock().unlock();
    }
  }

  private void openDatabase(Options dbOptions) throws IOException {
    final Path dbPath = getDbPath();
    if (!Files.isSymbolicLink(dbPath.getParent())) {
      Files.createDirectories(dbPath.getParent());
    }
    try {
      database = factory.open(dbPath.toFile(), dbOptions);
    } catch (IOException e) {
      if (e.getMessage().contains("Corruption:")) {
        factory.repair(dbPath.toFile(), dbOptions);
        database = factory.open(dbPath.toFile(), dbOptions);
      } else {
        throw e;
      }
    }
  }

  private void closeDB() {
    resetDbLock.writeLock().lock();
    try {
      if (!isAlive()) {
        return;
      }
      database.close();
      alive = false;
    } catch (IOException e) {
      System.out.println("Failed to find the dbStore file on the closeDB: " + dataBaseName);
    } finally {
      resetDbLock.writeLock().unlock();
    }
  }

  public byte[] getData(byte[] key) {
    resetDbLock.readLock().lock();
    try {
      return database.get(key);
    } catch (DBException e) {
    } finally {
      resetDbLock.readLock().unlock();
    }
    return null;
  }

  public void putData(byte[] key, byte[] value) {
    resetDbLock.readLock().lock();
    try {
      database.put(key, value);
    } finally {
      resetDbLock.readLock().unlock();
    }
  }

  public void putData(byte[] key, byte[] value, WriteOptions options) {
    resetDbLock.readLock().lock();
    try {
      database.put(key, value, options);
    } finally {
      resetDbLock.readLock().unlock();
    }
  }

  public void deleteData(byte[] key) {
    resetDbLock.readLock().lock();
    try {
      database.delete(key);
    } finally {
      resetDbLock.readLock().unlock();
    }
  }

  public void deleteData(byte[] key, WriteOptions options) {
    resetDbLock.readLock().lock();
    try {
      database.delete(key, options);
    } finally {
      resetDbLock.readLock().unlock();
    }
  }


  private static Options createDefaultDbOptions() {
    CompressionType DEFAULT_COMPRESSION_TYPE = CompressionType.SNAPPY;
    int DEFAULT_BLOCK_SIZE = 4 * 1024;
    int DEFAULT_WRITE_BUFFER_SIZE = 10 * 1024 * 1024;
    long DEFAULT_CACHE_SIZE = 10 * 1024 * 1024L;
    int DEFAULT_MAX_OPEN_FILES = 100;

    Options dbOptions = new Options();

    dbOptions.createIfMissing(true).paranoidChecks(true).verifyChecksums(true);

    dbOptions.compressionType(DEFAULT_COMPRESSION_TYPE).blockSize(DEFAULT_BLOCK_SIZE).
        writeBufferSize(DEFAULT_WRITE_BUFFER_SIZE).cacheSize(DEFAULT_CACHE_SIZE).
        maxOpenFiles(DEFAULT_MAX_OPEN_FILES);

    return dbOptions;
  }

  // 测试类
  public static void main(String[] args) {
    LevelDbSourceImpl dbSource = new LevelDbSourceImpl("ooo","db");
    System.out.println(dbSource.getDbPath());
    dbSource.initDB();
    WriteBatch batch = dbSource.database.createWriteBatch();
    batch.put("key1".getBytes(),"hello".getBytes());
    batch.put("key2".getBytes(),"hi".getBytes());
    batch.delete("key1".getBytes());
    dbSource.database.write(batch);
    dbSource.closeDB();
  }
}