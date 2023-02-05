package vn.com.momo.lowproducerfastconsumer;

import com.lmax.disruptor.RingBuffer;
import java.nio.ByteBuffer;
import vn.com.momo.clazz.LongEvent;

public class VerySlowLongEventProducer {
  private final RingBuffer<LongEvent> ringBuffer;

  public VerySlowLongEventProducer(RingBuffer<LongEvent> ringBuffer)
  {
    this.ringBuffer = ringBuffer;
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
      System.out.println("Publish sequence " + sequence);
      ringBuffer.publish(sequence);
    }
  }
}
