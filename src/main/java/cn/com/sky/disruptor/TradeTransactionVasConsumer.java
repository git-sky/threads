package cn.com.sky.disruptor;

import cn.com.sky.disruptor.demo1.TradeTransaction;

import com.lmax.disruptor.EventHandler;

public class TradeTransactionVasConsumer implements EventHandler<TradeTransaction> {

	@Override
	public void onEvent(TradeTransaction event, long sequence, boolean endOfBatch) throws Exception {
		// do something....
	}

}