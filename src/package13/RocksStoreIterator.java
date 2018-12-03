package package13;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import org.rocksdb.RocksIterator;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-10-31
 **/

public class RocksStoreIterator implements Iterator<Map.Entry<byte[], byte[]>> {

  private RocksIterator dbIterator;
  private boolean first = true;

  public RocksStoreIterator(RocksIterator dbIterator) {
    this.dbIterator = dbIterator;
  }

  @Override
  public boolean hasNext() {
    boolean hasNext = false;
    // true is first item
    try {
      if (first) {
        dbIterator.seekToFirst();
        first = false;
      }

      if (!(hasNext = dbIterator.isValid())) { // false is last item
        dbIterator.close();
      }
    } catch (Exception e) {
      System.out.println("e:" + e);
      try {
        dbIterator.close();
      } catch (Exception e1) {
        System.out.println("e1:" + e1);
      }
    }

    return hasNext;
  }

  @Override
  public Entry<byte[], byte[]> next() {
    if (!dbIterator.isValid()){
      throw new NoSuchElementException();
    }

    byte[] key = dbIterator.key();
    byte[] value = dbIterator.value();

    dbIterator.next();
    return new Entry<byte[], byte[]>() {
      @Override
      public byte[] getKey() {
        return key;
      }

      @Override
      public byte[] getValue() {
        return value;
      }

      @Override
      public byte[] setValue(byte[] value) {
        throw new UnsupportedOperationException();
      }
    };
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void forEachRemaining(Consumer<? super Entry<byte[], byte[]>> action) {

  }
}

