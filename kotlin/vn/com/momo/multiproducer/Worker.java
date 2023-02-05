package vn.com.momo.multiproducer;

import com.lmax.disruptor.RingBuffer;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;
import vn.com.momo.clazz.LongEvent;

public class Worker implements Runnable {
  private CountDownLatch countDownLatch;
  private RingBuffer<LongEvent> ringBuffer;

  public Worker(CountDownLatch countDownLatch, RingBuffer<LongEvent> ringBuffer) {
    this.countDownLatch = countDownLatch;
    this.ringBuffer = ringBuffer;
  }

  @Override
  public void run() {
    ByteBuffer bb = ByteBuffer.allocate(8);
    for (long l = 0; l < 2; l++) {
      bb.putLong(0, l);
      try {
        onData(bb);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    countDownLatch.countDown();
  }

  public void onData(ByteBuffer bb) throws InterruptedException {
    long sequence = ringBuffer.next();
    try
    {
      LongEvent event = ringBuffer.get(sequence);
      event.set(bb.getLong(0));
    }
    finally
    {
      System.out.println("======================Publish sequence " + sequence);
      ringBuffer.publish(sequence);
    }
  }
}
