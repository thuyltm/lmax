package vn.com.momo.clazz;

import com.lmax.disruptor.RingBuffer;
import java.nio.ByteBuffer;

public class LongEventProducer {
  private final RingBuffer<LongEvent> ringBuffer;

  public LongEventProducer(RingBuffer<LongEvent> ringBuffer)
  {
    this.ringBuffer = ringBuffer;
  }

  public void onData(ByteBuffer bb)
  {
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
