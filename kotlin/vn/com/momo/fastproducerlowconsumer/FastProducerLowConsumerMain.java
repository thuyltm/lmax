package vn.com.momo.fastproducerlowconsumer;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import java.nio.ByteBuffer;
import vn.com.momo.clazz.LongEvent;
import vn.com.momo.clazz.LongEventProducer;

public class FastProducerLowConsumerMain {

  public static void main(String[] args) {
    int ringBufferSize = 4;

    Disruptor<LongEvent> disruptor =
        new Disruptor<>(LongEvent::new, ringBufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE,
            new BlockingWaitStrategy());
    disruptor.handleEventsWith(new VerySlowLongEventHandler());
    disruptor.start();

    RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
    LongEventProducer producer = new LongEventProducer(ringBuffer);
    ByteBuffer bb = ByteBuffer.allocate(8);
    for (long l = 0; l < 2; l++)
    {
      bb.putLong(0, l);
      producer.onData(bb);
    }
  }
}
