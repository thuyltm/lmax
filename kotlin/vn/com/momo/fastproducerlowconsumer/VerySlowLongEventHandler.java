package vn.com.momo.fastproducerlowconsumer;

import com.lmax.disruptor.EventHandler;
import vn.com.momo.clazz.LongEvent;

public class VerySlowLongEventHandler implements EventHandler<LongEvent> {
  @Override
  public void onEvent(LongEvent event, long sequence, boolean endOfBatch)
      throws InterruptedException {
    Thread.sleep(10000L);//delay 10s
    System.out.println("Event: " + event + ", sequence: " + sequence);
    Thread.sleep(10000L);//delay 10s
  }

}
