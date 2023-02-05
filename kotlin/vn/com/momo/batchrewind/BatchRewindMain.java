package vn.com.momo.batchrewind;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RewindableException;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.SimpleBatchRewindStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import java.nio.ByteBuffer;
import vn.com.momo.clazz.LongEvent;
import vn.com.momo.clazz.LongEventFactory;

public class BatchRewindMain {
  public static void main(String[] args) throws InterruptedException {
    int ringBufferSize = 8;
    final int[] i = {0};
    Disruptor<LongEvent> disruptor =
        new Disruptor<LongEvent>(new LongEventFactory(),ringBufferSize, DaemonThreadFactory.INSTANCE,
            ProducerType.SINGLE,new BlockingWaitStrategy());
    RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
    SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
    BatchEventProcessor processord = new BatchEventProcessor(disruptor.getRingBuffer(),sequenceBarrier,
        (event, sequence, endOfBatch) -> {
      if (i[0] == 0 && sequence == 5) {
        i[0] = i[0] +1;
        throw new RewindableException(new Throwable("Test"));
      } else {
        System.out.println("Event: " + event + ", sequence: " + sequence);
      }});
    processord.setRewindStrategy(new SimpleBatchRewindStrategy());
    disruptor.handleEventsWith(processord);
    disruptor.start();//Start Disruptor
    ByteBuffer bb = ByteBuffer.allocate(8);
    for (long l = 0; l < 10; l++)
    {
      bb.putLong(0, l);
      ringBuffer.publishEvent((event, sequence, buffer) -> event.set(buffer.getLong(0)), bb);
    }
    disruptor.shutdown();
  }
}
