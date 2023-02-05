package vn.com.momo.consumerdependenciesgraph;

import com.lmax.disruptor.EventHandler;
import vn.com.momo.clazz.LongEvent;

public class SecondConsumer implements EventHandler<LongEvent>  {

  @Override
  public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
    System.out.println("SecondConsumer Event: " + event + ", sequence: " + sequence);
  }
}
