package vn.com.momo.multiproducer;

import static java.util.stream.Collectors.toList;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;
import vn.com.momo.clazz.LongEvent;

public class MultiProducerMain {

  public static void main(String[] args) throws InterruptedException {
    int ringBufferSize = 2;

    Disruptor<LongEvent> disruptor =
        new Disruptor<>(LongEvent::new, ringBufferSize, DaemonThreadFactory.INSTANCE,
            ProducerType.MULTI,
            new YieldingWaitStrategy());
    disruptor.handleEventsWith((event, sequence, endOfBatch) -> {
      System.out.println("Event: " + event + ", sequence: " + sequence);
      Thread.sleep(5000L);
    });
    disruptor.start();

    RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

    CountDownLatch countDownLatch = new CountDownLatch(2);
    List<Thread> workers = Stream
        .generate(() -> new Thread(new Worker(countDownLatch, ringBuffer)))
        .limit(2)
        .collect(toList());
    workers.forEach(Thread::start);
    countDownLatch.await();
  }
}
