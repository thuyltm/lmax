package vn.com.momo.consumerdependenciesgraph;

import com.lmax.disruptor.EventHandler;
import vn.com.momo.clazz.LongEvent;

public class ThirdConsumer implements EventHandler<LongEvent> {

  @Override
  public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
    System.out.println("ThirdConsumer Event: " + event + ", sequence: " + sequence);
  }
}
