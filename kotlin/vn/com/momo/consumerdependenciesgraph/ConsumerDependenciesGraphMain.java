package vn.com.momo.consumerdependenciesgraph;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import java.nio.ByteBuffer;
import vn.com.momo.clazz.LongEvent;
import vn.com.momo.clazz.LongEventFactory;
import vn.com.momo.clazz.LongEventProducer;

public class ConsumerDependenciesGraphMain {
  public static void main(String[] args) throws InterruptedException {
    int ringBufferSize = 8;
    //Waiting strategy – Defines how we would like to handle slow subscriber who doesn't keep up with producer's pace
    Disruptor<LongEvent> disruptor =
        new Disruptor<LongEvent>(new LongEventFactory(),ringBufferSize, DaemonThreadFactory.INSTANCE,
            ProducerType.SINGLE,new BlockingWaitStrategy());
    // journal consumer
    FirstConsumer firstConsumer = new FirstConsumer();
    // replication consumer
    SecondConsumer secondConsumer = new SecondConsumer();
    // application consumer
    ThirdConsumer thirdConsumer = new ThirdConsumer();

    SequenceBarrier sequenceBarrier2 = disruptor.handleEventsWith(firstConsumer,
        secondConsumer).asSequenceBarrier();
    // Way 1
    BatchEventProcessor processord = new BatchEventProcessor(disruptor.getRingBuffer(),sequenceBarrier2,
        thirdConsumer);
    disruptor.handleEventsWith(processord);
    // Way 2
    disruptor.after(firstConsumer, secondConsumer).handleEventsWith(thirdConsumer);

    RingBuffer<LongEvent> ringBuffer = disruptor.start();//Start Disruptor
    LongEventProducer producer = new LongEventProducer(ringBuffer);
    ByteBuffer bb = ByteBuffer.allocate(8);

    for(int i=0; i< 10; i++) {
      bb.putLong(0, i);
      producer.onData(bb);
    }

    disruptor.shutdown();
  }
}
