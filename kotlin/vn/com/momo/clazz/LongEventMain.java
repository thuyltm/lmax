package vn.com.momo.clazz;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import java.nio.ByteBuffer;

public class LongEventMain {

  public static void main(String[] args) throws Exception
  {
    int ringBufferSize = 2;

    Disruptor<LongEvent> disruptor =
        new Disruptor<>(LongEvent::new, ringBufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE,
            new BlockingWaitStrategy());
    disruptor.handleEventsWith(new LongEventHandler());
    disruptor.start();

    RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
    LongEventProducer producer = new LongEventProducer(ringBuffer);
    ByteBuffer bb = ByteBuffer.allocate(8);
    for (long l = 0; true; l++)
    {
      bb.putLong(0, l);
      producer.onData(bb);
    }
  }
}
