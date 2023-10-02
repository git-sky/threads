package cn.com.sky.disruptor;

import java.util.Random;

import cn.com.sky.disruptor.demo1.TradeTransaction;

import com.lmax.disruptor.EventTranslator;

class TradeTransactionEventTranslator implements EventTranslator<TradeTransaction> {
    private Random random = new Random();

    @Override
    public void translateTo(TradeTransaction event, long sequence) {
        this.generateTradeTransaction(event);
    }

    private TradeTransaction generateTradeTransaction(TradeTransaction trade) {
        trade.setPrice(random.nextDouble() * 9999);
        return trade;
    }
}
