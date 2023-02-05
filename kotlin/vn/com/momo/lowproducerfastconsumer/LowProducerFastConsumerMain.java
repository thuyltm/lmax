package vn.com.momo.lowproducerfastconsumer;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import java.nio.ByteBuffer;
import vn.com.momo.clazz.LongEvent;
import vn.com.momo.clazz.LongEventHandler;

public class LowProducerFastConsumerMain {
  public static void main(String[] args) throws InterruptedException {
    int ringBufferSize = 2;

    Disruptor<LongEvent> disruptor =
        new Disruptor<>(LongEvent::new, ringBufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE,
            new BlockingWaitStrategy());
    disruptor.handleEventsWith(new LongEventHandler());
    disruptor.start();

    RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
    VerySlowLongEventProducer producer = new VerySlowLongEventProducer(ringBuffer);
    ByteBuffer bb = ByteBuffer.allocate(8);
    for (long l = 0; l < 2; l++)
    {
      bb.putLong(0, l);
      producer.onData(bb);
      Thread.sleep(10000L);
    }
  }
}
