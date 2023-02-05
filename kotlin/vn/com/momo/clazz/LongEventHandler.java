package vn.com.momo.clazz;

import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent> {
  @Override
  public void onEvent(LongEvent event, long sequence, boolean endOfBatch)
      throws InterruptedException {
    System.out.println("Event: " + event + ", sequence: " + sequence);
  }

}
